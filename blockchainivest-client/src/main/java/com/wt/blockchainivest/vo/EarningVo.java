package com.wt.blockchainivest.vo;

import lombok.Data;

import java.util.Date;

@Data
public class EarningVo {

    private int id;
    private Date settlement_date; // 结算日期
    private Double total_invest; // 总投入
    private Double current_invest; // 当期投入
    private Double increase_rate; // 增长率(去当期)
    private Double increase_rate_monthly; // 增长率-月度(去当期)
    private Double increase_rate_yearly; // 增长率-年度(去当期)
    private Double total_value; // 总市值
}
