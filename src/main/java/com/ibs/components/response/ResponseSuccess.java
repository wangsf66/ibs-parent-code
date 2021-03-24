package com.ibs.components.response;

/**
 * 
 * @author DougLei
 */
public enum ResponseSuccess {
	/**
	 * 异常
	 */
	EXCEPTION(-1),
	/**
	 * 失败
	 */
	FAILURE(0),
	/**
	 * 成功
	 */
	SUCCESS(1),
	/**
	 * 部分成功
	 */
	PARTIAL_SUCCESS(2);
	
	private byte code;
	private ResponseSuccess(int code) {
		this.code = (byte)code;
	}
	public byte getCode() {
		return code;
	}
}
