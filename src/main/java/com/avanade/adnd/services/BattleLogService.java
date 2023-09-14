package com.avanade.adnd.services;

import com.avanade.adnd.exceptions.NotFoundException;
import com.avanade.adnd.model.Battle;
import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.payloads.BattleLogResponse;
import com.avanade.adnd.payloads.CreateBattleLogRequest;
import com.avanade.adnd.repository.BattleLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BattleLogService {

    private final BattleLogRepository battleLogRepository;

    public BattleLogService(BattleLogRepository battleLogRepository) {
        this.battleLogRepository = battleLogRepository;
    }

    public BattleLog createBattleLogWithRequest(CreateBattleLogRequest battleLogRequest, Battle battle) {
        BattleLog newBattleLog = new BattleLog();
        newBattleLog.setBattle(battle);
        newBattleLog.setTurn(battleLogRequest.getTurn());
        newBattleLog.setStatus(battleLogRequest.getStatus());
        newBattleLog.setPlayerCharacterAttackDice(battleLogRequest.getPlayerCharacterAttackDice());
        newBattleLog.setPlayerCharacterDefenseDice(battleLogRequest.getPlayerCharacterDefenseDice());
        newBattleLog.setPlayerCharacterAttackDamage(battleLogRequest.getPlayerCharacterAttackDamage());
        newBattleLog.setPlayerCharacterHealthStartOfTurn(battleLogRequest.getPlayerCharacterHealthStartOfTurn());
        newBattleLog.setNonPlayerCharacterAttackDice(battleLogRequest.getNonPlayerCharacterAttackDice());
        newBattleLog.setNonPlayerCharacterDefenseDice(battleLogRequest.getNonPlayerCharacterDefenseDice());
        newBattleLog.setNonPlayerCharacterAttackDamage(battleLogRequest.getNonPlayerCharacterAttackDamage());
        newBattleLog.setNonPlayerCharacterHealthStartOfTurn(battleLogRequest.getNonPlayerCharacterHealthStartOfTurn());
        return battleLogRepository.save(newBattleLog);
    }

    public BattleLog createBattleLog(BattleLog battleLog) {
        BattleLog newBattleLog = new BattleLog();
        newBattleLog.setBattle(battleLog.getBattle());
        newBattleLog.setTurn(battleLog.getTurn());
        newBattleLog.setStatus(battleLog.getStatus());
        newBattleLog.setPlayerCharacterAttackDice(battleLog.getPlayerCharacterAttackDice());
        newBattleLog.setPlayerCharacterDefenseDice(battleLog.getPlayerCharacterDefenseDice());
        newBattleLog.setPlayerCharacterAttackDamage(battleLog.getPlayerCharacterAttackDamage());
        newBattleLog.setPlayerCharacterHealthStartOfTurn(battleLog.getPlayerCharacterHealthStartOfTurn());
        newBattleLog.setNonPlayerCharacterAttackDice(battleLog.getNonPlayerCharacterAttackDice());
        newBattleLog.setNonPlayerCharacterDefenseDice(battleLog.getNonPlayerCharacterDefenseDice());
        newBattleLog.setNonPlayerCharacterAttackDamage(battleLog.getNonPlayerCharacterAttackDamage());
        newBattleLog.setNonPlayerCharacterHealthStartOfTurn(battleLog.getNonPlayerCharacterHealthStartOfTurn());
        return battleLogRepository.save(newBattleLog);
    }

    public List<BattleLogResponse> findAllBattleLogs() {
        return battleLogRepository.findAll().stream()
                .map(BattleLog::toResponse)
                .toList();
    }

    public List<BattleLogResponse> findAllByBattleId(Long battleId) {
        return battleLogRepository.findAllByBattleId(battleId).stream()
                .map(BattleLog::toResponse)
                .toList();
    }

    public BattleLog findLastBattleLogByBattleId(Long battleId) {
        return battleLogRepository.findLastBattleLogByBattleId(battleId);
    }

    public BattleLogResponse findBattleLogById(UUID id) throws Exception {
        return battleLogRepository.findById(id).orElseThrow(() -> new NotFoundException("BattleLog not found")).toResponse();
    }

    public BattleLog updateBattleLogUsingId(UUID battleLogId, BattleLog battleLog) throws Exception {
        this.findBattleLogById(battleLogId);
        battleLog.setId(battleLogId);
        return battleLogRepository.save(battleLog);
    }

    public BattleLog updateBattleLog(BattleLog battleLog) {
        return battleLogRepository.save(battleLog);
    }

    public void deleteBattleLogById(UUID battleLogId) {
        battleLogRepository.deleteById(battleLogId);
    }
}
