package com.ibs.components.filters.log.core.filter;

import com.ibs.components.filters.log.entity.LogOperation;

/**
 * 
 * @author DougLei
 */
public class LogContext {
	private static final ThreadLocal<LogOperation> context = new ThreadLocal<LogOperation>();
	
	/**
	 * 设置操作日志
	 * @param log
	 */
	static void setOperationLog(LogOperation log) {
		context.set(log);
	}
	
	/**
	 * 获取最终的操作日志, 并将操作日志从当前线程数据中移除
	 * @return
	 */
	static LogOperation getFinalOperationLog() {
		try {
			return context.get();
		} finally {
			context.remove();
		}
	}
	
	/**
	 * 是否不为空
	 * @return
	 */
	public static boolean unEmpty() {
		return context.get() != null;
	}
	
	/**
	 * 获取操作日志
	 * @return
	 */
	public static LogOperation getOperationLog() {
		return context.get();
	}
	
	/**
	 * 设置上传文件的信息到请求体中
	 * @param fileInfo
	 */
	public static void setFileInfo2ReqBody(Object fileInfo) {
		if(unEmpty())
			getOperationLog().getRequestLog().setFileInfo2ReqBody(fileInfo);
	}
}
