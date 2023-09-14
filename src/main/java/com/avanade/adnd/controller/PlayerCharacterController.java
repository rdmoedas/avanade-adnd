package com.avanade.adnd.controller;

import com.avanade.adnd.model.PlayerCharacter;
import com.avanade.adnd.payloads.CreatePlayerCharacterRequest;
import com.avanade.adnd.services.PlayerCharacterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player-character")
@Tag(name = "Player Character", description = "Player Character API")
public class PlayerCharacterController {

    private final PlayerCharacterService playerCharacterService;

    public PlayerCharacterController(PlayerCharacterService playerCharacterService) {
        this.playerCharacterService = playerCharacterService;
    }


    @PostMapping(value = "/")
    public PlayerCharacter createPlayerCharacter(@RequestBody CreatePlayerCharacterRequest createPlayerCharacterRequest) throws Exception {
        return this.playerCharacterService.insertPlayerCharacter(createPlayerCharacterRequest);
    }

    @GetMapping(value = "/")
    public List<PlayerCharacter> getPlayerCharacter() {
        return this.playerCharacterService.findAllPlayerCharacters();
    }

    @GetMapping(value = "/{id}")
    public PlayerCharacter getOnePlayerCharacter(String id) {
        return null;
    }

}
