package me.nettychannell.minigameapi.mini.service;

import me.nettychannell.minigameapi.mini.MinigameArena;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class MinigameService<K> {

    private final HashMap<K, MinigameArena<?, ?>> arenas = new HashMap<>();

    public void registerArena(K key, MinigameArena<?, ?> arena) {
        arenas.put(key, arena);
    }

    public Optional<MinigameArena<?, ?>> getArena(K key) {
        return Optional.ofNullable(arenas.get(key));
    }

    public void removeArena(K key) {
        arenas.remove(key);
    }

    public boolean hasArena(K key) {
        return arenas.containsKey(key);
    }

    public MinigameArena<?, ?> getArena(Player player) {
        return arenas.values().stream().filter(arena -> arena.getPlayers().contains(player)).findFirst().orElse(null);
    }

    public Collection<MinigameArena<?, ?>> getArenas() {
        return arenas.values();
    }
}
