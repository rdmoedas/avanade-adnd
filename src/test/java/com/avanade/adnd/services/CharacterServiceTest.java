package com.avanade.adnd.services;

import com.avanade.adnd.model.Character;
import com.avanade.adnd.payloads.CreateCharacterRequest;
import com.avanade.adnd.repository.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private CharacterRepository characterRepository;

    @Test
    @DisplayName("Should insert character with no errors")
    void shouldInsertCharacterWithNoErrors() {
        // GIVEN
        CreateCharacterRequest createCharacterRequest = mock(CreateCharacterRequest.class);
        // WHEN
        Assertions.assertDoesNotThrow(() -> characterService.insertCharacter(createCharacterRequest));
        // THEN
        verify(characterRepository, times(1))
                .save(any(Character.class));
    }

    @Test
    @DisplayName("Should find character by id with no errors")
    void shouldFindCharacterByIdWithNoErrors() throws Exception {
        // GIVEN
        Character character = mock(Character.class);
        when(characterRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(character));
        // WHEN
        Character result = characterService.findCharacterById(1L);
        // THEN
        Assertions.assertNotNull(result);
        Assertions.assertEquals(character, result);
        verify(characterRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @DisplayName("Should find all characters with no errors")
    void shouldFindAllCharactersWithNoErrors() {
        // GIVEN
        Character character = mock(Character.class);
        when(characterRepository.findAll())
                .thenReturn(List.of(character));
        // WHEN
        List<Character> result = characterService.findAllCharacters();
        // THEN
        Assertions.assertNotNull(result);
        verify(characterRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("Should update character with no errors")
    void shouldUpdateCharacterWithNoErrors() throws Exception {
        // GIVEN
        CreateCharacterRequest createCharacterRequest = mock(CreateCharacterRequest.class);
        Character character = mock(Character.class);
        when(characterRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(character));
        // WHEN
        Assertions.assertDoesNotThrow(() -> characterService.updateCharacter("1", createCharacterRequest));
        // THEN
        verify(characterRepository, times(1))
                .save(any(Character.class));
    }

    @Test
    @DisplayName("Should delete character with no errors")
    void shouldDeleteCharacterWithNoErrors() {
        // GIVEN
        Character character = new Character();
        character.setId((long) 1L);
        when(characterRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(character));
        // WHEN
        Assertions.assertDoesNotThrow(() -> characterService.deleteCharacterById((long) 1L));
        // THEN
        verify(characterRepository, times(1))
                .deleteById((long) 1L);
    }
}
