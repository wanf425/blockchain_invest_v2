package com.wt.blockchainivest.service;

import com.wt.blockchainivest.api.InvestApplicationI;
import com.wt.blockchainivest.domain.domainService.CoinDetailService;
import com.wt.blockchainivest.domain.domainService.CoinInfoService;
import com.wt.blockchainivest.domain.domainService.CoinSummaryService;
import com.wt.blockchainivest.domain.domainService.ConstantsService;
import com.wt.blockchainivest.domain.trasaction.CoinDetail;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import com.wt.blockchainivest.domain.trasaction.Constants;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.vo.CoinDetailVo;
import com.wt.blockchainivest.vo.CoinSummaryVo;
import com.wt.blockchainivest.vo.ConstantsVo;
import com.wt.blockchainivest.vo.IndexPageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IndexPageVo querySummary(String coinName) {
        IndexPageVo ipv = new IndexPageVo();
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
     * 查询人币汇率
     *
     * @return
     */
    @Override
    public double getExchangeRate() {
        return coinInfoService.getExchangeRate();
    }

    @Override
    public List<ConstantsVo> queryByType(String type) {
        List<Constants> list = constantsService.queryByType(type);

        List<ConstantsVo> reuslt = new ArrayList<>();
        for (Constants constants : list) {
            ConstantsVo vo = new ConstantsVo();
            BeanUtils.copyProperties(constants, vo);
            reuslt.add(vo);
        }

        return reuslt;
    }

    @Override
    public List<CoinDetailVo> queryById(int id) {
        List<CoinDetail> list = coinDetailService.queryById(id);

        List<CoinDetailVo> reuslt = new ArrayList<>();
        for (CoinDetail detail : list) {
            CoinDetailVo vo = new CoinDetailVo();
            BeanUtils.copyProperties(detail, vo);
            reuslt.add(vo);
        }

        return reuslt;
    }

    @Override
    public boolean doBackUp() {
        return coinDetailService.doBackUp();
    }

    @Override
    public boolean doRollBack() {
        return coinDetailService.doRollBack();
    }
}

