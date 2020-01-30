package com.wt.blockchain.domain.gateway;

import com.wt.blockchain.domain.customer.Customer;
import com.wt.blockchain.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
