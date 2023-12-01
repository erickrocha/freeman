package com.erocha.freeman.supply.gateways.repository;

import java.io.Serializable;

import com.erocha.freeman.supply.domains.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, Serializable> {

	Supplier findByEmail(String email);
}
