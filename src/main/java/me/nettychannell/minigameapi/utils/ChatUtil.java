package me.nettychannell.minigameapi.utils;

import org.bukkit.ChatColor;

public class ChatUtil {

    public static String color(String old) {
        return ChatColor.translateAlternateColorCodes('&', old);
    }

}
