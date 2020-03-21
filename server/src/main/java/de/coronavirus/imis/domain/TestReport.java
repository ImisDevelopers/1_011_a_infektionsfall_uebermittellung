package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class TestReport {
    @Id
    @Generated
    private Long id;

    @Lob
    byte[] additionalData;
}

