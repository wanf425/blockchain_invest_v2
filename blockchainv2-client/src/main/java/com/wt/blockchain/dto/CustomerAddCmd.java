package com.wt.blockchain.dto;

import com.alibaba.cola.dto.Command;
import com.wt.blockchain.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
