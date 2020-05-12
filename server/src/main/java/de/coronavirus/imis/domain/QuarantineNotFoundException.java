package de.coronavirus.imis.domain;

public class QuarantineNotFoundException extends RuntimeException {
	public QuarantineNotFoundException(String message) {
		super(message);
	}
}
