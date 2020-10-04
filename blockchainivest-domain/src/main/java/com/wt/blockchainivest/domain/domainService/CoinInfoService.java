package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.CoinInfoGatewayI;
import com.wt.blockchainivest.domain.gateway.CoinSummaryGatewayI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:12
 */
@Component
public class CoinInfoService {
    @Autowired
    private CoinInfoGatewayI coinInfoDao;

    /**
     * 查询人币汇率
     *
     * @return
     */
    public double getExchangeRate() {
        return coinInfoDao.getExchangeRate();
    }
}

