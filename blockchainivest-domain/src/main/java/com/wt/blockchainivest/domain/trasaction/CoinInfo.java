package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import com.wt.blockchainivest.vo.CoinInfoVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CoinInfo extends EntityObject {

    private String coin_name;
    private Double market_price;
    private String price_unit;
    private Double percent;
    public CoinInfo() {

    }
    public CoinInfo(CoinInfoVo vo) {
        BeanUtils.copyProperties(vo, this);
    }

    public CoinInfoVo toVo() {
        CoinInfoVo vo = new CoinInfoVo();
        BeanUtils.copyProperties(this, vo);
        return vo;
    }
}
