package com.erocha.freeman.crm.gateways;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.crm.domains.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface CustomerGateway {

	Customer persist(Customer customer);

	void delete(Customer customer);

	void delete(String id);

	Optional<Customer> findById(String id);

	Page<Customer> query(Query query, Pageable pageable);

	List<Customer> query(Query query);

	List<Customer> findAll();
}
