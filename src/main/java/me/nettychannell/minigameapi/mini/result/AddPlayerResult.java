package me.nettychannell.minigameapi.mini.result;

import lombok.Data;

@Data
public class AddPlayerResult {
    private final AddPlayerResultType type;
    // Nullable
    private final String error;

    public enum AddPlayerResultType {
        SUCCESS, FULL, STARTED, CANCELLED, ERROR
    }

}
