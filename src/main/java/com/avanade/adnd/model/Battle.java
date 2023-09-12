package com.avanade.adnd.model;

import com.avanade.adnd.model.enums.BattleStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "battle")
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @Column(nullable = false)
    private PlayerCharacter playerCharacter;

    @ManyToOne
    @Column(nullable = false)
    private Character enemyCharacter;

    @OneToMany
    private List<BattleLog> battleLogs;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    private Boolean playerHasInitiative;

    private Boolean playerCharacterWon;

}
