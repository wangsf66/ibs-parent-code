package com.ibs.login.util;

import com.douglei.tools.file.reader.PropertiesReader;

public class ReadProfile {
	//失效时间1800秒
	private static long failureDuration ;
	//最大失败请求次数3次
	private static long maximumNumberOfRequest;
	//请求失败锁定时间900秒
	private static long lockDuration;
	
	private static String fileUploudKeySeparator = null;
	
	private static Integer sizes = null;
	
	private static String fileUploudServerAddress = null;
	
	private static String[] notFilterUrls = null;
	
	

	static{
		PropertiesReader propertiesReader = new PropertiesReader("basicConfiguration.properties");
		failureDuration =(long)Integer.parseInt(propertiesReader.readProperty("failureDuration"));
		maximumNumberOfRequest =(long)Integer.parseInt(propertiesReader.readProperty("maximumNumberOfRequest"));
		lockDuration =(long)Integer.parseInt(propertiesReader.readProperty("lockDuration"));
		fileUploudKeySeparator = propertiesReader.readProperty("fileUploudKeySeparator", ".");
		sizes = Integer.parseInt(propertiesReader.readProperty("sizes", "10"));
		fileUploudServerAddress = propertiesReader.readProperty("fileUploudServerAddress");
		notFilterUrls = propertiesReader.readProperty("notFilterUrls").split(",");
	}
	
	public static String[] getNotFilterUrls() {
		return notFilterUrls;
	}

	public static long getFailureDuration() {
		return failureDuration;
	}

	public static long getMaximumNumberOfRequest() {
		return maximumNumberOfRequest;
	}

	public static long getLockDuration() {
		return lockDuration;
	}

	public static String getFileUploudKeySeparator() {
		return fileUploudKeySeparator;
	}

	public static Integer getSizes() {
		return sizes;
	}

	public static String getFileUploudServerAddress() {
		return fileUploudServerAddress;
	}

	
}
