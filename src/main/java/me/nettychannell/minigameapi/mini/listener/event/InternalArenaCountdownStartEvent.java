package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InternalArenaCountdownStartEvent {
    @Setter
    private boolean cancelled = false;
    private final int arenaId;

    public InternalArenaCountdownStartEvent(int arenaId) {
        this.arenaId = arenaId;
    }

}
