package com.wt.blockchain.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wt.blockchain.dto.CustomerAddCmd;
import com.wt.blockchain.dto.CustomerListByNameQry;
import com.wt.blockchain.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
