package me.nettychannell.minigameapi.plugin;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.nettychannell.minigameapi.MinigameAPI;
import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import me.nettychannell.minigameapi.mini.service.MinigameService;
import me.nettychannell.minigameapi.task.handler.TaskHandler;
import me.nettychannell.minigameapi.utils.ChatUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Getter
public class Minigame implements MinigameAPI {
    @Getter
    private static Minigame instance;
    private final JavaPlugin plugin;
    private final MinigameService minigameService;
    private final TaskHandler<JavaPlugin> taskHandler;
    @Setter
    private boolean debug, luckPermsSupport, placeholderSupport;
    private @Nullable ScoreboardSkull<?> scoreboard;

    public static MinigameAPIBuilder builder(JavaPlugin plugin) {
        return new MinigameAPIBuilder(plugin);
    }

    Minigame(JavaPlugin plugin, boolean debug, @Nullable ScoreboardSkull<?> scoreboard, @Nullable String tasksDirectory) {
        instance = this;
        this.plugin = plugin;
        this.debug = debug;
        this.minigameService = new MinigameService();
        this.taskHandler = TaskHandler.create(plugin);

        if (tasksDirectory != null) taskHandler.registerAll(tasksDirectory);

        if (scoreboard != null) this.enableScoreboard(scoreboard);

        loadDependency("LuckPerms");
        loadDependency("PlaceholderAPI");

    }

    public void loadDependency(String dependency) {
        if (plugin.getServer().getPluginManager().isPluginEnabled(dependency)) {
            plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &a" + dependency + " found, support enabled!"));
            return;
        }

        if (!debug) return;

        plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &c" + dependency + " not found, support disabled!"));
    }

    @Override
    public Optional<LuckPerms> getLuckPerms() {
        if (!luckPermsSupport) {
            return Optional.empty();
        }
        return Optional.of(LuckPermsProvider.get());
    }

    @Override
    public Optional<@NotNull String> parse(Player player, String text) {
        if (!placeholderSupport) {
            return Optional.empty();
        }
        return Optional.of(PlaceholderAPI.setPlaceholders(player, text));
    }

    @Override
    public Optional<List<String>> parse(Player player, List<String> texts) {
        if (!placeholderSupport) {
            return Optional.empty();
        }
        return Optional.of(PlaceholderAPI.setPlaceholders(player, texts));
    }

    @Override
    public <T extends Enum<?>> void enableScoreboard(ScoreboardSkull<T> scoreboard) {
        this.scoreboard = scoreboard;
    }


}
