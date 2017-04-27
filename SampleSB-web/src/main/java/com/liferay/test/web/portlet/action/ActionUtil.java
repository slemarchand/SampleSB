package com.liferay.test.web.portlet.action;

import com.google.common.collect.Lists;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
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
	 * Get Record
	 *
	 * @param primaryKey
	 *            Primary key
	 * @param idReset
	 *            true to reset primary key to 0
	 * @return SampleSB object
	 * @throws Exception
	 */
//	public static SampleSB getRecord(long primaryKey, boolean idReset) throws PortletException {
//
//		SampleSB record = null;
//
//		if (primaryKey > 0) {
//			// A recode has existed
//			try {
//				record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
//			} catch (PortalException e) {
//				throw new PortletException(e.getMessage());
//			}
//		} else {
//			// A recode doesn't exist
//			primaryKey = (true == idReset) ? 0 : CounterLocalServiceUtil.increment();
//			record = SampleSBLocalServiceUtil.createSampleSB(primaryKey);
//		}
//		return record;
//	}

	/**
	 * Get Record
	 *
	 * @param primaryKey
	 *            Primary key
	 * @return SampleSB object
	 * @throws PortletException 
	 */
	public static SampleSB getNewObject(long primaryKey) throws PortletException {

		primaryKey = (primaryKey <= 0) ? 0 : CounterLocalServiceUtil.increment();
		return SampleSBLocalServiceUtil.createSampleSB(primaryKey);
	}	
	
	/**
	 * Get Data list from Database
	 *
	 * @param request
	 *            PortletRequest
	 * @param pagenationContext
	 * @return SearchContainerResults<SampleSB>
	 */
	public static SearchContainerResults<SampleSB> getListFromDB(PortletRequest request,
			SearchContainer<?> searchContainer) {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletPreferences portletPreferences = request.getPreferences();

		// Filter type
		String prefsViewType = portletPreferences.getValue(SampleSBConfiguration.CONF_PREFS_VIEW_TYPE,
				SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT);

		long groupId = themeDisplay.getScopeGroupId();
		int containerStart = searchContainer.getStart();
		int containerEnd = searchContainer.getEnd();

		// TODO : need to impliment Comparator

		List<SampleSB> results = null;
		int total = 0;

		if (prefsViewType.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT)) {
			results = SampleSBLocalServiceUtil.findAllInGroup(groupId, containerStart, containerEnd, null);
			total = SampleSBLocalServiceUtil.countAllInGroup(groupId);

		} else if (prefsViewType.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_USER)) {
			results = SampleSBLocalServiceUtil.findAllInUser(themeDisplay.getUserId(), containerStart, containerEnd,
					null);
			total = SampleSBLocalServiceUtil.countAllInUser(themeDisplay.getUserId());

		} else {
			results = SampleSBLocalServiceUtil.findAllInUserAndGroup(themeDisplay.getUserId(), groupId, containerStart,
					containerEnd, null);
			total = SampleSBLocalServiceUtil.countAllInUserAndGroup(themeDisplay.getUserId(), groupId);

		}

		return new SearchContainerResults<>(results, total);
	}

	/**
	 * Get Data list from Index
	 *
	 * @param request
	 *            PortletRequest
	 * @param pagenationContext
	 * @return PagenationContext<SampleSB>
	 * @throws SearchException
	 */
	public static SearchContainerResults<SampleSB> getListFromIndex(PortletRequest request,
			SearchContainer<?> searchContainer) throws SearchException {

		// Search Key
		String searchFilter = ParamUtil.getString(request, DisplayTerms.KEYWORDS);

		Indexer<SampleSB> indexer = IndexerRegistryUtil.getIndexer(SampleSB.class);
		SearchContext searchContext = SearchContextFactory.getInstance(PortalUtil.getHttpServletRequest(request));

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
					_log.warn("SampleSB search index is stale and contains entry " + entryId);
				}

				continue;
			}
		}

		return new SearchContainerResults<>(tempResults, total);
	}

	/**
	 * Converte Date Time into Date()
	 * 
	 * @param request
	 *            PortletRequest
	 * @param prefix
	 *            Prefix of the parameter
	 * @return Date object
	 */
	public static Date getDateTimeFromRequest(PortletRequest request, String prefix) {
        int Year = ParamUtil.getInteger(request,prefix + "Year");
        int Month = ParamUtil.getInteger(request,prefix + "Month") + 1;
        int Day = ParamUtil.getInteger(request,prefix + "Day");
        int Hour = ParamUtil.getInteger(request,prefix + "Hour");
        int Minute = ParamUtil.getInteger(request,prefix + "Minute");
        int AmPm = ParamUtil.getInteger(request,prefix + "AmPm");

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
	public static SampleSB SampleSBFromRequest(long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		// Create or fetch existing data
		SampleSB sampleSB;
		if (primaryKey <= 0) {
			sampleSB = ActionUtil.getNewObject(primaryKey);
		} else {
			sampleSB = SampleSBLocalServiceUtil.fetchSampleSB(primaryKey);
		}

		sampleSB.setSamplesbId(primaryKey);
		sampleSB.setTitle(ParamUtil.getString(request, "title"));
		sampleSB.setStartDate(ActionUtil.getDateTimeFromRequest(request, "startDate"));
		sampleSB.setEndDate(ActionUtil.getDateTimeFromRequest(request, "endDate"));
		sampleSB.setSamplesbBooleanStat(ParamUtil.getBoolean(request, "samplesbBooleanStat"));
		sampleSB.setSamplesbDateTime(ActionUtil.getDateTimeFromRequest(request, "samplesbDateTime"));
		sampleSB.setSamplesbDocument(ParamUtil.getLong(request, "samplesbDocument"));
		sampleSB.setFolderDLId(ParamUtil.getLong(request, "folderDLId"));
		sampleSB.setSamplesbDocumentLibrary(ParamUtil.getString(request, "samplesbDocumentLibrary"));
		sampleSB.setSamplesbDouble(ParamUtil.getDouble(request, "samplesbDouble"));
		sampleSB.setSamplesbInteger(ParamUtil.getInteger(request, "samplesbInteger"));
		sampleSB.setSamplesbRichText(ParamUtil.getString(request, "samplesbRichText"));
		sampleSB.setSamplesbText(ParamUtil.getString(request, "samplesbText"));

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
	public static SampleSB SampleSBInitialize(long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		// Create or fetch existing data
		SampleSB sampleSB = ActionUtil.getNewObject(primaryKey);

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

		sampleSB.setCompanyId(themeDisplay.getCompanyId());
		sampleSB.setGroupId(themeDisplay.getScopeGroupId());
		sampleSB.setUserId(themeDisplay.getUserId());

		return sampleSB;
	}
	
	private static Log _log = LogFactoryUtil.getLog(ActionUtil.class);
}
