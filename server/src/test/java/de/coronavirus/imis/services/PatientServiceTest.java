package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.util.LikeOperatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PatientServiceTest {

    @Test
    void queryPatientsNullInOrder() {
        final PatientService patientService = new PatientService(Mockito.mock(PatientRepository.class),
                Mockito.mock(PatientEventService.class),
                new LikeOperatorService());
        final PatientSearchParamsDTO patientSearchParamsDTO = new PatientSearchParamsDTO();
        patientSearchParamsDTO.setOrder(null);
        patientSearchParamsDTO.setOrderBy(null);

        patientService.queryPatients(patientSearchParamsDTO);
    }
}
