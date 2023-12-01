package com.erocha.freeman.management.gateways;

import java.util.List;

import com.erocha.freeman.management.domains.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface TeamGateway {

	Team persist(Team team);

	void delete(String id);

	void delete(Team team);

	List<Team> findAll();

	List<Team> query(Query query);

	Page<Team> query(Query query, Pageable pageable);
}
