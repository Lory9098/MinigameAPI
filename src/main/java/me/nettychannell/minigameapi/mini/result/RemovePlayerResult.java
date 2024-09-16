package me.nettychannell.minigameapi.mini.result;

import lombok.Data;

@Data
public class RemovePlayerResult {
    private final RemovePlayerResult.RemovePlayerResultType type;
    // Nullable
    private final String error;

    public enum RemovePlayerResultType {
        SUCCESS, CANCELLED, ERROR
    }
}
