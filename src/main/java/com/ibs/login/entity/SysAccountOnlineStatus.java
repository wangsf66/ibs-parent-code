package com.ibs.login.entity;

public class SysAccountOnlineStatus {
    private String userId;
    private String userName;
    private int userAccountState;
    private String realName;
    private String departmentId;
    private String parentDepartmentId;
    private String departmentName;
    private String rolecodes;
    private String token;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getParentDepartmentId() {
		return parentDepartmentId;
	}
	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRolecodes() {
		return rolecodes;
	}
	public void setRolecodes(String rolecodes) {
		this.rolecodes = rolecodes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserAccountState() {
		return userAccountState;
	}
	public void setUserAccountState(int userAccountState) {
		this.userAccountState = userAccountState;
	}
	public SysAccountOnlineStatus() {
		super();
	}
}
