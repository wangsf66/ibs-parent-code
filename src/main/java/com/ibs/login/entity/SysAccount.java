package com.ibs.login.entity;

import com.ibs.code.entity.BasicEntity;

public class SysAccount extends BasicEntity{
    private String loginName;
    private String loginPwd;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
}
