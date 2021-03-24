package com.ibs.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.Transaction;
import com.douglei.orm.context.TransactionComponent;
import com.ibs.code.service.BasicService;
import com.ibs.components.response.ResponseContext;
import com.ibs.login.entity.DmTokenInfo;
import com.ibs.login.entity.SysAccount;
import com.ibs.login.entity.SysAccountOnlineStatus;
import com.ibs.login.util.ProcedurePrefixUtil;
import com.ibs.login.util.ResourceHandlerUtil;
import com.ibs.login.util.SetThreadLocalVariableUtil;

@TransactionComponent
public class SysAccountService extends BasicService{
	
	@Transaction
	public void accountLogin(SysAccount sysAccount) {
		//验证账号是否存在
		SysAccount account =SessionContext.getSQLSession().uniqueQuery(SysAccount.class,"accountLogin", "selectAccount",sysAccount);
	    if(account==null) {
	    	ResponseContext.addValidation("账户不存在", null, sysAccount);
	    	return;
	    }
	    SysAccountOnlineStatus sysAccountOnlineStatus = SessionContext.getSQLSession().uniqueQuery(SysAccountOnlineStatus.class,"accountLogin", "checkAccountState",sysAccount);
	    if(sysAccountOnlineStatus.getUserAccountState()!=1) {
	    	ResponseContext.addValidation("账户未启用，不能正常登录", null, sysAccount);
	    	return;
	    }
	    if(!DigestUtils.md5Hex(account.getLoginPwd()).equals(DigestUtils.md5Hex(sysAccount.getLoginPwd()))) {
	    	ResponseContext.addValidation("账号或者密码不正确", null, sysAccount);
	    	return;
	    }
        //保存token
        DmTokenInfo dmTokenInfo = new DmTokenInfo(ResourceHandlerUtil.getToken(),account.getId(),new Date(),new Date());
        sysAccountOnlineStatus.setToken(dmTokenInfo.getTokenId());
        //将用户信息放入threadlocal里
        SetThreadLocalVariableUtil.saveBaseInfo(sysAccount,sysAccountOnlineStatus);
        SessionContext.getSqlSession().executeUpdate("delete from DM_TOKEN_INFO where ACCOUNT_ID = '"+dmTokenInfo.getAccountId()+"'");
        SessionContext.getTableSession().save(setBasicPropertyValues(dmTokenInfo,true));
        //查询角色信息
        List<Map<String,Object>> roleList = SessionContext.getSQLSession().query("accountLogin", "selectRoles",sysAccountOnlineStatus.getUserId());
        //查询部门信息
        List<Map<String,Object>> deptList = SessionContext.getSQLSession().query("accountLogin", "selectDepartments",sysAccountOnlineStatus.getUserId());
        sysAccountOnlineStatus.setRolecodes(montageIds(roleList));
        sysAccountOnlineStatus.setDepartmentId(montageRightId(deptList));
		ResponseContext.addData(sysAccountOnlineStatus);
	}

	private String montageIds(List<Map<String,Object>> roleList) {
		StringBuilder ids = new StringBuilder();
		for(int i=0;i<roleList.size();i++) {
			if(i==roleList.size()-1) {
				ids.append(roleList.get(i).get("ID"));
			}else {
				ids.append(roleList.get(i).get("ID")+",");
			}
		}
		return ids.toString();
	}
	
	private String montageRightId(List<Map<String,Object>> roleList) {
		StringBuilder ids = new StringBuilder();
		for(int i=0;i<roleList.size();i++) {
			if(i==roleList.size()-1) {
				ids.append(roleList.get(i).get("AUDA_RIGHT_ID"));
			}else {
				ids.append(roleList.get(i).get("AUDA_RIGHT_ID")+",");
			}
		}
		return ids.toString();
	}
	
	@Transaction
	public void accountMenu(Map<String, Object> map) {
		sqlSessionExecuteProcedure(ProcedurePrefixUtil.CALL+"B_P_MENU_TEST", null, map);   
	}
	
	@Transaction
	public DmTokenInfo queryToken(String tokenId) {
		 return SessionContext.getSqlSession().uniqueQuery(DmTokenInfo.class,"select * from DM_TOKEN_INFO where TOKEN_ID = '"+tokenId+"'");
	}
	
	@Transaction
	public void updateToken(DmTokenInfo tokenInfo) {
		SessionContext.getTableSession().update(setBasicPropertyValues(tokenInfo,false));
	}
	
	@Transaction
	public void accountlLoginout(SysAccount sysAccount) {
		List<DmTokenInfo> tokenInfoList= SessionContext.getSqlSession().query(DmTokenInfo.class,"\r\n" + 
				"select * from DM_TOKEN_INFO where ACCOUNT_ID in (SELECT ID  FROM SMT_IMP_ACCOUNT A WHERE A.LOGIN_NAME = '"+sysAccount.getLoginName()+"')");
	    for(DmTokenInfo tokenInfo:tokenInfoList) {
	    	SessionContext.getSqlSession().executeUpdate("delete from  DM_TOKEN_INFO where id = '"+tokenInfo.getId()+"'");
	    }
	    ResponseContext.addData("退出登录成功");
	}
	@Transaction
	public void updateTokenNotBase(DmTokenInfo tokenInfo) {
		SessionContext.getTableSession().update(tokenInfo);
	}
}
