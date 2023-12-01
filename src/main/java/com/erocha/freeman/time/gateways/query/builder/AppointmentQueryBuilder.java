package com.erocha.freeman.time.gateways.query.builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.erocha.freeman.time.domains.Status;

public class AppointmentQueryBuilder {
	
	private static final String PROJECT_ID_FIELD = "project.id";

	private AppointmentQueryBuilder() {
	}

	public static Query queryByProjectAndDateBetweenAndStatus(String projectId, LocalDate startDate, LocalDate endDate, Status status) {
		Query query = new Query();
		Criteria projectCriteria = Criteria.where(PROJECT_ID_FIELD).is(projectId);
		Criteria dateCriteria = Criteria.where("date").lte(endDate).gte(startDate);
		Criteria statusCriteria = Criteria.where("status").is(status.name());
		query.addCriteria(projectCriteria);
		query.addCriteria(dateCriteria);
		query.addCriteria(statusCriteria);
		return query;
	}

	public static Query queryByProjectAndDateBetween(String projectId, LocalDate startDate, LocalDate endDate) {
		Query query = new Query();
		Criteria projectCriteria = Criteria.where(PROJECT_ID_FIELD).is(projectId);
		Criteria dateCriteria = Criteria.where("date").lte(endDate).gte(startDate);
		query.addCriteria(projectCriteria);
		query.addCriteria(dateCriteria);
		return query;
	}

	public static Query queryByParams(LocalDate startDate, LocalDate endDate, String pUserId, String pProjectId, String pPhase, Status pStatus) {
		Query query = new Query(Criteria.where("date").lte(endDate).gte(startDate));
		Optional.ofNullable(pUserId).ifPresent(userId -> query.addCriteria(Criteria.where("userId").is(userId)));
		Optional.ofNullable(pProjectId).ifPresent(project -> query.addCriteria(Criteria.where(PROJECT_ID_FIELD).is(project)));
		Optional.ofNullable(pStatus).ifPresent(status -> query.addCriteria(Criteria.where("status").is(status)));
		Optional.ofNullable(pPhase).ifPresent(phase -> query.addCriteria(Criteria.where("project.phases").is(phase)));
		return query;
	}

	public static Query queryByUsersDateBetween(List<String> membersId, LocalDate startDate, LocalDate endDate) {
		Query query = new Query();
		Criteria requiredCriteria = Criteria.where("userId").in(membersId);
		query.addCriteria(requiredCriteria);
		Criteria criteria = Criteria.where("date").lte(endDate).gte(startDate);
		query.addCriteria(criteria);
		return query;
	}
}
