package de.coronavirus.imis.services;

import de.coronavirus.imis.services.incidents.WriteIncidentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;

class PatientServiceTest {

	@Autowired
	private PatientMapper patientMapper;

	@Test
	void queryPatientsNullInOrderAndOrderByProperty() {
		final PatientService patientService = new PatientService(Mockito.mock(PatientRepository.class),
				Mockito.mock(PatientEventService.class),
				patientMapper,
				Mockito.mock(WriteIncidentService.class),
				Mockito.mock(SearchService.class)
		);
		final PatientSearchParamsDTO patientSearchParamsDTO = new PatientSearchParamsDTO();
		patientSearchParamsDTO.setOrder(null);
		patientSearchParamsDTO.setOrderBy(null);

		//no exception should be thrown here if the parameters are null
		patientService.queryPatients(patientSearchParamsDTO);
	}
}