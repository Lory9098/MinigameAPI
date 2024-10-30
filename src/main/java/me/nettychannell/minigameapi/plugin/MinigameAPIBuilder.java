package me.nettychannell.minigameapi.plugin;

import lombok.Getter;
import lombok.Setter;
import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class MinigameAPIBuilder {
    private JavaPlugin plugin;
    private boolean debug = false;
    private ScoreboardSkull<?> scoreboard = null;
    private String tasksDirectory = null;

    public MinigameAPIBuilder(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public MinigameAPIBuilder plugin(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public MinigameAPIBuilder debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public MinigameAPIBuilder scoreboard(@Nullable ScoreboardSkull<?> scoreboard) {
        this.scoreboard = scoreboard;
        return this;
    }

    public MinigameAPIBuilder tasksDirectory(@Nullable String tasksDirectory) {
        this.tasksDirectory = tasksDirectory;
        return this;
    }

    public Minigame build() {
        return new Minigame(plugin, debug, scoreboard, tasksDirectory);
    }

}
