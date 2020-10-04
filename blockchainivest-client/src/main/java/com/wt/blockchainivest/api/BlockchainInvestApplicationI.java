package com.wt.blockchainivest.api;

import com.wt.blockchainivest.vo.ConstantsVo;
import com.wt.blockchainivest.vo.IndexPageVo;

import java.util.List;

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

    /**
     * 根据type查询常量
     *
     * @param type
     * @return
     */
     List<ConstantsVo> queryByType(String type);


}
