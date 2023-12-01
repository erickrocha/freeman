package com.erocha.freeman.contract.usecases;

import com.erocha.freeman.contract.domains.Contract;
import com.erocha.freeman.contract.gateways.ContractGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetContract {

    private ContractGateway gateway;

    public GetContract(ContractGateway gateway) {
        this.gateway = gateway;
    }

    public List<Contract> execute() {
        return gateway.findAll();
    }
}
