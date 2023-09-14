package com.avanade.adnd.services;

import com.avanade.adnd.exceptions.NotFoundException;
import com.avanade.adnd.model.Character;
import com.avanade.adnd.model.enums.CharacterType;
import com.avanade.adnd.payloads.CreateCharacterRequest;
import com.avanade.adnd.repository.CharacterRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Character insertCharacter(CreateCharacterRequest character) {
        Character newCharacter = new Character(character);
        return characterRepository.save(newCharacter);
    }

    public List<Character> findAllCharacters(Pageable pageable) {
        return characterRepository.findAll(pageable).getContent();
    }

    public List<Character> findAllByType(CharacterType type) {
        return characterRepository.findAllByType(type);
    }

    public Character findCharacterById(Long id) throws Exception {
        return characterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Character with id ${id} not found"));
    }

    public Character updateCharacter(String id, CreateCharacterRequest updateCharacterRequest) throws Exception {
        Long characterId = Long.parseLong(id);
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NotFoundException("Character with id ${characterId} not found"));
        character.setType(updateCharacterRequest.type());
        character.setCategory(updateCharacterRequest.category());
        character.setHitPoints(updateCharacterRequest.hitPoints());
        character.setStrength(updateCharacterRequest.strength());
        character.setDefense(updateCharacterRequest.defense());
        character.setAgility(updateCharacterRequest.agility());
        character.setDiceAmount(updateCharacterRequest.diceAmount());
        character.setDiceSides(updateCharacterRequest.diceSides());
        return characterRepository.save(character);
    }

    public void deleteCharacterById(Long id) throws Exception {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Character with id ${id} not found"));
        characterRepository.deleteById(character.getId());
    }
}
