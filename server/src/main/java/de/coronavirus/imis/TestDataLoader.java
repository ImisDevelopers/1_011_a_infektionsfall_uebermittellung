package de.coronavirus.imis;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.services.InstitutionService;
import de.coronavirus.imis.services.LabTestService;
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
    private final LabTestService labTestService;

    @Autowired
    public TestDataLoader(PatientService patientService, InstitutionService institutionService, LabTestService labTestService) {
        this.patientService = patientService;
        this.institutionService = institutionService;
        this.labTestService = labTestService;
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
            var createPersonDTO = (CreatePatientDTO) makeDTO("createPerson.json", CreatePatientDTO.class);
            var person = patientService.addPatient(createPersonDTO);

            var createLaboratoryDTO = (CreateInstitutionDTO) makeDTO("createLaboratory.json", CreateInstitutionDTO.class);
            var laboratory = institutionService.createLaboratoryInstitution(createLaboratoryDTO);

            var createDoctorsOfficeDTO = (CreateInstitutionDTO) makeDTO("createDoctorsOffice.json", CreateInstitutionDTO.class);
            var doctorsOffice = institutionService.createDoctorInstitution(createDoctorsOfficeDTO);

            var createTestSiteDTO = (CreateInstitutionDTO) makeDTO("createTestSite.json", CreateInstitutionDTO.class);
            var testSite = institutionService.createTestSiteInstitution(createTestSiteDTO);

            var createClinicDTO = (CreateInstitutionDTO) makeDTO("createClinic.json", CreateInstitutionDTO.class);
            var clinic = institutionService.createClinicInstitution(createClinicDTO);


            labTestService.createLabTest(person.getId(), laboratory.getId(), "42");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}