package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class InternalArenaCountdownStopEvent {
    private boolean cancelled = false;
    private final CountdownStopCause cause;
    // if cause is MIN_PLAYERS_REACHED, this will be the player that quitted
    private final Player player;

    public InternalArenaCountdownStopEvent(CountdownStopCause cause, Player player) {
        this.cause = cause;
        this.player = player;
    }

    public void setCancelled(boolean cancelled) {
        if (this.cause == CountdownStopCause.USUAL_STOP) {
            throw new IllegalStateException("The countdown can only be stopped when the cause is MIN_PLAYERS_REACHED");
        }
        this.cancelled = cancelled;
    }

    public enum CountdownStopCause {
        MIN_PLAYERS_REACHED, // when the minimum amount of players has been reached in the countdown state
        USUAL_STOP // when the countdown has terminated
    }
}
