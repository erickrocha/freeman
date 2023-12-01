package com.erocha.freeman.supply.usecases

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException
import com.erocha.freeman.commons.utils.UUIDGenerator
import com.erocha.freeman.hr.domains.Employee
import com.erocha.freeman.hr.domains.Person
import com.erocha.freeman.hr.gateways.PersonGateway
import com.erocha.freeman.hr.usecases.UpdatePerson
import com.erocha.freeman.security.domains.User
import com.erocha.freeman.security.gateways.UserGateway
import com.erocha.freeman.security.usecases.AddUser
import com.erocha.freeman.supply.domains.Supplier

import com.erocha.freeman.supply.gateways.SupplierGateway
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class AddEmployeeSpec extends Specification {

    PersonGateway personGateway = Mock(PersonGateway.class);
    UpdatePerson updatePerson = new UpdatePerson(personGateway);

    UserGateway userGateway = Mock(UserGateway.class)
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    AddUser addUser = new AddUser(userGateway, updatePerson, bCryptPasswordEncoder,"121212121")
    SupplierGateway supplierGateway = Mock(SupplierGateway.class)

    AddEmployee addEmployee = new AddEmployee(supplierGateway,addUser,personGateway)

    def "add employee and already exist a person with same email"() {
        given: "I have a employee"

        String supplierId = UUIDGenerator.generate()
        supplierGateway.findById(_ as String) >> {
            return Optional.of(Supplier.builder().businessName("Supplier").legalNumber("87.609.221/0001-40").employees(new HashSet<Employee>()).active(true).build())
        }
        personGateway.findByEmail(_ as String) >> {
            return Optional.of(createPerson())
        }
        personGateway.persist(_ as Person) >> {
            return it;
        }
        when: "i call execute method in add employee"
        Employee expected = addEmployee.execute(supplierId, createEmployee(), "DEVELOPER")
        then:
        assert expected != null
        assert expected.getPerson() != null
    }

    def "add employee and nom already exist a person with same email"() {
        given: "I have a employee"
        String supplierId = UUIDGenerator.generate()
        supplierGateway.findById(_ as String) >> {
            return Optional.of(Supplier.builder().businessName("Supplier").legalNumber("87.609.221/0001-40").employees(new HashSet<Employee>()).active(true).build())
        }
        personGateway.findByEmail(_ as String) >> {
            return Optional.empty()
        }
        userGateway.persist(_ as User) >> {
            return User.builder().email("joaozinho.terrivel@mail.com").enabled(true).build()
        }
        when: "i call execute method in add employee"
        Employee expected = addEmployee.execute(supplierId, createEmployee(), "DEVELOPER")
        then:
        assert expected != null
        assert expected.getPerson() != null
    }

    def "add employee and supplir don't exist"() {
        given: "I have a employee"
        String supplierId = UUIDGenerator.generate()
        supplierGateway.findById(_ as String) >> {
            return Optional.empty()
        }
        when: "I call execute method in add employee"
        addEmployee.execute(supplierId, createEmployee(),"DEVELOPER")
        then:
        thrown(ResourceNotFoundException)
    }


    Employee createEmployee() {
        return new Employee(person: createPerson(), responsible: false)
    }

    Person createPerson() {
        return new Person(name: "Joazinho Terrivel", email: "joaozinho.terrivel@mail.com")
    }
}
