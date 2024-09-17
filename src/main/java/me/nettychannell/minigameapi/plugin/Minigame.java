package me.nettychannell.minigameapi.plugin;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.nettychannell.minigameapi.MinigameAPI;
import me.nettychannell.minigameapi.mini.scoreboard.ScoreboardSkull;
import me.nettychannell.minigameapi.mini.service.MinigameService;
import me.nettychannell.minigameapi.utils.ChatUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Getter
//                    K = arena key type
public class Minigame<K> implements MinigameAPI {
    @Getter
    private static Minigame<?> instance;
    private final JavaPlugin plugin;
    private final MinigameService<K> minigameService;
    @Setter
    private boolean debug, luckPermsSupport, placeholderSupport;
    private @Nullable ScoreboardSkull<?> scoreboard;

    public Minigame(JavaPlugin plugin, boolean debug) {
        instance = this;
        this.plugin = plugin;
        this.debug = debug;
        this.minigameService = new MinigameService<>();

        if (!plugin.getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
            if (debug) {
                plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &cLuckPerms not found, support disabled!"));
            }
        } else {
            plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &aLuckPerms found, support enabled!"));
        }
        if (!plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            if (debug) {
                plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &cPlaceholderAPI not found, support disabled!"));
            }
        } else {
            plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &aPlaceholderAPI found, support enabled!"));
        }
    }

    @Override
    public Optional<LuckPerms> getLuckPerms() {
        if (!luckPermsSupport) {
            return Optional.empty();
        }
        return Optional.of(LuckPermsProvider.get());
    }

    @Override
    public Optional<@NotNull String> parse(Player player, String text) {
        if (!placeholderSupport) {
            return Optional.empty();
        }
        return Optional.of(PlaceholderAPI.setPlaceholders(player, text));
    }

    @Override
    public Optional<List<String>> parse(Player player, List<String> texts) {
        if (!placeholderSupport) {
            return Optional.empty();
        }
        return Optional.of(PlaceholderAPI.setPlaceholders(player, texts));
    }

    @Override
    public <T extends Enum<?>> void enableScoreboard(ScoreboardSkull<T> scoreboard) {
        this.scoreboard = scoreboard;
    }


}
