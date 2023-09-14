package com.avanade.adnd.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class DiceServiceTest {

    @InjectMocks
    private DiceService diceService;

    @Test
    void shouldRollDiceWithNoErrors() {
        ArrayList<Integer> result = this.diceService.rollDices(1, 1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDiceSidesIsLessThanOne() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.diceService.rollDices(1, 0));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDiceSidesIsGreaterThanOneHundred() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.diceService.rollDices(1, 101));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDiceAmountIsLessThanOne() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.diceService.rollDices(0, 1));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDiceAmountIsGreaterThanTwentyFive() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.diceService.rollDices(26, 1));
    }
}
