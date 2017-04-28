package com.liferay.test.service.util;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.service.permission.SampleSBPermission;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.util.Date;
import java.util.Locale;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	immediate = true, service = Indexer.class)
public class SampleSBIndexer
	extends BaseIndexer<SampleSB> {

	public static final String CLASS_NAME = SampleSB.class.getName();

	public SampleSBIndexer() {
		setDefaultSelectedFieldNames(Field.ASSET_TAG_NAMES, Field.COMPANY_ID,
			Field.CONTENT, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, String entryClassName,
		long entryClassPK, String actionId) throws Exception {

		return SampleSBPermission.contains(permissionChecker, entryClassPK,
			ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		SampleSB entry = sampleSBLocalService.getSampleSB(classPK);

		return isVisible(entry.getStatus(), status);
	}

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);
	}

	@Override
	protected void doDelete(SampleSB SampleSB) throws Exception {
		deleteDocument(SampleSB.getCompanyId(), SampleSB.getSamplesbId());
	}

	@Override
	protected Document doGetDocument(SampleSB sampleSB) throws Exception {

		// TODO : These should be modified according to your requirements.

		Document document = getBaseModelDocument(CLASS_NAME, sampleSB);

		// TODO : This should be replaced by assetSummaryFieldName field
		document.addText(Field.CAPTION, sampleSB.getTitle());

		// TODO : This should be repleaced by fullContentFieldName field.
		document.addText(Field.CONTENT,
			HtmlUtil.extractText(sampleSB.getSamplesbRichText()));

		// TODO : This should be replaced by assetTitleFieldName field
		document.addText(Field.DESCRIPTION, sampleSB.getTitle());

		document.addDate(Field.MODIFIED_DATE, sampleSB.getModifiedDate());

		// TODO : This should be replaced by assetTitleFieldName field
		document.addText(Field.SUBTITLE, sampleSB.getTitle());

		// TODO : This should be replaced by assetTitleFieldName field
		document.addText(Field.TITLE, sampleSB.getTitle());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(SampleSB SampleSB) throws Exception {
		Document document = getDocument(SampleSB);

		indexWriterHelper.updateDocument(getSearchEngineId(),
			SampleSB.getCompanyId(), document, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		SampleSB entry = sampleSBLocalService.getSampleSB(classPK);

		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected void reindexEntries(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery = sampleSBLocalService
			.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property statusProperty = PropertyFactoryUtil
						.forName("status");

					Integer[] statuses = {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_IN_TRASH };

					dynamicQuery.add(statusProperty.in(statuses));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SampleSB>() {

				@Override
				public void performAction(SampleSB entry) {
					try {
						Document document = getDocument(entry);

						indexableActionableDynamicQuery.addDocuments(document);
					} catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to index SampleSB entry "
									+ entry.getSamplesbId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference
	protected SampleSBLocalService sampleSBLocalService;

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	private static final Log _log = LogFactoryUtil
		.getLog(SampleSBIndexer.class);

}
