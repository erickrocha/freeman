package com.erocha.freeman.supply.http;

import com.erocha.freeman.supply.http.json.EmployeeTO;
import com.erocha.freeman.supply.http.json.SupplierParams;
import com.erocha.freeman.supply.http.json.SupplierTO;
import com.erocha.freeman.supply.http.mapper.EmployeeMapper;
import com.erocha.freeman.supply.http.mapper.SupplierMapper;
import com.erocha.freeman.supply.usecases.AddEmployee;
import com.erocha.freeman.supply.usecases.AddSupplier;
import com.erocha.freeman.supply.usecases.GetSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {


    private GetSupplier getSupplier;


    private AddSupplier addSupplier;


    private AddEmployee addEmployee;


    private SupplierMapper supplierMapper;


    private EmployeeMapper employeeMapper;

    public SupplyController(GetSupplier getSupplier, AddSupplier addSupplier, AddEmployee addEmployee,
                            SupplierMapper supplierMapper, EmployeeMapper employeeMapper) {
        this.getSupplier = getSupplier;
        this.addSupplier = addSupplier;
        this.addEmployee = addEmployee;
        this.supplierMapper = supplierMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/suppliers")
    public Page<SupplierTO> get(SupplierParams params, Pageable pageable) {
        return supplierMapper.convertPaginated(getSupplier.execute(params, pageable));
    }

    @GetMapping("/supplier/{id}")
    public SupplierTO getById(@PathVariable(name = "id") String id) {
        return supplierMapper.convertTransferObject(getSupplier.executeById(id));
    }

    @PostMapping(value = "/supplier", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SupplierTO persist(@RequestBody SupplierTO supplier) {
        return supplierMapper.convertTransferObject(addSupplier.execute(supplierMapper.convertEntity(supplier)));
    }

    @PostMapping(value = "/supplier/{id}/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeTO addEmployee(@PathVariable("id") String id, @RequestBody EmployeeTO employee, @RequestParam(name = "profile", required = false) String profile) {
        return employeeMapper.convertTransferObject(addEmployee.execute(id, employeeMapper.convertEntity(employee), profile));
    }
}
