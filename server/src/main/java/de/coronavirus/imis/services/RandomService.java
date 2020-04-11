package de.coronavirus.imis.services;

import org.springframework.stereotype.Service;

@Service
public class RandomService {

    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public String getRandomString(final int size) {
        final StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < size; i++) {
            final int randomPosition = (int) (Math.random() * (characters.length()));
            randomString.append(characters.charAt(randomPosition));
        }
        return randomString.toString();
    }

}
