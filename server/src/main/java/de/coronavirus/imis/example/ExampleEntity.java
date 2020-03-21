package de.coronavirus.imis.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "example_entity")
public class ExampleEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String content;
}
