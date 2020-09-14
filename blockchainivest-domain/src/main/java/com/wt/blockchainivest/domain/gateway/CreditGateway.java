package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.customer.Customer;
import com.wt.blockchainivest.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
