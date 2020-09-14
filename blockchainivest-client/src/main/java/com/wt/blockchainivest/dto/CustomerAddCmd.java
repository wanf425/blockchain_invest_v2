package com.wt.blockchainivest.dto;

import com.alibaba.cola.dto.Command;
import com.wt.blockchainivest.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
