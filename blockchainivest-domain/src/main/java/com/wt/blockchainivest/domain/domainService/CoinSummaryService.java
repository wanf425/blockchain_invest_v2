package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.CoinSummaryGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-02 11:09
 */
@Service
public class CoinSummaryService {

    @Autowired
    private CoinSummaryGatewayI coinInfoSummaryRepositoryImpl;

    public List<CoinSummary> querySummary(String coinName) {
        return coinInfoSummaryRepositoryImpl.querySummary(coinName);
    }
}