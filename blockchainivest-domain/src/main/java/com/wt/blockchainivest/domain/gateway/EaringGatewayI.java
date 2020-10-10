package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.Earning;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:43
 */
public interface EaringGatewayI {

    /**
     * 查询结算记录
     *
     * @return
     */
    List<Earning> query();

    /**
     * 结算
     */
    boolean calEarning();
}
