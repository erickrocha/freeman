package com.erocha.freeman.hr.gateways.impl;

import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.hr.gateways.repository.PersonRepository;
import com.erocha.freeman.security.domains.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonGatewayImpl implements PersonGateway {


    private PersonRepository repository;

    private MongoTemplate mongoTemplate;

    public PersonGatewayImpl(PersonRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Person persist(Person person) {
        return repository.save(person);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Person person) {
        repository.delete(person);
    }

    @Override
    public Optional<Person> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Page<Person> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Person> persons = mongoTemplate.find(query, Person.class);
        return PageableExecutionUtils.getPage(persons, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Person.class));
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Person> findAllWithoutSysAdmin() {
        return repository.findAll().stream().filter(person -> !person.getProfile().equals(Profile.SYSADMIN)).toList();
    }
}
