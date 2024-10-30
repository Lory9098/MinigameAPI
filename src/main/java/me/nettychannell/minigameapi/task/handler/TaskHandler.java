package me.nettychannell.minigameapi.task.handler;

import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import me.nettychannell.minigameapi.task.Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

/**
 * Class used for handling tasks.
 *
 * @author echo
 * @from <a href="https://github.com/xEcho1337/SpigotEngine/blob/master/src/main/java/net/echo/spigotengine/tasks/Task.java">SpigotEngine</a>
 * @since 1.0.0
 */
public class TaskHandler<P extends JavaPlugin> {

    private static final BukkitScheduler executor = Bukkit.getScheduler();
    private final P plugin;

    protected TaskHandler(P plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates an instance of the task handler.
     *
     * @param plugin the parent plugin
     */
    public static <T extends JavaPlugin> TaskHandler<T> create(T plugin) {
        return new TaskHandler<>(plugin);
    }

    /**
     * Registers all the tasks in the specified path.
     */
    public void registerAll(String path) {
        consumeAll(Runnable.class, plugin, path, this::register);
    }

    /**
     * Submits a task to run it later.
     */
    public <T extends Runnable> void submit(@NotNull T task, long delay) {
        Preconditions.checkNotNull(task);
        executor.runTaskLater(plugin, task, delay);
    }

    /**
     * Submits a task to run it later asynchronously.
     */
    public <T extends Runnable> void submitAsync(@NotNull T task, long delay) {
        Preconditions.checkNotNull(task);
        executor.runTaskLaterAsynchronously(plugin, task, delay);
    }

    private <T, P extends JavaPlugin> void consumeAll(Class<T> type, P plugin, String path, Consumer<T> consumer) {
        try {
            ClassPath.from(plugin.getClass().getClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(info -> info.getPackageName().startsWith(path))
                    .forEach(info -> consumeClassInfo(type, plugin, info, consumer));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @SuppressWarnings("all")
    private <T, P extends JavaPlugin> void consumeClassInfo(Class<T> type, P plugin, ClassPath.ClassInfo info, Consumer<T> consumer) {
        try {
            Class<?> clazz = info.load();

            if (!type.isAssignableFrom(clazz)) return;

            try {
                // Try to find the no-arg constructor
                Constructor<?> noArgConstructor = clazz.getDeclaredConstructor();
                noArgConstructor.setAccessible(true);

                T instance = (T) noArgConstructor.newInstance();
                consumer.accept(instance);
            } catch (NoSuchMethodException ignored) {
                // OK, this class has a constructor, it's surely an instance
                try {
                    Constructor<?> constructor = clazz.getDeclaredConstructor(plugin.getClass());
                    constructor.setAccessible(true);

                    T instance = (T) constructor.newInstance(plugin);
                    consumer.accept(instance);
                } catch (Exception ignored1) {
                }
            }
        } catch (Exception ignored) {
        }
    }
    
    /**
     * Registers a task to be executed.
     *
     * @param task the task
     */
    public <T extends Runnable> void register(@NotNull T task) {
        Preconditions.checkNotNull(task);

        if (!task.getClass().isAnnotationPresent(Task.class)) {
            throw new IllegalStateException(task.getClass().getSimpleName() + " does not have the @Task annotation");
        }

        Task data = task.getClass().getAnnotation(Task.class);

        if (data.async()) {
            if (data.repeating()) {
                executor.runTaskTimerAsynchronously(plugin, task, data.delay(), data.period());
            } else {
                executor.runTaskAsynchronously(plugin, task);
            }
        } else {
            if (data.repeating()) {
                executor.runTaskTimer(plugin, task, data.delay(), data.period());
            } else {
                executor.runTask(plugin, task);
            }
        }
    }
}