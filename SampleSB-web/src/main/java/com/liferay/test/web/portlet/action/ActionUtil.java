package com.liferay.test.web.portlet.action;

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
import com.liferay.test.service.SampleSBLocalServiceUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class ActionUtil {

	/**
	 * Order string to boolean
	 * 
	 * @param order
	 * @return if true if order is "asc" or false
	 */
	protected static boolean getOrder(String order) {
		return ("asc".equalsIgnoreCase(order)) ? true : false;
	}

	/**
	 * 
	 * Order Comparetor
	 * 
	 * @param searchContainer
	 * @return OrderByComparator
	 */
	public static OrderByComparator<SampleSB> getOrderByComparator(
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
	public static SearchContainerResults<SampleSB> getListFromDB(
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
			results = SampleSBLocalServiceUtil.findAllInGroup(groupId,
				containerStart, containerEnd, orderByComparator);
			total = SampleSBLocalServiceUtil.countAllInGroup(groupId);

		} else if (prefsViewType
			.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_USER)) {
			results = SampleSBLocalServiceUtil.findAllInUser(
				themeDisplay.getUserId(), containerStart, containerEnd,
				orderByComparator);
			total = SampleSBLocalServiceUtil
				.countAllInUser(themeDisplay.getUserId());

		} else {
			results = SampleSBLocalServiceUtil.findAllInUserAndGroup(
				themeDisplay.getUserId(), groupId, containerStart, containerEnd,
				orderByComparator);
			total = SampleSBLocalServiceUtil
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
	public static SearchContainerResults<SampleSB> getListFromIndex(
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
				resReg = SampleSBLocalServiceUtil.getSampleSB(entryId);

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

	/**
	 * Converte Date Time into Date()
	 * 
	 * @param request PortletRequest
	 * @param prefix Prefix of the parameter
	 * @return Date object
	 */
	public static
		Date getDateTimeFromRequest(PortletRequest request, String prefix) {
		int Year = ParamUtil.getInteger(request, prefix + "Year");
		int Month = ParamUtil.getInteger(request, prefix + "Month") + 1;
		int Day = ParamUtil.getInteger(request, prefix + "Day");
		int Hour = ParamUtil.getInteger(request, prefix + "Hour");
		int Minute = ParamUtil.getInteger(request, prefix + "Minute");
		int AmPm = ParamUtil.getInteger(request, prefix + "AmPm");

		if (AmPm == Calendar.PM) {
			Hour += 12;
		}

		LocalDateTime ldt = LocalDateTime.of(Year, Month, Day, Hour, Minute, 0);
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Populate Model with values from a form
	 *
	 * @param request PortletRequest
	 * @return SampleSB Object
	 * @throws PortletException
	 */
	public static SampleSB getSampleSBFromRequest(
		long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB sampleSB;
		if (primaryKey <= 0) {
			sampleSB = SampleSBLocalServiceUtil.getNewObject(primaryKey);
		} else {
			sampleSB = SampleSBLocalServiceUtil.fetchSampleSB(primaryKey);
		}

		sampleSB.setSamplesbId(primaryKey);
		sampleSB.setTitle(ParamUtil.getString(request, "title"));
		sampleSB.setStartDate(
			ActionUtil.getDateTimeFromRequest(request, "startDate"));
		sampleSB
			.setEndDate(ActionUtil.getDateTimeFromRequest(request, "endDate"));
		sampleSB.setSamplesbBooleanStat(
			ParamUtil.getBoolean(request, "samplesbBooleanStat"));
		sampleSB.setSamplesbDateTime(
			ActionUtil.getDateTimeFromRequest(request, "samplesbDateTime"));
		sampleSB.setSamplesbDocument(
			ParamUtil.getLong(request, "samplesbDocument"));
		sampleSB.setFolderDLId(ParamUtil.getLong(request, "folderDLId"));
		sampleSB.setSamplesbDocumentLibrary(
			ParamUtil.getString(request, "samplesbDocumentLibrary"));
		sampleSB
			.setSamplesbDouble(ParamUtil.getDouble(request, "samplesbDouble"));
		sampleSB.setSamplesbInteger(
			ParamUtil.getInteger(request, "samplesbInteger"));
		sampleSB.setSamplesbRichText(
			ParamUtil.getString(request, "samplesbRichText"));
		sampleSB.setSamplesbText(ParamUtil.getString(request, "samplesbText"));
		sampleSB.setSamplesbTitleName(
			ParamUtil.getString(request, "samplesbTitleName"));
		sampleSB.setSamplesbSummaryName(
			ParamUtil.getString(request, "samplesbSummaryName"));

		sampleSB.setCompanyId(themeDisplay.getCompanyId());
		sampleSB.setGroupId(themeDisplay.getScopeGroupId());
		sampleSB.setUserId(themeDisplay.getUserId());

		return sampleSB;
	}

	/**
	 * Populate Model with values from a form
	 *
	 * @param primaryKey primaly key
	 * @param request PortletRequest
	 * @return SampleSB Object
	 * @throws PortletException
	 */
	public static SampleSB getInitializedSampleSB(
		long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB sampleSB = SampleSBLocalServiceUtil.getNewObject(primaryKey);

		sampleSB.setSamplesbId(primaryKey);
		sampleSB.setTitle("");
		sampleSB.setStartDate(new Date());
		sampleSB.setEndDate(new Date());
		sampleSB.setSamplesbBooleanStat(true);
		sampleSB.setSamplesbDateTime(new Date());
		sampleSB.setSamplesbDocument(0);
		sampleSB.setFolderDLId(0);
		sampleSB.setSamplesbDocumentLibrary("");
		sampleSB.setSamplesbDouble(0.0);
		sampleSB.setSamplesbInteger(0);
		sampleSB.setSamplesbRichText("");
		sampleSB.setSamplesbText("");
		sampleSB.setSamplesbTitleName("");
		sampleSB.setSamplesbSummaryName("");

		sampleSB.setCompanyId(themeDisplay.getCompanyId());
		sampleSB.setGroupId(themeDisplay.getScopeGroupId());
		sampleSB.setUserId(themeDisplay.getUserId());

		return sampleSB;
	}

	private static Log _log = LogFactoryUtil.getLog(ActionUtil.class);
}
