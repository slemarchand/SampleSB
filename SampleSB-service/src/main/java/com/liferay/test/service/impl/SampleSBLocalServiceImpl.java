package com.liferay.test.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.base.SampleSBLocalServiceBaseImpl;
import com.liferay.test.social.SampleSBActivityKeys;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.exception.TrashEntryException;
import com.liferay.trash.kernel.model.TrashEntry;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Yasuyuki Takeo
 */
public class SampleSBLocalServiceImpl
	extends SampleSBLocalServiceBaseImpl {

	@Override
	public void addEntryResources(
		long entryId, boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException {

		SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
		long entryId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, groupPermissions, guestPermissions);
	}

	@Override
	public void addEntryResources(
		SampleSB entry, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException {

		resourceLocalService.addResources(entry.getCompanyId(),
			entry.getGroupId(), entry.getUserId(), SampleSB.class.getName(),
			entry.getPrimaryKey(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEntryResources(
		SampleSB entry, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(entry.getCompanyId(),
			entry.getGroupId(), entry.getUserId(), SampleSB.class.getName(),
			entry.getPrimaryKey(), modelPermissions);
	}

	@Override
	public void addEntryResources(
		SampleSB entry, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(entry.getCompanyId(),
			entry.getGroupId(), entry.getUserId(), SampleSB.class.getName(),
			entry.getPrimaryKey(), groupPermissions, guestPermissions);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB addEntry(SampleSB orgEntry, ServiceContext serviceContext)
			throws PortalException {

		long userId = serviceContext.getUserId();

		SampleSB entry = _addSampleSB(orgEntry, serviceContext);

		// Resources

		if (serviceContext.isAddGroupPermissions()
				|| serviceContext.isAddGuestPermissions()) {

			addEntryResources(entry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		} else {
			addEntryResources(entry, serviceContext.getModelPermissions());
		}

		// Asset

		updateAsset(userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Workflow

		return startWorkflowInstance(userId, entry, serviceContext);
	}

	protected SampleSB startWorkflowInstance(
		long userId, SampleSB entry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		String userPortraitURL = StringPool.BLANK;
		String userURL = StringPool.BLANK;

		if (serviceContext.getThemeDisplay() != null) {
			User user = userPersistence.findByPrimaryKey(userId);

			userPortraitURL = user
				.getPortraitURL(serviceContext.getThemeDisplay());
			userURL = user.getDisplayURL(serviceContext.getThemeDisplay());
		}

		workflowContext.put(WorkflowConstants.CONTEXT_USER_PORTRAIT_URL,
			userPortraitURL);
		workflowContext.put(WorkflowConstants.CONTEXT_USER_URL, userURL);

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			entry.getCompanyId(), entry.getGroupId(), userId,
			SampleSB.class.getName(), entry.getPrimaryKey(), entry,
			serviceContext, workflowContext);
	}

	public int countAllInGroup(long groupId) {
		int count = sampleSBPersistence.countByG_S(groupId,
			WorkflowConstants.STATUS_APPROVED);
		return count;
	}

	public int countAllInUser(long userId) {
		int count = sampleSBPersistence.countByU_S(userId,
			WorkflowConstants.STATUS_APPROVED);
		return count;
	}

	public int countAllInUserAndGroup(long userId, long groupId) {
		int count = sampleSBPersistence.countByG_U_S(groupId, userId,
			WorkflowConstants.STATUS_APPROVED);
		return count;
	}

	public SampleSB deleteEntry(long primaryKey) throws PortalException {
		SampleSB entry = getSampleSB(primaryKey);
		return deleteEntry(entry);
	}
	
	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SampleSB deleteEntry(SampleSB entry) throws PortalException {

		// Entry

		sampleSBPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(entry.getCompanyId(),
			SampleSB.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
			entry.getPrimaryKey());

		// Asset

		assetEntryLocalService.deleteEntry(SampleSB.class.getName(),
			entry.getPrimaryKey());

		// Comment

		deleteDiscussion(entry);

		// Ratings

		ratingsStatsLocalService.deleteStats(SampleSB.class.getName(),
			entry.getPrimaryKey());

		// Trash

		trashEntryLocalService.deleteEntry(SampleSB.class.getName(),
			entry.getPrimaryKey());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			entry.getCompanyId(), entry.getGroupId(), SampleSB.class.getName(),
			entry.getPrimaryKey());

		return entry;
	}

	protected void deleteDiscussion(SampleSB entry) throws PortalException {
		CommentManagerUtil.deleteDiscussion(SampleSB.class.getName(),
			entry.getPrimaryKey());
	}

	public List<SampleSB> findAllInGroup(long groupId) {
		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence
			.findByG_S(groupId, WorkflowConstants.STATUS_APPROVED);
		return list;
	}

	public List<SampleSB> findAllInGroup(
		long groupId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
		return list;
	}

	public List<SampleSB> findAllInGroup(
		long groupId, OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) findAllInGroup(groupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	/**
	 * Get a user information
	 *
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public List<SampleSB> findAllInUser(long userId) {
		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence
			.findByU_S(userId, WorkflowConstants.STATUS_APPROVED);
		return list;
	}

	public List<SampleSB> findAllInUser(
		long userId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByU_S(
			userId, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
		return list;
	}

	public List<SampleSB> findAllInUser(
		long userId, OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) findAllInUser(userId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	public List<SampleSB> findAllInUserAndGroup(long userId, long groupId) {
		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence
			.findByG_U_S(groupId, userId, WorkflowConstants.STATUS_APPROVED);
		return list;
	}

	public List<SampleSB> findAllInUserAndGroup(
		long userId, long groupId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_U_S(
			groupId, userId, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
		return list;
	}

	public List<SampleSB> findAllInUserAndGroup(
		long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator) {

		List<SampleSB> list = (List<SampleSB>) findAllInUserAndGroup(groupId,
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	/**
	 * Get Company entries
	 *
	 * @param companyId Company Id
	 * @param status Workflow status
	 * @param start start index of entries
	 * @param end end index of entries
	 * @return
	 * @throws SystemException
	 */
	public List<SampleSB> getCompanyEntries(
		long companyId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return sampleSBPersistence.findByCompanyId(companyId, start, end);
		} else {
			return sampleSBPersistence.findByC_S(companyId, status, start, end);
		}
	}

	/**
	 * Get Company entries
	 *
	 * @param companyId Company Id
	 * @param status Workflow status
	 * @param start start index of entries
	 * @param end end index of entries
	 * @param obc Comparator for the order
	 * @return List of entries
	 * @throws SystemException
	 */
	public List<SampleSB> getCompanyEntries(
		long companyId, int status, int start, int end,
		OrderByComparator<SampleSB> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return sampleSBPersistence.findByCompanyId(companyId, start, end,
				obc);
		} else {
			return sampleSBPersistence.findByC_S(companyId, status, start, end,
				obc);
		}
	}

	/**
	 * Get Company entries counts
	 *
	 * @param companyId
	 * @param status
	 * @return
	 * @throws SystemException
	 */
	public int getCompanyEntriesCount(long companyId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return sampleSBPersistence.countByCompanyId(companyId);
		} else {
			return sampleSBPersistence.countByC_S(companyId, status);
		}
	}

	public SampleSB getSampleSBByUrlTitle(
		long groupId, String urlTitle, int status) throws PortalException {

		SampleSB entry = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			entry = sampleSBPersistence.fetchByG_UT(groupId, urlTitle);
		} else {
			List<SampleSB> results = sampleSBPersistence.findByG_UT_ST(groupId,
				urlTitle, status);

			if (results != null && results.size() > 0) {
				entry = results.get(0);
			}
		}

		return entry;
	}


	/**
	 * Moves the entry to the recycle bin. Social activity counters for this
	 * entry get disabled.
	 *
	 * @param userId the primary key of the user moving the entry
	 * @param entry the entry to be moved
	 * @return the moved entry
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB moveEntryToTrash(long userId, SampleSB entry)
		throws PortalException {

		// Entry
		if (entry.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = entry.getStatus();

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			entry.setStatus(WorkflowConstants.STATUS_DRAFT);

			sampleSBPersistence.update(entry);
		}

		entry = updateStatus(userId, entry.getPrimaryKey(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext(),
			new HashMap<String, Serializable>());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		// TODO: This field should be assetTitleFieldName
		extraDataJSONObject.put("title", entry.getTitle());

		SocialActivityManagerUtil.addActivity(userId, entry,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				entry.getCompanyId(), entry.getGroupId(),
				SampleSB.class.getName(), entry.getPrimaryKey());
		}

		return entry;
	}

	public SampleSB moveEntryToTrash(long userId, long entryId)
		throws PortalException {

		SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

		return moveEntryToTrash(userId, entry);
	}
	
	/**
	 * Restores the entry with the ID from the recycle bin. Social activity
	 * counters for this entry get activated.
	 *
	 * @param userId the primary key of the user restoring the entry
	 * @param entryId the primary key of the entry to be restored
	 * @return the restored entry from the recycle bin
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		// Entry

		SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

		if (!entry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		TrashEntry trashEntry = trashEntryLocalService
			.getEntry(SampleSB.class.getName(), entryId);

		updateStatus(userId, entryId, trashEntry.getStatus(),
			new ServiceContext(), new HashMap<String, Serializable>());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		// TODO: This field should be assetTitleFieldName
		extraDataJSONObject.put("title", entry.getTitle());

		SocialActivityManagerUtil.addActivity(userId, entry,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return entry;
	}

	@Override
	public void updateAsset(
		long userId, SampleSB entry, long[] assetCategoryIds,
		String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		boolean visible = false;

		if (entry.isApproved()) {
			visible = true;
		}

		String summary = HtmlUtil.extractText(
			StringUtil.shorten(entry.getSamplesbSummaryName(), 500));

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(userId,
			entry.getGroupId(), entry.getCreateDate(), entry.getModifiedDate(),
			SampleSB.class.getName(), entry.getPrimaryKey(), entry.getUuid(), 0,
			assetCategoryIds, assetTagNames, true, visible, null, null, null,
			null, ContentTypes.TEXT_HTML, entry.getSamplesbTitleName(), null,
			summary, null, null, 0, 0, priority);

		assetLinkLocalService.updateLinks(userId, assetEntry.getEntryId(),
			assetLinkEntryIds, AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public void updateEntryResources(
		SampleSB entry, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		resourceLocalService.updateResources(entry.getCompanyId(),
			entry.getGroupId(), SampleSB.class.getName(), entry.getPrimaryKey(),
			groupPermissions, guestPermissions);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB updateEntry(
		SampleSB entry, ServiceContext serviceContext) throws PortalException {

		User user = userPersistence.findByPrimaryKey(entry.getUserId());

		entry.setUserName(user.getFullName());

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
		entry.setUrlTitle(_getUniqueURLTitle(entry));

		if (entry.isPending() || entry.isDraft()) {
		} else {
			entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		SampleSB updatedEntry = sampleSBPersistence.update(entry);

		// Asset
		updateAsset(updatedEntry.getUserId(), updatedEntry,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		updatedEntry = startWorkflowInstance(user.getUserId(), updatedEntry,
			serviceContext);

		return updatedEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	public SampleSB updateStatus(
		long userId, long entryId, int status, ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

		int oldStatus = entry.getStatus();

		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setStatus(status);
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(serviceContext.getModifiedDate(now));

		sampleSBPersistence.update(entry);

		AssetEntry assetEntry = assetEntryLocalService
			.fetchEntry(SampleSB.class.getName(), entryId);

		if ((assetEntry == null) || (assetEntry.getPublishDate() == null)) {
			serviceContext.setCommand(Constants.ADD);
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		// TODO: this field should be assetTitleFieldName
		extraDataJSONObject.put("title", entry.getTitle());

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateEntry(SampleSB.class.getName(),
				entryId, entry.getModifiedDate(), null, true, true);

			// Social

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH)
					&& (oldStatus != WorkflowConstants.STATUS_SCHEDULED)) {

				if (serviceContext.isCommandUpdate()) {

					SocialActivityManagerUtil.addActivity(user.getUserId(),
						entry, SampleSBActivityKeys.UPDATE_SAMPLESB,
						extraDataJSONObject.toString(), 0);
				} else {
					SocialActivityManagerUtil.addUniqueActivity(
						user.getUserId(), entry,
						SampleSBActivityKeys.ADD_SAMPLESB,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil.restoreDiscussionFromTrash(
					SampleSB.class.getName(), entryId);

				trashEntryLocalService.deleteEntry(SampleSB.class.getName(),
					entryId);
			}

		} else {

			// Asset

			assetEntryLocalService.updateVisible(
				SampleSB.class.getName(),entryId, false);

			// Social

			if ((status == WorkflowConstants.STATUS_SCHEDULED) &&
					(oldStatus != WorkflowConstants.STATUS_IN_TRASH)) {

				if (serviceContext.isCommandUpdate()) {

					SocialActivityManagerUtil.addActivity(user.getUserId(),
						entry, SampleSBActivityKeys.UPDATE_SAMPLESB,
						extraDataJSONObject.toString(), 0);
				} else {
					SocialActivityManagerUtil.addUniqueActivity(
						user.getUserId(), entry,
						SampleSBActivityKeys.ADD_SAMPLESB,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (status == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil
					.moveDiscussionToTrash(SampleSB.class.getName(), entryId);

				trashEntryLocalService.addTrashEntry(
					userId, entry.getGroupId(), SampleSB.class.getName(),
					entry.getPrimaryKey(), entry.getUuid(), null, oldStatus, null,
					null);
				

			} else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil.restoreDiscussionFromTrash(
					SampleSB.class.getName(), entryId);

				trashEntryLocalService.deleteEntry(SampleSB.class.getName(),
					entryId);
			}

		}

		return entry;
	}

	protected SampleSB _addSampleSB(SampleSB entry, ServiceContext serviceContext)
		throws PortalException {

		SampleSB newEntry = sampleSBPersistence
			.create(counterLocalService.increment(SampleSB.class.getName()));

		User user = userPersistence.findByPrimaryKey(entry.getUserId());

		Date now = new Date();
		newEntry.setCompanyId(entry.getCompanyId());
		newEntry.setGroupId(entry.getGroupId());
		newEntry.setUserId(user.getUserId());
		newEntry.setUserName(user.getFullName());
		newEntry.setCreateDate(now);
		newEntry.setModifiedDate(now);

		newEntry.setUuid(serviceContext.getUuid());
		newEntry.setUrlTitle(_getUniqueURLTitle(newEntry));
		newEntry.setSamplesbTitleName(entry.getSamplesbTitleName());
		newEntry.setSamplesbSummaryName(entry.getSamplesbSummaryName());

		newEntry.setTitle(entry.getTitle());
		newEntry.setStartDate(entry.getStartDate());
		newEntry.setEndDate(entry.getEndDate());
		newEntry.setSamplesbBooleanStat(entry.getSamplesbBooleanStat());
		newEntry.setSamplesbDateTime(entry.getSamplesbDateTime());
		newEntry.setSamplesbDocument(entry.getSamplesbDocument());
		newEntry.setFolderDLId(entry.getFolderDLId());
		newEntry.setSamplesbDocumentLibrary(entry.getSamplesbDocumentLibrary());
		newEntry.setSamplesbDouble(entry.getSamplesbDouble());
		newEntry.setSamplesbInteger(entry.getSamplesbInteger());
		newEntry.setSamplesbRichText(entry.getSamplesbRichText());
		newEntry.setSamplesbText(entry.getSamplesbText());
		newEntry.setFolderDLId(entry.getFolderDLId());

		return sampleSBPersistence.update(newEntry);
	}

	protected String _createUrlTitle(long entryId, String title) {
		if (title == null) {
			return String.valueOf(entryId);
		}

		title = title.trim().toLowerCase();

		if (Validator.isNull(title) || Validator.isNumber(title)) {
			title = String.valueOf(entryId);
		} else {
			title = FriendlyURLNormalizerUtil.normalize(title,
				_friendlyURLPattern);
		}

		return ModelHintsUtil.trimString(SampleSB.class.getName(), "urlTitle",
			title);
	}

	protected String _getUniqueURLTitle(SampleSB entry) {
		String urlTitle = _createUrlTitle(entry.getPrimaryKey(),
			entry.getSamplesbTitleName());

		long entryId = entry.getPrimaryKey();

		for (int i = 1;; i++) {
			SampleSB tmpEntry = sampleSBPersistence
				.fetchByG_UT(entry.getGroupId(), urlTitle);

			if ((tmpEntry == null) || (entryId == tmpEntry.getPrimaryKey())) {
				break;
			} else {
				String suffix = StringPool.DASH + i;

				String prefix = urlTitle;

				if (urlTitle.length() > suffix.length()) {
					prefix = urlTitle.substring(0,
						urlTitle.length() - suffix.length());
				}

				urlTitle = prefix + suffix;
			}
		}

		return urlTitle;
	}

	private static Pattern _friendlyURLPattern = Pattern.compile("[^a-z0-9_-]");

}