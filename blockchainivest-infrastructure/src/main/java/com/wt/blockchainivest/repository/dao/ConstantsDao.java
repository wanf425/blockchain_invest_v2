package com.wt.blockchainivest.repository.dao;

import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.repository.dto.ConstantsDto;
import com.xiaoleilu.hutool.db.Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConstantsDao extends BaseDao<ConstantsDto> {

    public static void main(String[] args) {
        ConstantsDao d = new ConstantsDao();
        d.queryByType("opType");
    }

    public List<ConstantsDto> queryByType(String type) {
        List<ConstantsDto> result = new ArrayList<ConstantsDto>();

        try {
            List<Entity> list = runner.findAll(Entity.create("tc_constants").set("type", type));

            for (Entity en : list) {
                result.add(en.toBeanIgnoreCase(ConstantsDto.class));
            }
        } catch (SQLException e) {
            LogUtil.print("queryByType err", e);
        }

        return result;
    }
}
