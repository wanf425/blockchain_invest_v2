package com.wt.blockchainivest.vo;


import lombok.Data;

import java.util.Date;

/**
 * 交易明细
 *
 * @author wangtao
 */
@Data
public class CoinDetailVo {

    private Integer id;
    private String coin_name;
    private Double coin_num;
    private Double total_cost;
    private String total_cost_currency; // 货币单位
    private Double service_charge; // 手续费金额
    private String servcieChargeCurrency; // 手续费单位
    private String monetary_unit;
    private Double avarange_price;
    private String op_type;
    private Date op_time;
    private String op_market;
    private String remark; // 备注
    private Date settlement_date; // 结算时间
    private String settlement_version; // 结算版本号
    private Double settlement_price; // 结算价格
    private Integer is_settlement; // 是否结算生成数据
    private Date update_Date; // 修改时间
    private Date create_Date; // 创建时间

}
