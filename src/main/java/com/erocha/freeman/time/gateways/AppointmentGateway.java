package com.erocha.freeman.time.gateways;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.erocha.freeman.time.domains.Appointment;

public interface AppointmentGateway {

	Appointment persist(Appointment appointment);

	Optional<Appointment> findById(String id);

	List<Appointment> findAll();

	Stream<Appointment> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);

	Stream<Appointment> findByProjectIdAndDateBetween(String projectId, LocalDate startDate, LocalDate endDate);

	List<Appointment> findAllByUserId(String userId);

	void delete(String id);

	Stream<Appointment> query(Query query);

	Page<Appointment> query(Query query, Pageable pageable);

	List<Appointment> saveAll(List<Appointment> appointments);

}
