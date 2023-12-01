package com.erocha.freeman.crm.gateways.repository;

import java.io.Serializable;

import com.erocha.freeman.crm.domains.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Serializable> {
	
}
