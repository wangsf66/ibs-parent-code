package com.ibs.components.filters.dynamic.table.entity;

import java.util.Date;

import com.ibs.code.entity.Entity;

/**
 * 动态表
 * @author DougLei
 */
public class DynamicTable implements Entity {
	private static final long serialVersionUID = -7381601662423509725L;

	private int id;// 主键
	
	private int index;// 索引
	private String projectId;// 所属项目id
	
	private String createUserId;// 创建人id
	private Date createDate;// 创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
