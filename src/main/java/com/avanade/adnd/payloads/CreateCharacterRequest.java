package com.avanade.adnd.payloads;

import com.avanade.adnd.model.CharacterType;
import jakarta.validation.constraints.NotNull;

public record CreateCharacterRequest(
        @NotNull CharacterType type,
        @NotNull String category,
        @NotNull int hitPoints,
        @NotNull int strength,
        @NotNull int defense,
        @NotNull int agility,
        @NotNull int diceAmount,
        @NotNull int diceSides
        ) {
}
