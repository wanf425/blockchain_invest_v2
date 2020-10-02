package com.wt.blockchainivest.domain.trasaction;

import com.alibaba.cola.domain.EntityObject;
import lombok.Data;

@Data
public class Constants extends EntityObject {

    private int id;
    private String type;
    private String key;
    private String value;
    public Constants() {

    }
    public Constants(String key, String value) {
        setKey(key);
        setValue(value);
    }
}
