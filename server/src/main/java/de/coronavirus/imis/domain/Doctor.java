package de.coronavirus.imis.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Entity
@Data
public class Doctor extends Institution {

}
