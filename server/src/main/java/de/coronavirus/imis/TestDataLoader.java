package de.coronavirus.imis;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.services.InstitutionService;
import de.coronavirus.imis.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;


@Component
public class TestDataLoader implements ApplicationRunner {


    private final PatientService patientService;
    private final InstitutionService institutionService;

    @Autowired
    public TestDataLoader(PatientService patientService, InstitutionService institutionService) {
        this.patientService = patientService;
        this.institutionService = institutionService;
    }

    static Object makeDTO(String testFileName, Class clazz)
            throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("sample_data" + File.separator + testFileName);
        File file = resource.getFile();


        byte[] encoded = Files.readAllBytes(file.toPath());

        String str = new String(encoded, Charset.defaultCharset());
        return mapper.readValue(str, clazz);
    }

    public void run(ApplicationArguments args) {
        try {
            var patientDTO = (CreatePatientDTO) makeDTO("createPerson.json", CreatePatientDTO.class);
            patientService.addPatient(patientDTO);

            var laboratoryDTO = (CreateInstitutionDTO) makeDTO("createInstitution.json", CreateInstitutionDTO.class);
            institutionService.createLaboratoryInstitution(laboratoryDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}