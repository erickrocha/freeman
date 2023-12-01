package com.erocha.freeman.crm.http;

import com.erocha.freeman.crm.http.json.CustomerParams;
import com.erocha.freeman.crm.http.json.CustomerTO;
import com.erocha.freeman.crm.http.mapper.CustomerMapper;
import com.erocha.freeman.crm.usecases.AddCustomer;
import com.erocha.freeman.crm.usecases.GetCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crm")
public class CrmController {


    private GetCustomer getCustomer;

    private AddCustomer addCustomer;

    private CustomerMapper customerMapper;

    public CrmController(GetCustomer getCustomer, AddCustomer addCustomer, CustomerMapper customerMapper) {
        this.getCustomer = getCustomer;
        this.addCustomer = addCustomer;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/customers")
    public Page<CustomerTO> get(CustomerParams params, Pageable page) {
        return customerMapper.convertPaginated(getCustomer.execute(params, page));
    }

    @GetMapping("/customer/{id}")
    public CustomerTO getbyId(@PathVariable(name = "id") String id) {
        return customerMapper.convertTransferObject(getCustomer.executeById(id));
    }

    @GetMapping("/customers/all")
    public List<CustomerTO> get(CustomerParams params) {
        return customerMapper.convertTransferObject(getCustomer.execute(params));
    }

    @PostMapping("/customer")
    public CustomerTO add(@RequestBody CustomerTO customer) {
        return customerMapper.convertTransferObject(addCustomer.execute(customerMapper.convertEntity(customer)));
    }
}
