package com.avanade.adnd.services;

import com.avanade.adnd.model.Battle;
import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.payloads.CreateBattleLogRequest;
import com.avanade.adnd.repository.BattleLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleLogService {

    private final BattleLogRepository battleLogRepository;

    public BattleLogService(BattleLogRepository battleLogRepository) {
        this.battleLogRepository = battleLogRepository;
    }

    public BattleLog createBattleLog(CreateBattleLogRequest battleLogRequest, Battle battle) {
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

    public List<BattleLog> findAllByBattleId(Long battleId) {
        return battleLogRepository.findAllByBattleId(battleId);
    }

    public BattleLog findLastBattleLogByBattleId(Long battleId) {
        return battleLogRepository.findLastBattleLogByBattleId(battleId);
    }

    public BattleLog updateBattleLog(BattleLog battleLog) {
        return battleLogRepository.save(battleLog);
    }
}
