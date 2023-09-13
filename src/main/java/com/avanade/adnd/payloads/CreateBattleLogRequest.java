package com.avanade.adnd.payloads;

import com.avanade.adnd.model.enums.TurnStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBattleLogRequest {
    @NotNull
    private Long battleId;
    @NotNull
    private int turn;
    @NotNull
    private TurnStatus status;
    private Integer playerCharacterAttackDice;
    private Integer playerCharacterDefenseDice;
    private Integer playerCharacterAttackDamage;
    private Integer playerCharacterHealthStartOfTurn;
    private Integer nonPlayerCharacterAttackDice;
    private Integer nonPlayerCharacterDefenseDice;
    private Integer nonPlayerCharacterAttackDamage;
    private Integer nonPlayerCharacterHealthStartOfTurn;
}
