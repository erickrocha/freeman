package com.erocha.freeman.crm.gateways.impl;

import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.gateways.CustomerGateway;
import com.erocha.freeman.crm.gateways.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerGatewayImpl implements CustomerGateway {


    private CustomerRepository repository;

    private MongoTemplate mongoTemplate;

    public CustomerGatewayImpl(CustomerRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Customer persist(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        repository.delete(customer);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Page<Customer> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Customer> customers = mongoTemplate.find(query, Customer.class);
        return PageableExecutionUtils.getPage(customers, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Customer.class));
    }

    @Override
    public List<Customer> query(Query query) {
        return mongoTemplate.find(query, Customer.class);
    }
}
