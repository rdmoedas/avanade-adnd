package com.avanade.adnd.model;

import com.avanade.adnd.model.enums.TurnStatus;
import com.avanade.adnd.payloads.BattleLogResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "battle_log")
public class BattleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty()
    private Battle battle;
    @Column(nullable = false, unique = true)
    private int turn;
    @Enumerated(EnumType.STRING)
    private TurnStatus status;
    private Integer playerCharacterAttackDice;
    private Integer playerCharacterDefenseDice;
    private Integer playerCharacterAttackDamage;
    private Integer playerCharacterHealthStartOfTurn;
    private Integer playerCharacterHealthEndOfTurn;
    private Integer nonPlayerCharacterAttackDice;
    private Integer nonPlayerCharacterDefenseDice;
    private Integer nonPlayerCharacterAttackDamage;
    private Integer nonPlayerCharacterHealthStartOfTurn;
    private Integer nonPlayerCharacterHealthEndOfTurn;

    public BattleLogResponse toResponse() {
        return new BattleLogResponse(
            this.id,
            this.battle.getId(),
            this.status,
            this.playerCharacterAttackDice,
            this.playerCharacterDefenseDice,
            this.playerCharacterAttackDamage,
            this.playerCharacterHealthStartOfTurn,
            this.playerCharacterHealthEndOfTurn,
            this.nonPlayerCharacterAttackDice,
            this.nonPlayerCharacterDefenseDice,
            this.nonPlayerCharacterAttackDamage,
            this.nonPlayerCharacterHealthStartOfTurn,
            this.nonPlayerCharacterHealthEndOfTurn
        );
    }
}
