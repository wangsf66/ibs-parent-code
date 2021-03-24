package com.ibs.components.filters.token;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibs.components.filters.AbstractFilter;
import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.components.response.ResponseSuccess;

/**
 * token验证过滤器
 * @author DougLei
 */
public class TokenValidateFilter extends AbstractFilter{
	
	@Autowired
	private TokenConfigurationProperties properties;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
		//if(properties.getIgnoreValidateUrlMatcher() != null && !properties.getIgnoreValidateUrlMatcher().match(request.getServletPath())) { // 判断当前的url是否忽略验证token
			Response validateTokenResponse = validateToken();
			if(validateTokenResponse.getSuccess() != ResponseSuccess.SUCCESS.getCode()) {
				outputFailureResponse(response, validateTokenResponse);
				return;
			}
			RequestHeaderContext.setTokenEntity((TokenEntity)validateTokenResponse.getData());
		//}
		chain.doFilter(req, response);
	}
	
	/**
	 * 验证token
	 * @return
	 */
	private Response validateToken() {
		// TODO 临时去掉验证token不能为空的功能
//		String token = RequestHeaderContext.getToken();
//		if(StringUtil.isEmpty(token)) {
//			ResponseContext.addValidation(null, null, "ibs.token.isnull");
//			return ResponseContext.getFinalResponse();
//		}
		// TODO 这里要调用验证token的接口, 并获取返回的验证结果信息, 目前这里先返回临时的token对象, 完成验证, 后续改正
		
		TokenEntity entity = TmpTokenEntity.getTmpTokenEntity();
		ResponseContext.addData(entity);
		if(LogContext.unEmpty()) {
			LogContext.getOperationLog().setUserId(entity.getAccountId());
			LogContext.getOperationLog().setUserName(entity.getAccountId());// TODO 后续从token中获取用户的name
		}
		return ResponseContext.getFinalResponse();
	}
}

class TmpTokenEntity {
	private static final TokenEntity te = new TokenEntity();
	static {
		te.setId(1);
		te.setLanguage("zh_CN");
		te.setAccountId("accountId");
		te.setProjectId("projectId");
		te.setCustomerId("customerId");
		te.setToken("token");
		te.setTryLoginTimes((byte)1);
		te.setLoginDate(new Date());
		te.setLastOperDate(new Date());
		te.setLoginMethod((byte)1);
		te.setClientIp("clientIp");
		te.setClientMac("clientMac");
		te.setClientType((byte)1);
		te.setClientInstanceName("clientInstanceName");
	}
	
	public static TokenEntity getTmpTokenEntity() {
		return te;
	}
}
