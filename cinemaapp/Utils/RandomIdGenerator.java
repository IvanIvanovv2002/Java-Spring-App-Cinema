package com.example.cinemaapp.Utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomIdGenerator {
    private static final Integer LENGTH = 7;
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();
    private static final String POSSIBLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXY#abcdefghilkmnopqrstuvwxyz0123456789";
    private static final StringBuilder STRING = new StringBuilder();
    public String generate() {
        for (int i = 1; i <= LENGTH; i++) {
            int randomIndex = RANDOM_GENERATOR.nextInt(POSSIBLE_CHARACTERS.length());
            char randomChar = POSSIBLE_CHARACTERS.charAt(randomIndex);
            STRING.append(randomChar);
        }
        return STRING.toString();
    }
}
