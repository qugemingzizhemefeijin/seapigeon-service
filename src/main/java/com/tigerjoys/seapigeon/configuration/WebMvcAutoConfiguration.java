package com.tigerjoys.seapigeon.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tigerjoys.seapigeon.common.utils.JacksonUtils;

@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(JacksonUtils.wrapperObjectMapper());
    }

}
