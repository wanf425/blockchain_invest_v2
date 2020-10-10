package com.wt.blockchainivest.repository.dao;

import com.wt.blockchainivest.domain.gateway.CoinInfoGatewayI;
import com.wt.blockchainivest.domain.gateway.EaringGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import com.wt.blockchainivest.domain.trasaction.Earning;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.domain.util.NumberUtil;
import com.wt.blockchainivest.repository.dto.EarningDto;
import com.xiaoleilu.hutool.db.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangtao
 */
@Component
public class EarningDao extends BaseDao<EarningDto> implements EaringGatewayI {

    @Autowired
    private CoinInfoGatewayI coinInfoRepository;
    @Autowired
    private CoinSummaryDao coinSummaryDao;

    @Override
    public List<Earning> query() {
        List<Earning> result = new ArrayList<>();

        try {
            // 查询最近的结算信息
            session.beginTransaction();
            String sql = "select * from tb_earning order by id desc";
            List<Entity> list = session.query(sql);

            list.forEach(t -> result.add(t.toBeanIgnoreCase(Earning.class)));

            session.commit();
        } catch (Exception e) {
            session.quietRollback();
            LogUtil.print("calEarning err", e);
        }

        return result;
    }

    /**
     * 结算
     */
    @Override
    public boolean calEarning() {
        boolean result = false;

        try {
            // 查询最近的结算信息
            session.beginTransaction();
            String sql = "select * from tb_earning order by id desc";
            List<Entity> list = session.query(sql);

            Double totalInvest = 0.0;
            Double currentInvest = 0.0;
            Double increase_rate = 0.0;
            Double totalValue = 0.0;
            Double lastValue = 0.0;

            // 查询上次结算后的投资信息
            sql = " select if(sum(TOTAL_COST) is null,0.0,sum(TOTAL_COST)) as currentInvest from tb_coin_detail where coin_name = ? and op_type = ? and op_time > ? ";
            List<Object> param = new ArrayList<Object>();
            param.add(Constatns.Currency.RMB);
            param.add(Constatns.OpType.buy);

            if (!list.isEmpty()) {
                // 获取最近的统计信息
                EarningDto earningInfo = list.get(0).toBeanIgnoreCase(EarningDto.class);
                param.add(earningInfo.getSettlement_date());
                totalInvest = earningInfo.getTotal_invest();
                lastValue = earningInfo.getTotal_value();
            } else {
                param.add("2018-01-02");
                totalInvest = 20000.0;
            }

            // 当期投入和总投入
            List<Entity> list2 = session.query(sql, param.toArray(new Object[param.size()]));
            currentInvest = Double.valueOf(list2.get(0).get("currentInvest").toString());
            totalInvest = totalInvest + currentInvest;

            // 总市值
            List<CoinSummary> summary = coinSummaryDao.querySummary("");
            for (CoinSummary cs : summary) {
                totalValue += cs.getCoin_num() * cs.getMarket_price();
            }
            totalValue = totalValue / coinInfoRepository.getExchangeRate();

            // 增长率
            if (lastValue == 0.0) {
                increase_rate = 0.0;
            } else {
                increase_rate = NumberUtil
                        .formateNumDouble(NumberUtil.divide(totalValue - currentInvest, lastValue, 0.0)) - 1;
            }

            // 插入结算信息
            Entity entity = Entity.create("tb_earning").set("settlement_date", new Date())
                    .set("total_invest", totalInvest).set("current_invest", currentInvest)
                    .set("increase_rate", increase_rate).set("total_value", totalValue);

            session.insert(entity);

            session.commit();
            result = true;
        } catch (Exception e) {
            session.quietRollback();
            LogUtil.print("calEarning err", e);
        }

        return result;
    }
}
