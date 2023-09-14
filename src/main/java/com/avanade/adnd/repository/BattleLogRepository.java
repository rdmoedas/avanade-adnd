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

    @Query(value = "SELECT bl FROM BattleLog bl JOIN FETCH bl.battle b WHERE b.id = ?1 ORDER BY bl.turn DESC LIMIT 1")
    BattleLog findLastBattleLogByBattleId(Long battleId);
}
