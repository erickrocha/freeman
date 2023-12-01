package com.erocha.freeman.app.gateways.repository;

import com.erocha.freeman.app.domains.Menu;

import java.util.List;

public interface MenuRepository {

    List<Menu> findAll();
}
