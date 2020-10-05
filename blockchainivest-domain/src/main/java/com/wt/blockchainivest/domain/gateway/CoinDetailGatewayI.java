package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinDetail;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:43
 */
public interface CoinDetailGatewayI {

    /**
     * 查询比指定ID大的明细数据
     *
     * @param id
     * @return
     */
    List<CoinDetail> queryById(int id);

    /**
     * 备份交易明细数据
     */
    boolean doBackUp();

    /**
     * 根据备份的交易明细数据，回滚
     */
    boolean doRollBack();
}
