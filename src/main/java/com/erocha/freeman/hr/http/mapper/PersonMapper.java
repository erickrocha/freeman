package com.erocha.freeman.hr.http.mapper;

import com.erocha.freeman.commons.mapper.AddressMapper;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.commons.utils.ImageBase64Handler;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.hr.http.json.PersonTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonMapper implements Mapper<Person, PersonTO> {

    private AddressMapper addressMapper;

    public PersonMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public PersonTO convertTransferObject(Person person) {
        PersonTO to = PersonTO.builder().id(person.getId()).socialSecurity(person.getSocialSecurity()).name(person.getName()).birthDate(person.getBirthDate())
                .email(person.getEmail()).phone(person.getPhone()).registerDate(person.getRegisterDate()).build();
        Optional.ofNullable(person.getAddress()).ifPresent(fieldValue -> to.setAddress(addressMapper.convertTransferObject(person.getAddress())));
        Optional.ofNullable(person.getAvatar()).ifPresent(fieldValur -> to.setAvatarBase64(ImageBase64Handler.fromByteArrayToString(person.getAvatar())));
        return to;
    }

    @Override
    public Person convertEntity(PersonTO to) {
        Person person = Person.builder().id(to.getId()).socialSecurity(to.getSocialSecurity()).name(to.getName()).birthDate(to.getBirthDate())
                .email(to.getEmail()).phone(to.getPhone()).registerDate(to.getRegisterDate()).build();
        Optional.ofNullable(to.getAddress()).ifPresent(fieldValue -> person.setAddress(addressMapper.convertEntity(to.getAddress())));
        Optional.ofNullable(to.getAvatarBase64()).ifPresent(fieldValue -> person.setAvatar(ImageBase64Handler.fromStringToByteArray(to.getAvatarBase64())));
        return person;
    }
}
