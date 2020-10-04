package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.ConstantsGateWayI;
import com.wt.blockchainivest.domain.trasaction.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:42
 */
@Component
public class ConstantsService  {

    @Autowired
    private ConstantsGateWayI constantsDao;

    public List<Constants> queryByType(String type) {
        return constantsDao.queryByType(type);
    }
}