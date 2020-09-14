package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
