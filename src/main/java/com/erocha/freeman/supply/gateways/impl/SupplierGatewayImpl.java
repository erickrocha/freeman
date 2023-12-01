package com.erocha.freeman.supply.gateways.impl;

import com.erocha.freeman.supply.domains.Supplier;
import com.erocha.freeman.supply.gateways.SupplierGateway;
import com.erocha.freeman.supply.gateways.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SupplierGatewayImpl implements SupplierGateway {

    private SupplierRepository repository;

    private MongoTemplate mongoTemplate;

    public SupplierGatewayImpl(SupplierRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Supplier persist(Supplier supplier) {
        return repository.save(supplier);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Supplier supplier) {
        repository.delete(supplier);
    }

    @Override
    public Supplier findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Supplier> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Supplier> query(Query query) {
        return mongoTemplate.find(query, Supplier.class);
    }

    @Override
    public Page<Supplier> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Supplier> supplierList = mongoTemplate.find(query, Supplier.class);
        return PageableExecutionUtils.getPage(supplierList, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Supplier.class));
    }

    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
