package com.liferay.test.web.portlet.action;

import com.google.common.collect.Lists;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
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
import com.liferay.test.web.constants.PagenationWebKeys;
import com.liferay.test.web.util.EntryListContainer;
import com.liferay.test.web.util.EntryListContainerImpl;
import com.liferay.test.web.util.PagenationContext;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class ActionUtil {

    /**
     * Get Record
     *
     * @param primaryKey Primary key
     * @param idReset    true to reset primary key to 0
     * @return SampleSB object
     * @throws Exception
     */
    public static SampleSB getRecord(long primaryKey, boolean idReset) throws Exception {

        SampleSB record = null;

        if (primaryKey > 0) {
            // A recode has existed
            record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
        } else {
            // A recode doesn't exist
            primaryKey = CounterLocalServiceUtil.increment();
            record = SampleSBLocalServiceUtil.createSampleSB(primaryKey);
            if (true == idReset) {
                // Reset primary key
                record.setPrimaryKey(0);
            }
        }

        return record;
    }

    /**
     * Get Data list from Database
     *
     * @param request           PortletRequest
     * @param pagenationContext
     * @return EntryListContainer<SampleSB>
     */
    public static EntryListContainer<SampleSB> getListFromDB(
        PortletRequest request,
        PagenationContext<SampleSB> pagenationContext
    ) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        PortletPreferences portletPreferences = request.getPreferences();

        // Filter type
        String prefsViewType = portletPreferences.getValue(
            SampleSBConfiguration.CONF_PREFS_VIEW_TYPE,
            SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT
        );

        long groupId = themeDisplay.getScopeGroupId();
        int containerStart = pagenationContext.getContainerStart();
        int containerEnd = pagenationContext.getContainerEnd();

        // TODO : need to impliment Comparator

        List<SampleSB> results = null;
        int total = 0;

        if (prefsViewType.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT)) {
            results = SampleSBLocalServiceUtil.findAllInGroup(
                groupId,
                containerStart,
                containerEnd,
                null
            );
            total = SampleSBLocalServiceUtil.countAllInGroup(groupId);

        } else if (prefsViewType.equals(SampleSBConfiguration.PREFS_VIEW_TYPE_USER)) {
            results = SampleSBLocalServiceUtil.findAllInUser(
                themeDisplay.getUserId(),
                containerStart,
                containerEnd,
                null
            );
            total = SampleSBLocalServiceUtil.countAllInUser(themeDisplay.getUserId());

        } else {
            results = SampleSBLocalServiceUtil.findAllInUserAndGroup(
                themeDisplay.getUserId(),
                groupId,
                containerStart,
                containerEnd, null
            );
            total = SampleSBLocalServiceUtil.countAllInUserAndGroup(themeDisplay.getUserId(), groupId);

        }

        EntryListContainer<SampleSB> entryListContainer = new EntryListContainerImpl<SampleSB>();
        entryListContainer.setResults(results);
        entryListContainer.setTotal(total);
        return entryListContainer;
    }

    /**
     * Get Data list from Index
     *
     * @param request           PortletRequest
     * @param pagenationContext
     * @return PagenationContext<SampleSB>
     * @throws SearchException
     */
    public static EntryListContainer<SampleSB> getListFromIndex(
        PortletRequest request,
        PagenationContext<SampleSB> pagenationContext
    ) throws SearchException {

        // Search Key
        String searchFilter = ParamUtil.getString(request, PagenationWebKeys.SEARCH_FILTER);

        int containerStart = pagenationContext.getContainerStart();
        int containerEnd = pagenationContext.getContainerEnd();
        Indexer<SampleSB> indexer = IndexerRegistryUtil.getIndexer(SampleSB.class);
        SearchContext searchContext = SearchContextFactory.getInstance(PortalUtil.getHttpServletRequest(request));

        searchContext.setEnd(containerEnd);
        searchContext.setKeywords(searchFilter);
        searchContext.setStart(containerStart);

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

        EntryListContainer<SampleSB> entryListContainer = new EntryListContainerImpl<SampleSB>();
        entryListContainer.setResults(tempResults);
        entryListContainer.setTotal(total);
        return entryListContainer;
    }

    private static Log _log = LogFactoryUtil.getLog(ActionUtil.class);
}
