package de.coronavirus.imis.domain;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
  @Type(name = "generic", value = GenericInfectionSource.class),
  @Type(name = "person", value = ExposureContact.class)
})
public abstract class InfectionSource {
  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  private Patient contact;
  private LocalDate dateOfContact;
  private String comment;
}
