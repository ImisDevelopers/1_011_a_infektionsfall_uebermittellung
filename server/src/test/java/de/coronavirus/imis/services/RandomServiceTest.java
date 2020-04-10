package de.coronavirus.imis.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomServiceTest {

    @Test
    void getRandomString() {
        final RandomService randomService = new RandomService();
        final int size = 12;
        final String randomString = randomService.getRandomString(size);
        Assertions.assertEquals(size, randomString.length());
    }
}
