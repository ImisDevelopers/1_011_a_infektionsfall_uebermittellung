package de.coronavirus.imis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> stringList) {
		return String.join(SPLIT_CHAR, stringList);
	}

	@Override
	public List<String> convertToEntityAttribute(String string) {
		return Arrays.asList(string.split(SPLIT_CHAR));
	}
}