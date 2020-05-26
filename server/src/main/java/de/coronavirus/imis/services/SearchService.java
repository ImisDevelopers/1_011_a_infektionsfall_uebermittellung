package de.coronavirus.imis.services;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SearchService {
	
	@Autowired
	EntityManagerFactory emf;

	@PostConstruct
	public void triggerIndexing() {
		try {
			getFullTextEntityManager().createIndexer().startAndWait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method for searching with multiple selections all the parameters are used with 'and'
	 *
	 * @param dto parameters for the search
	 * @return list of entities that match the specified search
	 */

	public List<Patient> queryPatientsDetail(PatientSearchParamsDTO dto) {
		return queryPatientDetailExec(dto).getResultList();
	}

	public Long getResultSizePatientsDetail(PatientSearchParamsDTO dto) {
		return (long) queryPatientDetailExec(dto).getResultSize();
	}


	/**
	 * simple search with one textbox
	 *
	 * @param dto what you are searching
	 * @return list off all entities that match the search parameter in one field
	 */
	public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO dto) {
		return queryPatientsExec(dto.getQuery(), dto.getOrderBy(), dto.getPageSize().intValue(), dto.getOffsetPage().intValue()).getResultList();
	}

	public Long getResultSizePatientsSimple(String query) {
		return (long) queryPatientsExec(query, null, 10, 0).getResultSize();
	}

	private FullTextQuery queryPatientsExec(String queryString, String orderBy, int pageSize, int offset) {
		var queryBuilder = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Patient.class).get();
		Query query = null;
		if (Strings.isNullOrEmpty(queryString)) {
			query = queryBuilder.all().createQuery();
		} else {
			query = queryBuilder.simpleQueryString()
					.onField("lastName")
					.andField("firstName")
					.andField("city")
					.andField("phoneNumber")
					.matching(queryString)
					.createQuery();
		}
		final FullTextQuery fulltextQuery = getFullTextEntityManager().createFullTextQuery(query);
		if (!Strings.isNullOrEmpty(orderBy)) {
			var sort = queryBuilder.sort().byField(orderBy).createSort();
			fulltextQuery.setSort(sort);
		}
		return fulltextQuery.setMaxResults(pageSize).setFirstResult(pageSize * offset);
	}

	private FullTextEntityManager getFullTextEntityManager() {
		return Search.getFullTextEntityManager(emf.createEntityManager());
	}

	private FullTextQuery queryPatientDetailExec(PatientSearchParamsDTO dto) {
		var queryBuilder = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Patient.class).get().bool();
		Map<String, Object> objectProps = beanProperties(dto);
		var maxResults = dto.getPageSize().intValue();
		var firstResult = dto.getPageSize().intValue() * dto.getOffsetPage().intValue();
		if (objectProps.isEmpty()) {
			var query = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Patient.class).get().all().createQuery();
			return getFullTextEntityManager().createFullTextQuery(query).setMaxResults(maxResults).setFirstResult(firstResult);
		}
		objectProps.entrySet().stream()
				.map(this::buildSubQuery)
				.forEach(queryBuilder::must);
		final FullTextQuery fulltextQuery = getFullTextEntityManager().createFullTextQuery(queryBuilder.createQuery());
		if (!Strings.isNullOrEmpty(dto.getOrderBy())) {
			var sort = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Patient.class).get().sort()
					.byField(dto.getOrderBy())
					.createSort();
			fulltextQuery.setSort(sort);
		}
		return fulltextQuery.setMaxResults(maxResults).setFirstResult(firstResult);
	}


	private static Map<String, Object> beanProperties(Object bean) {
		try {
			Map<String, Object> map = new HashMap<>();
			var allowedTypes = List.of(String.class, Long.class);
			var blackList = List.of("pageSize", "orderBy", "order", "doctorId", "laboratoryId", "offsetPage");
			for (PropertyDescriptor pd : Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors()) {
				if (Objects.nonNull(pd.getReadMethod())) {
					try {
						Object value = pd.getReadMethod().invoke(bean);
						if (value != null && allowedTypes.contains(value.getClass()) && !blackList.contains(pd.getName())) {
							if (value instanceof String) {
								if (!Strings.isNullOrEmpty((String) value)) {
									map.put(pd.getName(), value);
								}
							} else {
								map.put(pd.getName(), value);
							}
						}
					} catch (Exception e) {
						log.error("error converting request :" + bean.toString());
					}
				}
			}
			return map;
		} catch (IntrospectionException e) {
			log.info("error introspecting bean {}", bean.toString(), e);
			return Collections.emptyMap();
		}
	}

	private Query buildSubQuery(Map.Entry<String, Object> entry) {
		QueryBuilder queryBuilder = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Patient.class).get();
		return queryBuilder.keyword().onFields(entry.getKey()).matching(entry.getValue()).createQuery();
	}

}
