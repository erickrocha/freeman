package com.erocha.freeman.security.gateways.impl;

import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.security.gateways.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {


    private final UserRepository repository;


    private final MongoTemplate mongoTemplate;


    public UserGatewayImpl(UserRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<User> findAllByProfile(Profile profile) {
        return repository.findAllByProfile(profile);
    }

    @Override
    public User persist(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> findAllById(String id) {
        return repository.findAllById(id);
    }

    @Override
    public List<User> query(Query query) {
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Page<User> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<User> users = mongoTemplate.find(query, User.class);
        return PageableExecutionUtils.getPage(users, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), User.class));
    }
}
