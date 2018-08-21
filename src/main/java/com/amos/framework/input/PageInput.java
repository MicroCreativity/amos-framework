package com.amos.framework.input;

import java.io.Serializable;

/**
 * ================================
 * 作者:linyantao
 * 时间:2018年7月4日下午4:15:29
 * 内容:分页请求报文数据传输对象
 * ================================
 */
public class PageInput<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1191629728566992069L;

	/**
	 * 当前页数
	 */
	private int currentPage;
	
	/**
	 * 每页显示条数
	 */
	private int pageSize;
	
	/**
	 * 请求业务参数
	 */
	private T input;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public T getInput() {
		return input;
	}

	public void setInput(T input) {
		this.input = input;
	}
	
	
}
