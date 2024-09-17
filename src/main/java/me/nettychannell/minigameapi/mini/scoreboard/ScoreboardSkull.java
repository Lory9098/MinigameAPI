package me.nettychannell.minigameapi.mini.scoreboard;

import lombok.Getter;
import me.nettychannell.minigameapi.MinigameAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

//                                    T = The game state enum
public abstract class ScoreboardSkull<T extends Enum<?>> extends BukkitRunnable {
    @Getter
    private final int tickInterval;

    /**
     * ScoreboardSkull Constructor
     * @param tickInterval Interval in ticks to update the scoreboard
     * */
    public ScoreboardSkull(int tickInterval) {
        this.tickInterval = tickInterval;

        runTaskTimer(MinigameAPI.getInstance().getPlugin(), 0, tickInterval);
    }

    protected abstract String getTitle(T state);

    protected abstract List<String> getLines(T state);

    protected abstract void handle(T state, Player player);

    @Override
    public void run() {//TODO: add lobby scoreboard
        MinigameAPI.getInstance().getMinigameService().getArenas().forEach(arena -> {
            arena.getPlayers().forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);

                handle((T) arena.getGameState(), player);
            });
        });
    }

}
