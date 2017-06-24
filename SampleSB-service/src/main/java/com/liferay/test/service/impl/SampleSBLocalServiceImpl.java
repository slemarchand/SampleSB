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
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.test.exception.SampleSBValidateException;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.base.SampleSBLocalServiceBaseImpl;
import com.liferay.test.service.util.SampleSBValidator;
import com.liferay.test.social.SampleSBActivityKeys;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.exception.TrashEntryException;
import com.liferay.trash.kernel.model.TrashEntry;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

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

	/**
	 * Add Entry
	 * 
	 * @param orgEntry SampleSB model
	 * @param serviceContext ServiceContext
	 * @exception PortalException
	 * @exception SampleSBValidateException
	 * @return created SampleSB model.
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB addEntry(SampleSB orgEntry, ServiceContext serviceContext)
		throws PortalException, SampleSBValidateException {

		long userId = serviceContext.getUserId();

		//Validation
		
		ModelValidator<SampleSB> modelValidator = new SampleSBValidator();
		modelValidator.validate(orgEntry);
		
		SampleSB entry = _addEntry(orgEntry, serviceContext);
		
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

	/**
	 * Start workflow
	 * 
	 * @param userId User id of this model's owner
	 * @param entry model object
	 * @param serviceContext ServiceContext
	 * @return model with workflow configrations.
	 * @throws PortalException
	 */
	protected SampleSB startWorkflowInstance(
		long userId, SampleSB entry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<String, Serializable>();

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

	/**
	 * Delete entry
	 * 
	 * @param entry SampleSB
	 * @return SampleSB oject
	 * @exception PortalException
	 */
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

	/**
	 * Delete discussion (comments)
	 * 
	 * @param entry
	 * @throws PortalException
	 */
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
	 * Moves the entry to the recycle bin.
	 * 
	 * Social activity counters for this entry get disabled.
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

	/**
	 * Edit Entry
	 * 
	 * @param orgEntry SampleSB model
	 * @param serviceContext ServiceContext
	 * @exception PortalException
	 * @exception SampleSBValidateException
	 * @return updated SampleSB model.
	 */	
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleSB updateEntry(SampleSB orgEntry, ServiceContext serviceContext)
			throws PortalException, SampleSBValidateException {

		User user = userPersistence.findByPrimaryKey(orgEntry.getUserId());

        //Validation
        
        ModelValidator<SampleSB> modelValidator = new SampleSBValidator();
        modelValidator.validate(orgEntry);
        
		// Update entry
		SampleSB entry = _updateEntry(orgEntry.getPrimaryKey(), orgEntry,
			serviceContext);

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

			assetEntryLocalService.updateVisible(SampleSB.class.getName(),
				entryId, false);

			// Social

			if ((status == WorkflowConstants.STATUS_SCHEDULED)
					&& (oldStatus != WorkflowConstants.STATUS_IN_TRASH)) {

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

				trashEntryLocalService.addTrashEntry(userId, entry.getGroupId(),
					SampleSB.class.getName(), entry.getPrimaryKey(),
					entry.getUuid(), null, oldStatus, null, null);

			} else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil.restoreDiscussionFromTrash(
					SampleSB.class.getName(), entryId);

				trashEntryLocalService.deleteEntry(SampleSB.class.getName(),
					entryId);
			}

		}

		return entry;
	}

	/**
	 * Copy models at add entry
	 * 
	 * To process storing a record into database, copy the model passed into a
	 * new model object here.
	 * 
	 * @param entry model object
	 * @param serviceContext ServiceContext
	 * @return
	 * @throws PortalException
	 */
	protected SampleSB _addEntry(SampleSB entry, ServiceContext serviceContext)
		throws PortalException {

		long id = counterLocalService.increment(SampleSB.class.getName());
		
		SampleSB newEntry = sampleSBPersistence
			.create(id);

		User user = userPersistence.findByPrimaryKey(entry.getUserId());

		Date now = new Date();
		newEntry.setCompanyId(entry.getCompanyId());
		newEntry.setGroupId(entry.getGroupId());
		newEntry.setUserId(user.getUserId());
		newEntry.setUserName(user.getFullName());
		newEntry.setCreateDate(now);
		newEntry.setModifiedDate(now);

		newEntry.setUuid(serviceContext.getUuid());
		newEntry.setUrlTitle(
				getUniqueUrlTitle(
					id, 
					entry.getGroupId(), 
					String.valueOf(newEntry.getPrimaryKey()), 
					entry.getSamplesbTitleName(), 
					null, 
					serviceContext));		
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

	/**
	 * Copy models at update entry
	 * 
	 * To process storing a record into database, copy the model passed into a
	 * new model object here.
	 * 
	 * @param primaryKey Primary key
	 * @param entry model object
	 * @param serviceContext ServiceContext
	 * @return updated entry
	 * @throws PortalException
	 */
	protected SampleSB _updateEntry(
		long primaryKey, SampleSB entry, ServiceContext serviceContext)
		throws PortalException {

		SampleSB updateEntry = fetchSampleSB(primaryKey);

		User user = userPersistence.findByPrimaryKey(entry.getUserId());

		Date now = new Date();
		updateEntry.setCompanyId(entry.getCompanyId());
		updateEntry.setGroupId(entry.getGroupId());
		updateEntry.setUserId(user.getUserId());
		updateEntry.setUserName(user.getFullName());
		updateEntry.setCreateDate(entry.getCreateDate());
		updateEntry.setModifiedDate(now);

		updateEntry.setUuid(entry.getUuid());
		updateEntry.setUrlTitle(
				getUniqueUrlTitle(
					updateEntry.getPrimaryKey(), 
					entry.getGroupId(), 
					String.valueOf(updateEntry.getPrimaryKey()), 
					entry.getSamplesbTitleName(), 
					updateEntry.getUrlTitle(), 
					serviceContext));		
		updateEntry.setSamplesbTitleName(entry.getSamplesbTitleName());
		updateEntry.setSamplesbSummaryName(entry.getSamplesbSummaryName());

		updateEntry.setTitle(entry.getTitle());
		updateEntry.setStartDate(entry.getStartDate());
		updateEntry.setEndDate(entry.getEndDate());
		updateEntry.setSamplesbBooleanStat(entry.getSamplesbBooleanStat());
		updateEntry.setSamplesbDateTime(entry.getSamplesbDateTime());
		updateEntry.setSamplesbDocument(entry.getSamplesbDocument());
		updateEntry.setFolderDLId(entry.getFolderDLId());
		updateEntry
			.setSamplesbDocumentLibrary(entry.getSamplesbDocumentLibrary());
		updateEntry.setSamplesbDouble(entry.getSamplesbDouble());
		updateEntry.setSamplesbInteger(entry.getSamplesbInteger());
		updateEntry.setSamplesbRichText(entry.getSamplesbRichText());
		updateEntry.setSamplesbText(entry.getSamplesbText());

		return updateEntry;
	}

	/**
	 * Get Record
	 *
	 * @param primaryKey Primary key
	 * @return SampleSB object
	 * @throws PortletException
	 */
	public SampleSB getNewObject(long primaryKey) {

		primaryKey = (primaryKey <= 0) ? 0 : counterLocalService.increment();
		return createSampleSB(primaryKey);
	}

	/**
	 * Generating URL Title for unique URL
	 * 
	 * @param entryId primaryKey of the model
	 * @param title title for the asset
	 * @return URL title string
	 */
	protected String getUrlTitle(long entryId, String title) {
		if (title == null) {
			return String.valueOf(entryId);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title)) {
			title = String.valueOf(entryId);
		} else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(title);
		}

		return ModelHintsUtil.trimString(
				SampleSB.class.getName(), "urlTitle", title);
	}


	
	/**
	 * Get Unique UrlTitle
	 * 
	 * @param id CounterLocalService's generated ID at a record creation
	 * @param groupId Group ID
	 * @param primaryKey Generated record's id. Usually it's a primary Key
	 * @param title Title of the record. Usually asset's title.
	 * @return Unique UrlTitle strings
	 * @throws PortalException
	 */
	protected String getUniqueUrlTitle(
			long id, long groupId, String primaryKey, String title)
		throws PortalException {

		String urlTitle = getUrlTitle(id, title);

		return getUniqueUrlTitle(groupId, primaryKey, urlTitle);
	}

	/**
	 * Get Unique UrlTitle
	 * 
	 * @param id CounterLocalService's generated ID at a record creation
	 * @param groupId Group ID
	 * @param primaryKey Generated record's id. Usually it's a primary Key
	 * @param title Title of the record. Usually asset's title.
	 * @param oldUrlTitle Old url title to be repleaced with title
	 * @param serviceContext 
	 * @return Unique UrlTitle strings
	 * @throws PortalException
	 */
	protected String getUniqueUrlTitle(
			long id, long groupId, String primaryKey, String title,
			String oldUrlTitle, ServiceContext serviceContext)
		throws PortalException {

		String serviceContextUrlTitle = ParamUtil.getString(
			serviceContext, "urlTitle");

		String urlTitle = null;

		if (Validator.isNotNull(serviceContextUrlTitle)) {
			urlTitle = getUrlTitle(id, serviceContextUrlTitle);
		}
		else if (Validator.isNotNull(oldUrlTitle)) {
			return oldUrlTitle;
		}
		else {
			urlTitle = getUniqueUrlTitle(id, groupId, primaryKey, title);
		}

		SampleSB entry = getSampleSBByUrlTitle(
				groupId, urlTitle, WorkflowConstants.STATUS_ANY);
		if ((entry != null) &&
			!Objects.equals(entry.getPrimaryKey(), primaryKey)) {

			urlTitle = getUniqueUrlTitle(id, groupId, primaryKey, urlTitle);
		}

		return urlTitle;
	}

	/**
	 * Returns the record's unique URL title.
	 *
	 * @param  groupId the primary key of the record's group
	 * @param  primaryKey the primary key of the record
	 * @param  urlTitle the record's accessible URL title
	 * @return the record's unique URL title
	 */	
	public String getUniqueUrlTitle(
			long groupId, String primaryKey, String urlTitle)
		throws PortalException {

		for (int i = 1;; i++) {
			SampleSB entry = getSampleSBByUrlTitle(groupId, urlTitle, WorkflowConstants.STATUS_ANY);

			if ((entry == null) || primaryKey.equals(entry.getPrimaryKey())) {
				break;
			}
			else {
				String suffix = StringPool.DASH + i;

				String prefix = urlTitle;

				if (urlTitle.length() > suffix.length()) {
					prefix = urlTitle.substring(
						0, urlTitle.length() - suffix.length());
				}

				urlTitle = prefix + suffix;
			}
		}

		return urlTitle;
	}	
	
	/**
	 * Converte Date Time into Date()
	 * 
	 * @param request PortletRequest
	 * @param prefix Prefix of the parameter
	 * @return Date object
	 */
	public Date getDateTimeFromRequest(PortletRequest request, String prefix) {
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
	 * @throws SampleSBValidateException 
	 */
	public SampleSB getSampleSBFromRequest(
		long primaryKey, PortletRequest request) throws PortletException, SampleSBValidateException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB entry;
		if (primaryKey <= 0) {
			entry = getNewObject(primaryKey);
		} else {
			entry = fetchSampleSB(primaryKey);
		}

		try {
			entry.setSamplesbId(primaryKey);
			entry.setTitle(ParamUtil.getString(request, "title"));
			entry.setStartDate(getDateTimeFromRequest(request, "startDate"));
			entry.setEndDate(getDateTimeFromRequest(request, "endDate"));
			entry.setSamplesbBooleanStat(
				ParamUtil.getBoolean(request, "samplesbBooleanStat"));
			entry.setSamplesbDateTime(
				getDateTimeFromRequest(request, "samplesbDateTime"));
			entry.setSamplesbDocument(
				ParamUtil.getLong(request, "samplesbDocument"));
			entry.setFolderDLId(ParamUtil.getLong(request, "folderDLId"));
			entry.setSamplesbDocumentLibrary(
				ParamUtil.getString(request, "samplesbDocumentLibrary"));
			entry
				.setSamplesbDouble(ParamUtil.getDouble(request, "samplesbDouble"));
			entry.setSamplesbInteger(
				ParamUtil.getInteger(request, "samplesbInteger"));
			entry.setSamplesbRichText(
				ParamUtil.getString(request, "samplesbRichText"));
			entry.setSamplesbText(ParamUtil.getString(request, "samplesbText"));
			entry.setSamplesbTitleName(
				ParamUtil.getString(request, "samplesbTitleName"));
			entry.setSamplesbSummaryName(
				ParamUtil.getString(request, "samplesbSummaryName"));

			entry.setCompanyId(themeDisplay.getCompanyId());
			entry.setGroupId(themeDisplay.getScopeGroupId());
			entry.setUserId(themeDisplay.getUserId());
		} catch (Throwable e) {
			List<String> error = new ArrayList<>();
			error.add("value-convert-error");
			throw new SampleSBValidateException(error);
		}

		return entry;
	}

	/**
	 * Populate Model with values from a form
	 *
	 * @param primaryKey primaly key
	 * @param request PortletRequest
	 * @return SampleSB Object
	 * @throws PortletException
	 */
	public SampleSB getInitializedSampleSB(
		long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB entry = getNewObject(primaryKey);

		entry.setSamplesbId(primaryKey);
		entry.setTitle("");
		entry.setStartDate(new Date());
		entry.setEndDate(new Date());
		entry.setSamplesbBooleanStat(true);
		entry.setSamplesbDateTime(new Date());
		entry.setSamplesbDocument(0);
		entry.setFolderDLId(0);
		entry.setSamplesbDocumentLibrary("");
		entry.setSamplesbDouble(0.0);
		entry.setSamplesbInteger(0);
		entry.setSamplesbRichText("");
		entry.setSamplesbText("");
		entry.setSamplesbTitleName("");
		entry.setSamplesbSummaryName("");

		entry.setCompanyId(themeDisplay.getCompanyId());
		entry.setGroupId(themeDisplay.getScopeGroupId());
		entry.setUserId(themeDisplay.getUserId());

		return entry;
	}

	private static Pattern _friendlyURLPattern = Pattern.compile("[^a-z0-9_-]");

}