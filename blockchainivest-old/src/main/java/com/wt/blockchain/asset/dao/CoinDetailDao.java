package com.wt.blockchain.asset.dao;

import com.mysql.cj.util.StringUtils;
import com.wt.blockchain.asset.dto.CoinDetail;
import com.wt.blockchain.asset.dto.CoinInfo;
import com.wt.blockchain.asset.dto.CoinSummary;
import com.wt.blockchain.asset.util.Constatns;
import com.wt.blockchain.asset.util.Constatns.Market;
import com.wt.blockchain.asset.util.Constatns.OpType;
import com.wt.blockchain.asset.util.LogUtil;
import com.xiaoleilu.hutool.db.Entity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoinDetailDao extends BaseDao<CoinDetail> {
    CoinInfoDao coinInfoDao = new CoinInfoDao();
    CoinSummaryDao coinSummaryDao = new CoinSummaryDao();

    public String doCancel(String coinName) {
        String result = "";
        try {
            session.beginTransaction();
            String sql = "select * from tb_coin_detail where COIN_NAME = ? ORDER BY ID desc limit 1 ";
            List<Entity> list = session.query(sql, new Object[]{coinName});

            if (list.isEmpty()) {
                throw new Exception("明细信息不存在");
            }

            CoinDetail detail = list.get(0).toBeanIgnoreCase(CoinDetail.class);

            if (!StringUtils.isNullOrEmpty(detail.getSettlement_version())) {
                throw new Exception("已结算数据不能撤销");
            }

            // 删除代币和法币的明细记录
            sql = "delete from tb_coin_detail where OP_TIME = ? and TOTAL_COST = ? and CREATE_DATE =? ";
            session.execute(sql, new Object[]{detail.getOp_time(), detail.getTotal_cost(), detail.getCreate_Date()});

            coinSummaryDao.updateSummary(coinName, false);
            coinSummaryDao.updateSummary(detail.getMonetary_unit(), false);

            session.commit();
        } catch (Exception e) {
            session.quietRollback();
            LogUtil.print("doCancel err", e);
            result = e.getMessage();
        }

        return result;
    }

    /**
     * 查询明细数据
     *
     * @param coinName
     * @return
     * @throws SQLException
     */
    public List<CoinDetail> query(String coinName) {
        List<CoinDetail> result = new ArrayList<>();

        try {
            String sql = "select * from tb_coin_detail where COIN_NAME = ? ORDER BY ID ";
            List<Entity> list = runner.query(sql, new Object[]{coinName});

            list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinDetail.class)));
        } catch (Exception e) {
            LogUtil.print("query err", e);
        }

        return result;
    }

    public void putMonet(Double money) throws Exception {
        try {
            session.beginTransaction();
            Entity entity = Entity.create("tb_coin_detail").set("COIN_NAME", Constatns.Currency.RMB)
                    .set("COIN_NUM", money).set("TOTAL_COST", money).set("SERVICE_CHARGE", 0)
                    .set("MONETARY_UNIT", Constatns.Currency.RMB).set("AVARANGE_PRICE", 1)
                    .set("OP_TYPE", Constatns.OpType.buy).set("OP_TIME", new Date()).set("OP_MARKET", "");
            session.insert(entity);

            coinSummaryDao.updateSummary(Constatns.Currency.RMB, false);
            session.commit();
        } catch (SQLException e) {
            session.quietRollback();
            LogUtil.print("doSave err", e);
            throw new Exception("操作失败！");
        }
    }

    /**
     * 补差额
     *
     * @param coinName
     * @param refund
     */
    public void doRefund(String coinName, Double refund, String remark) throws Exception {
        try {
            session.beginTransaction();

            String opType = refund >= 0 ? OpType.buy : OpType.sell;
            // 插入代币明细数据
            Entity entity = Entity.create("tb_coin_detail").set("COIN_NAME", coinName).set("COIN_NUM", Math.abs(refund))
                    .set("TOTAL_COST", 0).set("SERVICE_CHARGE", 0).set("MONETARY_UNIT", Constatns.Currency.USDT)
                    .set("AVARANGE_PRICE", 0).set("OP_TYPE", opType).set("OP_TIME", new Date())
                    .set("OP_MARKET", Market.OKOEX).set("REMARK", remark);
            session.insert(entity);

            // 修改代币汇总数据
            coinSummaryDao.updateSummary(coinName, false);

            session.commit();
        } catch (SQLException e) {
            session.quietRollback();
            LogUtil.print("doSave err", e);
            throw new Exception("操作失败！");
        }
    }

    public void doSave(CoinDetail detail) throws Exception {

        // 代币明细数据
        try {
            session.beginTransaction();
            // 插入代币明细数据
            Entity entity = Entity.create("tb_coin_detail").set("COIN_NAME", detail.getCoin_name())
                    .set("COIN_NUM", detail.getCoin_num()).set("TOTAL_COST", detail.getTotal_cost())
                    .set("SERVICE_CHARGE", getServiceCharge(detail.getCoin_name(), detail))
                    .set("MONETARY_UNIT", detail.getMonetary_unit()).set("AVARANGE_PRICE", detail.getAvarange_price())
                    .set("OP_TYPE", detail.getOp_type()).set("OP_TIME", detail.getOp_time())
                    .set("OP_MARKET", detail.getOp_market()).set("MONETARY_UNIT", detail.getMonetary_unit());
            session.insert(entity);

            // 修改代币汇总数据
            coinSummaryDao.updateSummary(detail.getCoin_name(), false);

            // 插入货币明细数据
            Entity entity2 = Entity.create("tb_coin_detail").set("COIN_NAME", detail.getMonetary_unit())
                    .set("COIN_NUM", detail.getTotal_cost()).set("TOTAL_COST", detail.getTotal_cost())
                    .set("SERVICE_CHARGE", getServiceCharge(detail.getTotal_cost_currency(), detail))
                    .set("MONETARY_UNIT", detail.getMonetary_unit()).set("AVARANGE_PRICE", 0)
                    .set("OP_TYPE", Constatns.reverseOpType(detail.getOp_type())).set("OP_TIME", detail.getOp_time())
                    .set("OP_MARKET", detail.getOp_market()).set("MONETARY_UNIT", detail.getMonetary_unit());
            session.insert(entity2);

            // 修改法币汇总数据
            coinSummaryDao.updateSummary(detail.getTotal_cost_currency(), false);

            session.commit();
        } catch (SQLException e) {
            session.quietRollback();
            LogUtil.print("doSave err", e);
            throw new Exception("操作失败！");
        }
    }

    private Double getServiceCharge(String coinName, CoinDetail detail) {
        if (detail.getServcieChargeCurrency().equals(coinName)) {
            return detail.getService_charge();
        } else {
            return 0.0;
        }
    }

    /**
     * 结算
     *
     * @param coinName
     * @throws Exception
     */
    public void doSettlement(String coinName) throws Exception {
        try {
            session.beginTransaction();
            // 结算版本号
            String settlementVersion = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
            Date updateDate = new Date();

            // 修改明细数据为已结算
            CoinInfo coinInfo = coinInfoDao.queryCoinInfo(coinName).get(0);

            String sql = "update tb_coin_detail set UPDATE_DATE = ?, SETTLEMENT_DATE = ? , SETTLEMENT_VERSION = ?,  SETTLEMENT_PRICE = ? where SETTLEMENT_VERSION is null and COIN_NAME = ? ";
            session.execute(sql,
                    new Object[]{updateDate, updateDate, settlementVersion, coinInfo.getMarket_price(), coinName});

            // 新增明细数据
            sql = "select * from tb_coin_summary where COIN_NAME = ? ";
            List<Entity> list = session.query(sql, new Object[]{coinName});
            CoinSummary summary = list.get(0).toBeanIgnoreCase(CoinSummary.class);
            Entity entity = Entity.create("tb_coin_detail").set("COIN_NAME", summary.getCoin_name())
                    .set("COIN_NUM", summary.getCoin_num()).set("TOTAL_COST", 0).set("SERVICE_CHARGE", 0)
                    .set("MONETARY_UNIT", summary.getMonetary_unit()).set("AVARANGE_PRICE", 0)
                    .set("OP_TYPE", Constatns.OpType.buy).set("OP_TIME", new Date())
                    .set("OP_MARKET", Constatns.Market.SYSTEM).set("REMARK", "结算生成")
                    .set("IS_SETTLEMENT", Constatns.SETTLEMENT_STATE.IS_SETTLEMENT)
                    .set("SETTLEMENT_PRICE", coinInfo.getMarket_price());
            session.insert(entity);

            // 修改汇总数据
            coinSummaryDao.updateSummary(coinName, false);
            session.commit();
        } catch (SQLException e) {
            session.quietRollback();
            LogUtil.print("doSettlement err", e);
            throw new Exception("操作失败！");
        }
    }

    /**
     * 查询比指定ID大的明细数据
     *
     * @param id
     * @return
     */
    public List<CoinDetail> queryById(int id) {
        List<CoinDetail> result = new ArrayList<>();

        try {
            String sql = "select * from tb_coin_detail where ID > ? ORDER BY ID DESC";
            List<Entity> list = runner.query(sql, new Object[]{id});

            list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinDetail.class)));
        } catch (Exception e) {
            LogUtil.print("query err", e);
        }

        return result;
    }
}
