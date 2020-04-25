package de.coronavirus.imis.config.domain.access;

import lombok.Getter;

import de.coronavirus.imis.config.domain.UserRole;

@Getter
public class DoctorAccessRights extends AccessRights {

	private boolean canViewPatients = true;
	private boolean canRegisterTest = true;

	public DoctorAccessRights(final UserRole userRole) {
		super(userRole);
	}
}
