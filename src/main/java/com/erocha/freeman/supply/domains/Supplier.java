package com.erocha.freeman.supply.domains;

import com.erocha.freeman.commons.domains.Address;
import com.erocha.freeman.commons.utils.UUIDGenerator;
import com.erocha.freeman.hr.domains.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier {

    @Builder.Default
    protected String id = UUIDGenerator.generate();

    protected LocalDate dateRegister;

    protected String legalNumber;

    protected String legalName;

    protected String email;

    protected String phone;

    protected String website;

    protected String businessName;

    protected Address address;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Set<Employee> employees = new HashSet<>();

    private Contract contract;

    public void addEmployee(Employee employee) {
        if (isNull(this.employees))
            this.employees = new HashSet<>();
        this.employees.add(employee);
    }

}
