package com.wt.blockchainivest.repository.dto;

import com.wt.blockchainivest.domain.trasaction.Constants;
import lombok.Data;

@Data
public class ConstantsDto extends Constants implements BaseDto {

    private int id;
    private String type;
    private String key;
    private String value;

    public ConstantsDto() {

    }
    public ConstantsDto(String key, String value) {
        this.key = key;
        this.value = value;
    }


}
