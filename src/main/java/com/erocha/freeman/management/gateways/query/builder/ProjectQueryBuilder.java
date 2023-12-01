package com.erocha.freeman.management.gateways.query.builder;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ProjectQueryBuilder {

	private ProjectQueryBuilder(){

	}

	public static Query queryByOwner(String ownerId) {
		Query query = new Query();
		Criteria requiredCriteria = Criteria.where("owner.id").is(ownerId);
		query.addCriteria(requiredCriteria);
		return query;
	}

	public static Query queryByProjectManager(String managerId) {
		Query query = new Query();
		Criteria requiredCriteria = Criteria.where("manager.id").is(managerId);
		query.addCriteria(requiredCriteria);
		return query;
	}

}
