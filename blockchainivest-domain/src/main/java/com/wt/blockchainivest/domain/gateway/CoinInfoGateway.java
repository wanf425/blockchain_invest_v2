package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinInfo;

import java.util.List;

/**
 * @author wangtao
 */
public interface CoinInfoGateway {

    List<CoinInfo> queryCoinInfo(String coinName);

    /**
     * 人币汇率
     *
     * @return
     */
     double getExchangeRate();
}
