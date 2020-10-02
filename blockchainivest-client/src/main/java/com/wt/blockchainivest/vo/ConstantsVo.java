package com.wt.blockchainivest.vo;

import lombok.Data;

@Data
public class ConstantsVo {

    private int id;
    private String type;
    private String key;
    private String value;

    public ConstantsVo() {

    }

    public ConstantsVo(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
