package com.erocha.freeman.management.gateways.impl;

import com.erocha.freeman.management.domains.Project;
import com.erocha.freeman.management.gateways.ProjectGateway;
import com.erocha.freeman.management.gateways.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class ProjectGatewayImpl implements ProjectGateway {


    private ProjectRepository repository;


    private MongoTemplate mongoTemplate;

    public ProjectGatewayImpl(ProjectRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Project persist(Project project) {
        return repository.save(project);
    }

    @Override
    public Optional<Project> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Project> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Project> findByNameStartingWith(String name) {
        return repository.findByNameStartingWith(name);
    }

    @Override
    public Page<Project> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Project> projects = mongoTemplate.find(query, Project.class);
        return PageableExecutionUtils.getPage(projects, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Project.class));
    }

    @Override
    public List<Project> query(Query query) {
        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public Stream<Project> findAllByManagerId(String managerId) {
        return repository.findAllByManagerId(managerId);
    }
}
