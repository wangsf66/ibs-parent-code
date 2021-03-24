package com.ibs.spring.resolver.method.argument;

import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author wangShuFang
 */
@Configuration
public class ConfigAdapter implements WebMvcConfigurer {
	
    @Bean
    CommonParamsArgumentResolver argumentResolver(){
        return  new CommonParamsArgumentResolver();
    }
   
    //注册解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver());
    }
}