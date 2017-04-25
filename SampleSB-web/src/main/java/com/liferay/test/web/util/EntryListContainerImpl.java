package com.liferay.test.web.util;

import java.util.List;

/**
 * @author Yasuyuki Takeo
 */
public class EntryListContainerImpl<T> implements EntryListContainer<T> {

	private List<T> result;
	private int total;

	@Override
	public List<T> getResults() {
		// TODO Auto-generated method stub
		return this.result;
	}

	@Override
	public void setResults(List<T> results) {
		this.result = results;
	}

	@Override
	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return this.total;
	}

}
