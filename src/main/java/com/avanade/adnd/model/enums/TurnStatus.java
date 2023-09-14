package com.avanade.adnd.model.enums;

public enum TurnStatus {
    FINISHED("Turn finished"),
    AWAITING_PLAYER_DEFENSE("Defend!"),
    AWAITING_PLAYER_ATTACK("Attack!"),
    AWAITING_PLAYER_DAMAGE("Roll damage!");

    private final String description;

    TurnStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
