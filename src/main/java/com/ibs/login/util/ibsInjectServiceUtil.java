package com.ibs.login.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibs.login.SysAccountService;
import com.ibs.login.TokenCheckService;

@Component
public class ibsInjectServiceUtil {
    
    @Autowired
   	private SysAccountService sysAccountService;
   
    @Autowired
   	private TokenCheckService tokenCheckService;
 
    @PostConstruct
    public void init(){
        ibsInjectServiceUtil.getInstance().sysAccountService = this.sysAccountService;
        ibsInjectServiceUtil.getInstance().tokenCheckService = this.tokenCheckService;
    }
 
    /**
     *  实现单例 start
     */
    private static class SingletonHolder {
        private static final ibsInjectServiceUtil INSTANCE = new ibsInjectServiceUtil();
    }
    private ibsInjectServiceUtil (){}
    public static final ibsInjectServiceUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }
	
	public SysAccountService getSysAccountService() {
		return sysAccountService;
	}
	public TokenCheckService getTokenCheckService() {
		return tokenCheckService;
	}
	
}
