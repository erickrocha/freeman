package com.erocha.freeman.management.gateways.repository;

import java.io.Serializable;

import com.erocha.freeman.management.domains.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, Serializable> {
}
