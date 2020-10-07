package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import com.wt.blockchainivest.vo.ConstantsVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class Constants extends EntityObject {

    private int id;
    private String type;
    private String key;
    private String value;

    public Constants() {

    }

    public Constants(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Constants(ConstantsVo vo) {
        BeanUtils.copyProperties(vo, this);
    }

    public ConstantsVo toVo() {
        ConstantsVo vo = new ConstantsVo();
        BeanUtils.copyProperties(this, vo);
        return vo;
    }
}
