package com.erocha.freeman.security.usecases

import com.erocha.freeman.hr.domains.Person
import com.erocha.freeman.hr.gateways.PersonGateway
import com.erocha.freeman.hr.usecases.UpdatePerson
import com.erocha.freeman.security.domains.User
import com.erocha.freeman.security.gateways.UserGateway
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class AddUserSpec extends Specification {

    private UserGateway userGateway = Mock(UserGateway.class)

    private PersonGateway personGateway = Mock(PersonGateway.class)

    private UpdatePerson updatePerson = new UpdatePerson(personGateway)

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private AddUser addUser = new AddUser(userGateway, updatePerson, bCryptPasswordEncoder,"2323233")

    def "Add an user "() {
        given: "I have a user"
        userGateway.persist(_ as User) >> {
            return it[0]
        }
        personGateway.findById(_ as String) >> {
            return Optional.of(Person.builder().name("Admin").email("admin@freeman.com.br").build())
        }
        personGateway.persist(_ as Person) >> {
            return it[0]
        }
        when: "I call a execute method on adduser useCase"
        User user = User.builder().email("admin@freeman.com.br").password("162364").name("Admin").build()
        User response = addUser.execute(user)
        then: "An user should be created with an id"
        assert response.id != null
    }
}
