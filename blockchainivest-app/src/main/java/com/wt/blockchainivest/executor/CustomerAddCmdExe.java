package com.wt.blockchainivest.executor;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.wt.blockchainivest.dto.CustomerAddCmd;
import com.wt.blockchainivest.dto.domainmodel.ErrorCode;
import org.springframework.stereotype.Component;


@Component
public class CustomerAddCmdExe{

    public Response execute(CustomerAddCmd cmd) {
        //The flow of usecase is defined here.
        //The core ablility should be implemented in Domain. or sink to Domian gradually
        if(cmd.getCustomer().getCompanyName().equals("ConflictCompanyName")){
            throw new BizException(ErrorCode.B_CUSTOMER_companyNameConflict, "公司名冲突");
        }
        return Response.buildSuccess();
    }

}
