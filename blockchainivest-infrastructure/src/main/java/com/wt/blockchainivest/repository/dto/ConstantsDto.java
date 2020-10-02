package com.wt.blockchainivest.repository.dto;

import com.wt.blockchainivest.domain.trasaction.Constants;
import lombok.Data;

@Data
public class ConstantsDto extends Constants implements BaseDto {

	public ConstantsDto() {

	}

	public ConstantsDto(String key, String value) {
		setKey(key);
		setValue(value);
	}
}
