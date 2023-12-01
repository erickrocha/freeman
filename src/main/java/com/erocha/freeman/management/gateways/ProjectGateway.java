package com.erocha.freeman.management.gateways;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.erocha.freeman.management.domains.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface ProjectGateway {

	Project persist(Project project);

	Optional<Project> findById(String id);

	Optional<Project> findByName(String id);

	List<Project> findAll();

	Page<Project> query(Query query, Pageable pageable);

	List<Project> query(Query query);

	Stream<Project> findAllByManagerId(String managerId);

	List<Project> findByNameStartingWith(String name);

}
