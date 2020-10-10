package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinDetail;

import java.sql.SQLException;
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

    /**
     * 保存明细信息
     *
     * @param detail
     * @return
     */
    void doSave(CoinDetail detail) throws Exception;

    /**
     * 查询明细数据
     *
     * @param coinName
     * @return
     */
    List<CoinDetail> query(String coinName);

    /**
     * 撤销
     *
     * @param coinName
     * @return
     */
    String doCancel(String coinName);

    /**
     * 结算
     *
     * @param coinName
     * @throws Exception
     */
    void doSettlement(String coinName) throws Exception;

    /**
     * 资金投入
     *
     * @param money
     * @throws Exception
     */
    void putMonet(Double money) throws Exception;
}
