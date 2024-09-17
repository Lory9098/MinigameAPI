package me.nettychannell.minigameapi.mini.service;

import me.nettychannell.minigameapi.mini.MinigameArena;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class MinigameService {

    private final HashMap<String, MinigameArena<?, ?>> arenas = new HashMap<>();

    public void registerArena(String key, MinigameArena<?, ?> arena) {
        arenas.put(key, arena);
    }

    public Optional<MinigameArena<?, ?>> getArena(String key) {
        return Optional.ofNullable(arenas.get(key));
    }

    public void removeArena(String key) {
        arenas.remove(key);
    }

    public boolean hasArena(String key) {
        return arenas.containsKey(key);
    }

    public MinigameArena<?, ?> getArena(Player player) {
        return getArena(player.getUniqueId());
    }

    public MinigameArena<?, ?> getArena(UUID uuid) {
        return arenas.values().stream().filter(arena -> arena.getPlayers().contains(uuid)).findFirst().orElse(null);
    }

    public Collection<MinigameArena<?, ?>> getArenas() {
        return arenas.values();
    }
}
