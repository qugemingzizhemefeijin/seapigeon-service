package com.tigerjoys.seapigeon.inter.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tigerjoys.seapigeon.inter.entity.Payment;

@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {

}
