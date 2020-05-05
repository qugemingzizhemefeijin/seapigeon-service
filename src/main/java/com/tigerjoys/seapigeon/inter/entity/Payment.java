package com.tigerjoys.seapigeon.inter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tigerjoys.seapigeon.common.enums.SexEnum;

@TableName("payment")
public class Payment {

	@TableId(value = "id", type = IdType.AUTO)
    private Long id;
	
	private String serial;
	
	private SexEnum sex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

}
