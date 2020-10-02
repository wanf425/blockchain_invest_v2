package com.wt.blockchainivest.domain.trasaction;

import com.wt.blockchainivest.vo.ConstantsVo;
import lombok.Data;

@Data
public class Constants extends ConstantsVo {

    public Constants() {

    }

    public Constants(String key, String value) {
        setKey(key);
        setValue(value);
    }
}
