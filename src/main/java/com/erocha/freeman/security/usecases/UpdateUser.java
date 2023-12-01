package com.erocha.freeman.security.usecases;

import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser {

    private UserGateway userGateway;

    public UpdateUser(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User pUser) {
        return userGateway.persist(pUser);
    }
}
