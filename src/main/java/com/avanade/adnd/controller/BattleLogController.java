package com.avanade.adnd.controller;

import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.payloads.BattleLogResponse;
import com.avanade.adnd.services.BattleLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battle-log")
@Tag(name = "Battle Log", description = "Battle Log API")
public class BattleLogController {

    BattleLogService battleLogService;

    public BattleLogController(BattleLogService battleLogService) {
        this.battleLogService = battleLogService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<BattleLogResponse>> findAllBattleLogs() throws Exception {
        List<BattleLogResponse> battleLogs = this.battleLogService.findAllBattleLogs();
        return ResponseEntity.ok(battleLogs);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BattleLogResponse findBattleLogById(@PathVariable String id) throws Exception {
        return this.battleLogService.findBattleLogById(UUID.fromString(id));
    }

    @RequestMapping(value = "/battle/{battleId}", method = RequestMethod.GET)
    public ResponseEntity<List<BattleLogResponse>> findAllBattleLogsByBattleId(@PathVariable String battleId) throws Exception {
        List<BattleLogResponse> battleLogs = this.battleLogService.findAllByBattleId(Long.valueOf(battleId));
        return ResponseEntity.ok(battleLogs);
    }

    @RequestMapping(value = "/battle/{battleId}/last", method = RequestMethod.GET)
    public BattleLogResponse findLastBattleLogByBattleId(@PathVariable String battleId) throws Exception {
        return this.battleLogService.findLastBattleLogByBattleId(Long.valueOf(battleId));
    }

    @PostMapping(value = "/")
    public BattleLog createBattleLog(@RequestBody BattleLog battleLog) throws Exception {
        return this.battleLogService.createBattleLog(battleLog);
    }

    @PutMapping(value = "/{battleLogId}")
    public BattleLog updateBattleLog(@PathVariable String battleLogId, @RequestBody BattleLog battleLog) throws Exception {
        return this.battleLogService.updateBattleLogUsingId(UUID.fromString(battleLogId), battleLog);
    }

    @DeleteMapping(value = "/{battleLogId}")
    public void deleteBattleLog(@PathVariable String battleLogId) throws Exception {
        this.battleLogService.deleteBattleLogById(UUID.fromString(battleLogId));
    }
}
