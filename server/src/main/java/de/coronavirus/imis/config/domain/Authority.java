package de.coronavirus.imis.config.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import de.coronavirus.imis.domain.InstitutionType;

@Data
@Entity
public class Authority {
    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private InstitutionType role;

    private String displayName;

}
