package com.erocha.freeman.security.usecases;

import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.usecases.UpdatePerson;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddUser {


    private UserGateway userGateway;

    private UpdatePerson updatePerson;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String defaultPassword;

    @Autowired
    public AddUser(UserGateway userGateway, UpdatePerson updatePerson, BCryptPasswordEncoder bCryptPasswordEncoder, @Value("${freeman.security.default-password}") String defaultPassword) {
        this.userGateway = userGateway;
        this.updatePerson = updatePerson;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.defaultPassword = defaultPassword;
    }

    public User execute(User user) {
        if (user.getPassword() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
        }else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setFirstLogin(true);
        user = userGateway.persist(user);
        updatePerson.execute(Person.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).profile(user.getProfile()).build());
        return user;
    }
}
