package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.CoinInfoGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    public void updateAll(List<CoinInfo> list) {
        coinInfoDao.updateAll(list);
    }

    /**
     * 查询代币基础信息
     *
     * @param coinName
     * @return
     */
    public List<CoinInfo> queryCoinInfo(String coinName) {
        return coinInfoDao.queryCoinInfo(coinName);
    }
}

