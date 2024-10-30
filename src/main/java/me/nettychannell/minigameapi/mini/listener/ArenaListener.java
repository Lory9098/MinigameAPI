package me.nettychannell.minigameapi.mini.listener;

import me.nettychannell.minigameapi.mini.listener.event.*;

public interface ArenaListener {

    default void onPlayerJoin(InternalArenaJoinEvent e) {}

    default void onArenaFull(InternalArenaFullEvent e) {}

    default void onPlayerQuit(InternalArenaQuitEvent e) {}

    default void onCountdownStart(InternalArenaCountdownStartEvent e) {}

    default void onCountdownStop(InternalArenaCountdownStopEvent e) {}

    default void onArenaStart() {}

}
