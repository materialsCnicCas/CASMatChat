package com.materials.gpt.entity;

import java.io.Serializable;

public class PageInfo implements Serializable {

	private Integer pageNo = 1;
	private Integer pageSize = 10;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if(pageNo<=0){
			throw new RuntimeException("分页参数错误");
		}
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
