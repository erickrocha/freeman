package com.erocha.freeman.app.gateways;

import com.erocha.freeman.commons.domains.Province;

import java.util.List;

public interface ProvinceGateway {

    List<Province> findAll();

    Province findByAcronym(String acronym);
}
