package com.wt.blockchain.domain.gateway;

import com.wt.blockchain.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
