package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.services.util.LikeOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Service
@RequiredArgsConstructor
public class PatientQueryService {

	private final LikeOperatorService likeOperatorService;
	@PersistenceContext
	private final EntityManager sessionFactory;

	public <T> TypedQuery<T> getQuery(String sql, PatientSearchParamsDTO patientSearchParamsDTO, Class<T> returnClazz) {
		String genderQueryString = "%";
		// Cant surround gender with "%" like the other attributes (%male% matches female)
		if (patientSearchParamsDTO.getGender() != null && !patientSearchParamsDTO.getGender().isBlank()) {
			genderQueryString = patientSearchParamsDTO.getGender();
		}
		final TypedQuery<T> query = this.sessionFactory.createQuery(sql, returnClazz);
		query.setParameter(1, likeOperatorService.like(patientSearchParamsDTO.getFirstName()));
		query.setParameter(2, likeOperatorService.like(patientSearchParamsDTO.getLastName()));
		query.setParameter(3, likeOperatorService.like(patientSearchParamsDTO.getId()));
		query.setParameter(4, genderQueryString);
		query.setParameter(5, likeOperatorService.like(patientSearchParamsDTO.getStreet()));
		query.setParameter(6, likeOperatorService.like(patientSearchParamsDTO.getHouseNumber()));
		query.setParameter(7, likeOperatorService.like(patientSearchParamsDTO.getZip()));
		query.setParameter(8, likeOperatorService.like(patientSearchParamsDTO.getCity()));
		query.setParameter(9, likeOperatorService.like(patientSearchParamsDTO.getInsuranceCompany()));
		int positionParameter = 10;
		if (patientSearchParamsDTO.getQuarantineStatus() != null && !patientSearchParamsDTO.getQuarantineStatus().isEmpty()) {
			for (EventType quarantineStatus : patientSearchParamsDTO.getQuarantineStatus()) {
				if (quarantineStatus != null) {
					query.setParameter(positionParameter++, quarantineStatus.name());
				}
			}
		}
		return query;
	}

	public String getQueryPatientsSql(PatientSearchParamsDTO patientSearchParamsDTO) {
		StringBuilder sql = new StringBuilder(
				"left join PatientEvent pe on pe.patient = pat.id " +
						"left join QuarantineIncident qi on qi.patient = pat " +
						"where lower(pat.firstName) like lower(?1) " +
						"AND lower(pat.lastName) like lower(?2) " +
						"AND lower(pat.id) like lower(?3) " +
						"AND lower(pat.gender) like lower(?4) " +
						"AND lower(pat.street) like lower(?5) " +
						"AND lower(pat.houseNumber) like lower(?6) " +
						"AND lower(pat.zip) like lower(?7) " +
						"AND lower(pat.city) like lower(?8) " +
						"AND lower(pat.insuranceCompany) like lower(?9) "
		);
		int positionSql = 10;
		if (patientSearchParamsDTO.getQuarantineStatus() != null && !patientSearchParamsDTO.getQuarantineStatus().isEmpty()) {
			sql.append("AND ( 1=0 ");
			for (EventType quarantineStatus : patientSearchParamsDTO.getQuarantineStatus()) {
				if (quarantineStatus != null) {
					sql.append("OR lower(qi.eventType) = lower(?").append(positionSql++).append(") ");
				} else {
					sql.append("OR qi.eventType is null ");
				}
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}
}
