package com.avanade.adnd.controller;

import com.avanade.adnd.model.Battle;
import com.avanade.adnd.payloads.BattleLogResponse;
import com.avanade.adnd.payloads.CreateBattleRequest;
import com.avanade.adnd.services.BattleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battle")
@Tag(name = "Battle", description = "Battle API")
public class BattleController {
    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @Tag(name = "Battle", description = "Create Battle a new Battle")
    @PostMapping(value = "/")
    public Battle createBattle(@RequestBody CreateBattleRequest createBattleRequest) throws Exception {
        return this.battleService.createBattle(createBattleRequest);
    }

    @Tag(name = "Battle", description = "Get All Battles")
    @RequestMapping(value = "/info/", method = RequestMethod.GET)
    public List<Battle> findAllBattles() {
        return this.battleService.findAllBattles();
    }

    @Tag(name = "Battle", description = "Get Battle By Id")
    @RequestMapping(value = "/info/{battleId}", method = RequestMethod.GET)
    public Battle findBattleById(@PathVariable String battleId) throws Exception {
        return this.battleService.findBattleById(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Get All Battles For Player Character")
    @RequestMapping(value = "/playerCharacter/{playerCharacterId}", method = RequestMethod.GET)
    public List<Battle> findAllBattlesForPlayerCharacter(@PathVariable String playerCharacterId) {
        return this.battleService.findAllBattlesForPlayerCharacter(UUID.fromString(playerCharacterId));
    }

    @Tag(name = "Battle", description = "Get All Battles For Monster")
    @PutMapping(value = "/{battleId}")
    public Battle updateBattle(@PathVariable String battleId, @RequestBody Battle battle) throws Exception {
        return this.battleService.updateBattleWithId(Long.valueOf(battleId), battle);
    }

    @Tag(name = "Battle", description = "Delete Battle")
    @DeleteMapping(value = "/{battleId}")
    public void deleteBattle(@PathVariable String battleId) throws Exception {
        this.battleService.deleteBattleById(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Roll Initiative for a new Battle")
    @RequestMapping(value = "/{battleId}/rollInitiative", method = RequestMethod.GET)
    public String rollInitiative(@PathVariable String battleId) throws Exception {
        return this.battleService.rollInitiative(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Get Last Battle Log")
    @RequestMapping(value = "/{battleId}/get-last-log", method = RequestMethod.GET)
    public BattleLogResponse getLastBattleLog(@PathVariable String battleId) throws Exception {
        return this.battleService.getLastBattleLogResponseByBattleId(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Make Attack")
    @RequestMapping(value = "/{battleId}/make-attack", method = RequestMethod.GET)
    public String makeAttack(@PathVariable String battleId) throws Exception {
        return this.battleService.makePlayerCharacterAttack(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Make Defense")
    @RequestMapping(value = "/{battleId}/defend", method = RequestMethod.GET)
    public String defend(@PathVariable String battleId) throws Exception {
        return this.battleService.makePlayerCharacterDefense(Long.valueOf(battleId));
    }

    @Tag(name = "Battle", description = "Calculate Attack Damage")
    @RequestMapping(value = "/{battleId}/damage", method = RequestMethod.GET)
    public String damage(@PathVariable String battleId) throws Exception {
        return this.battleService.calculateAttackDamage(Long.valueOf(battleId));
    }
}
