package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class InternalArenaJoinEvent {

    private boolean cancelled;
    private final int arenaId;
    private final Player player;
    private final long joinTime;

    public InternalArenaJoinEvent(int arenaId, Player player, long joinTime) {
        this.arenaId = arenaId;
        this.player = player;
        this.joinTime = joinTime;
        this.cancelled = false;
    }

}
