package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import com.wt.blockchainivest.vo.EarningVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class Earning extends EntityObject {

    private int id;
    private Date settlement_date; // 结算日期
    private Double total_invest; // 总投入
    private Double current_invest; // 当期投入
    private Double increase_rate; // 增长率(去当期)
    private Double increase_rate_monthly; // 增长率-月度(去当期)
    private Double increase_rate_yearly; // 增长率-年度(去当期)
    private Double total_value; // 总市值


    public Earning() {

    }

    public Earning(EarningVo vo) {
        BeanUtils.copyProperties(vo, this);
    }

    public EarningVo toVo() {
        EarningVo vo = new EarningVo();
        BeanUtils.copyProperties(this, vo);
        return vo;
    }
}
