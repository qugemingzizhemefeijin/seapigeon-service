package com.tigerjoys.seapigeon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.tigerjoys.seapigeon.common.mybatisplus.CommonMetaObjectHandler;

@Configuration
public class MybatisPlusAutoConfiguration {
	
	/**
     * 分页
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    public CommonMetaObjectHandler commonMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }

    /**
     * 自定义注入语句
     *
     * @return
     */
    @Bean
    public MybatisPlusSqlInjector mybatisPlusSqlInjector() {
        return new MybatisPlusSqlInjector();
    }

}
