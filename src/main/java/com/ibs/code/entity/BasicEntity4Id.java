package com.ibs.code.entity;

import java.util.UUID;

import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class BasicEntity4Id implements Entity{
	
	protected String id;// 主键
	
	public String getId() {
		if(StringUtil.isEmpty(this.id)) 
			this.id = UUID.randomUUID().toString();
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
