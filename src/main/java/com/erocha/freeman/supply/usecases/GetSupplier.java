package com.erocha.freeman.supply.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.supply.domains.Supplier;
import com.erocha.freeman.supply.gateways.SupplierGateway;
import com.erocha.freeman.supply.http.json.SupplierParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetSupplier {

    @Autowired
    private SupplierGateway gateway;

    public List<Supplier> execute(String legalName, String legalNumber) {
        Query query = new Query();
        Optional.ofNullable(legalNumber).ifPresent(param -> query.addCriteria(Criteria.where("legalNumber").is(legalNumber)));
        Optional.ofNullable(legalName)
                .ifPresent(param -> query.addCriteria(Criteria.where("legalName").is(legalName).orOperator(Criteria.where("businessName").is(legalName))));
        return gateway.query(query);
    }

    public Page<Supplier> execute(SupplierParams params, Pageable pageable) {
        Query query = new Query();
        Optional.ofNullable(params.getLegalNumber()).ifPresent(param -> query.addCriteria(Criteria.where("legalNumber").is(param)));
        Optional.ofNullable(params.getLegalName())
                .ifPresent(param -> query.addCriteria(Criteria.where("legalName").is(param).orOperator(Criteria.where("businessName").is(param))));
        return gateway.query(query, pageable);
    }

    public Supplier executeById(String id) {
        return gateway.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
}
