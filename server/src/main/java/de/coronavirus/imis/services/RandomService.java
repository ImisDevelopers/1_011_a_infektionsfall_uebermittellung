package de.coronavirus.imis.services;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomService {

	public static String getRandomString(final int size) {
		return RandomStringUtils.randomAlphanumeric(12);
	}

}
