package com.erocha.freeman.crm.http.mapper;

import com.erocha.freeman.commons.mapper.AddressMapper;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.crm.domains.Customer;
import com.erocha.freeman.crm.http.json.CustomerTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerTO> {

    private AddressMapper addressMapper;

    public CustomerMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public CustomerTO convertTransferObject(Customer customer) {
        CustomerTO to = CustomerTO.builder().id(customer.getId()).legalName(customer.getLegalName()).businessName(customer.getBusinessName())
                .dateRegister(customer.getDateRegister()).legalNumber(customer.getLegalNumber()).phone(customer.getPhone()).website(customer.getWebsite())
                .email(customer.getEmail()).build();
        Optional.ofNullable(customer.getAddress()).ifPresent(address -> to.setAddress(addressMapper.convertTransferObject(address)));
        return to;
    }

    @Override
    public Customer convertEntity(CustomerTO to) {
        Customer customer = Customer.builder().id(to.getId()).legalName(to.getLegalName()).legalNumber(to.getLegalNumber()).businessName(to.getBusinessName())
                .phone(to.getPhone()).email(to.getEmail()).build();
        Optional.ofNullable(to.getDateRegister())
                .ifPresentOrElse(customer::setDateRegister, () -> customer.setDateRegister(LocalDate.now()));
        Optional.ofNullable(to.getAddress()).ifPresent(address -> customer.setAddress(addressMapper.convertEntity(address)));
        return customer;
    }

}
