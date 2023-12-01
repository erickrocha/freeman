package com.erocha.freeman.app.gateways;

import com.erocha.freeman.app.domains.Menu;

import java.util.List;

public interface MenuGateway {

    List<Menu> findAll();
}
