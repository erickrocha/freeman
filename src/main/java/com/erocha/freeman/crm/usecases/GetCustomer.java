package com.erocha.freeman.crm.usecases;

import java.util.List;
import java.util.Optional;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.gateways.CustomerGateway;
import com.erocha.freeman.crm.http.json.CustomerParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class GetCustomer {


	private CustomerGateway gateway;

	public GetCustomer(CustomerGateway gateway) {
		this.gateway = gateway;
	}

	public Customer executeById(String id) {
		return gateway.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	public List<Customer> execute(CustomerParams params) {
		Query query = buildQuery(params);
		return gateway.query(query);
	}

	private Query buildQuery(CustomerParams params) {
		Query query = new Query();
		Optional.ofNullable(params.getLegalNumber()).ifPresent(param -> query.addCriteria(Criteria.where("legalNumber").is(param)));
		Optional.ofNullable(params.getLegalName())
				.ifPresent(param -> query.addCriteria(Criteria.where("legalName").is(param).orOperator(Criteria.where("businessName").is(param))));
		return query;
	}

	public Page<Customer> execute(CustomerParams params, Pageable pageable) {
		return gateway.query(buildQuery(params), pageable);
	}
}
