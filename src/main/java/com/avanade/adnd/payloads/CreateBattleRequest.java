package com.avanade.adnd.payloads;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateBattleRequest {
    UUID playerCharacterId;
    Long enemyId;
}
