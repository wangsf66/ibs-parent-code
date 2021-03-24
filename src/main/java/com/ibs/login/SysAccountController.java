package com.ibs.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.login.entity.SysAccount;
import com.ibs.spring.resolver.method.argument.CommonParams;
import com.ibs.spring.resolver.method.argument.CommonParamsModel;

@RestController
@RequestMapping("/account")
public class SysAccountController {
	
	@Autowired
	private SysAccountService sysAccountService;
	
	
	@RequestMapping({"/login"})
	public Response login(@CommonParams(cls=SysAccount.class)CommonParamsModel<SysAccount> commonParamsModel) {
		if(commonParamsModel.getList()!=null) {
			sysAccountService.accountLogin(commonParamsModel.getList().get(0));
        }
		return ResponseContext.getFinalResponse(commonParamsModel.getIsBatch());
	}
	
	@RequestMapping({"/menu"})
	public Response menu(@CommonParams(cls=Map.class)CommonParamsModel<Map<String,Object>> commonParamsModel) {
		if(commonParamsModel.getList()!=null) {
			sysAccountService.accountMenu(commonParamsModel.getList().get(0));
        }
		return ResponseContext.getFinalResponse(commonParamsModel.getIsBatch());
	}
	
	@RequestMapping({"/loginout"})
	public Response loginout(@CommonParams(cls=SysAccount.class)CommonParamsModel<SysAccount> commonParamsModel) {
		if(commonParamsModel.getList()!=null) {
			sysAccountService.accountlLoginout(commonParamsModel.getList().get(0));
        }
		return ResponseContext.getFinalResponse(commonParamsModel.getIsBatch());
	}
}
