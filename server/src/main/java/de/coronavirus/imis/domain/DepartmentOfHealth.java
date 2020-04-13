package de.coronavirus.imis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class DepartmentOfHealth extends InstitutionImpl {

	@Override
	public InstitutionType getType() {
		return InstitutionType.DEPARTMENT_OF_HEALTH;
	}
}
