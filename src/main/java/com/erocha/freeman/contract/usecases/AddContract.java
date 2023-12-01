package com.erocha.freeman.contract.usecases;

import com.erocha.freeman.contract.domains.Contract;
import com.erocha.freeman.contract.gateways.ContractGateway;
import org.springframework.stereotype.Service;

@Service
public class AddContract {


    private ContractGateway contractGateway;

    public AddContract(ContractGateway contractGateway) {
        this.contractGateway = contractGateway;
    }

    public Contract execute(Contract contract) {
        return contractGateway.persist(contract);
    }
}
