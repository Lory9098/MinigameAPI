package me.nettychannell.minigameapi.mini.scoreboard.implementation;

import me.nettychannell.minigameapi.MinigameAPI;
import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import me.nettychannell.minigameapi.utils.ChatUtil;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public abstract class NeznamyScoreboard<T extends Enum<?>> extends ScoreboardSkull<T> {
    private final TabAPI tabAPI = TabAPI.getInstance();

    /**
     * ScoreboardSkull Constructor
     *
     * @param tickInterval Interval in ticks to update the scoreboard
     */
    public NeznamyScoreboard(int tickInterval) {
        super(tickInterval);
    }

    @Override
    protected void handle(T state, Player player) {
        TabPlayer tabPlayer = tabAPI.getPlayer(player.getUniqueId());

        if (tabPlayer == null) {
            return;
        }

        String title = getTitle(state);
        List<String> lines = getLines(state);

        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, ChatUtil.color(lines.get(i)));
        }

        if (MinigameAPI.getInstance().isPlaceholderSupport()) {
            title = MinigameAPI.getInstance().parse(player, title).get();
            lines = MinigameAPI.getInstance().parse(player, lines).get();
        }

        ScoreboardManager scoreboardManager = tabAPI.getScoreboardManager();

        if (scoreboardManager == null) {
            MinigameAPI.getInstance().getPlugin().getLogger().log(Level.SEVERE, ChatUtil.color("&b[MinigamesAPI] &cUsing TAB but scoreboard is disabled in their config!"));
            return;
        }

        Scoreboard scoreboard = scoreboardManager.createScoreboard("willy wonka", title, lines);

        tabAPI.getScoreboardManager().showScoreboard(tabPlayer, scoreboard);
    }
}
