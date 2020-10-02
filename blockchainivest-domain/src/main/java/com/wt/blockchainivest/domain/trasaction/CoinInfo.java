package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import lombok.Data;

@Data
public class CoinInfo extends EntityObject {

    private String coin_name;
    private Double market_price;
    private String price_unit;
    private Double percent;

}
