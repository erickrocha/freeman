package com.erocha.freeman.security.gateways.repository;

import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Serializable> {

    Optional<User> findByEmail(String email);

    List<User> findAllByProfile(Profile profile);

    List<User> findAllById(String id);
}
