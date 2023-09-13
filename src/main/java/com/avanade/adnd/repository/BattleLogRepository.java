package com.avanade.adnd.repository;

import com.avanade.adnd.model.BattleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleLogRepository extends JpaRepository<BattleLog, UUID> {

    List<BattleLog> findAllByBattleId(Long battleId);

    @Query(value = "SELECT * FROM battle_log WHERE battle_id = ?1 ORDER BY turn DESC LIMIT 1", nativeQuery = true)
    BattleLog findLastBattleLogByBattleId(Long battleId);
}
