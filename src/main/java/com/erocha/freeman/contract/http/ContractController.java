package com.erocha.freeman.contract.http;

import com.erocha.freeman.contract.domains.Contract;
import com.erocha.freeman.contract.usecases.GetContract;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {


    private GetContract getContract;

    public ContractController(GetContract getContract) {
        this.getContract = getContract;
    }

    @GetMapping
    public List<Contract> get() {
        return getContract.execute();
    }
}
