package com.wt.blockchainivest.repository.dto;

import com.alibaba.cola.domain.EntityObject;
import com.wt.blockchainivest.domain.trasaction.CoinSummary;
import com.xiaoleilu.hutool.bean.BeanUtil;
import org.springframework.beans.BeanUtils;

/**
 * @author wangtao
 */
public interface BaseDto<T extends EntityObject> {

    /**
     * 将Dto转换为Entity
     *
     * @return
     */
    default void convert(T target) {
        BeanUtils.copyProperties(this,target);
    }
}
