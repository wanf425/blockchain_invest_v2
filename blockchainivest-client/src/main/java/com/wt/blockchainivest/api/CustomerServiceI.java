package com.wt.blockchainivest.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wt.blockchainivest.dto.CustomerAddCmd;
import com.wt.blockchainivest.dto.CustomerListByNameQry;
import com.wt.blockchainivest.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
