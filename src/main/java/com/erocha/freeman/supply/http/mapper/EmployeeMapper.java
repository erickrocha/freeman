package com.erocha.freeman.supply.http.mapper;

import com.erocha.freeman.commons.mapper.AddressMapper;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.hr.domains.Employee;
import com.erocha.freeman.hr.domains.Person;
import com.erocha.freeman.supply.http.json.EmployeeTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class EmployeeMapper implements Mapper<Employee, EmployeeTO> {


    private AddressMapper addressMapper;

    public EmployeeMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public EmployeeTO convertTransferObject(Employee employee) {
        EmployeeTO.EmployeeTOBuilder builder = EmployeeTO.builder();
        builder.id(employee.getId()).responsible(employee.isResponsible());
        Optional.ofNullable(employee.getPerson())
                .ifPresent(
                        person -> {
                            builder
                                    .name(person.getName())
                                    .socialSecurity(person.getSocialSecurity())
                                    .registerDate(person.getRegisterDate());
                            builder.address(addressMapper.convertTransferObject(person.getAddress()));
                            builder.email(person.getEmail()).phone(person.getPhone());
                        });
        return builder.build();
    }

    @Override
    public Employee convertEntity(EmployeeTO to) {
        Employee.EmployeeBuilder builder = Employee.builder();
        builder.id(to.getId()).responsible(to.isResponsible());
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder
                .id(to.getId())
                .name(to.getName())
                .socialSecurity(to.getSocialSecurity())
                .birthDate(to.getBirthDate())
                .email(to.getEmail())
                .phone(to.getPhone());
        personBuilder.address(addressMapper.convertEntity(to.getAddress()));
        builder.person(personBuilder.build());
        return builder.build();
    }

    @Override
    public List<EmployeeTO> convertTransferObject(Stream<Employee> e) {
        return e.filter(applyEntityFilter())
                .sorted(applySort())
                .map(this::convertTransferObject)
                .toList();
    }

    public Comparator<Employee> applySort() {
        return (o1, o2) -> Boolean.compare(o1.isResponsible(), o2.isResponsible());
    }
}
