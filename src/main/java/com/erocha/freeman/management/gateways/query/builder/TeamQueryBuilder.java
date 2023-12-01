package com.erocha.freeman.management.gateways.query.builder;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class TeamQueryBuilder {

    private TeamQueryBuilder() {
    }

    public static Query queryByManagerId(String managerId) {
        return new Query().addCriteria(Criteria.where("manager.id").is(managerId));
    }
}
