package com.liferay.test.web.util;

import com.liferay.test.web.constants.PagenationWebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class PagenationContextImpl<T> implements PagenationContext<T> {
	@Override
	public int getContainerStart() {
		return getCur() * getRowsPerPage();
	}

	@Override
	public int getContainerEnd() {
		return getContainerStart() + getRowsPerPage();
	}

	@Override
	public int getRowsPerPage() {

		return rowsPerPage;
	}

	@Override
	public void setRowsPerPage(int rowsPerPage) {

		this.rowsPerPage = rowsPerPage;
	}

	@Override
	public int getCur() {

		return cur;
	}

	@Override
	public void setCur(int cur) {

		this.cur = cur;
	}

	@Override
	public String getKeyword() {

		return keyword;
	}

	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public List<T> getResult() {
		return this.results;
	}

	@Override
	public void setResult(List<T> results) {
		this.results = results;
	}

	@Override
	public int getTotal() {
		return this.total;
	}

	@Override
	public void setTotal(int total) {
		this.total = total;
	}

	public int getRowPerPage() {
		return rowsPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowsPerPage = rowPerPage;
	}

	@Override
	public String getOrderByCol() {
		// TODO Auto-generated method stub
		return orderByCol;
	}

	@Override
	public void setOrderByCol(String orderByCol) {
		this.orderByCol = orderByCol;
	}

	@Override
	public String getOrderByType() {
		// TODO Auto-generated method stub
		return orderByType;
	}

	@Override
	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;

	}

	@Override
	public void setNextParams(HttpServletRequest request) {
		request.setAttribute(PagenationWebKeys.CUR, String.valueOf(getCur()));
		request.setAttribute(PagenationWebKeys.ORDER_BY_COL, String.valueOf(getOrderByCol()));
		request.setAttribute(PagenationWebKeys.ORDER_BY_TYPE, String.valueOf(getOrderByType()));
	}

	@Override
	public String toString() {
		return "rowsPerPage=" + String.valueOf(rowsPerPage) +
				" cur=" + String.valueOf(cur) +
				" keyword=" + (null!=keyword ? keyword : "null") +
				" results=" + (null!=results ? results.toString() : "null") +
				" total=" + String.valueOf(total) +
				" orderByCol=" + (null!=orderByCol ? orderByCol : "null") +
				" orderByType=" + (null!=orderByType ? orderByType : "null");
	}
	
	private int rowsPerPage = Integer.MIN_VALUE;
	private int cur = Integer.MIN_VALUE;

	private String keyword = null;

	private List<T> results = null;
	private int total = 0;

	private String orderByCol;
	private String orderByType;

}
