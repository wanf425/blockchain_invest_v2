package com.wt.blockchainivest.api;

import com.wt.blockchainivest.vo.IndexPageVo;

public interface BlockchainInvestApplicationI {

    /**
     * 首页-查询汇总信息
     *
     * @return
     */
    IndexPageVo querySummary(String coinName);

    /**
     * 查询人币汇率
     *
     * @return
     */
    double getExchangeRate();

}
