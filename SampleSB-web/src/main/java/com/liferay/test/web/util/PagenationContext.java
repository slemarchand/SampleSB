package com.liferay.test.web.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yasuyuki Takeo
 */
public interface PagenationContext<T> {

    public int getContainerStart();

    public int getContainerEnd();

    public int getRowsPerPage();

    public void setRowsPerPage(int rowsPerPage);

    public int getCur();

    public void setCur(int cur);

    public String getKeyword();

    public void setKeyword(String keyword);

    public List<T> getResult();

    public void setResult(List<T> results);

    public int getTotal();

    public void setTotal(int total);
    
    public String getOrderByCol();
    
    public void setOrderByCol(String orderByCol);
    
    public String getOrderByType();
    
    public void setOrderByType(String orderByType);
    
    public void setNextParams(HttpServletRequest request);
    
    public String toString();
}
