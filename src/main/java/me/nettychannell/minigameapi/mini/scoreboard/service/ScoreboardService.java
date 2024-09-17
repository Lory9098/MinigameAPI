package me.nettychannell.minigameapi.mini.scoreboard.service;

import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;

public class ScoreboardService<T extends ScoreboardSkull<?>> {
    private ScoreboardSkull<?> scoreboardSkull;

    public ScoreboardService(T scoreboard) {
        this.scoreboardSkull = scoreboard;
    }

}
