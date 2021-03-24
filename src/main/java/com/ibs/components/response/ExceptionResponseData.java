package com.ibs.components.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.api.doc.annotation.ApiEntity;
import com.douglei.tools.ExceptionUtil;

/**
 * 异常响应体, 当程序抛出异常且未被捕获时, 会返回此响应体
 * @author DougLei
 */
@ApiEntity
class ExceptionResponseData extends ResponseData {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResponseData.class);
	
	public ExceptionResponseData(Exception exception) {
		super(null, "系统出现异常, 请联系管理员, 查看日志", "ibs.system.exception");
		logger.error("操作出现异常: {}", ExceptionUtil.getStackTrace(exception));
	}
}
