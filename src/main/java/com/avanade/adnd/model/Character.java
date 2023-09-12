package com.avanade.adnd.model;

import com.avanade.adnd.model.enums.CharacterType;
import com.avanade.adnd.payloads.CreateCharacterRequest;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharacterType type;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int hitPoints;

    @Column(nullable = false)
    private int strength;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private int agility;

    @Column(nullable = false)
    private int diceAmount;

    @Column(nullable = false)
    private int diceSides;

    public Character() {
    }

    public Character(CreateCharacterRequest character) {
        this.type = character.type();
        this.category = character.category();
        this.hitPoints = character.hitPoints();
        this.strength = character.strength();
        this.defense = character.defense();
        this.agility = character.agility();
        this.diceAmount = character.diceAmount();
        this.diceSides = character.diceSides();
    }
}
