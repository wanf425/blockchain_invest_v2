package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.CoinDetailGatewayI;
import com.wt.blockchainivest.domain.trasaction.CoinDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 18:18
 */
@Component
public class CoinDetailService {

    @Autowired
    CoinDetailGatewayI coinDetailDao;


    public List<CoinDetail> queryById(int id) {
        return coinDetailDao.queryById(id);
    }

    public boolean doBackUp() {
        return coinDetailDao.doBackUp();
    }


    public boolean doRollBack() {
        return coinDetailDao.doRollBack();
    }

    public void save(CoinDetail detail) throws Exception {
        coinDetailDao.doSave(detail);
    }

    public List<CoinDetail> queryCoinDetail(String coinName) {
        return coinDetailDao.query(coinName);
    }

    public void doSettlement(String coinName) throws Exception {
        coinDetailDao.doSettlement(coinName);
    }

    public String doCancel(String coinName) {


        return coinDetailDao.doCancel(coinName);
    }

    public void putMonet(Double money) throws Exception {
        coinDetailDao.putMonet(money);
    }


    public void doRefund(String coinName, Double refund, String remark) throws Exception {
        coinDetailDao.doRefund(coinName,refund,remark);
    }

}