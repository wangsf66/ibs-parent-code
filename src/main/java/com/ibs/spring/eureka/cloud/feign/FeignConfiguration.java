package com.ibs.spring.eureka.cloud.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author DougLei
 */
@Configuration
public class FeignConfiguration {
	
	@Bean
	@LoadBalanced // 启用负载均衡功能
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplateWrapper restTemplateWrapper() {
		return new RestTemplateWrapper(restTemplate());
	}
}
