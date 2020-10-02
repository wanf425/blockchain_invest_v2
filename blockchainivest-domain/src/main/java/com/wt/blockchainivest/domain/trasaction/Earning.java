package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import lombok.Data;

import java.util.Date;

@Data
public class Earning extends EntityObject {

    private int id;
    private Date settlement_date; // 结算日期
    private Double total_invest; // 总投入
    private Double current_invest; // 当期投入
    private Double increase_rate; // 增长率(去当期)
    private Double total_value; // 总市值
}
