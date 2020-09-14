package com.wt.blockchainivest.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.wt.blockchainivest.dto.CustomerListByNameQry;
import com.wt.blockchainivest.dto.domainmodel.Customer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class CustomerListByNameQryExe{

    public MultiResponse<Customer> execute(CustomerListByNameQry cmd) {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerName("Frank");
        customerList.add(customer);
        return MultiResponse.ofWithoutTotal(customerList);
    }
}
