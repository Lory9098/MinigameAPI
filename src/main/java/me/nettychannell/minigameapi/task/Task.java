package me.nettychannell.minigameapi.task;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation containing various information about a task.
 *
 * @author echo
 * @from <a href="https://github.com/xEcho1337/SpigotEngine/blob/master/src/main/java/net/echo/spigotengine/tasks/Task.java">SpigotEngine</a>
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {

    /**
     * Determines whether the task should be run asynchronously.
     */
    boolean async() default false;

    /**
     * Determines whether the task should repeat.
     */
    boolean repeating() default false;

    /**
     * Determines how much time to wait between executions in ticks.
     * This only applies if <code>repeating</code> is <code>true</code>.
     */
    long period() default 0;

    /**
     * Determines the initial delay before the task starts.
     */
    long delay() default 0;
}