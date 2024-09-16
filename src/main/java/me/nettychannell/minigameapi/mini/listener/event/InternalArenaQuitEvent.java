package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class InternalArenaQuitEvent {

    private boolean cancelled;
    private final Player player;
    private final long joinTime;

    public InternalArenaQuitEvent(Player player, long joinTime) {
        this.player = player;
        this.joinTime = joinTime;
        this.cancelled = false;
    }

}
