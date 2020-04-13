package de.coronavirus.imis.domain;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Laboratory extends InstitutionImpl {


	@OneToMany(mappedBy = "laboratory")
	private List<LabTest> assignedLabTest;


	@Override
	public InstitutionType getType() {
		return InstitutionType.LABORATORY;
	}
}
