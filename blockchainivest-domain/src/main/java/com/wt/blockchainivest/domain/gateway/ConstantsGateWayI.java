package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.Constants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: blockchainivest
 * @description:
 * @author: wang tao
 * @create: 2020-10-04 17:43
 */
public interface ConstantsGateWayI {

    List<Constants> queryByType(String type);
}