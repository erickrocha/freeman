package com.erocha.freeman.contract.gateways.repository;

import com.erocha.freeman.contract.domains.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;

public interface ContractRepository extends MongoRepository<Contract, Serializable> {
}
