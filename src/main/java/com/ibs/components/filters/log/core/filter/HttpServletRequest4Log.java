package com.ibs.components.filters.log.core.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <code>HttpServletRequest</code>的自定义包装类
 * 现在要将请求体记录到日志中, 所以自己实现一个HttpServletRequest的包装类, 预读取请求体并保存起来, 
 * 后续我们根据body, 生成<code>ByteArrayInputStream</code>流对象, 让其他地方, 依然可以读取请求体数据
 * @author DougLei
 */
public class HttpServletRequest4Log extends HttpServletRequestWrapper {
	private static final String MULTIPART = "multipart/";
	private byte[] body;
	
	public HttpServletRequest4Log(HttpServletRequest request) throws IOException {
		super(request);
		String contentType = request.getContentType();
		if(contentType != null && contentType.startsWith(MULTIPART)) {
			return; // 说明是上传文件, 直接结束
		}
		
		if(request.getContentLength() > 0) {
			body = new byte[request.getContentLength()];
			try(InputStream in = request.getInputStream()) {
				int off = 0;
				short len = 2048;
				while(off < body.length && (len = (short) in.read(body, off, len)) != -1) {
					off += len;
				}
			}
		}else {
			body = new byte[0];
		}
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream4Log(body);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body)));
	}
	
	/**
	 * 获取请求体字符串
	 * @return
	 */
	public String getRequestBody2String() {
		if(body == null || body.length == 0) {
			return null;
		}
		return new String(body, StandardCharsets.UTF_8).trim();
	}
}
