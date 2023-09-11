package com.avanade.adnd.controller;

import com.avanade.adnd.model.Character;
import com.avanade.adnd.payloads.CreateCharacterRequest;
import com.avanade.adnd.services.CharacterService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping(value = "/")
    Character createCharacter(@RequestBody CreateCharacterRequest createCharacterRequest) {
        return characterService.insertCharacter(createCharacterRequest);
    }

    @GetMapping(value = "/")
    List<Character> getCharacters(Pageable pageable) {
        return characterService.findAllCharacters(pageable);
    }

    @GetMapping("/{id}")
    Character getOneCharacter(@PathVariable String id) throws Exception {
        return characterService.findCharacterById(Long.parseLong(id));
    }

    @PutMapping("{id}")
    Character updateCharacter(@PathVariable String id, @RequestBody CreateCharacterRequest updateCharacterRequest) throws Exception {
        return characterService.updateCharacter(id, updateCharacterRequest);
    }

    @DeleteMapping("{id}")
    String deleteCharacter(@PathVariable String id) throws Exception {
        characterService.deleteCharacterById(Long.parseLong(id));
        return "Character " + id + " deleted";
    }
}
