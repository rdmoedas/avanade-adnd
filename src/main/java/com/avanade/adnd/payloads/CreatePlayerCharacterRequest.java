package com.avanade.adnd.payloads;

import java.util.UUID;

public record CreatePlayerCharacterRequest(UUID id, String name, String playerName, Long characterId) {
}
