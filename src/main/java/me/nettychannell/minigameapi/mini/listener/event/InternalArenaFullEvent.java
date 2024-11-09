package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class InternalArenaFullEvent {

    private final int arenaId;
    private final Player player;

    public InternalArenaFullEvent(int arenaId, Player player) {
        this.arenaId = arenaId;
        this.player = player;
    }

}
