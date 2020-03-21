package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Illness {
    @Id
    private String icdCode;
    private String displayName;
    private String comment;

}
