package com.wt.blockchainivest.service;

import com.wt.blockchainivest.api.InvestApplicationI;
import com.wt.blockchainivest.domain.domainService.*;
import com.wt.blockchainivest.domain.trasaction.*;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 区块链投资分析Application
 *
 * @author wangtao
 */
@Service
public class InvestApplicationImpl implements InvestApplicationI {
    @Autowired
    CoinSummaryService coinSummaryService;
    @Autowired
    CoinDetailService coinDetailService;
    @Autowired
    CoinInfoService coinInfoService;
    @Autowired
    ConstantsService constantsService;
    @Autowired
    EarningService earningService;

    @Override
    public CoinSummaryPageVo querySummary(String coinName) {
        CoinSummaryPageVo ipv = new CoinSummaryPageVo();
        List<CoinSummary> list = coinSummaryService.querySummary(coinName);

        Double totalNum = 0.0;
        Double coinNum = 0.0;
        Double cash = 0.0;
        Double usdtNum = 0.0;

        for (CoinSummary cs : list) {
            CoinSummaryVo csv = new CoinSummaryVo();
            BeanUtils.copyProperties(cs, csv);
            ipv.getSummaryList().add(csv);

            if (Constatns.Currency.RMB.equals(cs.getCoin_name())) {
                cash = cs.getCoin_num() * cs.getMarket_price();
                totalNum += cash;
            } else if (Constatns.Currency.USDT.equals(cs.getCoin_name())) {
                usdtNum = cs.getCoin_num();
                totalNum += usdtNum;
            } else {
                totalNum += cs.getCoin_num() * cs.getMarket_price();
                coinNum += cs.getCoin_num() * cs.getMarket_price();
            }
        }

        ipv.setTotalNum(totalNum);
        ipv.setCoinNum(coinNum);
        ipv.setCash(cash);
        ipv.setUsdtNum(usdtNum);

        return ipv;
    }

    /**
     * 更新所有汇总记录
     */
    @Override
    public void updateAllSummary() throws SQLException {
        coinSummaryService.updateAllSummary();
    }

    /**
     * 更新指定币种的汇总记录
     *
     * @throws SQLException
     */
    @Override
    public void updateSummary(String coinName) throws SQLException {
        coinSummaryService.updateSummary(coinName);

    }
    /**
     * 查询人币汇率
     *
     * @return
     */
    @Override
    public double getExchangeRate() {
        return coinInfoService.getExchangeRate();
    }


    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    @Override
    public void updateCoinInfos(List<CoinInfoVo> list) {

        if (list != null && list.size() > 0) {
            List<CoinInfo> infos = new ArrayList<>();

            for (CoinInfoVo vo : list) {
                CoinInfo coinInfo = new CoinInfo(vo);
                infos.add(coinInfo);
            }

            coinInfoService.updateAll(infos);
        }
    }

    /**
     * 查询代币基础信息
     *
     * @param coinName
     * @return
     */
    @Override
    public List<CoinInfoVo> queryCoinInfo(String coinName) {
        List<CoinInfo> coinInfos = coinInfoService.queryCoinInfo(coinName);
        List<CoinInfoVo> result = new ArrayList<>();

        if (coinInfos != null && coinInfos.size() > 0) {
            for (CoinInfo coinInfo : coinInfos) {
                result.add(coinInfo.toVo());
            }
        }

        return result;
    }

    @Override
    public List<ConstantsVo> queryByType(String type) {
        List<Constants> list = constantsService.queryByType(type);

        List<ConstantsVo> reuslt = new ArrayList<>();
        for (Constants constants : list) {
            reuslt.add(constants.toVo());
        }

        return reuslt;
    }

    @Override
    public List<CoinDetailVo> queryById(int id) {
        List<CoinDetail> list = coinDetailService.queryById(id);

        List<CoinDetailVo> reuslt = new ArrayList<>();
        for (CoinDetail detail : list) {
            reuslt.add(detail.toVo());
        }

        return reuslt;
    }

    /**
     * 补差额
     *
     * @param coinName
     * @param refund
     * @param remark
     */
    @Override
    public void doRefund(String coinName, Double refund, String remark) throws Exception {
        coinDetailService.doRefund(coinName, refund, remark);
    }

    /**
     * 查询明细数据
     *
     * @param coinName
     * @return
     * @throws SQLException
     */
    @Override
    public List<CoinDetailVo> queryCoinDetail(String coinName) {
        List<CoinDetail> list = coinDetailService.queryCoinDetail(coinName);
        List<CoinDetailVo> result = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (CoinDetail detail : list) {
                result.add(detail.toVo());
            }
        }

        return result;
    }

    /**
     * 撤销
     *
     * @param coinName
     * @return
     */
    @Override
    public String doCancel(String coinName) {
        return coinDetailService.doCancel(coinName);
    }

    /**
     * 结算
     *
     * @param coinName
     * @throws Exception
     */
    @Override
    public void doSettlement(String coinName) throws Exception {
        coinDetailService.doSettlement(coinName);
    }

    @Override
    public boolean doBackUp() {
        return coinDetailService.doBackUp();
    }

    @Override
    public boolean doRollBack() {
        return coinDetailService.doRollBack();
    }

    @Override
    public void saveDetail(CoinDetailVo CoinDetailVo) throws Exception {
        CoinDetail coinDetail = new CoinDetail(CoinDetailVo);
        coinDetailService.save(coinDetail);
    }

    @Override
    public void putMonet(Double money) throws Exception {
        coinDetailService.putMonet(money);
    }

    @Override
    public List<EarningVo> query() {
        List<EarningVo> result = new ArrayList<>();
        List<Earning> list = earningService.query();

        if (list != null && list.size() > 0) {
            for (Earning earning : list) {
                result.add(earning.toVo());
            }
        }

        return result;
    }

    /**
     * 结算
     */
    @Override
    public boolean calEarning() {
        return earningService.calEarning();
    }

}

