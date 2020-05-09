package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.any;

class PatientServiceTest {

	@Autowired
	private PatientMapper patientMapper;

	@Test
	void queryPatientsNullInOrderAndOrderByProperty() {
		final PatientQueryService queryServiceMock = Mockito.mock(PatientQueryService.class);
		Mockito.when(queryServiceMock.getQuery(any(), any(), any())).thenReturn(Mockito.mock(TypedQuery.class));
		final PatientService patientService = new PatientService(Mockito.mock(PatientRepository.class),
				Mockito.mock(PatientEventService.class),
				new RandomService(),
				patientMapper,
				Mockito.mock(IncidentService.class),
				queryServiceMock
		);
		final PatientSearchParamsDTO patientSearchParamsDTO = new PatientSearchParamsDTO();
		patientSearchParamsDTO.setOrder(null);
		patientSearchParamsDTO.setOrderBy(null);

		//no exception should be thrown here if the parameters are null
		patientService.queryPatients(patientSearchParamsDTO);
	}
}