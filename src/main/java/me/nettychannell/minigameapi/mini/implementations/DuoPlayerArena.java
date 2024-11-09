package me.nettychannell.minigameapi.mini.implementations;

import me.nettychannell.minigameapi.mini.MinigameArena;
import me.nettychannell.minigameapi.mini.listener.ArenaListener;
import me.nettychannell.minigameapi.mini.listener.event.*;
import me.nettychannell.minigameapi.mini.result.AddPlayerResult;
import me.nettychannell.minigameapi.mini.result.RemovePlayerResult;
import me.nettychannell.minigameapi.pair.Pair;
import me.nettychannell.minigameapi.plugin.Minigame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

//                                      GameState,         MinigameArena
public abstract class DuoPlayerArena<T extends Enum<?>, E extends MinigameArena<?, ?>> extends MinigameArena<T, E> {
    private final List<Pair<Player, Player>> teams = new ArrayList<>();
    private final boolean findPairOnJoin;

    /**
     * DuoPlayerArena Constructor
     * @param minPlayers Minimum amount of players required to start the game, -1 for no minimum
     * @param maxPlayers Maximum amount of players allowed in the game, -1 for no maximum
     * */
    public DuoPlayerArena(int arenaId, boolean findPairOnJoin, int minPlayers, int maxPlayers, int countdownTime) {
        super(arenaId, minPlayers, maxPlayers, countdownTime);
        setGameState(getWaitingState());
        this.findPairOnJoin = findPairOnJoin;
    }

    @Override
    public AddPlayerResult addPlayer(Player player) {
        try {
            boolean canExecute = true;

            if (getMinigameArena() instanceof ArenaListener) {
                ArenaListener listener = (ArenaListener) getMinigameArena();
                InternalArenaJoinEvent e = new InternalArenaJoinEvent(super.getId(), player, System.currentTimeMillis());
                listener.onPlayerJoin(e);
                if (e.isCancelled()) {
                    canExecute = false;
                }
            }
            if (!canExecute) {
                return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.CANCELLED, null);
            }

            if (getGameState() == getPlayingState() || getGameState() == getEndingState()) {
                return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.STARTED, null);
            }

            if (getMaxPlayers() != -1) {
                if (getPlayers().size() >= getMaxPlayers()) {
                    if (getMinigameArena() instanceof ArenaListener) {
                        ArenaListener listener = (ArenaListener) getMinigameArena();
                        InternalArenaFullEvent e = new InternalArenaFullEvent(super.getId(), player);
                        listener.onArenaFull(e);
                    }
                    return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.FULL, null);
                }
            }

            getPlayers().add(player.getUniqueId());

            if (findPairOnJoin) {
                Player pair = findPair(player);

                if (pair != null) {
                    if (getMinigameArena() instanceof ArenaListener) {
                        ArenaListener listener = (ArenaListener) getMinigameArena();
                        ServerChosedBialPlayerPairEvent e = new ServerChosedBialPlayerPairEvent(getId(), player, pair);
                        listener.onServerChoosedBialPlayerPair(e);

                        if (!e.isCancelled()) {
                            pair = e.getPairedPlayer();
                        }
                    }
                }

                //TODO: create team
            }

            // TODO: SEE THIS EVENT
            if (getMinPlayers() != -1 && getPlayers().size() >= getMinPlayers() && getGameState() == getWaitingState()) {
                if (getMinigameArena() instanceof ArenaListener) {
                    ArenaListener arenaListener = (ArenaListener) getMinigameArena();

                    InternalArenaCountdownStartEvent e = new InternalArenaCountdownStartEvent(super.getId());
                    arenaListener.onCountdownStart(e);

                    if (e.isCancelled()) {
                        return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.SUCCESS, null);
                    }
                }
                setCountdown(generateCountdown());
                setGameState(getStartingState());

                getCountdown().runTaskTimer(Minigame.getInstance().getPlugin(), 0, 20);
            }
            return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.SUCCESS, null);
        } catch (Exception e) {
            return new AddPlayerResult(AddPlayerResult.AddPlayerResultType.ERROR, e.getMessage());
        }
    }

    private Player findPair(Player player) {
        //TODO: matchmaking logic

        if (getPlayers().size() == 0) return null;


    }

    @Override
    public RemovePlayerResult removePlayer(Player player) {
        try {
            boolean canExecute = true;

            if (getMinigameArena() instanceof ArenaListener) {
                ArenaListener listener = (ArenaListener) getMinigameArena();
                InternalArenaQuitEvent e = new InternalArenaQuitEvent(super.getId(), player, System.currentTimeMillis());
                listener.onPlayerQuit(e);
                if (e.isCancelled()) {
                    canExecute = false;
                }
            }

            if (!canExecute) {
                return new RemovePlayerResult(RemovePlayerResult.RemovePlayerResultType.CANCELLED, null);
            }

            getPlayers().remove(player.getUniqueId());

            if (getMinPlayers() != -1 && getPlayers().size() < getMinPlayers() && getGameState() == getStartingState()) {
                stopCountdown();
            }

            return new RemovePlayerResult(RemovePlayerResult.RemovePlayerResultType.SUCCESS, null);
        } catch (Exception e) {
            return new RemovePlayerResult(RemovePlayerResult.RemovePlayerResultType.ERROR, e.getMessage());
        }
    }

}
