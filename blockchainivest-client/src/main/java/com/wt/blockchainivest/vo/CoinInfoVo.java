package com.wt.blockchainivest.vo;

import lombok.Data;

@Data
public class CoinInfoVo {

    private String coin_name;
    private Double market_price;
    private String price_unit;
    private Double percent;
}
