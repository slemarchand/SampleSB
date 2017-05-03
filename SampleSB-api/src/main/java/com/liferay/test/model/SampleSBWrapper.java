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

package com.liferay.test.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link SampleSB}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleSB
 * @generated
 */
@ProviderType
public class SampleSBWrapper implements SampleSB, ModelWrapper<SampleSB> {
	public SampleSBWrapper(SampleSB sampleSB) {
		_sampleSB = sampleSB;
	}

	@Override
	public Class<?> getModelClass() {
		return SampleSB.class;
	}

	@Override
	public String getModelClassName() {
		return SampleSB.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("samplesbId", getSamplesbId());
		attributes.put("title", getTitle());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("samplesbBooleanStat", getSamplesbBooleanStat());
		attributes.put("samplesbDateTime", getSamplesbDateTime());
		attributes.put("samplesbDocument", getSamplesbDocument());
		attributes.put("folderDLId", getFolderDLId());
		attributes.put("samplesbDocumentLibrary", getSamplesbDocumentLibrary());
		attributes.put("samplesbDouble", getSamplesbDouble());
		attributes.put("samplesbInteger", getSamplesbInteger());
		attributes.put("samplesbRichText", getSamplesbRichText());
		attributes.put("samplesbText", getSamplesbText());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("samplesbTitleName", getSamplesbTitleName());
		attributes.put("samplesbSummaryName", getSamplesbSummaryName());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long samplesbId = (Long)attributes.get("samplesbId");

		if (samplesbId != null) {
			setSamplesbId(samplesbId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Boolean samplesbBooleanStat = (Boolean)attributes.get(
				"samplesbBooleanStat");

		if (samplesbBooleanStat != null) {
			setSamplesbBooleanStat(samplesbBooleanStat);
		}

		Date samplesbDateTime = (Date)attributes.get("samplesbDateTime");

		if (samplesbDateTime != null) {
			setSamplesbDateTime(samplesbDateTime);
		}

		Long samplesbDocument = (Long)attributes.get("samplesbDocument");

		if (samplesbDocument != null) {
			setSamplesbDocument(samplesbDocument);
		}

		Long folderDLId = (Long)attributes.get("folderDLId");

		if (folderDLId != null) {
			setFolderDLId(folderDLId);
		}

		String samplesbDocumentLibrary = (String)attributes.get(
				"samplesbDocumentLibrary");

		if (samplesbDocumentLibrary != null) {
			setSamplesbDocumentLibrary(samplesbDocumentLibrary);
		}

		Double samplesbDouble = (Double)attributes.get("samplesbDouble");

		if (samplesbDouble != null) {
			setSamplesbDouble(samplesbDouble);
		}

		Integer samplesbInteger = (Integer)attributes.get("samplesbInteger");

		if (samplesbInteger != null) {
			setSamplesbInteger(samplesbInteger);
		}

		String samplesbRichText = (String)attributes.get("samplesbRichText");

		if (samplesbRichText != null) {
			setSamplesbRichText(samplesbRichText);
		}

		String samplesbText = (String)attributes.get("samplesbText");

		if (samplesbText != null) {
			setSamplesbText(samplesbText);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String samplesbTitleName = (String)attributes.get("samplesbTitleName");

		if (samplesbTitleName != null) {
			setSamplesbTitleName(samplesbTitleName);
		}

		String samplesbSummaryName = (String)attributes.get(
				"samplesbSummaryName");

		if (samplesbSummaryName != null) {
			setSamplesbSummaryName(samplesbSummaryName);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public SampleSB toEscapedModel() {
		return new SampleSBWrapper(_sampleSB.toEscapedModel());
	}

	@Override
	public SampleSB toUnescapedModel() {
		return new SampleSBWrapper(_sampleSB.toUnescapedModel());
	}

	/**
	* Returns the samplesb boolean stat of this sample sb.
	*
	* @return the samplesb boolean stat of this sample sb
	*/
	@Override
	public boolean getSamplesbBooleanStat() {
		return _sampleSB.getSamplesbBooleanStat();
	}

	/**
	* Returns <code>true</code> if this sample sb is approved.
	*
	* @return <code>true</code> if this sample sb is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _sampleSB.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _sampleSB.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this sample sb is denied.
	*
	* @return <code>true</code> if this sample sb is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _sampleSB.isDenied();
	}

	/**
	* Returns <code>true</code> if this sample sb is a draft.
	*
	* @return <code>true</code> if this sample sb is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _sampleSB.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _sampleSB.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this sample sb is expired.
	*
	* @return <code>true</code> if this sample sb is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _sampleSB.isExpired();
	}

	/**
	* Returns <code>true</code> if this sample sb is in the Recycle Bin.
	*
	* @return <code>true</code> if this sample sb is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _sampleSB.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this sample sb is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this sample sb is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _sampleSB.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _sampleSB.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _sampleSB.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this sample sb is inactive.
	*
	* @return <code>true</code> if this sample sb is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _sampleSB.isInactive();
	}

	/**
	* Returns <code>true</code> if this sample sb is incomplete.
	*
	* @return <code>true</code> if this sample sb is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _sampleSB.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _sampleSB.isNew();
	}

	/**
	* Returns <code>true</code> if this sample sb is pending.
	*
	* @return <code>true</code> if this sample sb is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _sampleSB.isPending();
	}

	/**
	* Returns <code>true</code> if this sample sb is samplesb boolean stat.
	*
	* @return <code>true</code> if this sample sb is samplesb boolean stat; <code>false</code> otherwise
	*/
	@Override
	public boolean isSamplesbBooleanStat() {
		return _sampleSB.isSamplesbBooleanStat();
	}

	/**
	* Returns <code>true</code> if this sample sb is scheduled.
	*
	* @return <code>true</code> if this sample sb is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _sampleSB.isScheduled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _sampleSB.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SampleSB> toCacheModel() {
		return _sampleSB.toCacheModel();
	}

	/**
	* Returns the trash handler for this sample sb.
	*
	* @return the trash handler for this sample sb
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _sampleSB.getTrashHandler();
	}

	/**
	* Returns the trash entry created when this sample sb was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this sample sb.
	*
	* @return the trash entry created when this sample sb was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sampleSB.getTrashEntry();
	}

	/**
	* Returns the samplesb double of this sample sb.
	*
	* @return the samplesb double of this sample sb
	*/
	@Override
	public double getSamplesbDouble() {
		return _sampleSB.getSamplesbDouble();
	}

	@Override
	public int compareTo(SampleSB sampleSB) {
		return _sampleSB.compareTo(sampleSB);
	}

	/**
	* Returns the samplesb integer of this sample sb.
	*
	* @return the samplesb integer of this sample sb
	*/
	@Override
	public int getSamplesbInteger() {
		return _sampleSB.getSamplesbInteger();
	}

	/**
	* Returns the status of this sample sb.
	*
	* @return the status of this sample sb
	*/
	@Override
	public int getStatus() {
		return _sampleSB.getStatus();
	}

	@Override
	public int hashCode() {
		return _sampleSB.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _sampleSB.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new SampleSBWrapper((SampleSB)_sampleSB.clone());
	}

	/**
	* Returns the samplesb document library of this sample sb.
	*
	* @return the samplesb document library of this sample sb
	*/
	@Override
	public java.lang.String getSamplesbDocumentLibrary() {
		return _sampleSB.getSamplesbDocumentLibrary();
	}

	/**
	* Returns the samplesb rich text of this sample sb.
	*
	* @return the samplesb rich text of this sample sb
	*/
	@Override
	public java.lang.String getSamplesbRichText() {
		return _sampleSB.getSamplesbRichText();
	}

	/**
	* Returns the samplesb summary name of this sample sb.
	*
	* @return the samplesb summary name of this sample sb
	*/
	@Override
	public java.lang.String getSamplesbSummaryName() {
		return _sampleSB.getSamplesbSummaryName();
	}

	/**
	* Returns the samplesb text of this sample sb.
	*
	* @return the samplesb text of this sample sb
	*/
	@Override
	public java.lang.String getSamplesbText() {
		return _sampleSB.getSamplesbText();
	}

	/**
	* Returns the samplesb title name of this sample sb.
	*
	* @return the samplesb title name of this sample sb
	*/
	@Override
	public java.lang.String getSamplesbTitleName() {
		return _sampleSB.getSamplesbTitleName();
	}

	/**
	* Returns the status by user name of this sample sb.
	*
	* @return the status by user name of this sample sb
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _sampleSB.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this sample sb.
	*
	* @return the status by user uuid of this sample sb
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _sampleSB.getStatusByUserUuid();
	}

	/**
	* Returns the title of this sample sb.
	*
	* @return the title of this sample sb
	*/
	@Override
	public java.lang.String getTitle() {
		return _sampleSB.getTitle();
	}

	/**
	* Returns the url title of this sample sb.
	*
	* @return the url title of this sample sb
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _sampleSB.getUrlTitle();
	}

	/**
	* Returns the user name of this sample sb.
	*
	* @return the user name of this sample sb
	*/
	@Override
	public java.lang.String getUserName() {
		return _sampleSB.getUserName();
	}

	/**
	* Returns the user uuid of this sample sb.
	*
	* @return the user uuid of this sample sb
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _sampleSB.getUserUuid();
	}

	/**
	* Returns the uuid of this sample sb.
	*
	* @return the uuid of this sample sb
	*/
	@Override
	public java.lang.String getUuid() {
		return _sampleSB.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _sampleSB.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _sampleSB.toXmlString();
	}

	/**
	* Returns the create date of this sample sb.
	*
	* @return the create date of this sample sb
	*/
	@Override
	public Date getCreateDate() {
		return _sampleSB.getCreateDate();
	}

	/**
	* Returns the end date of this sample sb.
	*
	* @return the end date of this sample sb
	*/
	@Override
	public Date getEndDate() {
		return _sampleSB.getEndDate();
	}

	/**
	* Returns the modified date of this sample sb.
	*
	* @return the modified date of this sample sb
	*/
	@Override
	public Date getModifiedDate() {
		return _sampleSB.getModifiedDate();
	}

	/**
	* Returns the samplesb date time of this sample sb.
	*
	* @return the samplesb date time of this sample sb
	*/
	@Override
	public Date getSamplesbDateTime() {
		return _sampleSB.getSamplesbDateTime();
	}

	/**
	* Returns the start date of this sample sb.
	*
	* @return the start date of this sample sb
	*/
	@Override
	public Date getStartDate() {
		return _sampleSB.getStartDate();
	}

	/**
	* Returns the status date of this sample sb.
	*
	* @return the status date of this sample sb
	*/
	@Override
	public Date getStatusDate() {
		return _sampleSB.getStatusDate();
	}

	/**
	* Returns the company ID of this sample sb.
	*
	* @return the company ID of this sample sb
	*/
	@Override
	public long getCompanyId() {
		return _sampleSB.getCompanyId();
	}

	/**
	* Returns the folder dl ID of this sample sb.
	*
	* @return the folder dl ID of this sample sb
	*/
	@Override
	public long getFolderDLId() {
		return _sampleSB.getFolderDLId();
	}

	/**
	* Returns the group ID of this sample sb.
	*
	* @return the group ID of this sample sb
	*/
	@Override
	public long getGroupId() {
		return _sampleSB.getGroupId();
	}

	/**
	* Returns the primary key of this sample sb.
	*
	* @return the primary key of this sample sb
	*/
	@Override
	public long getPrimaryKey() {
		return _sampleSB.getPrimaryKey();
	}

	/**
	* Returns the samplesb document of this sample sb.
	*
	* @return the samplesb document of this sample sb
	*/
	@Override
	public long getSamplesbDocument() {
		return _sampleSB.getSamplesbDocument();
	}

	/**
	* Returns the samplesb ID of this sample sb.
	*
	* @return the samplesb ID of this sample sb
	*/
	@Override
	public long getSamplesbId() {
		return _sampleSB.getSamplesbId();
	}

	/**
	* Returns the status by user ID of this sample sb.
	*
	* @return the status by user ID of this sample sb
	*/
	@Override
	public long getStatusByUserId() {
		return _sampleSB.getStatusByUserId();
	}

	/**
	* Returns the class primary key of the trash entry for this sample sb.
	*
	* @return the class primary key of the trash entry for this sample sb
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _sampleSB.getTrashEntryClassPK();
	}

	/**
	* Returns the user ID of this sample sb.
	*
	* @return the user ID of this sample sb
	*/
	@Override
	public long getUserId() {
		return _sampleSB.getUserId();
	}

	@Override
	public void persist() {
		_sampleSB.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_sampleSB.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this sample sb.
	*
	* @param companyId the company ID of this sample sb
	*/
	@Override
	public void setCompanyId(long companyId) {
		_sampleSB.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this sample sb.
	*
	* @param createDate the create date of this sample sb
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_sampleSB.setCreateDate(createDate);
	}

	/**
	* Sets the end date of this sample sb.
	*
	* @param endDate the end date of this sample sb
	*/
	@Override
	public void setEndDate(Date endDate) {
		_sampleSB.setEndDate(endDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_sampleSB.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_sampleSB.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_sampleSB.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the folder dl ID of this sample sb.
	*
	* @param folderDLId the folder dl ID of this sample sb
	*/
	@Override
	public void setFolderDLId(long folderDLId) {
		_sampleSB.setFolderDLId(folderDLId);
	}

	/**
	* Sets the group ID of this sample sb.
	*
	* @param groupId the group ID of this sample sb
	*/
	@Override
	public void setGroupId(long groupId) {
		_sampleSB.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this sample sb.
	*
	* @param modifiedDate the modified date of this sample sb
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_sampleSB.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_sampleSB.setNew(n);
	}

	/**
	* Sets the primary key of this sample sb.
	*
	* @param primaryKey the primary key of this sample sb
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_sampleSB.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_sampleSB.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this sample sb is samplesb boolean stat.
	*
	* @param samplesbBooleanStat the samplesb boolean stat of this sample sb
	*/
	@Override
	public void setSamplesbBooleanStat(boolean samplesbBooleanStat) {
		_sampleSB.setSamplesbBooleanStat(samplesbBooleanStat);
	}

	/**
	* Sets the samplesb date time of this sample sb.
	*
	* @param samplesbDateTime the samplesb date time of this sample sb
	*/
	@Override
	public void setSamplesbDateTime(Date samplesbDateTime) {
		_sampleSB.setSamplesbDateTime(samplesbDateTime);
	}

	/**
	* Sets the samplesb document of this sample sb.
	*
	* @param samplesbDocument the samplesb document of this sample sb
	*/
	@Override
	public void setSamplesbDocument(long samplesbDocument) {
		_sampleSB.setSamplesbDocument(samplesbDocument);
	}

	/**
	* Sets the samplesb document library of this sample sb.
	*
	* @param samplesbDocumentLibrary the samplesb document library of this sample sb
	*/
	@Override
	public void setSamplesbDocumentLibrary(
		java.lang.String samplesbDocumentLibrary) {
		_sampleSB.setSamplesbDocumentLibrary(samplesbDocumentLibrary);
	}

	/**
	* Sets the samplesb double of this sample sb.
	*
	* @param samplesbDouble the samplesb double of this sample sb
	*/
	@Override
	public void setSamplesbDouble(double samplesbDouble) {
		_sampleSB.setSamplesbDouble(samplesbDouble);
	}

	/**
	* Sets the samplesb ID of this sample sb.
	*
	* @param samplesbId the samplesb ID of this sample sb
	*/
	@Override
	public void setSamplesbId(long samplesbId) {
		_sampleSB.setSamplesbId(samplesbId);
	}

	/**
	* Sets the samplesb integer of this sample sb.
	*
	* @param samplesbInteger the samplesb integer of this sample sb
	*/
	@Override
	public void setSamplesbInteger(int samplesbInteger) {
		_sampleSB.setSamplesbInteger(samplesbInteger);
	}

	/**
	* Sets the samplesb rich text of this sample sb.
	*
	* @param samplesbRichText the samplesb rich text of this sample sb
	*/
	@Override
	public void setSamplesbRichText(java.lang.String samplesbRichText) {
		_sampleSB.setSamplesbRichText(samplesbRichText);
	}

	/**
	* Sets the samplesb summary name of this sample sb.
	*
	* @param samplesbSummaryName the samplesb summary name of this sample sb
	*/
	@Override
	public void setSamplesbSummaryName(java.lang.String samplesbSummaryName) {
		_sampleSB.setSamplesbSummaryName(samplesbSummaryName);
	}

	/**
	* Sets the samplesb text of this sample sb.
	*
	* @param samplesbText the samplesb text of this sample sb
	*/
	@Override
	public void setSamplesbText(java.lang.String samplesbText) {
		_sampleSB.setSamplesbText(samplesbText);
	}

	/**
	* Sets the samplesb title name of this sample sb.
	*
	* @param samplesbTitleName the samplesb title name of this sample sb
	*/
	@Override
	public void setSamplesbTitleName(java.lang.String samplesbTitleName) {
		_sampleSB.setSamplesbTitleName(samplesbTitleName);
	}

	/**
	* Sets the start date of this sample sb.
	*
	* @param startDate the start date of this sample sb
	*/
	@Override
	public void setStartDate(Date startDate) {
		_sampleSB.setStartDate(startDate);
	}

	/**
	* Sets the status of this sample sb.
	*
	* @param status the status of this sample sb
	*/
	@Override
	public void setStatus(int status) {
		_sampleSB.setStatus(status);
	}

	/**
	* Sets the status by user ID of this sample sb.
	*
	* @param statusByUserId the status by user ID of this sample sb
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_sampleSB.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this sample sb.
	*
	* @param statusByUserName the status by user name of this sample sb
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_sampleSB.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this sample sb.
	*
	* @param statusByUserUuid the status by user uuid of this sample sb
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_sampleSB.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this sample sb.
	*
	* @param statusDate the status date of this sample sb
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_sampleSB.setStatusDate(statusDate);
	}

	/**
	* Sets the title of this sample sb.
	*
	* @param title the title of this sample sb
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_sampleSB.setTitle(title);
	}

	/**
	* Sets the url title of this sample sb.
	*
	* @param urlTitle the url title of this sample sb
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_sampleSB.setUrlTitle(urlTitle);
	}

	/**
	* Sets the user ID of this sample sb.
	*
	* @param userId the user ID of this sample sb
	*/
	@Override
	public void setUserId(long userId) {
		_sampleSB.setUserId(userId);
	}

	/**
	* Sets the user name of this sample sb.
	*
	* @param userName the user name of this sample sb
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_sampleSB.setUserName(userName);
	}

	/**
	* Sets the user uuid of this sample sb.
	*
	* @param userUuid the user uuid of this sample sb
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_sampleSB.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this sample sb.
	*
	* @param uuid the uuid of this sample sb
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_sampleSB.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SampleSBWrapper)) {
			return false;
		}

		SampleSBWrapper sampleSBWrapper = (SampleSBWrapper)obj;

		if (Objects.equals(_sampleSB, sampleSBWrapper._sampleSB)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _sampleSB.getStagedModelType();
	}

	@Override
	public SampleSB getWrappedModel() {
		return _sampleSB;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _sampleSB.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _sampleSB.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_sampleSB.resetOriginalValues();
	}

	private final SampleSB _sampleSB;
}