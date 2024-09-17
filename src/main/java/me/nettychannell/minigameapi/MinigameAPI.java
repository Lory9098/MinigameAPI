package me.nettychannell.minigameapi;

import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import me.nettychannell.minigameapi.plugin.Minigame;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public interface MinigameAPI {

    static Minigame getInstance() {
        return Minigame.getInstance();
    }

    boolean isDebug();

    boolean isLuckPermsSupport();

    boolean isPlaceholderSupport();

    Optional<LuckPerms> getLuckPerms();

    Optional<String> parse(Player player, String text);

    Optional<List<String>> parse(Player player, List<String> texts);

    <T extends Enum<?>> void enableScoreboard(ScoreboardSkull<T> scoreboard);

    <T extends ScoreboardSkull<?>> T getScoreboard();

}