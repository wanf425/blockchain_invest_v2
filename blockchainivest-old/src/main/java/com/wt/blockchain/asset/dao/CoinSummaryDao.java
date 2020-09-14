package com.wt.blockchain.asset.dao;

import static com.wt.blockchain.asset.util.NumberUtil.add;
import static com.wt.blockchain.asset.util.NumberUtil.sub;
import static com.wt.blockchain.asset.util.NumberUtil.divide;
import static com.wt.blockchain.asset.util.Constatns.getCost;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mysql.cj.util.StringUtils;
import com.wt.blockchain.asset.dto.CoinInfo;
import com.wt.blockchain.asset.dto.CoinSummary;
import com.wt.blockchain.asset.util.CommonUtil;
import com.wt.blockchain.asset.util.Constatns;
import com.wt.blockchain.asset.util.LogUtil;
import com.wt.blockchain.asset.util.NumberUtil;
import com.xiaoleilu.hutool.db.Entity;

public class CoinSummaryDao extends BaseDao<CoinSummary> {
	CoinInfoDao coinInfoDao = new CoinInfoDao();

	/**
	 * 查询汇总数据
	 * 
	 * @param coinName
	 * @return
	 * @throws SQLException
	 */
	public List<CoinSummary> queryAll() {
		List<CoinSummary> result = new ArrayList<>();

		try {
			String sql = "select * from tb_coin_summary ORDER BY ID desc";
			List<Entity> list = runner.query(sql, new Object[] {});

			list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinSummary.class)));

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
	public List<CoinSummary> query(String coinName) {
		List<CoinSummary> result = new ArrayList<>();

		try {
			String sql = "select * from tb_coin_summary where COIN_NAME = ? ORDER BY ID desc";
			List<Entity> list = runner.query(sql, new Object[] { coinName });

			list.forEach(en -> result.add(en.toBeanIgnoreCase(CoinSummary.class)));

		} catch (Exception e) {
			LogUtil.print("query err", e);
		}

		return result;
	}

	public List<CoinSummary> querySummary(String coinName) {
		List<CoinSummary> result = new ArrayList<>();

		try {
			StringBuffer sql = new StringBuffer("SELECT s.*, i.market_price,i.percent as pre_percent ");
			sql.append(" FROM tb_coin_summary s inner JOIN tc_coin_info i ON s.coin_name = i.coin_name ");

			Object[] params = new Object[] {};

			if (!StringUtils.isNullOrEmpty(coinName)) {
				sql.append(" WHERE s.COIN_NAME = ?");
				params = new Object[] { coinName };
			}

			sql.append(" ORDER BY s.coin_name ");

			List<Entity> list = runner.query(sql.toString(), params);

			Double total = 0.0;
			for (Entity en : list) {
				CoinSummary cs = en.toBeanIgnoreCase(CoinSummary.class);
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
	public void updateAllSummary() throws SQLException {
		List<CoinSummary> list = queryAll();

		for (CoinSummary cs : list) {
			updateSummary(cs.getCoin_name(), true);
		}
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

		List<Entity> list = session.query(sql, new Object[] { coinName });

		CoinSummary csSummary = new CoinSummary();
		csSummary.setCoin_name(coinName);
		Map<String, CoinInfo> coinInfos = coinInfoDao.queryAllMap();

		for (Entity en : list) {
			CoinSummary cs = en.toBeanIgnoreCase(CoinSummary.class);

			// 代币信息不存在
			if (!coinInfos.containsKey(cs.getMonetary_unit())) {
				continue;
			}

			Double totalCost = getCost(cs.getTotal_cost(), coinInfos.get(cs.getMonetary_unit()));
			Double serviceCharge = getCost(cs.getService_charge(), coinInfos.get(cs.getMonetary_unit()));

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

		csSummary.setAvarange_price(divide(csSummary.getTotal_cost(), csSummary.getCoin_num()));
		csSummary.setMonetary_unit(Constatns.Currency.USDT);

		// 查询汇总记录是否存在
		String countSQL = "SELECT COUNT(1) as count FROM tb_coin_summary WHERE coin_name = ? ";
		List<Entity> countList = session.query(countSQL, new Object[] { coinName });
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