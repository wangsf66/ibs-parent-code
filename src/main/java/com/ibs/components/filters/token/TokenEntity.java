package com.ibs.components.filters.token;

import java.util.Date;

import com.ibs.code.entity.Entity;

/**
 * 
 * @author DougLei
 */
public class TokenEntity implements Entity{
	private static final long serialVersionUID = 4761616980549637376L;
	
	protected int id;// 主键
	protected String language;// 语言
	protected String accountId;// 账户id
	protected String projectId;// 项目id
	protected String customerId;// 租户id
	protected String databaseId;// 数据库id
	protected String token;// token
	
	protected byte tryLoginTimes;// 尝试登陆次数
	protected Date loginDate;// 登陆时间
	protected Date lastOperDate;// 最后操作时间
	
	protected byte loginMethod;// 登陆方式
	protected String clientIp;// 登陆的客户端ip
	protected String clientMac;// 登陆的客户端mac
	protected byte clientType;// 登录的客户端类型
	protected String clientInstanceName;// 登陆的客户端实例名
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDatabaseId() {
		return databaseId;
	}
	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public byte getTryLoginTimes() {
		return tryLoginTimes;
	}
	public void setTryLoginTimes(byte tryLoginTimes) {
		this.tryLoginTimes = tryLoginTimes;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLastOperDate() {
		return lastOperDate;
	}
	public void setLastOperDate(Date lastOperDate) {
		this.lastOperDate = lastOperDate;
	}
	public byte getLoginMethod() {
		return loginMethod;
	}
	public void setLoginMethod(byte loginMethod) {
		this.loginMethod = loginMethod;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
	public byte getClientType() {
		return clientType;
	}
	public void setClientType(byte clientType) {
		this.clientType = clientType;
	}
	public String getClientInstanceName() {
		return clientInstanceName;
	}
	public void setClientInstanceName(String clientInstanceName) {
		this.clientInstanceName = clientInstanceName;
	}
}
