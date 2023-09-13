package com.avanade.adnd.controller;

import com.avanade.adnd.model.Battle;
import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.payloads.CreateBattleRequest;
import com.avanade.adnd.services.BattleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battle")
public class BattleController {
    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping(value = "/")
    public Battle createBattle(@RequestBody CreateBattleRequest createBattleRequest) throws Exception {
        return this.battleService.createBattle(createBattleRequest);
    }

    @RequestMapping("/info/")
    public List<Battle> findAllBattles() {
        return this.battleService.findAllBattles();
    }

    @RequestMapping("/info/{battleId}")
    public Battle findBattleById(@PathVariable String battleId) throws Exception {
        return this.battleService.findBattleById(Long.valueOf(battleId));
    }

    @RequestMapping("/playerCharacter/{playerCharacterId}")
    public List<Battle> findAllBattlesForPlayerCharacter(@PathVariable String playerCharacterId) {
        return this.battleService.findAllBattlesForPlayerCharacter(UUID.fromString(playerCharacterId));
    }

    @PutMapping(value = "/{battleId}")
    public Battle updateBattle(@PathVariable String battleId, @RequestBody Battle battle) throws Exception {
        return this.battleService.updateBattleWithId(Long.valueOf(battleId), battle);
    }

    @DeleteMapping(value = "/{battleId}")
    public void deleteBattle(@PathVariable String battleId) throws Exception {
        this.battleService.deleteBattleById(Long.valueOf(battleId));
    }

    @RequestMapping(value = "/{battleId}/rollInitiative")
    public String rollInitiative(@PathVariable String battleId) throws Exception {
        return this.battleService.rollInitiative(Long.valueOf(battleId));
    }

    @RequestMapping(value = "/{battleId}/get-last-log")
    public BattleLog getLastBattleLog(@PathVariable String battleId) throws Exception {
        return this.battleService.getLastBattleLogByBattleId(Long.valueOf(battleId));
    }

    @RequestMapping(value = "/{battleId}/make-attack")
    public String makeAttack(@PathVariable String battleId) throws Exception {
        return this.battleService.makePlayerCharacterAttack(Long.valueOf(battleId));
    }

    @RequestMapping(value = "/{battleId}/defend")
    public String defend(@PathVariable String battleId) throws Exception {
        return this.battleService.makePlayerCharacterDefense(Long.valueOf(battleId));
    }

    @RequestMapping(value = "/{battleId}/damage")
    public String damage(@PathVariable String battleId) throws Exception {
        return this.battleService.calculateAttackDamage(Long.valueOf(battleId));
    }
}
