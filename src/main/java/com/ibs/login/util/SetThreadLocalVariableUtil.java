package com.ibs.login.util;

import java.util.Date;

import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.filters.token.TokenEntity;
import com.ibs.login.entity.DmTokenInfo;
import com.ibs.login.entity.SysAccount;
import com.ibs.login.entity.SysAccountOnlineStatus;

public class SetThreadLocalVariableUtil {
	
	//将线程静态变量放到threadLocal中
	public static void saveBaseInfo(DmTokenInfo tokenInfo) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setAccountId(tokenInfo.getAccountId());
		tokenEntity.setProjectId("projectId");
		tokenEntity.setCustomerId(tokenInfo.getCustomerId());
		tokenEntity.setToken(tokenInfo.getTokenId());
		RequestHeaderContext.setTokenEntity(tokenEntity);
	} 
	
	public static void saveBaseInfo(SysAccount sysAccount,SysAccountOnlineStatus sysAccountOnlineStatus) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setAccountId(sysAccount.getId());
		tokenEntity.setProjectId("projectId");
		tokenEntity.setCustomerId(sysAccountOnlineStatus.getUserId());
		tokenEntity.setToken(sysAccountOnlineStatus.getToken());
		RequestHeaderContext.setTokenEntity(tokenEntity);
	}
	
	public static boolean computeTimeDifference(Date lastUpdateDate) {
		long date1=lastUpdateDate.getTime();
		long date2=new Date().getTime();
		long times =(date2-date1)/1000;
		if(times>ReadProfile.getFailureDuration()) {
			return true;
		}else {
			return false;
		}
	}
}
