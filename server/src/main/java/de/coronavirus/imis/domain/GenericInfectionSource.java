package de.coronavirus.imis.domain;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@DiscriminatorValue("generic")
public class GenericInfectionSource extends InfectionSource {
  private String description;
}
