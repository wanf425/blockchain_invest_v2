package com.wt.blockchainivest.vo;

import lombok.Data;

import java.util.Date;

/**
 * 交易汇总
 *
 * @author wangtao
 */
@Data
public class CoinSummaryVo {

    private Integer id;
    private String coin_name;
    private Double coin_num = 0.0;
    private Double total_cost = 0.0;
    private Double service_charge = 0.0;
    private String monetary_unit;
    private Double avarange_price = 0.0;
    private Double settlement_price = 0.0;
    private Date settlement_date;
    private String settlement_version;
    private String op_type;
    private Double market_price;
    private Double rate_percent;
    private Double rate_num;
    private Double asset_percent;
    private Double pre_percent;
}
