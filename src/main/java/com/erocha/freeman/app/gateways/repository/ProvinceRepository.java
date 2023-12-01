package com.erocha.freeman.app.gateways.repository;

import com.erocha.freeman.commons.domains.Province;

import java.util.List;

public interface ProvinceRepository {

    List<Province> findAll();

    Province findByAcronym(String acronym);
}
