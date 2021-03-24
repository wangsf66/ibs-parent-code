package com.ibs.code.service.file;

/**
 * 
 * @author DougLei
 */
public class DownloadFileException extends Exception{
	private static final long serialVersionUID = -102219476996407423L;

	public DownloadFileException(DownloadFile file, Throwable cause) {
		super("下载文件["+file.getName()+"]时出现异常", cause);
	}
}
