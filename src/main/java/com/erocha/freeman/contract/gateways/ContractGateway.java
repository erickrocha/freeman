package com.erocha.freeman.contract.gateways;

import com.erocha.freeman.contract.domains.Contract;

import java.util.List;

public interface ContractGateway {

    Contract persist(Contract contract);

    List<Contract> findAll();

}
