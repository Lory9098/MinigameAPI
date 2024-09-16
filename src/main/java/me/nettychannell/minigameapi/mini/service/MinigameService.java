package me.nettychannell.minigameapi.mini.service;

import me.nettychannell.minigameapi.mini.MinigameArena;

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

}
