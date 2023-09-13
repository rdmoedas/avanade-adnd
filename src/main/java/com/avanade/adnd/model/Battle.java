package com.avanade.adnd.model;

import com.avanade.adnd.model.enums.BattleStatus;
import com.avanade.adnd.model.enums.Initiative;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "battle")
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlayerCharacter playerCharacter;

    @ManyToOne
    private Character enemy;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BattleLog> battleLogs;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    @Enumerated(EnumType.STRING)
    private Initiative initiative;

    public int getPlayerHitPoints() {
        return this.playerCharacter.getCharacter().getHitPoints();
    }

    public int getEnemyHitPoints() {
        return this.enemy.getHitPoints();
    }

    public int getPlayerStrength() {
        return this.playerCharacter.getCharacter().getStrength();
    }

    public int getEnemyStrength() {
        return this.enemy.getStrength();
    }

    public int getPlayerAgility() {
        return this.playerCharacter.getCharacter().getAgility();
    }

    public int getEnemyAgility() {
        return this.enemy.getAgility();
    }

    public int getPlayerDefense() {
        return this.playerCharacter.getCharacter().getDefense();
    }

    public int getEnemyDefense() {
        return this.enemy.getDefense();
    }

    public int getPlayerDiceAmount() {
        return this.playerCharacter.getCharacter().getDiceAmount();
    }

    public int getPlayerDiceSides() {
        return this.playerCharacter.getCharacter().getDiceSides();
    }

}
