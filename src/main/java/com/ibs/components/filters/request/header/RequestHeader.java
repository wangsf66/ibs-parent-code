package com.ibs.components.filters.request.header;

import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.api.doc.annotation.ApiEntityParam;
import com.douglei.api.doc.types.DataType;
import com.douglei.tools.StringUtil;
import com.ibs.components.filters.log.core.filter.LogHeader;
import com.ibs.components.filters.token.TokenEntity;

/**
 * 请求头
 * @author DougLei
 */
@ApiEntity
public class RequestHeader {
	
	@ApiEntityParam(name="_token", length=36, required=true, description="登录成功后系统返回的token值")
	private String token;
	private TokenEntity tokenEntity; // 根据token, 获取到的实体
	
	@ApiEntityParam(name="_language", length=5, required=true, description="客户端选择的语言", egValue="Zh_CN")
	private String language;
	
	@ApiEntityParam(name="_log", dataType=DataType.STRING, description="相关日志信息(json格式)")
	private LogHeader log;

	
	void setToken(String token) {
		this.token = token;
	}
	void setTokenEntity(TokenEntity tokenEntity) {
		this.tokenEntity = tokenEntity;
	}
	void clearTokenEntity() {
		this.tokenEntity = null;
	}
	void setLanguage(String language) {
		this.language = language;
	}
	void setLog(String log) {
		this.log = LogHeader.newInstance(log);
	}
	
	
	public String getToken() {
		return token;
	}
	public TokenEntity getTokenEntity() {
		return tokenEntity;
	}
	public String getLanguage() {
		if(StringUtil.unEmpty(language)) {
			return language;
		}
		if(tokenEntity != null) {
			return tokenEntity.getLanguage();
		}
		return null;
	}
	public LogHeader getLogHeader() {
		return log;
	}
}	
