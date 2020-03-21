package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
abstract class Institution {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String comment;

}
