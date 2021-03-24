package com.ibs.code.service.file;

import java.io.File;

/**
 * 
 * @author DougLei
 */
public class CreateFileException extends Exception{
	private static final long serialVersionUID = 5593413620884151580L;

	public CreateFileException(File file, Throwable cause) {
		super("创建文件["+file.getName()+"]时出现异常", cause);
	}
}
