package com.wt.blockchainivest.domain.gateway;

import com.wt.blockchainivest.domain.trasaction.CoinSummary;

import java.util.List;

public interface CoinSummaryGatewayI {

    List<CoinSummary> querySummary(String coinName);
}
