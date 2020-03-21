package de.coronavirus.imis.domain;

import lombok.Data;

@Data
abstract class Institution {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String comment;

}
