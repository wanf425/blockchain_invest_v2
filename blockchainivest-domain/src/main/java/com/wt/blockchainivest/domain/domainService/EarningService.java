package com.wt.blockchainivest.domain.domainService;

import com.wt.blockchainivest.domain.gateway.EaringGatewayI;
import com.wt.blockchainivest.domain.trasaction.Earning;
import com.wt.blockchainivest.vo.EarningVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 18:17
 */
@Component
public class EarningService {

    @Autowired
    EaringGatewayI earingDao;

    /**
     * 查询结算记录
     *
     * @return
     */
    public List<Earning> query() {
        return earingDao.query();

    }

    /**
     * 结算
     */
    public boolean calEarning() {
        return earingDao.calEarning();
    }
}