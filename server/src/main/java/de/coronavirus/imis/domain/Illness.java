package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Illness {
    @Id
    private String icdCode;
    private String displayName;
    private String comment;

}
