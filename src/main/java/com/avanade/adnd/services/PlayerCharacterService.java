package com.avanade.adnd.services;

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

    // create player character
    public PlayerCharacter insertPlayerCharacter(CreatePlayerCharacterRequest playerCharacter) throws Exception {
        if (characterService.findCharacterById(playerCharacter.characterId()) == null) {
            throw new Exception("Character with id ${playerCharacter.characterId()} not found");
        }
        PlayerCharacter newPlayerCharacter = new PlayerCharacter();
        newPlayerCharacter.setName(playerCharacter.name());
        newPlayerCharacter.setPlayerName(playerCharacter.playerName());
        newPlayerCharacter.setCharacter(characterService.findCharacterById(playerCharacter.characterId()));
        return playerCharacterRepository.save(newPlayerCharacter);
    }

    // get all player characters
    public List<PlayerCharacter> findAllPlayerCharacters() {
        return playerCharacterRepository.findAll();
    }

    // get one player character
    public PlayerCharacter findPlayerCharacterById(UUID id) throws Exception {
        return playerCharacterRepository.findById(id)
                .orElseThrow(() -> new Exception("Player Character with id ${id} not found"));
    }

}
