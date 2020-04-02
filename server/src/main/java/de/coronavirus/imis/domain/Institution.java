package de.coronavirus.imis.domain;

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
}
