package com.wt.blockchainivest.repository.dao;

import com.mysql.cj.util.StringUtils;
import com.wt.blockchainivest.domain.gateway.CoinSummaryGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinInfo;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import com.wt.blockchainivest.domain.util.CommonUtil;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.domain.util.NumberUtil;
import com.wt.blockchainivest.repository.dto.CoinSummaryDto;
import com.xiaoleilu.hutool.bean.BeanUtil;
import com.xiaoleilu.hutool.db.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wt.blockchainivest.domain.util.Constatns.getCost;
import static com.wt.blockchainivest.domain.util.NumberUtil.add;
import static com.wt.blockchainivest.domain.util.NumberUtil.sub;

@Component
public class CoinSummaryDao extends BaseDao<CoinSummaryDto> implements CoinSummaryGatewayI {
    @Autowired
    private CoinInfoDao coinInfoDao;

    @Override
    public List<CoinSummary> querySummary(String coinName) {
        List<CoinSummary> result = new ArrayList<>();

        try {
            StringBuffer sql = new StringBuffer("SELECT s.*, i.market_price,i.percent as pre_percent ");
            sql.append(" FROM tb_coin_summary s inner JOIN tc_coin_info i ON s.coin_name = i.coin_name ");

            Object[] params = new Object[]{};

            if (!StringUtils.isNullOrEmpty(coinName)) {
                sql.append(" WHERE s.COIN_NAME = ?");
                params = new Object[]{coinName};
            }

            sql.append(" ORDER BY s.coin_name ");

            List<Entity> list = runner.query(sql.toString(), params);

            Double total = 0.0;
            for (Entity en : list) {
                CoinSummaryDto cs = en.toBeanIgnoreCase(CoinSummaryDto.class);
                Double marketPrice = Double.valueOf(cs.getMarket_price());
                // 总市值
                total += marketPrice * cs.getCoin_num();

                if (Constatns.Currency.USDT.equals(cs.getCoin_name())
                        || Constatns.Currency.RMB.equals(cs.getCoin_name())) {
                    // 人民币、USDT不计算 总花费、均价收益率和收益数
                    cs.setTotal_cost(0.0);
                    cs.setAvarange_price(0.0);
                    cs.setRate_num(0.0);
                    cs.setRate_percent(0.0);
                } else {
                    // 收益率
                    cs.setRate_percent(CommonUtil.getRatePercent(cs.getAvarange_price(), marketPrice));
                    // 收益数
                    cs.setRate_num(CommonUtil.getRateNum(cs.getAvarange_price(), cs.getCoin_num(), marketPrice));
                }

                result.add(cs);
            }

            for (CoinSummary cs : result) {
                Double marketPrice = Double.valueOf(cs.getMarket_price());
                cs.setAsset_percent(NumberUtil.formateNumDouble(marketPrice * cs.getCoin_num() / total * 100));
            }
        } catch (SQLException e) {
            LogUtil.print("querySummary err", e);
        }

        return result;
    }

    /**
     * 更新所有汇总记录
     */
    @Override
    public void updateAllSummary() throws SQLException {
        List<CoinSummaryDto> list = queryAll();

        for (CoinSummaryDto cs : list) {
            updateSummary(cs.getCoin_name(), true);
        }
    }

    /**
     * 查询汇总数据
     *
     * @return
     * @throws SQLException
     */
    public List<CoinSummaryDto> queryAll() {
        List<CoinSummaryDto> result = new ArrayList<>();

        try {
            String sql = "select * from tb_coin_summary ORDER BY ID desc";
            List<Entity> list = runner.query(sql, new Object[]{});

            list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinSummaryDto.class)));

        } catch (Exception e) {
            LogUtil.print("query err", e);
        }

        return result;
    }

    /**
     * 查询汇总数据
     *
     * @param coinName
     * @return
     * @throws SQLException
     */
    public List<CoinSummaryDto> query(String coinName) {
        List<CoinSummaryDto> result = new ArrayList<>();

        try {
            String sql = "select * from tb_coin_summary where COIN_NAME = ? ORDER BY ID desc";
            List<Entity> list = runner.query(sql, new Object[]{coinName});

            list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinSummaryDto.class)));

        } catch (Exception e) {
            LogUtil.print("query err", e);
        }

        return result;
    }

    public void updateSummary(String coinName, boolean isNeedTransaction) throws SQLException {
        if (isNeedTransaction) {
            try {
                session.beginTransaction();
                doUpdateSummary(coinName);
                session.commit();
            } catch (Exception e) {
                session.quietRollback();
                LogUtil.print("updateSummary err", e);
            }
        } else {
            doUpdateSummary(coinName);
        }
    }

    private void doUpdateSummary(String coinName) throws SQLException {
        String sql = "SELECT OP_TYPE,COIN_NUM,TOTAL_COST,SERVICE_CHARGE,MONETARY_UNIT "
                + " FROM tb_coin_detail WHERE SETTLEMENT_VERSION is null and COIN_NAME = ? ";

        List<Entity> list = session.query(sql, new Object[]{coinName});

        CoinSummaryDto csSummary = new CoinSummaryDto();
        csSummary.setCoin_name(coinName);
        Map<String, CoinInfo> coinInfos = coinInfoDao.queryAllMap();

        for (Entity en : list) {
            CoinSummaryDto cs = en.toBeanIgnoreCase(CoinSummaryDto.class);

            // 代币信息不存在
            if (!coinInfos.containsKey(cs.getMonetary_unit())) {
                continue;
            }

            CoinInfo coinInfo = new CoinInfo();
            BeanUtil.copyProperties(coinInfos.get(cs.getMonetary_unit()), coinInfo);
            Double totalCost = getCost(cs.getTotal_cost(), coinInfo);
            Double serviceCharge = getCost(cs.getService_charge(), coinInfo);

            if (Constatns.OpType.buy.equals(cs.getOp_type())) {
                // 买入
                csSummary.setCoin_num(add(csSummary.getCoin_num(), cs.getCoin_num()));
                csSummary.setTotal_cost(add(csSummary.getTotal_cost(), totalCost));
                csSummary.setService_charge(add(csSummary.getService_charge(), serviceCharge));
            } else {
                // 卖出
                csSummary.setCoin_num(sub(csSummary.getCoin_num(), cs.getCoin_num()));
                csSummary.setTotal_cost(sub(csSummary.getTotal_cost(), totalCost));
                csSummary.setService_charge(add(csSummary.getService_charge(), serviceCharge));
            }
        }

        csSummary.setAvarange_price(NumberUtil.divide(csSummary.getTotal_cost(), csSummary.getCoin_num()));
        csSummary.setMonetary_unit(Constatns.Currency.USDT);

        // 查询汇总记录是否存在
        String countSQL = "SELECT COUNT(1) as count FROM tb_coin_summary WHERE coin_name = ? ";
        List<Entity> countList = session.query(countSQL, new Object[]{coinName});
        Long count = (Long) countList.get(0).get("count");

        if (count == 0) {
            Entity entity = Entity.create("tb_coin_summary").set("coin_name", coinName)
                    .set("coin_num", csSummary.getCoin_num()).set("total_cost", csSummary.getTotal_cost())
                    .set("Service_charge", csSummary.getService_charge())
                    .set("monetary_unit", csSummary.getMonetary_unit())
                    .set("Avarange_price", csSummary.getAvarange_price());
            session.insert(entity);
        } else {
            Entity entity = Entity.create("tb_coin_summary").set("coin_num", csSummary.getCoin_num())
                    .set("total_cost", NumberUtil.formateNumDouble(csSummary.getTotal_cost(), "#0.00000000"))
                    .set("Service_charge", NumberUtil.formateNumDouble(csSummary.getService_charge(), "#0.00000000"))
                    .set("monetary_unit", csSummary.getMonetary_unit())
                    .set("Avarange_price", NumberUtil.formateNumDouble(csSummary.getAvarange_price(), "#0.00000000"))
                    .set("UPDATE_DATE", new Date());
            Entity where = Entity.create("tb_coin_summary").set("coin_name", coinName);
            session.update(entity, where);
        }
    }
}
