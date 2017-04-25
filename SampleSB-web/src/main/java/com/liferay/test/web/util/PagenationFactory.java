package com.liferay.test.web.util;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.web.constants.PagenationWebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class PagenationFactory {

	static final public int ROWS_PER_PAGE = 20;
	static final public int CUR_INIT = 0;

	static public <T> PagenationContext<T> create(PortletRequest request) {
		PagenationContext<T> pagenationContext = new PagenationContextImpl<>();

		pagenationContext
				.setRowsPerPage(ParamUtil.getInteger(request, PagenationWebKeys.ROWS_PER_PAGE, Integer.MIN_VALUE));
		pagenationContext.setCur(ParamUtil.getInteger(request, PagenationWebKeys.CUR, Integer.MIN_VALUE));
		pagenationContext.setOrderByCol(ParamUtil.getString(request, PagenationWebKeys.ORDER_BY_COL));
		pagenationContext.setOrderByType(ParamUtil.getString(request, PagenationWebKeys.ORDER_BY_TYPE));

		// Initialize if it's not
		if (!isInitilized(request)) {
			pagenationContext.setRowsPerPage(ROWS_PER_PAGE);
			pagenationContext.setCur(CUR_INIT);
			pagenationContext.setOrderByCol("");
			pagenationContext.setOrderByType("");
		}

		// Handling search searchFilter
		String searchFilter = ParamUtil.getString(request, PagenationWebKeys.SEARCH_FILTER, "");
		pagenationContext.setKeyword(searchFilter);

		// When search filter exists, reset cursor.
		if (Validator.isNotNull(searchFilter) || !searchFilter.equalsIgnoreCase("")) {
			pagenationContext.setCur(CUR_INIT);
		}

		return pagenationContext;
	}

	static public boolean isInitilized(PortletRequest request) {
		if (ParamUtil.getInteger(request, PagenationWebKeys.ROWS_PER_PAGE, Integer.MIN_VALUE) < 0
			|| ParamUtil.getInteger(request, PagenationWebKeys.CUR, Integer.MIN_VALUE) < 0) {
			return false;
		}
		return true;
	}
}
