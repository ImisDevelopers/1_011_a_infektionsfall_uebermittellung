package de.coronavirus.imis.domain;

import de.coronavirus.imis.config.domain.User;
import java.util.List;

public interface Institution {
	String getId();

	String getName();

	String getHouseNumber();

	String getStreet();

	String getCity();

	String getZip();

	String getEmail();

	String getPhoneNumber();

	String getComment();

	InstitutionType getType();

	List<User> getUsers();
}
