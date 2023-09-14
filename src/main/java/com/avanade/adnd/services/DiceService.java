package com.avanade.adnd.services;

import java.util.ArrayList;
import java.util.Random;

public class DiceService {

    private static Random random = new Random();

    public static ArrayList<Integer> rollDices(int diceAmount, int diceSides) throws IllegalArgumentException {
        if(diceSides < 1) {
            throw new IllegalArgumentException("Number of faces must be greater than 0");
        }
        if(diceSides > 100) {
            throw new IllegalArgumentException("Number of faces must be less than 100");
        }
        if(diceAmount < 1) {
            throw new IllegalArgumentException("Number of dices must be greater than 0");
        }
        if(diceAmount >= 26) {
            throw new IllegalArgumentException("Max number of dices is 25");
        }
        ArrayList<Integer> response = new ArrayList<>();
        for (int i = 0; i < diceAmount; i++) {
            response.add(random.nextInt(diceSides) + 1);
        }
        return response;
    }

    public static int randomIntFromRange(int min, int max) throws IllegalArgumentException {
        if(min > max) {
            throw new IllegalArgumentException("Min must be less than max");
        }
        return random.nextInt(max - min + 1) + min;
    }
}
