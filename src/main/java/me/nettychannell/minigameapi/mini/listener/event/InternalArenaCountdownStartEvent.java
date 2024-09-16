package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;

public class InternalArenaCountdownStartEvent {
    @Getter
    @Setter
    private boolean cancelled = false;

}
