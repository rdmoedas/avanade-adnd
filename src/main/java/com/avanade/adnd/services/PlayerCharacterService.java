package com.avanade.adnd.services;

import com.avanade.adnd.exceptions.NotFoundException;
import com.avanade.adnd.model.Character;
import com.avanade.adnd.model.PlayerCharacter;
import com.avanade.adnd.payloads.CreatePlayerCharacterRequest;
import com.avanade.adnd.repository.PlayerCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerCharacterService {

    private final CharacterService characterService;
    private final PlayerCharacterRepository playerCharacterRepository;

    public PlayerCharacterService(CharacterService characterService, PlayerCharacterRepository playerCharacterRepository) {
        this.characterService = characterService;
        this.playerCharacterRepository = playerCharacterRepository;
    }

    public PlayerCharacter insertPlayerCharacter(CreatePlayerCharacterRequest playerCharacter) throws Exception {
        Character character = this.characterService.findCharacterById(playerCharacter.characterId());
        PlayerCharacter newPlayerCharacter = new PlayerCharacter();
        newPlayerCharacter.setName(playerCharacter.name());
        newPlayerCharacter.setPlayerName(playerCharacter.playerName());
        newPlayerCharacter.setCharacter(character);
        return playerCharacterRepository.save(newPlayerCharacter);
    }

    public List<PlayerCharacter> findAllPlayerCharacters() {
        return playerCharacterRepository.findAll();
    }

    public PlayerCharacter findPlayerCharacterById(UUID id) throws Exception {
        return playerCharacterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player Character with id ${id} not found"));
    }

    public PlayerCharacter updatePlayerCharacter(UUID id, CreatePlayerCharacterRequest updatePlayerCharacterRequest) throws Exception {
        PlayerCharacter playerCharacter = playerCharacterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player Character with id ${id} not found"));
        Character character = this.characterService.findCharacterById(updatePlayerCharacterRequest.characterId());
        playerCharacter.setName(updatePlayerCharacterRequest.name());
        playerCharacter.setPlayerName(updatePlayerCharacterRequest.playerName());
        playerCharacter.setCharacter(character);
        return playerCharacterRepository.save(playerCharacter);
    }

    public void deletePlayerCharacterById(UUID id) throws Exception {
        PlayerCharacter playerCharacter = playerCharacterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player Character with id ${id} not found"));
        playerCharacterRepository.deleteById(playerCharacter.getId());
    }
}
