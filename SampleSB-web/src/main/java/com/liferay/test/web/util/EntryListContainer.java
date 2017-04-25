package com.liferay.test.web.util;

import java.util.List;

/**
 * @author Yasuyuki Takeo
 */
public interface EntryListContainer<T> {
    public List<T> getResults();
    public int getTotal();
}
