package com.ibs.login.entity;

import java.util.Date;

import com.ibs.code.entity.BasicEntity;

public class DmTokenInfo extends BasicEntity{
	private String tokenId;
	private String accountId;
	private Date firstLoginTime;
	private Date lastOperateTime;

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Date getFirstLoginTime() {
		return firstLoginTime;
	}
	public void setFirstLoginTime(Date firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}
	public Date getLastOperateTime() {
		return lastOperateTime;
	}
	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}
	
	public DmTokenInfo(String tokenId, String accountId, Date firstLoginTime, Date lastOperateTime) {
		super();
		this.tokenId = tokenId;
		this.accountId = accountId;
		this.firstLoginTime = firstLoginTime;
		this.lastOperateTime = lastOperateTime;
	}
	public DmTokenInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
} 
