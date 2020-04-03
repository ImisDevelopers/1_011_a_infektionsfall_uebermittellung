package de.coronavirus.imis.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.coronavirus.imis.config.domain.User;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.util.List;

@Data
@Entity(name = "institution")
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public abstract class InstitutionImpl implements Institution {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;
    private String houseNumber;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phoneNumber;
    private String comment;

    @OneToMany(mappedBy = "institution")
    private List<User> users;


}
