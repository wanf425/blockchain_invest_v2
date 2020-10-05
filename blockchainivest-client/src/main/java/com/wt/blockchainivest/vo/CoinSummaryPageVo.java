package com.wt.blockchainivest.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 首页
 * @description:
 * @author: wang tao
 * @create: 2020-10-02 13:20
 */
@Data
public class CoinSummaryPageVo {

    /**
     * 汇总列表
     */
    private List<CoinSummaryVo> summaryList = new ArrayList<>();

    /**
     * 总资产
     */
    private Double totalNum = 0.0;
    /**
     * 代币现值
     */
    private Double coinNum = 0.0;
    /**
     * 现金
     */
    private Double cash = 0.0;
    /**
     * USDT现值
     */
    private Double usdtNum = 0.0;
}