/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.test.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.social.kernel.service.SocialActivityLocalService;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.base.SampleSBLocalServiceBaseImpl;
import com.liferay.test.social.SampleSBActivityKeys;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalService;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.component.Reference;

/**
 * @author Jack A. Rider
 * @author Juan Gonzalez P.
 * @author Yasuyuki Takeo
 */
@ProviderType
public class SampleSBLocalServiceImpl extends SampleSBLocalServiceBaseImpl {

    /**
     * Get Company entries
     *
     * @param companyId Company Id
     * @param status    Workflow status
     * @param start     start index of entries
     * @param end       end index of entries
     * @param obc       Comparator for the order
     * @return List of entries
     * @throws SystemException
     */
    public List<SampleSB> getCompanyEntries(
        long companyId, int status, int start, int end,
        OrderByComparator<SampleSB> obc)
        throws SystemException {

        if (status == WorkflowConstants.STATUS_ANY) {
            return sampleSBPersistence.findByCompanyId(
                companyId, start, end, obc);
        } else {
            return sampleSBPersistence.findByC_S(
                companyId, status, start, end, obc);
        }
    }

    /**
     * Get Company entries
     *
     * @param companyId Company Id
     * @param status    Workflow status
     * @param start     start index of entries
     * @param end       end index of entries
     * @return
     * @throws SystemException
     */
    public List<SampleSB> getCompanyEntries(
        long companyId, int status, int start, int end)
        throws SystemException {

        if (status == WorkflowConstants.STATUS_ANY) {
            return sampleSBPersistence.findByCompanyId(
                companyId, start, end);
        } else {
            return sampleSBPersistence.findByC_S(
                companyId, status, start, end);
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
    public int getCompanyEntriesCount(
        long companyId, int status)
        throws SystemException {

        if (status == WorkflowConstants.STATUS_ANY) {
            return sampleSBPersistence.countByCompanyId(companyId);
        } else {
            return sampleSBPersistence.countByC_S(companyId, status);
        }
    }

    /**
     * Get a user information
     *
     * @param userId
     * @return
     * @throws SystemException
     */
    public List<SampleSB> findAllInUser(long userId) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByU_S(userId, WorkflowConstants.STATUS_APPROVED);
        return list;
    }

    public int countAllInUser(long userId) throws SystemException {
        int count = sampleSBPersistence.countByU_S(userId, WorkflowConstants.STATUS_APPROVED);
        return count;
    }

    public List<SampleSB> findAllInUser(long userId, OrderByComparator<SampleSB> orderByComparator) throws SystemException {

        List<SampleSB> list = (List<SampleSB>) findAllInUser(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
        return list;
    }

    public List<SampleSB> findAllInUser(long userId, int start, int end, OrderByComparator<SampleSB> orderByComparator) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByU_S(userId, WorkflowConstants.STATUS_APPROVED, start, end, orderByComparator);
        return list;
    }

    public List<SampleSB> findAllInGroup(long groupId) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_S(groupId, WorkflowConstants.STATUS_APPROVED);
        return list;
    }

    public int countAllInGroup(long groupId) throws SystemException {
        int count = sampleSBPersistence.countByG_S(groupId, WorkflowConstants.STATUS_APPROVED);
        return count;
    }

    public List<SampleSB> findAllInGroup(long groupId, OrderByComparator<SampleSB> orderByComparator) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) findAllInGroup(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
        return list;
    }

    public List<SampleSB> findAllInGroup(long groupId, int start, int end, OrderByComparator<SampleSB> orderByComparator) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_S(groupId, WorkflowConstants.STATUS_APPROVED, start, end, orderByComparator);
        return list;
    }

    public List<SampleSB> findAllInUserAndGroup(long userId, long groupId) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_U_S(groupId, userId, WorkflowConstants.STATUS_APPROVED);
        return list;
    }

    public int countAllInUserAndGroup(long userId, long groupId) throws SystemException {
        int count = sampleSBPersistence.countByG_U_S(groupId, userId, WorkflowConstants.STATUS_APPROVED);
        return count;
    }

    public List<SampleSB> findAllInUserAndGroup(long userId, long groupId, OrderByComparator<SampleSB> orderByComparator) throws SystemException {

        List<SampleSB> list = (List<SampleSB>) findAllInUserAndGroup(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
        return list;
    }

    public List<SampleSB> findAllInUserAndGroup(long userId, long groupId, int start, int end, OrderByComparator<SampleSB> orderByComparator) throws SystemException {
        List<SampleSB> list = (List<SampleSB>) sampleSBPersistence.findByG_U_S(groupId, userId, WorkflowConstants.STATUS_APPROVED, start, end, orderByComparator);
        return list;
    }

    public SampleSB getSampleSBByUrlTitle(
        long groupId, String urlTitle, int status)
        throws PortalException, SystemException {

        SampleSB entry = null;

        if (status == WorkflowConstants.STATUS_ANY) {
            entry = sampleSBPersistence.fetchByG_UT(
                groupId, urlTitle);
        } else {
            List<SampleSB> results = sampleSBPersistence.findByG_UT_ST(
                groupId, urlTitle, status);
            if (results != null && results.size() > 0) {
                entry = results.get(0);
            }
        }

        return entry;
    }

    public SampleSB addSampleSB(SampleSB validSampleSB, ServiceContext serviceContext) throws PortalException, SystemException {
        SampleSB retVal = _addSampleSB(validSampleSB, serviceContext);

        // Resources
        if (serviceContext.isAddGroupPermissions() ||
            serviceContext.isAddGuestPermissions()) {

            addEntryResources(
                retVal, serviceContext.isAddGroupPermissions(),
                serviceContext.isAddGuestPermissions());
        } else {
            addEntryResources(
                retVal, serviceContext.getGroupPermissions(),
                serviceContext.getGuestPermissions());
        }

        // Social
        _socialActivityLocalService.addActivity(
            retVal.getUserId(), retVal.getGroupId(),
            SampleSB.class.getName(), retVal.getPrimaryKey(),
            SampleSBActivityKeys.ADD_SAMPLESB,
            StringPool.BLANK, 0);

        // Asset
        updateAsset(
            retVal.getUserId(), retVal, serviceContext.getAssetCategoryIds(),
            serviceContext.getAssetTagNames(),
            serviceContext.getAssetLinkEntryIds());

        WorkflowHandlerRegistryUtil.startWorkflowInstance(
            validSampleSB.getCompanyId(), validSampleSB.getGroupId(), validSampleSB.getUserId(),
            SampleSB.class.getName(), retVal.getPrimaryKey(), retVal, serviceContext);

        return retVal;
    }

    public SampleSB updateSampleSB(SampleSB validSampleSB, ServiceContext serviceContext) throws PortalException, SystemException {

        SampleSB entry = sampleSBPersistence.findByPrimaryKey(validSampleSB.getPrimaryKey());
        validSampleSB.setCreateDate(entry.getCreateDate());
        User user = userPersistence.findByPrimaryKey(validSampleSB.getUserId());
        validSampleSB.setUserName(user.getFullName());

        validSampleSB.setModifiedDate(serviceContext.getModifiedDate(null));
        validSampleSB.setUuid(entry.getUuid());
        validSampleSB.setUrlTitle(_getUniqueURLTitle(validSampleSB));

        // Social
        _socialActivityLocalService.addActivity(
            validSampleSB.getUserId(), validSampleSB.getGroupId(),
            SampleSB.class.getName(), validSampleSB.getPrimaryKey(),
            SampleSBActivityKeys.UPDATE_SAMPLESB, StringPool.BLANK, 0);

        SampleSB retVal = sampleSBPersistence.update(validSampleSB);

        // Resources
        if ((serviceContext.getGroupPermissions() != null) ||
            (serviceContext.getGuestPermissions() != null)) {

            updateEntryResources(
                retVal, serviceContext.getGroupPermissions(),
                serviceContext.getGuestPermissions());
        }


        // Asset
        updateAsset(
            retVal.getUserId(), retVal, serviceContext.getAssetCategoryIds(),
            serviceContext.getAssetTagNames(),
            serviceContext.getAssetLinkEntryIds());

        WorkflowHandlerRegistryUtil.startWorkflowInstance(
            retVal.getCompanyId(), retVal.getGroupId(), retVal.getUserId(),
            SampleSB.class.getName(), retVal.getPrimaryKey(), retVal, serviceContext);

        return retVal;
    }

    private SampleSB _addSampleSB(SampleSB validSampleSB, ServiceContext serviceContext) throws PortalException, SystemException {
        SampleSB fileobj = sampleSBPersistence.create(counterLocalService.increment(SampleSB.class.getName()));

        User user = userPersistence.findByPrimaryKey(validSampleSB.getUserId());

        Date now = new Date();
        fileobj.setCompanyId(validSampleSB.getCompanyId());
        fileobj.setGroupId(validSampleSB.getGroupId());
        fileobj.setUserId(user.getUserId());
        fileobj.setUserName(user.getFullName());
        fileobj.setCreateDate(now);
        fileobj.setModifiedDate(now);

        fileobj.setUuid(serviceContext.getUuid());
        fileobj.setUrlTitle(_getUniqueURLTitle(fileobj));
        fileobj.setTitle(validSampleSB.getTitle());
        fileobj.setFolderDLId(validSampleSB.getFolderDLId());

        return sampleSBPersistence.update(fileobj);
    }

    public void deleteSampleSBEntry(SampleSB fileobj) throws PortalException, SystemException {

        sampleSBPersistence.remove(fileobj.getPrimaryKey());

        // Resources

        resourceLocalService.deleteResource(
            fileobj.getCompanyId(), SampleSB.class.getName(),
            ResourceConstants.SCOPE_INDIVIDUAL, fileobj.getPrimaryKey());
        // Asset

        assetEntryLocalService.deleteEntry(
            SampleSB.class.getName(), fileobj.getPrimaryKey());

        //Remove DocumentFile dir
        Long dlFolder = fileobj.getFolderDLId();
        if (dlFolder != null && dlFolder != 0L) {
            dlFolderPersistence.remove(dlFolder);
        }

        // Social

        _socialActivityLocalService.deleteActivities(
            SampleSB.class.getName(), fileobj.getPrimaryKey());
        // Trash

        _trashEntryLocalService.deleteEntry(
            SampleSB.class.getName(), fileobj.getPrimaryKey());
        // Indexer

        Indexer<SampleSB> indexer = _indexerRegistry.nullSafeGetIndexer(SampleSB.class);

        indexer.delete(fileobj);
        // Workflow
        workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
            fileobj.getCompanyId(), fileobj.getGroupId(),
            SampleSB.class.getName(), fileobj.getPrimaryKey());

    }


    public SampleSB updateStatus(long userId, long entryId, int status, ServiceContext serviceContext) throws PortalException, SystemException {

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

        // Indexer

        Indexer<SampleSB> indexer = _indexerRegistry.nullSafeGetIndexer(SampleSB.class);

        if (status == WorkflowConstants.STATUS_APPROVED) {

            assetEntryLocalService.updateEntry(
                SampleSB.class.getName(), entryId,
                entry.getModifiedDate(), null, true, true);

            // Trash
            if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
                _trashEntryLocalService.deleteEntry(
                    SampleSB.class.getName(), entryId);
            }
            indexer.reindex(entry);
        } else {
            assetEntryLocalService.updateVisible(
                SampleSB.class.getName(), entryId, false);
            // Trash

            if (status == WorkflowConstants.STATUS_IN_TRASH) {
                _trashEntryLocalService.addTrashEntry(
                    userId, entry.getGroupId(), SampleSB.class.getName(),
                    entryId, entry.getUuid(), null, oldStatus, null,
                    null);
            } else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
                _trashEntryLocalService.deleteEntry(
                    SampleSB.class.getName(), entryId);
            }
            // Indexer
            if (status == WorkflowConstants.STATUS_IN_TRASH) {
                indexer.reindex(entry);
            } else {
                indexer.delete(entry);
            }
        }

        return entry;
    }

    public SampleSB moveEntryToTrash(long userId, SampleSB entry)
        throws PortalException, SystemException {

        // Entry

        int oldStatus = entry.getStatus();

        if (oldStatus == WorkflowConstants.STATUS_PENDING) {
            entry.setStatus(WorkflowConstants.STATUS_DRAFT);

            sampleSBPersistence.update(entry);
        }

        updateStatus(
            userId, entry.getPrimaryKey(), WorkflowConstants.STATUS_IN_TRASH,
            new ServiceContext());

        // Workflow

        if (oldStatus == WorkflowConstants.STATUS_PENDING) {
            workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
                entry.getCompanyId(), entry.getGroupId(),
                SampleSB.class.getName(), entry.getPrimaryKey());
        }
        return entry;
    }


    public SampleSB moveEntryToTrash(long userId, long entryId)
        throws PortalException, SystemException {

        SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

        return moveEntryToTrash(userId, entry);
    }

    public void restoreEntryFromTrash(long userId, long entryId)
        throws PortalException, SystemException {

        // Entry
        TrashEntry trashEntry = _trashEntryLocalService.getEntry(
            SampleSB.class.getName(), entryId);

        updateStatus(
            userId, entryId, trashEntry.getStatus(), new ServiceContext());
    }

    public void addEntryResources(
        SampleSB entry, boolean addGroupPermissions,
        boolean addGuestPermissions)
        throws PortalException, SystemException {

        resourceLocalService.addResources(
            entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
            SampleSB.class.getName(), entry.getPrimaryKey(), false,
            addGroupPermissions, addGuestPermissions);
    }

    public void addEntryResources(
        SampleSB entry, String[] groupPermissions,
        String[] guestPermissions)
        throws PortalException, SystemException {

        resourceLocalService.addModelResources(
            entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
            SampleSB.class.getName(), entry.getPrimaryKey(), groupPermissions,
            guestPermissions);
    }

    public void addEntryResources(
        long entryId, boolean addGroupPermissions,
        boolean addGuestPermissions)
        throws PortalException, SystemException {

        SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

        addEntryResources(entry, addGroupPermissions, addGuestPermissions);
    }

    public void addEntryResources(
        long entryId, String[] groupPermissions, String[] guestPermissions)
        throws PortalException, SystemException {

        SampleSB entry = sampleSBPersistence.findByPrimaryKey(entryId);

        addEntryResources(entry, groupPermissions, guestPermissions);
    }

    public void updateEntryResources(
        SampleSB entry, String[] groupPermissions,
        String[] guestPermissions)
        throws PortalException, SystemException {

        resourceLocalService.updateResources(
            entry.getCompanyId(), entry.getGroupId(),
            SampleSB.class.getName(), entry.getPrimaryKey(), groupPermissions,
            guestPermissions);
    }

    public void updateAsset(
        long userId, SampleSB entry, long[] assetCategoryIds,
        String[] assetTagNames, long[] assetLinkEntryIds)
        throws PortalException, SystemException {
        boolean visible = false;

        if (entry.isApproved()) {
            visible = true;
        }

        String summary = HtmlUtil.extractText(
            StringUtil.shorten(entry.getSamplesbSummaryName(), 500));

        AssetEntry assetEntry = assetEntryLocalService.updateEntry(
            userId, entry.getGroupId(), entry.getCreateDate(),
            entry.getModifiedDate(), SampleSB.class.getName(),
            entry.getPrimaryKey(), entry.getUuid(), 0, assetCategoryIds,
            assetTagNames, true, visible, null, null, null, null, ContentTypes.TEXT_HTML,
            entry.getSamplesbTitleName(), null, summary, null, null, 0, 0,
            0.0);

        assetLinkLocalService.updateLinks(
            userId, assetEntry.getEntryId(), assetLinkEntryIds,
            AssetLinkConstants.TYPE_RELATED);
    }

    private String _getUniqueURLTitle(SampleSB entry) throws SystemException {
        String urlTitle = _createUrlTitle(entry.getPrimaryKey(), entry.getSamplesbTitleName());

        long entryId = entry.getPrimaryKey();

        for (int i = 1; ; i++) {
            SampleSB tmpEntry = sampleSBPersistence.fetchByG_UT(
                entry.getGroupId(), urlTitle);

            if ((tmpEntry == null) || (entryId == tmpEntry.getPrimaryKey())) {
                break;
            } else {
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

    private String _createUrlTitle(long entryId, String title) {
        if (title == null) {
            return String.valueOf(entryId);
        }

        title = title.trim().toLowerCase();

        if (Validator.isNull(title) || Validator.isNumber(title)) {

            title = String.valueOf(entryId);
        } else {
            title = FriendlyURLNormalizerUtil.normalize(title, _friendlyURLPattern);
        }

        return ModelHintsUtil.trimString(
            SampleSB.class.getName(), "urlTitle", title);
    }

    @Reference(unbind = "-")
    protected void setTrashEntryLocalService(
        TrashEntryLocalService trashEntryLocalService) {

        _trashEntryLocalService = trashEntryLocalService;
    }

    @Reference(unbind = "-")
    protected void setSocialActivityLocalService(
        SocialActivityLocalService socialActivityLocalService) {

        _socialActivityLocalService = socialActivityLocalService;
    }

    @Reference(unbind = "-")
    protected void setIndexerRegistry(IndexerRegistry indexerRegistry) {
        _indexerRegistry = indexerRegistry;
    }

    private IndexerRegistry _indexerRegistry;
    private TrashEntryLocalService _trashEntryLocalService;
    private SocialActivityLocalService _socialActivityLocalService;
    private static Pattern _friendlyURLPattern = Pattern.compile("[^a-z0-9_-]");
}