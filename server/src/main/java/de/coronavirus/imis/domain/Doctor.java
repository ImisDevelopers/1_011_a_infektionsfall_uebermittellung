package de.coronavirus.imis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Entity
@Data
public class Doctor extends InstitutionImpl {

	@Override
	public InstitutionType getType() {
		return InstitutionType.DOCTORS_OFFICE;
	}

}
