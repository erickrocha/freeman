package com.erocha.freeman.hr.gateways;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.hr.domains.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface PersonGateway {

	Person persist(Person person);

	void delete(String id);

	void delete(Person person);

	Optional<Person> findById(String id);

	Page<Person> query(Query query,Pageable pageable);

	List<Person> findAll();

	List<Person> findAllWithoutSysAdmin();

	Optional<Person> findByEmail(String email);

}
