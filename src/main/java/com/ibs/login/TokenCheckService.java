package com.ibs.login;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.douglei.orm.context.Transaction;
import com.douglei.orm.context.TransactionComponent;
import com.ibs.code.service.BasicService;
import com.ibs.components.response.ResponseUtil;
import com.ibs.login.entity.DmTokenInfo;
import com.ibs.login.util.SetThreadLocalVariableUtil;
import com.ibs.login.util.ibsInjectServiceUtil;
@TransactionComponent
public class TokenCheckService extends BasicService{
	    
	    @Transaction
		public String checkTokenId(String tokenId,HttpServletResponse httpServletResponse) {
	    	//httpServletResponse  当参数为null时表示当前请求是websocket请求
	    	DmTokenInfo tokenInfo = ibsInjectServiceUtil.getInstance().getSysAccountService().queryToken(tokenId);
	        if (tokenInfo == null){
	        	if(httpServletResponse!=null) {
	        		ResponseUtil.outputJSON(httpServletResponse, "token失效请重新登录");
	        	}
	        	return "false";
	        }
	    	if(SetThreadLocalVariableUtil.computeTimeDifference(tokenInfo.getLastUpdateDate())) {
	    		if(httpServletResponse!=null) {
	        		ResponseUtil.outputJSON(httpServletResponse, "token失效请重新登录");
	        	}
	    		return "false";
	    	}else {
	    		if(httpServletResponse!=null) {
	    			SetThreadLocalVariableUtil.saveBaseInfo(tokenInfo);
	        	}
	        	tokenInfo.setLastOperateTime(new Date());
	        	if(httpServletResponse==null) {
	        		ibsInjectServiceUtil.getInstance().getSysAccountService().updateTokenNotBase(tokenInfo);
	        	}else {
	        		ibsInjectServiceUtil.getInstance().getSysAccountService().updateToken(tokenInfo);
	        	}
	        	return tokenInfo.getAccountId();
	    	}
		}
}
