package com.erocha.freeman.time.gateways.impl;

import com.erocha.freeman.time.domains.Appointment;
import com.erocha.freeman.time.gateways.AppointmentGateway;
import com.erocha.freeman.time.gateways.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class AppointmentGatewayImpl implements AppointmentGateway {


    private AppointmentRepository repository;

    private MongoTemplate mongoTemplate;

    public AppointmentGatewayImpl(AppointmentRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Appointment persist(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    @Override
    public Stream<Appointment> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate) {
        return repository.findAllByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Override
    public Stream<Appointment> findByProjectIdAndDateBetween(String projectId, LocalDate startDate, LocalDate endDate) {
        return repository.findAllByProjectIdAndDateBetween(projectId, startDate, endDate);
    }

    @Override
    public List<Appointment> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Stream<Appointment> query(Query query) {
        return mongoTemplate.find(query, Appointment.class).stream();
    }

    @Override
    public Page<Appointment> query(Query query, Pageable pageable) {
        query.with(pageable);
        List<Appointment> appointments = mongoTemplate.find(query, Appointment.class);
        return PageableExecutionUtils.getPage(appointments, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Appointment.class));
    }

    @Override
    public List<Appointment> saveAll(List<Appointment> appointments) {
        return repository.saveAll(appointments);
    }
}
