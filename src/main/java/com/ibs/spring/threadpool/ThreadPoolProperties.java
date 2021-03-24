package com.ibs.spring.threadpool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix = "ibs.parent.thread.pool")
public class ThreadPoolProperties {
	private int corePoolSize = 10;
	private int maxPoolSize = 50;
	private int queueCapacity = 200;
	private int keepAliveSeconds = 60;
	private String threadNamePrefix = "ibs-thread-pool-";
	private boolean waitForTasksToCompleteOnShutdown = true;
	private int awaitTerminationSeconds = 60;
	
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getQueueCapacity() {
		return queueCapacity;
	}
	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}
	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}
	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}
	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}
	public boolean isWaitForTasksToCompleteOnShutdown() {
		return waitForTasksToCompleteOnShutdown;
	}
	public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
		this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
	}
	public int getAwaitTerminationSeconds() {
		return awaitTerminationSeconds;
	}
	public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
		this.awaitTerminationSeconds = awaitTerminationSeconds;
	}
}
