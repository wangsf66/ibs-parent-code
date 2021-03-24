package com.ibs.components.response;

/**
 * 过多的响应数据异常
 * @author DougLei
 */
public class TooManyResponseDataException extends RuntimeException{
	private static final long serialVersionUID = 6712925336914513843L;

	public TooManyResponseDataException() {
		super("对于非批量操作请求, 应该响应单条数据, 但实际结果响应了多条数据");
	}
}
