package com.liferay.test.web.util;

import com.google.common.collect.Lists;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchContainerResults;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.web.portlet.action.SampleSBConfiguration;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = SampleSBViewHelper.class)
public class SampleSBViewHelper {
	/**
	 * Order string to boolean
	 * 
	 * @param order
	 * @return if true if order is "asc" or false
	 */
	protected boolean getOrder(String order) {
		return ("asc".equalsIgnoreCase(order)) ? true : false;
	}

	/**
	 * 
	 * Order Comparetor
	 * 
	 * @param searchContainer
	 * @return OrderByComparator
	 */
	public OrderByComparator<SampleSB> getOrderByComparator(
		SearchContainer<?> searchContainer) {

		if (_log.isDebugEnabled()) {
			_log.debug("searchContainer.getOrderByCol()"
					+ (null != searchContainer.getOrderByCol()
						? searchContainer.getOrderByCol()
						: "null"));
			_log.debug("searchContainer.getOrderByType()"
					+ (null != searchContainer.getOrderByType()
						? searchContainer.getOrderByType()
						: "null"));
		}

		return OrderByComparatorFactoryUtil.create("SampleSB_SampleSB",
			searchContainer.getOrderByCol(),
			getOrder(searchContainer.getOrderByType()));
	}

	/**
	 * Get Data list from Database
	 *
	 * @param request PortletRequest
	 * @param searchContainer SearchContainer<?>
	 * @return SearchContainerResults<SampleSB>
	 */
	public SearchContainerResults<SampleSB> getListFromDB(
		PortletRequest request, SearchContainer<?> searchContainer) {

		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);
		PortletPreferences portletPreferences = request.getPreferences();

		// Filter type
		String prefsViewType = portletPreferences.getValue(
			SampleSBConfiguration.CONF_PREFS_VIEW_TYPE,
			SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT);

		long groupId = themeDisplay.getScopeGroupId();
		int containerStart = searchContainer.getStart();
		int containerEnd = searchContainer.getEnd();

		List<SampleSB> results = null;
		int total = 0;

		// Get Order
		OrderByComparator<SampleSB> orderByComparator = getOrderByComparator(
			searchContainer);

		if (prefsViewType
			.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT)) {
			results = _sampleSBLocalService.findAllInGroup(groupId,
				containerStart, containerEnd, orderByComparator);
			total = _sampleSBLocalService.countAllInGroup(groupId);

		} else if (prefsViewType
			.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_USER)) {
			results = _sampleSBLocalService.findAllInUser(
				themeDisplay.getUserId(), containerStart, containerEnd,
				orderByComparator);
			total = _sampleSBLocalService
				.countAllInUser(themeDisplay.getUserId());

		} else {
			results = _sampleSBLocalService.findAllInUserAndGroup(
				themeDisplay.getUserId(), groupId, containerStart, containerEnd,
				orderByComparator);
			total = _sampleSBLocalService
				.countAllInUserAndGroup(themeDisplay.getUserId(), groupId);

		}

		return new SearchContainerResults<>(results, total);
	}

	/**
	 * Get Data list from Index
	 *
	 * @param request PortletRequest
	 * @return searchContainer SearchContainer<?>
	 * @throws SearchException
	 */
	public SearchContainerResults<SampleSB> getListFromIndex(
		PortletRequest request, SearchContainer<?> searchContainer)
		throws SearchException {

		// Search Key
		String searchFilter = ParamUtil.getString(request,
			DisplayTerms.KEYWORDS);

		Indexer<SampleSB> indexer = IndexerRegistryUtil
			.nullSafeGetIndexer(SampleSB.class);

		SearchContext searchContext = SearchContextFactory
			.getInstance(PortalUtil.getHttpServletRequest(request));

		searchContext.setKeywords(searchFilter);
		searchContext.setStart(searchContainer.getStart());
		searchContext.setEnd(searchContainer.getEnd());

		// Search in index
		Hits results = indexer.search(searchContext);

		// Initialize return values
		int total = results.getLength();
		List<SampleSB> tempResults = Lists.newArrayList();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			SampleSB resReg = null;

			// Entry
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			try {
				resReg = _sampleSBLocalService.getSampleSB(entryId);

				resReg = resReg.toEscapedModel();

				tempResults.add(resReg);
			} catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"SampleSB search index is stale and contains entry "
								+ entryId);
				}

				continue;
			}
		}

		return new SearchContainerResults<>(tempResults, total);
	}

	@Reference(unbind = "-")
	protected void setSampleSBLocalService(
		SampleSBLocalService samplesblocalservice) {
		_sampleSBLocalService = samplesblocalservice;
	}

	private SampleSBLocalService _sampleSBLocalService;
	
	private static Log _log = LogFactoryUtil
		.getLog(SampleSBViewHelper.class);
}
