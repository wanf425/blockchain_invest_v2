package com.wt.blockchainivest.repository.dto;

import lombok.Data;

import java.util.Date;

/**
 * 交易明细
 *
 * @author wangtao
 */
@Data
public class CoinDetailDto extends BaseDto {

    private Integer id;
    private String coin_name;
    private Double coin_num;
    private Double total_cost;
    // 货币单位
    private String total_cost_currency;
    // 手续费金额
    private Double service_charge;
    //手续费单位
    private String servcieChargeCurrency;
    private String monetary_unit;
    private Double avarange_price;
    private String op_type;
    private Date op_time;
    private String op_market;
    // 备注
    private String remark;
    // 结算时间
    private Date settlement_date;
    // 结算版本号
    private String settlement_version;
    // 结算价格
    private Double settlement_price;
    // 是否结算生成数据
    private Integer is_settlement;
    // 修改时间
    private Date update_Date;
    // 创建时间
    private Date create_Date;

}
