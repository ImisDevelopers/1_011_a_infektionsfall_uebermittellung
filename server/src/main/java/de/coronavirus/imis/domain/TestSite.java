package de.coronavirus.imis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TestSite extends InstitutionImpl {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Override
	public InstitutionType getType() {
		return InstitutionType.TEST_SITE;
	}
}
