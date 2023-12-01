package com.erocha.freeman.crm.usecases;

import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.gateways.CustomerGateway;
import org.springframework.stereotype.Component;

@Component
public class AddCustomer {


    private CustomerGateway gateway;

    public AddCustomer(CustomerGateway gateway) {
        this.gateway = gateway;
    }

    public Customer execute(Customer customer) {
        return gateway.persist(customer);
    }

}
