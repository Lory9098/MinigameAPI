package me.nettychannell.minigameapi.mini.countdown;

import lombok.Getter;
import me.nettychannell.minigameapi.mini.MinigameArena;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public abstract class Countdown extends BukkitRunnable {
    private final MinigameArena<?, ?> arena;
    private int remainingTime;

    public Countdown(MinigameArena<?, ?> arena) {
        this.arena = arena;
        this.remainingTime = arena.getCountdownTime();
    }

    @Override
    public void run() {
        remainingTime--;
        if (remainingTime <= 0) {
            arena.start();
            cancel();
            return;
        }
        handle();
    }

    /**
     * don't worry, i handle the end task :)
     * */
    public abstract void handle();
}
