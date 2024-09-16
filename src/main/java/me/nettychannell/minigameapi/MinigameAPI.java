package me.nettychannell.minigameapi;

import me.nettychannell.minigameapi.plugin.Minigame;
import net.luckperms.api.LuckPerms;

import java.util.Optional;

public interface MinigameAPI {

    static Minigame<?> getInstance() {
        return Minigame.getInstance();
    }

    Optional<LuckPerms> getLuckPerms();

}