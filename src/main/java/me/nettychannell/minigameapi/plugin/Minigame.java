package me.nettychannell.minigameapi.plugin;

import lombok.Getter;
import lombok.Setter;
import me.nettychannell.minigameapi.MinigameAPI;
import me.nettychannell.minigameapi.mini.service.MinigameService;
import me.nettychannell.minigameapi.utils.ChatUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.logging.Level;

@Getter
//                    arena key type
public class Minigame<K> implements MinigameAPI {
    @Getter
    private static Minigame<?> instance;
    private final JavaPlugin plugin;
    private final MinigameService<K> minigameService;
    @Setter
    private boolean debug;

    public Minigame(JavaPlugin plugin, boolean debug) {
        instance = this;
        this.plugin = plugin;
        this.debug = debug;
        this.minigameService = new MinigameService<>();
    }

    @Override
    public Optional<LuckPerms> getLuckPerms() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
            if (debug) {
                plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &cLuckPerms not found, support disabled!"));
            }
            return Optional.empty();
        }
        plugin.getLogger().log(Level.INFO, ChatUtil.color("&b[MinigamesAPI] &aLuckPerms found, support enabled!"));
        return Optional.of(LuckPermsProvider.get());
    }
}
