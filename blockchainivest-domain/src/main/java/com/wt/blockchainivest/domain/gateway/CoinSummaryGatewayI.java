package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinSummary;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:43
 */
public interface CoinSummaryGatewayI {

    /**
     * 根据coinName查询汇总信息
     *
     * @param coinName
     * @return
     */
    List<CoinSummary> querySummary(String coinName);

    /**
     * 更新所有汇总记录
     */
     void updateAllSummary() throws SQLException;
}
