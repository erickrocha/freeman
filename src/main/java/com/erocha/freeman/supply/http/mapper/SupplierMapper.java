package com.erocha.freeman.supply.http.mapper;

import com.erocha.freeman.commons.mapper.AddressMapper;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.hr.domains.Employee;
import com.erocha.freeman.supply.domains.Contract;
import com.erocha.freeman.supply.domains.Supplier;
import com.erocha.freeman.supply.http.json.ContractTO;
import com.erocha.freeman.supply.http.json.SupplierTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupplierMapper implements Mapper<Supplier, SupplierTO> {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public SupplierTO convertTransferObject(Supplier e) {
        SupplierTO.SupplierTOBuilder builder = SupplierTO.builder();
        builder.id(e.getId()).legalName(e.getLegalName()).businessName(e.getBusinessName()).legalNumber(e.getLegalNumber()).dateRegister(e.getDateRegister())
                .phone(e.getPhone()).website(e.getWebsite()).email(e.getEmail()).responsibleName(getResponsibleName(e)).active(e.isActive()).build();
        Optional.ofNullable(e.getAddress()).ifPresent(address -> builder.address(addressMapper.convertTransferObject(address)));
        Optional.ofNullable(e.getEmployees()).ifPresent(employees -> builder.employees(employeeMapper.convertTransferObject(employees)));
        return builder.build();
    }

    @Override
    public Supplier convertEntity(SupplierTO to) {
        Supplier.SupplierBuilder builder = Supplier.builder();
        builder.id(to.getId()).legalName(to.getLegalName()).legalNumber(to.getLegalNumber()).businessName(to.getBusinessName()).phone(to.getPhone())
                .email(to.getEmail()).build();
        Optional.ofNullable(to.getId()).ifPresentOrElse(active -> builder.active(to.isActive()), () -> builder.active(true));
        Optional.ofNullable(to.getDateRegister()).ifPresentOrElse(localDate -> builder.dateRegister(localDate), () -> builder.dateRegister(LocalDate.now()));
        Optional.ofNullable(to.getAddress()).ifPresent(address -> builder.address(addressMapper.convertEntity(address)));
        Optional.ofNullable(to.getEmployees()).ifPresent(employees -> builder.employees(employeeMapper.convertEntity(employees.stream().collect(Collectors.toSet()))));
        return builder.build();
    }

    private String getResponsibleName(Supplier e) {
        Optional<Employee> optionalEmployee = e.getEmployees().stream().filter(Employee::isResponsible).findFirst();
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get().getPerson().getName();
        }
        return "";
    }

    public Mapper<Contract, ContractTO> getContractMapper() {
        return new Mapper<>() {
            @Override
            public ContractTO convertTransferObject(Contract c) {
                return ContractTO.builder().dateRegister(c.getDateRegister()).endDate(c.getEndDate()).number(c.getNumber())
                        .valuePerHourInCents(c.getValuePerHourInCents()).build();
            }

            @Override
            public Contract convertEntity(ContractTO to) {
                return Contract.builder().dateRegister(to.getDateRegister()).endDate(to.getEndDate()).valuePerHourInCents(to.getValuePerHourInCents())
                        .number(to.getNumber()).build();
            }
        };
    }
}
