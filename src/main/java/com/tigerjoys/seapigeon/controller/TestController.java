package com.tigerjoys.seapigeon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigerjoys.seapigeon.common.enums.SexEnum;
import com.tigerjoys.seapigeon.inter.entity.Payment;
import com.tigerjoys.seapigeon.inter.mapper.PaymentMapper;

@RestController
public class TestController {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@GetMapping("/test")
	public Payment testHello() {
		Payment payment = paymentMapper.selectById(1);
		return payment;
	}

	@GetMapping("/insert")
	public Payment insertHello() {
		Payment entity = new Payment();
		entity.setSerial("adasdad阿萨德");
		entity.setSex(SexEnum.WOMAN);
		
		paymentMapper.insert(entity);
		
		return entity;
	}

}
