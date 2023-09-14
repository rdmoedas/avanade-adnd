package com.avanade.adnd.payloads;

import com.avanade.adnd.model.enums.TurnStatus;

import java.util.UUID;

public record BattleLogResponse(
    UUID id,
    Long battleId,
    Integer turn,
    TurnStatus turnStatus,
    Integer playerCharacterAttackDice,
    Integer playerCharacterDefenseDice,
    Integer playerCharacterAttackDamage,
    Integer playerCharacterHealthStartOfTurn,
    Integer playerCharacterHealthEndOfTurn,
    Integer nonPlayerCharacterAttackDice,
    Integer nonPlayerCharacterDefenseDice,
    Integer nonPlayerCharacterAttackDamage,
    Integer nonPlayerCharacterHealthStartOfTurn,
    Integer nonPlayerCharacterHealthEndOfTurn
) {
}
