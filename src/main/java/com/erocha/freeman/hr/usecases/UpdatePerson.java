package com.erocha.freeman.hr.usecases;

import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePerson {

    private PersonGateway personGateway;

    @Autowired
    public UpdatePerson(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public Person execute(Person person) {
        return personGateway.persist(person);
    }
}
