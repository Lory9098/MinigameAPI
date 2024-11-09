package me.nettychannell.minigameapi.mini;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.nettychannell.minigameapi.mini.countdown.Countdown;
import me.nettychannell.minigameapi.mini.listener.ArenaListener;
import me.nettychannell.minigameapi.mini.result.AddPlayerResult;
import me.nettychannell.minigameapi.mini.result.RemovePlayerResult;
import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//                                  GameState
// DO NOT EXTEND THIS CLASS IN YOUR CODE
@Setter @Getter
public abstract class MinigameArena<T extends Enum<?>, E extends MinigameArena<?, ?>> {
    private int id;
    private T gameState;
    private final int minPlayers, maxPlayers, countdownTime;
    private Countdown countdown;
    private final List<UUID> players;

    /**
     * MinigameArena Constructor
     * @param minPlayers Minimum amount of players required to start the game, -1 for no minimum
     * @param maxPlayers Maximum amount of players allowed in the game, -1 for no maximum
     * */
    public MinigameArena(int id, int minPlayers, int maxPlayers, int countdownTime) {
        this.id = id;
        this.gameState = getWaitingState();
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.countdownTime = countdownTime;
        this.countdown = generateCountdown();
        this.players = new ArrayList<>();
    }

    public abstract AddPlayerResult addPlayer(Player player);
    public abstract RemovePlayerResult removePlayer(Player player);

    public abstract T getWaitingState();

    public abstract T getStartingState();

    public abstract T getPlayingState();

    public abstract T getEndingState();

    public abstract E getMinigameArena();

    public abstract Countdown generateCountdown();

    public void stopCountdown() {
        countdown.cancel();
    }

    public void start() {
        setGameState(getPlayingState());

        if (getMinigameArena() instanceof ArenaListener) {
            ArenaListener listener = (ArenaListener) getMinigameArena();
            listener.onArenaStart();
        }
    }

}