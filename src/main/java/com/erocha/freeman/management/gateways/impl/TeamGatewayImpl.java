package com.erocha.freeman.management.gateways.impl;

import com.erocha.freeman.management.domains.Team;
import com.erocha.freeman.management.gateways.TeamGateway;
import com.erocha.freeman.management.gateways.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamGatewayImpl implements TeamGateway {

    private TeamRepository repository;

    private MongoTemplate mongoTemplate;

    public TeamGatewayImpl(TeamRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Team persist(Team team) {
        return repository.save(team);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Team team) {
        repository.delete(team);
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Team> query(Query query) {
        return mongoTemplate.find(query, Team.class);
    }

    @Override
    public Page<Team> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Team> teams = mongoTemplate.find(query, Team.class);
        return PageableExecutionUtils.getPage(teams, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Team.class));
    }
}
