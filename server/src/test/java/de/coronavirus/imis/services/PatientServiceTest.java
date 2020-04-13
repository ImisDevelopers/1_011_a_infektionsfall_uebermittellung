package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.util.LikeOperatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PatientServiceTest {

	@Test
	void queryPatientsNullInOrderAndOrderByProperty() {
		final PatientService patientService = new PatientService(Mockito.mock(PatientRepository.class),
				Mockito.mock(PatientEventService.class),
				new LikeOperatorService(),
				new RandomService());
		final PatientSearchParamsDTO patientSearchParamsDTO = new PatientSearchParamsDTO();
		patientSearchParamsDTO.setOrder(null);
		patientSearchParamsDTO.setOrderBy(null);

		//no exception should be thrown here if the parameters are null
		patientService.queryPatients(patientSearchParamsDTO);
	}
}
