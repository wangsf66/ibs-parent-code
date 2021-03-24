package com.ibs.spring.threadpool;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池注册器
 * @author DougLei
 */
@Configuration
@EnableAsync
public class ThreadPoolRegistry {
	
	@Autowired
	private ThreadPoolProperties properties;
	
	@Bean
    public Executor threadPool(){
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(properties.getCorePoolSize());
        threadPoolExecutor.setMaxPoolSize(properties.getMaxPoolSize());
        threadPoolExecutor.setQueueCapacity(properties.getQueueCapacity());
        threadPoolExecutor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        threadPoolExecutor.setThreadNamePrefix(properties.getThreadNamePrefix());
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(properties.isWaitForTasksToCompleteOnShutdown());
        threadPoolExecutor.setAwaitTerminationSeconds(properties.getAwaitTerminationSeconds());
        return threadPoolExecutor;
    }
}
