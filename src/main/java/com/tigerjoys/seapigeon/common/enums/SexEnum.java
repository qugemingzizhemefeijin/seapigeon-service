package com.tigerjoys.seapigeon.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tigerjoys.seapigeon.common.exception.UnknownEnumException;

public enum SexEnum implements IEnum<Integer> {
	
	MAN(1, "男"),
	WOMAN(2, "女");
	
	@EnumValue
	private final int value;
    private final String desc;
    
    SexEnum(int value, String desc) {
    	this.value = value;
    	this.desc = desc;
    }
    
    @Override
    @JsonValue
    public Integer getValue() {
        return this.value;
    }

	public String getDesc() {
		return desc;
	}
	
	@JsonCreator
    public static SexEnum getEnum(int value) {
        for (SexEnum menuTypeEnum : SexEnum.values()) {
            if (menuTypeEnum.getValue() == value) {
                return menuTypeEnum;
            }
        }
        throw new UnknownEnumException("Error: Invalid MenuTypeEnum type value: " + value);
    }

}
