package com.erocha.freeman.supply.usecases;

import com.erocha.freeman.supply.domains.Supplier;
import com.erocha.freeman.supply.gateways.SupplierGateway;
import org.springframework.stereotype.Service;

@Service
public class AddSupplier {


    private SupplierGateway gateway;

    public AddSupplier(SupplierGateway gateway) {
        this.gateway = gateway;
    }

    public Supplier execute(Supplier supplier) {
        return gateway.persist(supplier);
    }
}
