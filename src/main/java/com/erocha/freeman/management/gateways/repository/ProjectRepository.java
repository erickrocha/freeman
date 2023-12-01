package com.erocha.freeman.management.gateways.repository;

import com.erocha.freeman.management.domains.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends MongoRepository<Project, Serializable> {

	Stream<Project> findAllByManagerId(String managerId);

	List<Project> findByNameStartingWith(String name);

	Optional<Project> findByName(String name);
}
