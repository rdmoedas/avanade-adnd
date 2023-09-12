package com.avanade.adnd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "battle_log")
public class BattleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @Column(nullable = false)
    private Battle battle;

    @Column(nullable = false)
    private Long turn;

    private Long playerCharacterAttackDice;

    private Long playerCharacterDefenseDice;

    private Long playerCharacterAttackDamage;

    private Long playerCharacterHealthStartOfTurn;

    private Long nonPlayerCharacterAttackDice;

    private Long nonPlayerCharacterDefenseDice;

    private Long nonPlayerCharacterAttackDamage;

    private Long nonPlayerCharacterHealthStartOfTurn;
}
