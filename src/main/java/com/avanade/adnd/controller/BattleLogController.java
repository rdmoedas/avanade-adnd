package com.avanade.adnd.controller;

import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.payloads.BattleLogResponse;
import com.avanade.adnd.services.BattleLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battle-log")
public class BattleLogController {

    BattleLogService battleLogService;

    public BattleLogController(BattleLogService battleLogService) {
        this.battleLogService = battleLogService;
    }

    @RequestMapping("/")
    public ResponseEntity<List<BattleLogResponse>> findAllBattleLogs() throws Exception {
        List<BattleLogResponse> battleLogs = this.battleLogService.findAllBattleLogs();
        return ResponseEntity.ok(battleLogs);
    }

    @RequestMapping("/{id}")
    public BattleLogResponse findBattleLogById(@PathVariable String id) throws Exception {
        return this.battleLogService.findBattleLogById(UUID.fromString(id));
    }

    @RequestMapping("/battle/{battleId}")
    public ResponseEntity<List<BattleLogResponse>> findAllBattleLogsByBattleId(@PathVariable String battleId) throws Exception {
        List<BattleLogResponse> battleLogs = this.battleLogService.findAllByBattleId(Long.valueOf(battleId));
        return ResponseEntity.ok(battleLogs);
    }

    @RequestMapping("/battle/{battleId}/last")
    public BattleLogResponse findLastBattleLogByBattleId(@PathVariable String battleId) throws Exception {
        BattleLog battleLog = this.battleLogService.findLastBattleLogByBattleId(Long.valueOf(battleId));
        return battleLog.toResponse();
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
