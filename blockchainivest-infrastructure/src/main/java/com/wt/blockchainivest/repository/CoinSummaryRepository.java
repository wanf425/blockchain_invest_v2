package com.wt.blockchainivest.repository;

import com.wt.blockchainivest.domain.gateway.CoinSummaryGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import com.wt.blockchainivest.repository.dao.CoinSummaryDao;
import com.wt.blockchainivest.repository.dto.CoinSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-02 11:31
 */
@Repository
public class CoinSummaryRepository implements CoinSummaryGatewayI {

    @Autowired
    private CoinSummaryDao coinSummaryDao;

    @Override
    public List<CoinSummary> querySummary(String coinName) {

        List<CoinSummaryDto> list = coinSummaryDao.querySummary(coinName);
        List<CoinSummary> result = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (CoinSummaryDto dto : list) {
                CoinSummary coinSummary = new CoinSummary();
                dto.convert(coinSummary);
                result.add(coinSummary);
            }
        }

        return result;
    }
}