package com.erocha.freeman.time.gateways.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.erocha.freeman.time.domains.Appointment;

public interface AppointmentRepository extends MongoRepository<Appointment, Serializable> {

	Stream<Appointment> findAllByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);

	Stream<Appointment> findAllByProjectIdAndDateBetween(String projectId, LocalDate startDate, LocalDate endDate);

	List<Appointment> findAllByUserId(String userId);
}
