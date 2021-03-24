package com.ibs.code.service.dynamic.table;

import com.douglei.tools.file.reader.FileBufferedReader;

/**
 * 动态表功能的工具类
 * @author DougLei
 */
public abstract class DynamicTableUtil {
	protected String[] mappingCodes;
	protected String[] mappingTemplates;
	
	/**
	 * 根据映射模板的资源路径数组, 设置对应的映射模板内容数组
	 * @param mappingTemplateResourcePaths
	 * @return
	 */
	protected final void setMappingTemplates(String... mappingTemplateResourcePaths) {
		this.mappingTemplates = new String[mappingTemplateResourcePaths.length];
		
		for (byte i = 0; i < mappingTemplateResourcePaths.length; i++) {
			try(FileBufferedReader reader = new FileBufferedReader(mappingTemplateResourcePaths[i])){
				mappingTemplates[i] = reader.readAll(2000).trim();
			}
		}
	}
}
