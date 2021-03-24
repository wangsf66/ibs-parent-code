package com.ibs.login.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.smartone.login.token")
public class Token {
	private  long  maximumNumberRequest;
	private  long  failureDuration;
	private  long  lockDuration;
	private  String notFilterUrls;
	
	public long getMaximumNumberRequest() {
		return maximumNumberRequest;
	}
	
	public void setMaximumNumberRequest(long maximumNumberRequest) {
		this.maximumNumberRequest = maximumNumberRequest;
	}
	public long getFailureDuration() {
		return failureDuration;
	}
	public void setFailureDuration(long failureDuration) {
		
		this.failureDuration = failureDuration;
	}
	public long getLockDuration() {
		return lockDuration;
	}
	public void setLockDuration(long lockDuration) {
		this.lockDuration = lockDuration;
	}
	public String getNotFilterUrls() {
		return notFilterUrls;
	}
	public void setNotFilterUrls(String notFilterUrls) {
		this.notFilterUrls = notFilterUrls;
	}
	@Override
	public String toString() {
		return "TokenConfigApplication [maximumNumberRequest=" + maximumNumberRequest + ", failureDuration="
				+ failureDuration + ", lockDuration=" + lockDuration + ", notFilterUrls=" + notFilterUrls + "]";
	}

}
