package com.wt.blockchainivest.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.wt.blockchainivest.api.CustomerServiceI;
import com.wt.blockchainivest.dto.CustomerAddCmd;
import com.wt.blockchainivest.dto.CustomerListByNameQry;
import com.wt.blockchainivest.dto.domainmodel.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wt.blockchainivest.executor.CustomerAddCmdExe;
import com.wt.blockchainivest.executor.query.CustomerListByNameQryExe;

import javax.annotation.Resource;


@Service
public class CustomerServiceImpl implements CustomerServiceI {

    @Resource
    private CustomerAddCmdExe customerAddCmdExe;

    @Resource
    private CustomerListByNameQryExe customerListByNameQryExe;

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }

}
