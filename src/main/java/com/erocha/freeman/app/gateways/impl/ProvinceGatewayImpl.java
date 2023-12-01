package com.erocha.freeman.app.gateways.impl;


import com.erocha.freeman.app.gateways.ProvinceGateway;
import com.erocha.freeman.app.gateways.repository.ProvinceRepository;
import com.erocha.freeman.commons.domains.Province;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProvinceGatewayImpl implements ProvinceGateway {


    private ProvinceRepository repository;

    public ProvinceGatewayImpl(ProvinceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Province> findAll() {
        return repository.findAll();
    }

    @Override
    public Province findByAcronym(String acronym) {
        return repository.findByAcronym(acronym);
    }
}
