package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinInfo;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:43
 */
public interface CoinInfoGatewayI {

    /**
     * 查询代币基础信息
     *
     * @param coinName
     * @return
     */
    List<CoinInfo> queryCoinInfo(String coinName);

    /**
     * 人币汇率
     *
     * @return
     */
     double getExchangeRate();

    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    void updateAll(List<CoinInfo> list);
}
