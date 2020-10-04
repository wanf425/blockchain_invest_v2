package com.wt.blockchainivest.repository.dao;

import com.wt.blockchainivest.domain.gateway.ConstantsGateWayI;
import com.wt.blockchainivest.domain.trasaction.Constants;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.repository.dto.ConstantsDto;
import com.xiaoleilu.hutool.db.Entity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 常量对象数据库类
 *
 * @author wangtao
 */
@Component
public class ConstantsDao extends BaseDao<ConstantsDto> implements ConstantsGateWayI {

    public static void main(String[] args) {
        ConstantsDao d = new ConstantsDao();
        d.queryByType("opType");
    }

    @Override
    public List<Constants> queryByType(String type) {
        List<Constants> result = new ArrayList<>();

        try {
            List<Entity> list = runner.findAll(Entity.create("tc_constants").set("type", type));

            for (Entity en : list) {
                result.add(en.toBeanIgnoreCase(Constants.class));
            }
        } catch (SQLException e) {
            LogUtil.print("queryByType err", e);
        }

        return result;
    }
}
