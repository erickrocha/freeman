package com.erocha.freeman.supply.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.hr.domains.Employee;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.gateways.PersonGateway;
import com.erocha.freeman.security.domains.Profile;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.usecases.AddUser;
import com.erocha.freeman.supply.domains.Supplier;
import com.erocha.freeman.supply.gateways.SupplierGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddEmployee {


    private SupplierGateway gateway;

    private AddUser addUser;

    private PersonGateway personGateway;

    public AddEmployee(SupplierGateway gateway, AddUser addUser, PersonGateway personGateway) {
        this.gateway = gateway;
        this.addUser = addUser;
        this.personGateway = personGateway;
    }

    public Employee execute(String supplierId, Employee employee, String profile) {
        Supplier supplier = gateway.findById(supplierId).orElseThrow(ResourceNotFoundException::new);
        Optional<Person> possiblePerson = personGateway.findByEmail(employee.getPerson().getEmail());
        Profile selectedProfile = Optional.of(Profile.valueOf(profile)).orElse(Profile.DEVELOPER);
        if (possiblePerson.isPresent()) {
            Person alreadyExists = possiblePerson.get();
            alreadyExists.setProfile(selectedProfile);
            employee.setId(alreadyExists.getId());
            employee.getPerson().setId(alreadyExists.getId());
        }
        supplier.addEmployee(employee);
        gateway.persist(supplier);
        if (possiblePerson.isEmpty()) {
            Person person = employee.getPerson();
            person.setProfile(selectedProfile);
            User user = User.builder().id(person.getId()).name(person.getName()).email(person.getEmail()).profile(selectedProfile).build();
            addUser.execute(user);
        }
        return employee;
    }
}
