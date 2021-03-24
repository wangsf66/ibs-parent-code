package com.ibs.components.filters.dynamic.table;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.parent.dynamic.table")
public class DynamicTableConfigurationProperties {
	private String projectName;// 项目名
	private String name;// 动态表表名
	
	private short countCeiling;// 动态表的数量上限
	private short warnCountCeiling;// 动态表数量达到该值时, 系统会开始发出警告
	
	public void setCountCeiling(short countCeiling) {
		this.countCeiling = countCeiling;
	}
	public void setWarnCountCeiling(short warnCountCeiling) {
		this.warnCountCeiling = warnCountCeiling;
	}
	
	/**
	 * 设置项目信息
	 * @param projectName
	 */
	public void setProjectInfo(String projectName) {
		this.projectName = projectName; // 记录项目名
		this.name = projectName +"_DYNAMIC_TABLE"; // 由项目名, 获取动态表的表名
	}
	
	public String getProjectName() {
		return projectName;
	}
	public String getName() {
		return name;
	}
	public short getCountCeiling() {
		if(countCeiling < 1)
			countCeiling = 200;
		return countCeiling;
	}
	public short getWarnCountCeiling() {
		if(warnCountCeiling < 1)
			warnCountCeiling = (short) (getCountCeiling()*0.95);
		if(warnCountCeiling < 1)
			warnCountCeiling = 1;
		return warnCountCeiling;
	}
}
