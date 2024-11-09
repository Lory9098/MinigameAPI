package me.nettychannell.minigameapi.pair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class Pair<T, K> {
    private final T first;
    private final K second;
}
