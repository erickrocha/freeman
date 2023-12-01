package com.erocha.freeman.hr.gateways.repository;

import java.io.Serializable;
import java.util.Optional;

import com.erocha.freeman.hr.domains.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Serializable> {

	Optional<Person> findByEmail(String email);
}
