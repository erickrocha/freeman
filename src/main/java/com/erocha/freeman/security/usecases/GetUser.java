package com.erocha.freeman.security.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import com.erocha.freeman.security.http.json.UserParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetUser {


    private UserGateway userGateway;


    public GetUser(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User executeById(String id) {
        return userGateway.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Optional<User> execute(String email) {
        return userGateway.findByEmail(email);
    }

    public List<User> execute() {
        return userGateway.findAll();
    }

    public Page<User> execute(UserParams params, Pageable pageable) {
        Query query = new Query();
        Optional.ofNullable(params.getName()).ifPresent(param -> query.addCriteria(Criteria.where("name").is(param)));
        Optional.ofNullable(params.getEmail()).ifPresent(param -> query.addCriteria(Criteria.where("email").is(param)));
        Optional.ofNullable(params.getCompany()).ifPresent(param -> query.addCriteria(Criteria.where("company").is(param)));
        Optional.ofNullable(params.getProfile()).ifPresent(param -> query.addCriteria(Criteria.where("profile").is(Profile.valueOf(param))));
        return userGateway.query(query, pageable);
    }
}
