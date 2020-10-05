package com.wt.blockchainivest.api;

import com.wt.blockchainivest.vo.CoinDetailVo;
import com.wt.blockchainivest.vo.ConstantsVo;
import com.wt.blockchainivest.vo.CoinSummaryPageVo;

import java.util.List;

/**
 * Application层统一接口类
 *
 * @author wangtao
 */
public interface InvestApplicationI {

    /**
     * 查询汇总信息
     *
     * @param coinName
     * @return
     */
    CoinSummaryPageVo querySummary(String coinName);

    /**
     * 查询人币汇率
     *
     * @return
     */
    double getExchangeRate();

    /**
     * 根据type查询常量
     *
     * @param type
     * @return
     */
     List<ConstantsVo> queryByType(String type);

    /**
     * 查询比指定ID大的明细数据
     *
     * @param id
     * @return
     */
    List<CoinDetailVo> queryById(int id);

    /**
     * 备份交易明细数据
     */
    boolean doBackUp();

    /**
     * 根据备份的交易明细数据，回滚
     */
    boolean doRollBack();

    /**
     * 保存明细信息
     *
     * @param CoinDetailVo
     * @return
     */
    void saveDetail(CoinDetailVo CoinDetailVo) throws Exception;


}
