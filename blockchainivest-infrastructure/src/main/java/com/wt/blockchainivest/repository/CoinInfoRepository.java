package com.wt.blockchainivest.repository;

import com.wt.blockchainivest.domain.gateway.CoinInfoGateway;
import com.wt.blockchainivest.domain.trasaction.CoinInfo;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.repository.dao.CoinInfoDao;
import com.wt.blockchainivest.repository.dto.CoinInfoDto;
import com.xiaoleilu.hutool.bean.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoinInfoRepository implements CoinInfoGateway {
    CoinInfoDao CoinInfoDao = new CoinInfoDao();

    @Override
    public List<CoinInfo> queryCoinInfo(String coinName) {
        List<CoinInfoDto> list = CoinInfoDao.queryCoinInfo(coinName);
        List<CoinInfo> result = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (CoinInfoDto dto : list) {
                CoinInfo info = new CoinInfo();
                BeanUtil.copyProperties(dto, info);
                result.add(info);
            }
        }

        return result;
    }

    @Override
    public double getExchangeRate() {
        List<CoinInfo> list = queryCoinInfo(Constatns.Currency.RMB);

        return list.get(0).getMarket_price();
    }
}
