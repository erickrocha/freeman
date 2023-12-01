package com.erocha.freeman.hr.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPerson {

    private PersonGateway personGateway;

    @Autowired
    public GetPerson(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public Person execute(String id) {
        return personGateway.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

}
