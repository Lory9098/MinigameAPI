package me.nettychannell.minigameapi.mini.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
* This event will be executed when the
* player teammate is chosed in a duo game
* */
@Getter @Setter
public class ServerChosedBialPlayerPairEvent {

    private boolean cancelled;
    private final int arenaId;
    private final Player player;
    private @NotNull Player pairedPlayer;

    public ServerChosedBialPlayerPairEvent(int arenaId, Player player, @NotNull Player pairedPlayer) {
        this.arenaId = arenaId;
        this.player = player;
        this.pairedPlayer = pairedPlayer;
        this.cancelled = false;
    }

}
