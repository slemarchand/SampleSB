/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.test.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SampleSB. This utility wraps
 * {@link com.liferay.test.service.impl.SampleSBLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SampleSBLocalService
 * @see com.liferay.test.service.base.SampleSBLocalServiceBaseImpl
 * @see com.liferay.test.service.impl.SampleSBLocalServiceImpl
 * @generated
 */
@ProviderType
public class SampleSBLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.test.service.impl.SampleSBLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the sample sb to the database. Also notifies the appropriate model listeners.
	*
	* @param sampleSB the sample sb
	* @return the sample sb that was added
	*/
	public static com.liferay.test.model.SampleSB addSampleSB(
		com.liferay.test.model.SampleSB sampleSB) {
		return getService().addSampleSB(sampleSB);
	}

	public static com.liferay.test.model.SampleSB addSampleSB(
		com.liferay.test.model.SampleSB validSampleSB,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addSampleSB(validSampleSB, serviceContext);
	}

	/**
	* Creates a new sample sb with the primary key. Does not add the sample sb to the database.
	*
	* @param samplesbId the primary key for the new sample sb
	* @return the new sample sb
	*/
	public static com.liferay.test.model.SampleSB createSampleSB(
		long samplesbId) {
		return getService().createSampleSB(samplesbId);
	}

	/**
	* Deletes the sample sb from the database. Also notifies the appropriate model listeners.
	*
	* @param sampleSB the sample sb
	* @return the sample sb that was removed
	*/
	public static com.liferay.test.model.SampleSB deleteSampleSB(
		com.liferay.test.model.SampleSB sampleSB) {
		return getService().deleteSampleSB(sampleSB);
	}

	/**
	* Deletes the sample sb with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samplesbId the primary key of the sample sb
	* @return the sample sb that was removed
	* @throws PortalException if a sample sb with the primary key could not be found
	*/
	public static com.liferay.test.model.SampleSB deleteSampleSB(
		long samplesbId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSampleSB(samplesbId);
	}

	public static com.liferay.test.model.SampleSB fetchSampleSB(long samplesbId) {
		return getService().fetchSampleSB(samplesbId);
	}

	/**
	* Returns the sample sb matching the UUID and group.
	*
	* @param uuid the sample sb's UUID
	* @param groupId the primary key of the group
	* @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	*/
	public static com.liferay.test.model.SampleSB fetchSampleSBByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchSampleSBByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the sample sb with the primary key.
	*
	* @param samplesbId the primary key of the sample sb
	* @return the sample sb
	* @throws PortalException if a sample sb with the primary key could not be found
	*/
	public static com.liferay.test.model.SampleSB getSampleSB(long samplesbId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSampleSB(samplesbId);
	}

	public static com.liferay.test.model.SampleSB getSampleSBByUrlTitle(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSampleSBByUrlTitle(groupId, urlTitle, status);
	}

	/**
	* Returns the sample sb matching the UUID and group.
	*
	* @param uuid the sample sb's UUID
	* @param groupId the primary key of the group
	* @return the matching sample sb
	* @throws PortalException if a matching sample sb could not be found
	*/
	public static com.liferay.test.model.SampleSB getSampleSBByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSampleSBByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.test.model.SampleSB moveEntryToTrash(
		long userId, com.liferay.test.model.SampleSB entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveEntryToTrash(userId, entry);
	}

	public static com.liferay.test.model.SampleSB moveEntryToTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveEntryToTrash(userId, entryId);
	}

	/**
	* Updates the sample sb in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sampleSB the sample sb
	* @return the sample sb that was updated
	*/
	public static com.liferay.test.model.SampleSB updateSampleSB(
		com.liferay.test.model.SampleSB sampleSB) {
		return getService().updateSampleSB(sampleSB);
	}

	public static com.liferay.test.model.SampleSB updateSampleSB(
		com.liferay.test.model.SampleSB validSampleSB,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSampleSB(validSampleSB, serviceContext);
	}

	public static com.liferay.test.model.SampleSB updateStatus(long userId,
		long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(userId, entryId, status, serviceContext);
	}

	public static int countAllInGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countAllInGroup(groupId);
	}

	public static int countAllInUser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countAllInUser(userId);
	}

	public static int countAllInUserAndGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countAllInUserAndGroup(userId, groupId);
	}

	/**
	* Get Company entries counts
	*
	* @param companyId
	* @param status
	* @return
	* @throws SystemException
	*/
	public static int getCompanyEntriesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntriesCount(companyId, status);
	}

	/**
	* Returns the number of sample sbs.
	*
	* @return the number of sample sbs
	*/
	public static int getSampleSBsCount() {
		return getService().getSampleSBsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.test.model.impl.SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.test.model.impl.SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInGroup(groupId);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInGroup(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInGroup(groupId, orderByComparator);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInGroup(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .findAllInGroup(groupId, start, end, orderByComparator);
	}

	/**
	* Get a user information
	*
	* @param userId
	* @return
	* @throws SystemException
	*/
	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUser(userId);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUser(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUser(userId, orderByComparator);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUser(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUser(userId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUserAndGroup(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUserAndGroup(userId, groupId);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUserAndGroup(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .findAllInUserAndGroup(userId, groupId, orderByComparator);
	}

	public static java.util.List<com.liferay.test.model.SampleSB> findAllInUserAndGroup(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .findAllInUserAndGroup(userId, groupId, start, end,
			orderByComparator);
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
	public static java.util.List<com.liferay.test.model.SampleSB> getCompanyEntries(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, status, start, end);
	}

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
	public static java.util.List<com.liferay.test.model.SampleSB> getCompanyEntries(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, status, start, end, obc);
	}

	/**
	* Returns a range of all the sample sbs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.test.model.impl.SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sample sbs
	* @param end the upper bound of the range of sample sbs (not inclusive)
	* @return the range of sample sbs
	*/
	public static java.util.List<com.liferay.test.model.SampleSB> getSampleSBs(
		int start, int end) {
		return getService().getSampleSBs(start, end);
	}

	/**
	* Returns all the sample sbs matching the UUID and company.
	*
	* @param uuid the UUID of the sample sbs
	* @param companyId the primary key of the company
	* @return the matching sample sbs, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.test.model.SampleSB> getSampleSBsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getSampleSBsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of sample sbs matching the UUID and company.
	*
	* @param uuid the UUID of the sample sbs
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of sample sbs
	* @param end the upper bound of the range of sample sbs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching sample sbs, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.test.model.SampleSB> getSampleSBsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.test.model.SampleSB> orderByComparator) {
		return getService()
				   .getSampleSBsByUuidAndCompanyId(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void addEntryResources(
		com.liferay.test.model.SampleSB entry, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.test.model.SampleSB entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addEntryResources(entry, groupPermissions, guestPermissions);
	}

	public static void addEntryResources(long entryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entryId, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(long entryId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entryId, groupPermissions, guestPermissions);
	}

	public static void deleteSampleSBEntry(
		com.liferay.test.model.SampleSB fileobj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSampleSBEntry(fileobj);
	}

	public static void restoreEntryFromTrash(long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreEntryFromTrash(userId, entryId);
	}

	public static void updateAsset(long userId,
		com.liferay.test.model.SampleSB entry, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, entry, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public static void updateEntryResources(
		com.liferay.test.model.SampleSB entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateEntryResources(entry, groupPermissions, guestPermissions);
	}

	public static SampleSBLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SampleSBLocalService, SampleSBLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SampleSBLocalService.class);
}