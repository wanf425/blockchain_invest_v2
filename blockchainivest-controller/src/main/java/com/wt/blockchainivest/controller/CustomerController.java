package com.wt.blockchainivest.controller;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wt.blockchainivest.api.CustomerServiceI;
import com.wt.blockchainivest.dto.CustomerAddCmd;
import com.wt.blockchainivest.dto.CustomerListByNameQry;
import com.wt.blockchainivest.dto.domainmodel.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerServiceI customerService;

    @GetMapping(value = "/customer")
    public MultiResponse<Customer> listCustomerByName(@RequestParam String name){
        CustomerListByNameQry customerListByNameQry = new CustomerListByNameQry();
        customerListByNameQry.setName(name);
        return customerService.listByName(customerListByNameQry);
    }

    @PostMapping(value = "/customer")
    public Response addCustomer(@RequestBody CustomerAddCmd customerAddCmd){
        return customerService.addCustomer(customerAddCmd);
    }
}
