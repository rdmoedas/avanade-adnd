package com.avanade.adnd.model;

import com.avanade.adnd.model.enums.TurnStatus;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battle_id", nullable = false)
    private Battle battle;
    @Column(nullable = false)
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
}
