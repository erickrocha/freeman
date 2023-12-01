package com.erocha.freeman.app.gateways.impl;

import com.erocha.freeman.app.domains.Menu;
import com.erocha.freeman.app.gateways.MenuGateway;
import com.erocha.freeman.app.gateways.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuGatewayImpl implements MenuGateway {

    private MenuRepository menuRepository;

    public MenuGatewayImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
