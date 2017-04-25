package com.liferay.test.web.util;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class EntryListContainerFactory {
	static public EntryListContainer<SampleSB> create(PortletRequest request,
			PagenationContext<SampleSB> pagenationContext) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		long userId = themeDisplay.getUserId();

//		// TODO : Need to impliment comparator
//		// TODO : Need to consider of Indexer when a search filter is not
//		// populated.
//		@SuppressWarnings("unchecked")
//		List<SampleSB> results = SampleSBLocalServiceUtil.findAllInUserAndGroup(
//				userId, groupId,
//				QueryUtil.ALL_POS,//pagenationContext.getContainerStart(), 
//				QueryUtil.ALL_POS,//pagenationContext.getContainerEnd(), 
//				null);
//		
//		int total = SampleSBLocalServiceUtil.countAllInUserAndGroup(userId, groupId);
		
		List<SampleSB> results = SampleSBLocalServiceUtil.findAllInGroup(groupId, null);
		int total = SampleSBLocalServiceUtil.countAllInGroup(groupId);								

		return new EntryListContainer<SampleSB>() {
			@Override
			public List<SampleSB> getResults() {
				return results;
			}

			@Override
			public int getTotal() {
				return total;
			}
		};
	}
}
