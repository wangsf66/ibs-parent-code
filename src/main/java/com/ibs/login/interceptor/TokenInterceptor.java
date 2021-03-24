package com.ibs.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.douglei.orm.context.Transaction;
import com.ibs.login.util.ReadProfile;
import com.ibs.login.util.ibsInjectServiceUtil;

public class TokenInterceptor implements HandlerInterceptor{
	
	
	@Override
	@Transaction
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("token");
        // 遍历需要忽略拦截的路径
        for(String item :ReadProfile.getNotFilterUrls()){
            if (item.equals(url)){
                return true;
            }
        }
        if(ibsInjectServiceUtil.getInstance().getTokenCheckService().checkTokenId(token, httpServletResponse)=="false") {
        	return false;
        }else {
        	return true;
        }
    }
}
