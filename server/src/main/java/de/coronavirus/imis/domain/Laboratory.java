package de.coronavirus.imis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Laboratory extends InstitutionImpl {


	@OneToMany(mappedBy = "laboratory")
	private List<TestIncident> assignedTestIncidents;


	@Override
	public InstitutionType getType() {
		return InstitutionType.LABORATORY;
	}
}
