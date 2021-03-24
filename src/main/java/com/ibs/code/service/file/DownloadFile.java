package com.ibs.code.service.file;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 
 * @author DougLei
 */
public class DownloadFile {
	private String name;
	private File file;
	private Boolean exists;
	
	public DownloadFile(String pathname) {
		this(new File(pathname));
	}
	public DownloadFile(File file) {
		this.name = file.getName();
		this.file = file;
	}
	public DownloadFile(String name, File file) {
		this.name = name;
		this.file = file;
	}
	
	/**
	 * 对文件名进行转码
	 * @param encode
	 * @param decode
	 * @return
	 */
	public DownloadFile nameTranscoding(Charset encode, Charset decode) {
		this.name = new String(this.name.getBytes(encode), decode);
		return this;
	}
	
	public String getName() {
		return name;
	}
	public String getNameWhenFileNonExists() {
		return name + ".non-exists.txt";
	}
	public File getFile() {
		return file;
	}
	public boolean exists() {
		if(exists == null) {
			exists = file.exists();
		}
		return exists;
	}
}
