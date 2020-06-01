package de.coronavirus.imis;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserRepository;
import de.coronavirus.imis.config.domain.UserRole;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.domain.TestType;
import de.coronavirus.imis.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;


@Component
@Profile("!swagger-export")
@Slf4j
@RequiredArgsConstructor
public class TestDataLoader implements ApplicationRunner {
	private final Environment env;

	private final PatientService patientService;
	private final InstitutionService institutionService;
	private final LabTestService labTestService;
	private final PatientEventService eventService;
	private final StatsService statsService;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	private static final ObjectMapper mapper = new ObjectMapper();

	static <T> T makeDTO(InputStream dataInputStream, Class<T> clazz)
			throws IOException {

		return mapper.readValue(dataInputStream, clazz);
	}
	static <T> T makeDTO(String testFileName, Class<T> clazz)
			throws IOException {

		return makeDTO(getResourceStream(testFileName), clazz);
	}
	static <T> T makeDTO(Resource testResource, Class<T> clazz)
			throws IOException {

		return makeDTO(testResource.getInputStream(), clazz);
	}

	static BufferedInputStream getResourceStream(String resourcePath) throws IOException {
		final String fullResourcePath = "sample_data" + File.separator + resourcePath;
		final InputStream rStream = TestDataLoader.class.getClassLoader().getResourceAsStream(fullResourcePath);
		if (rStream == null) {
			throw new IOException("Ressource " + resourcePath + " nicht vorhanden.");
		} else {
			return new BufferedInputStream(rStream);
		}
	}

	public void loadPatientTestData(ResourcePatternResolver resourceResolver) {
		try {
			log.info("Inserting patients");
			for (Resource patientTestDataResource : resourceResolver.getResources("classpath:sample_data/persons/person*.json")) {
				var createPersonDTO = makeDTO(patientTestDataResource, CreatePatientDTO.class);
				patientService.addPatient(createPersonDTO, true);
			}
		} catch (Exception e) {
			log.error("Exception occured during population with test patients:");
			e.printStackTrace();
		}
	}

	public void run(ApplicationArguments args) {
		log.info("Creating test data");
		try {

			ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());

			// SETUP OUR WORLD
			log.info("Inserting laboratory");
			var createLaboratoryDTO = (CreateInstitutionDTO) makeDTO("createLaboratory.json", CreateInstitutionDTO.class);
			var laboratory = institutionService.addInstitution(createLaboratoryDTO);

			log.info("Inserting doctor");
			var createDoctorsOfficeDTO = (CreateInstitutionDTO) makeDTO("createDoctorsOffice.json", CreateInstitutionDTO.class);
			var doctorsOffice = institutionService.addInstitution(createDoctorsOfficeDTO);

			log.info("Inserting test site");
			var createTestSiteDTO = (CreateInstitutionDTO) makeDTO("createTestSite.json", CreateInstitutionDTO.class);
			var testSite = institutionService.addInstitution(createTestSiteDTO);

			log.info("Inserting clinic");
			var createClinicDTO = (CreateInstitutionDTO) makeDTO("createClinic.json", CreateInstitutionDTO.class);
			var clinic = institutionService.addInstitution(createClinicDTO);

			log.info("Inserting department of health");
			var createDepartmentOfHealthDTO = (CreateInstitutionDTO) makeDTO("createDepartmentOfHealth.json", CreateInstitutionDTO.class);
			var departmentOfHealth = institutionService.addInstitution(createDepartmentOfHealthDTO);

			var user = User.builder()
					.userRole(UserRole.USER_ROLE_ADMIN)
					.username("test_lab")
					.firstName("Max")
					.lastName("Mustermann")
					.institution(laboratory)
					.password(encoder.encode("asdf"))
					.build();
			userRepository.saveAndFlush(user);

			var testDoc = User.builder()
					.username("test_doctor")
					.firstName("Sabine")
					.lastName("Musterfrau")
					.institution(doctorsOffice)
					.password(encoder.encode("asdf"))
					.userRole(UserRole.USER_ROLE_ADMIN)
					.build();
			userRepository.saveAndFlush(testDoc);
			var testTestingSiteUser = User.builder()
					.username("test_testing_site")
					.institution(testSite)
					.firstName("Norbert")
					.lastName("Mustermann")
					.password(encoder.encode("asdf"))
					.userRole(UserRole.USER_ROLE_ADMIN)
					.build();
			userRepository.saveAndFlush(testTestingSiteUser);
			var departmentOfHealthUser = User.builder()
					.username("test_department_of_health")
					.institution(departmentOfHealth)
					.firstName("Hildegard")
					.lastName("Musterfrau")
					.password(encoder.encode("asdf"))
					.userRole(UserRole.USER_ROLE_ADMIN)
					.build();
			userRepository.saveAndFlush(departmentOfHealthUser);

			// PATIENT TEST DATA
			loadPatientTestData(resourceResolver);

			// PERSON GETS SICK AND GOES TO THE DOCTOR
			// PERSON GETS REGISTERED
			var createPersonDTO = (CreatePatientDTO) makeDTO("createPerson.json", CreatePatientDTO.class);
			var person = patientService.addPatient(createPersonDTO, true);

			// THE DOCTOR CREATES AND SEND SAMPLE TO LAB
			// FIXME: 22.03.20 the naming of the API endpoint is off...
			eventService.createScheduledEvent(person, laboratory.getId(), doctorsOffice.getId());

			// LAB RECEIVES SAMPLE AND PROCESSES IT
			final String testId = "42EF42";
			final String comment = "comment";
			var labTest = LabTest.builder()
					.laboratory((Laboratory) laboratory)
					.testId(testId)
					.comment(comment)
					.testType(TestType.PCR)
					.build();
			labTest = labTestService.createLabTest(person, labTest);


			// LAB HAS RESULT AND SOTRES IT
			// FIXME: 22.03.20 report cannot be attached
			var updateTestStatus = UpdateTestStatusDTO.builder()
					.status(TestStatus.TEST_POSITIVE)
					.comment(comment)
					.testId(testId)
					.build();
			labTestService.updateTestStatus(laboratory.getId(), updateTestStatus);

			// HEALTH OFFICE WANTS TO SEE ALL DATA
			var allPatients = patientService.getAllPatients();

			// RKI WANTS SO SEE STATS FOR ZIP
			var patiensByZip = statsService.resultZipList("0", "9999999");


		} catch (IOException e) {
			log.error("Error creating test data", e);
		}
		log.info("Finished creating test data");
	}
}
