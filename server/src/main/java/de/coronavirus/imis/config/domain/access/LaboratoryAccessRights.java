package de.coronavirus.imis.config.domain.access;

import de.coronavirus.imis.config.domain.UserRole;

public class LaboratoryAccessRights extends AccessRights {
	public LaboratoryAccessRights(final UserRole userRole) {
		super(userRole);
	}

	boolean canSubmitTestResult() {
		return true;
	}
}
