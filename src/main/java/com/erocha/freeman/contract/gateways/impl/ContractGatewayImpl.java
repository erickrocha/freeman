package com.erocha.freeman.contract.gateways.impl;

import com.erocha.freeman.contract.domains.Contract;
import com.erocha.freeman.contract.gateways.ContractGateway;
import com.erocha.freeman.contract.gateways.repository.ContractRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractGatewayImpl implements ContractGateway {


    private ContractRepository repository;

    public ContractGatewayImpl(ContractRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contract persist(Contract contract) {
        return null;
    }

    @Override
    public List<Contract> findAll() {
        return repository.findAll();
    }
}
