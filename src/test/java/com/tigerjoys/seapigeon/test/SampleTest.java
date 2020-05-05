package com.tigerjoys.seapigeon.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tigerjoys.seapigeon.common.utils.JacksonUtils;
import com.tigerjoys.seapigeon.inter.entity.Payment;
import com.tigerjoys.seapigeon.inter.mapper.PaymentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Test
	public void test() {
		Payment payment = paymentMapper.selectById(1);
		System.out.println(JacksonUtils.toJson(payment));
	}

}
