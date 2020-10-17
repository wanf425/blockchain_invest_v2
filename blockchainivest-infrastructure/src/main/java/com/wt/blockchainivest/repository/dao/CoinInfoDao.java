package com.wt.blockchainivest.repository.dao;

import com.wt.blockchainivest.domain.gateway.CoinInfoGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinInfo;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.repository.dto.CoinInfoDto;
import com.xiaoleilu.hutool.db.Entity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CoinInfoDao extends BaseDao<CoinInfoDto> implements CoinInfoGatewayI {

    @Override
    public double getExchangeRate() {
        List<CoinInfo> list = queryCoinInfo(Constatns.Currency.RMB);

        return list.get(0).getMarket_price();
    }

    @Override
    public void updateAll(List<CoinInfo> list) {

        try {
            session.beginTransaction();
            session.execute("truncate table tc_coin_info", null);

            for (CoinInfo en : list) {
                session.insert(Entity.create("tc_coin_info").set("COIN_NAME", en.getCoin_name())
                        .set("MARKET_PRICE", en.getMarket_price()).set("PRICE_UNIT", Constatns.Currency.USDT)
                        .set("PERCENT", en.getPercent()));
            }
            session.commit();
        } catch (SQLException e) {
            session.quietRollback();
            LogUtil.print("updateAll err", e);
        }
    }

    @Override
    public List<CoinInfo> queryCoinInfo(String coinName) {
        List<CoinInfo> result = new ArrayList<CoinInfo>();

        try {
            List<Entity> list = new ArrayList<>();

            if (StringUtils.isEmpty(coinName)) {
                list = runner.findAll(Entity.create("tc_coin_info"));
            } else {
                list = runner.findAll(Entity.create("tc_coin_info").set("COIN_NAME", coinName));
            }

            for (Entity en : list) {
                result.add(en.toBeanIgnoreCase(CoinInfo.class));
            }
        } catch (SQLException e) {
            LogUtil.print("queryCoinInfo err", e);
        }

        return result;
    }

    public Map<String, CoinInfo> queryAllMap() {
        List<CoinInfo> list = queryCoinInfo(null);

        Map<String, CoinInfo> map = new HashMap<>();
        list.forEach(t -> map.put(t.getCoin_name(), t));

        return map;
    }
}
