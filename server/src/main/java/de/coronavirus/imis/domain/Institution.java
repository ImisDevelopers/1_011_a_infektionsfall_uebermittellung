package de.coronavirus.imis.domain;

import de.coronavirus.imis.config.domain.User;

import java.util.List;

public interface Institution {
    String getId();

    String getName();
    void setName(String name);

    String getHouseNumber();
    void setHouseNumber(String houseNumber);

    String getStreet();
    void setStreet(String street);

    String getCity();
    void setCity(String city);

    String getZip();
    void setZip(String zip);

    String getEmail();
    void setEmail(String email);

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    String getComment();
    void setComment(String comment);

    InstitutionType getType();

    List<User> getUsers();
}
