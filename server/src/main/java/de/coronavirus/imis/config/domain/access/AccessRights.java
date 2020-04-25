package de.coronavirus.imis.config.domain.access;

import javax.persistence.Entity;

import org.apache.commons.lang3.NotImplementedException;

import lombok.Getter;

import de.coronavirus.imis.config.domain.UserRole;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.InstitutionImpl;
import de.coronavirus.imis.domain.Laboratory;

@Getter
public abstract class AccessRights {
	private UserRole userRole;

	private boolean canViewPatients = false;
	private boolean canRegisterTest = false;
	private boolean canSubmitTestResult = false;

	public AccessRights(UserRole userRole) {
		this.userRole = userRole;
	}

	public static AccessRights forInstitutionType(InstitutionImpl institution, UserRole userRole) {
		if (institution instanceof Laboratory)
			return new LaboratoryAccessRights(userRole);
		else if (institution instanceof Doctor)
			return new DoctorAccessRights(userRole);
		else
			throw new NotImplementedException("Access Rights not implemented");
	}

	boolean canAccessUsers() {
		return userRole == UserRole.USER_ROLE_ADMIN;
	}

}
