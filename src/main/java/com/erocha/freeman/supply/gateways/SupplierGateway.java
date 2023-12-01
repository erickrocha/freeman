package com.erocha.freeman.supply.gateways;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.supply.domains.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface SupplierGateway {

	Supplier persist(Supplier supplier);

	void delete(String id);

	void delete(Supplier supplier);

	Supplier findByEmail(String email);

	Optional<Supplier> findById(String id);

	List<Supplier> findAll();

	List<Supplier> query(Query query);

	Page<Supplier> query(Query query, Pageable pageable);

	Page<Supplier> findAll(Pageable pageable);

}
