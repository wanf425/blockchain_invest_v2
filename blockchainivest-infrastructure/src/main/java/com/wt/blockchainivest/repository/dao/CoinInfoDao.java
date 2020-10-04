package com.wt.blockchainivest.repository.dao;

import com.wt.blockchainivest.domain.gateway.CoinInfoGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinInfo;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.repository.dto.CoinInfoDto;
import com.xiaoleilu.hutool.db.Entity;
import org.springframework.stereotype.Component;

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
    public List<CoinInfo> queryCoinInfo(String coinName) {
        List<CoinInfo> result = new ArrayList<CoinInfo>();

        try {
            List<Entity> list = runner.findAll(Entity.create("tc_coin_info").set("COIN_NAME", coinName));

            for (Entity en : list) {
                result.add(en.toBeanIgnoreCase(CoinInfo.class));
            }
        } catch (SQLException e) {
            LogUtil.print("queryCoinInfo err", e);
        }

        return result;
    }

    public Map<String, CoinInfoDto> queryAllMap() {
        List<CoinInfoDto> list = queryAllList();

        Map<String, CoinInfoDto> map = new HashMap<>();
        list.forEach(t -> map.put(t.getCoin_name(), t));

        return map;
    }

    public List<CoinInfoDto> queryAllList() {
        List<CoinInfoDto> result = new ArrayList<CoinInfoDto>();

        try {
            List<Entity> list = runner.findAll(Entity.create("tc_coin_info"));

            for (Entity en : list) {
                result.add(en.toBeanIgnoreCase(CoinInfoDto.class));
            }
        } catch (SQLException e) {
            LogUtil.print("queryCoinInfo err", e);
        }

        return result;
    }

    public void updateAll(List<CoinInfoDto> list) {

        try {
            session.beginTransaction();
            session.execute("truncate table tc_coin_info", null);

            for (CoinInfoDto en : list) {
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
}
