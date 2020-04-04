package de.coronavirus.imis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserRepository;
import de.coronavirus.imis.config.domain.UserRole;
import de.coronavirus.imis.services.InstitutionService;
import de.coronavirus.imis.services.LabTestService;
import de.coronavirus.imis.services.PatientEventService;
import de.coronavirus.imis.services.PatientService;
import de.coronavirus.imis.services.StatsService;


@Component
@RequiredArgsConstructor
public class TestDataLoader implements ApplicationRunner {


    private final PatientService patientService;
    private final InstitutionService institutionService;
    private final LabTestService labTestService;
    private final PatientEventService eventService;
    private final StatsService statsService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


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
            for (int i = 0; i < 5; i++) {
                var createPersonDTO = (CreatePatientDTO) makeDTO("persons" + File.separator + "person" + i + ".json", CreatePatientDTO.class);
                patientService.addPatient(createPersonDTO);
            }

            // SETUP OUR WORLD
            var createLaboratoryDTO = (CreateInstitutionDTO) makeDTO("createLaboratory.json", CreateInstitutionDTO.class);
            var laboratory = institutionService.createLaboratoryInstitution(createLaboratoryDTO);

            var createDoctorsOfficeDTO = (CreateInstitutionDTO) makeDTO("createDoctorsOffice.json", CreateInstitutionDTO.class);
            var doctorsOffice = institutionService.createDoctorInstitution(createDoctorsOfficeDTO);

            var createTestSiteDTO = (CreateInstitutionDTO) makeDTO("createTestSite.json", CreateInstitutionDTO.class);
            var testSite = institutionService.createTestSiteInstitution(createTestSiteDTO);

            var createClinicDTO = (CreateInstitutionDTO) makeDTO("createClinic.json", CreateInstitutionDTO.class);
            var clinic = institutionService.createClinicInstitution(createClinicDTO);


            var user = User.builder()
                    .userRole(UserRole.USER_ROLE_ADMIN)
                    .username("test_lab")
                    .institution(laboratory)
                    .password(encoder.encode("asdf"))
                    .build();
            userRepository.saveAndFlush(user);

            var testDoc = User.builder()
                    .username("test_doctor")
                    .institution(doctorsOffice)
                    .password(encoder.encode("asdf"))
                    .userRole(UserRole.USER_ROLE_ADMIN)
                    .build();
            userRepository.saveAndFlush(testDoc);
            // PERSON GETS SICK AND GOES TO THE DOCTOR
            // PERSON GETS REGISTERED
            var createPersonDTO = (CreatePatientDTO) makeDTO("createPerson.json", CreatePatientDTO.class);
            var person = patientService.addPatient(createPersonDTO);

            // THE DOCTOR CREATES AND SEND SAMPLE TO LAB
            // FIXME: 22.03.20 the naming of the API endpoint is off...
            eventService.createScheduledEvent(person, laboratory.getId(), doctorsOffice.getId());

            // LAB RECEIVES SAMPLE AND PROCESSES IT
            final String testId = "42";
            final String comment = "comment";
            var labTest = labTestService.createLabTest(person.getId(), laboratory.getId(), testId, comment);


            // LAB HAS RESULT AND SOTRES IT
            // FIXME: 22.03.20 report cannot be attached
            labTestService.updateTestStatus(testId, laboratory.getId(), "TEST_POSITIVE", comment, null);

            // HEALTH OFFICE WANTS TO SEE ALL DATA
            var allPatients = patientService.getAllPatients();

            // RKI WANTS SO SEE STATS FOR ZIP
            var patiensByZip = statsService.resultZipList(0, 9999999);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}