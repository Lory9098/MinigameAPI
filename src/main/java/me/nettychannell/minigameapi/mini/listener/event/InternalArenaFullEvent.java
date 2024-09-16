package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class InternalArenaFullEvent {

    private final Player player;

    public InternalArenaFullEvent(Player player) {
        this.player = player;
    }

}
