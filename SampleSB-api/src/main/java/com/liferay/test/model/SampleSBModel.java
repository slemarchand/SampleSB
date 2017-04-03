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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the SampleSB service. Represents a row in the &quot;SampleSB_SampleSB&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.test.model.impl.SampleSBModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.test.model.impl.SampleSBImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleSB
 * @see com.liferay.test.model.impl.SampleSBImpl
 * @see com.liferay.test.model.impl.SampleSBModelImpl
 * @generated
 */
@ProviderType
public interface SampleSBModel extends BaseModel<SampleSB>, GroupedModel,
	ShardedModel, StagedAuditedModel, WorkflowedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a sample sb model instance should use the {@link SampleSB} interface instead.
	 */

	/**
	 * Returns the primary key of this sample sb.
	 *
	 * @return the primary key of this sample sb
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this sample sb.
	 *
	 * @param primaryKey the primary key of this sample sb
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this sample sb.
	 *
	 * @return the uuid of this sample sb
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this sample sb.
	 *
	 * @param uuid the uuid of this sample sb
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the samplesb ID of this sample sb.
	 *
	 * @return the samplesb ID of this sample sb
	 */
	public long getSamplesbId();

	/**
	 * Sets the samplesb ID of this sample sb.
	 *
	 * @param samplesbId the samplesb ID of this sample sb
	 */
	public void setSamplesbId(long samplesbId);

	/**
	 * Returns the title of this sample sb.
	 *
	 * @return the title of this sample sb
	 */
	@AutoEscape
	public String getTitle();

	/**
	 * Sets the title of this sample sb.
	 *
	 * @param title the title of this sample sb
	 */
	public void setTitle(String title);

	/**
	 * Returns the start date of this sample sb.
	 *
	 * @return the start date of this sample sb
	 */
	public Date getStartDate();

	/**
	 * Sets the start date of this sample sb.
	 *
	 * @param startDate the start date of this sample sb
	 */
	public void setStartDate(Date startDate);

	/**
	 * Returns the end date of this sample sb.
	 *
	 * @return the end date of this sample sb
	 */
	public Date getEndDate();

	/**
	 * Sets the end date of this sample sb.
	 *
	 * @param endDate the end date of this sample sb
	 */
	public void setEndDate(Date endDate);

	/**
	 * Returns the samplesb boolean stat of this sample sb.
	 *
	 * @return the samplesb boolean stat of this sample sb
	 */
	public boolean getSamplesbBooleanStat();

	/**
	 * Returns <code>true</code> if this sample sb is samplesb boolean stat.
	 *
	 * @return <code>true</code> if this sample sb is samplesb boolean stat; <code>false</code> otherwise
	 */
	public boolean isSamplesbBooleanStat();

	/**
	 * Sets whether this sample sb is samplesb boolean stat.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat of this sample sb
	 */
	public void setSamplesbBooleanStat(boolean samplesbBooleanStat);

	/**
	 * Returns the samplesb date time of this sample sb.
	 *
	 * @return the samplesb date time of this sample sb
	 */
	public Date getSamplesbDateTime();

	/**
	 * Sets the samplesb date time of this sample sb.
	 *
	 * @param samplesbDateTime the samplesb date time of this sample sb
	 */
	public void setSamplesbDateTime(Date samplesbDateTime);

	/**
	 * Returns the samplesb document of this sample sb.
	 *
	 * @return the samplesb document of this sample sb
	 */
	public long getSamplesbDocument();

	/**
	 * Sets the samplesb document of this sample sb.
	 *
	 * @param samplesbDocument the samplesb document of this sample sb
	 */
	public void setSamplesbDocument(long samplesbDocument);

	/**
	 * Returns the folder dl ID of this sample sb.
	 *
	 * @return the folder dl ID of this sample sb
	 */
	public long getFolderDLId();

	/**
	 * Sets the folder dl ID of this sample sb.
	 *
	 * @param folderDLId the folder dl ID of this sample sb
	 */
	public void setFolderDLId(long folderDLId);

	/**
	 * Returns the samplesb document library of this sample sb.
	 *
	 * @return the samplesb document library of this sample sb
	 */
	@AutoEscape
	public String getSamplesbDocumentLibrary();

	/**
	 * Sets the samplesb document library of this sample sb.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library of this sample sb
	 */
	public void setSamplesbDocumentLibrary(String samplesbDocumentLibrary);

	/**
	 * Returns the samplesb double of this sample sb.
	 *
	 * @return the samplesb double of this sample sb
	 */
	public double getSamplesbDouble();

	/**
	 * Sets the samplesb double of this sample sb.
	 *
	 * @param samplesbDouble the samplesb double of this sample sb
	 */
	public void setSamplesbDouble(double samplesbDouble);

	/**
	 * Returns the samplesb integer of this sample sb.
	 *
	 * @return the samplesb integer of this sample sb
	 */
	public int getSamplesbInteger();

	/**
	 * Sets the samplesb integer of this sample sb.
	 *
	 * @param samplesbInteger the samplesb integer of this sample sb
	 */
	public void setSamplesbInteger(int samplesbInteger);

	/**
	 * Returns the samplesb rich text of this sample sb.
	 *
	 * @return the samplesb rich text of this sample sb
	 */
	@AutoEscape
	public String getSamplesbRichText();

	/**
	 * Sets the samplesb rich text of this sample sb.
	 *
	 * @param samplesbRichText the samplesb rich text of this sample sb
	 */
	public void setSamplesbRichText(String samplesbRichText);

	/**
	 * Returns the samplesb text of this sample sb.
	 *
	 * @return the samplesb text of this sample sb
	 */
	@AutoEscape
	public String getSamplesbText();

	/**
	 * Sets the samplesb text of this sample sb.
	 *
	 * @param samplesbText the samplesb text of this sample sb
	 */
	public void setSamplesbText(String samplesbText);

	/**
	 * Returns the group ID of this sample sb.
	 *
	 * @return the group ID of this sample sb
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this sample sb.
	 *
	 * @param groupId the group ID of this sample sb
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this sample sb.
	 *
	 * @return the company ID of this sample sb
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this sample sb.
	 *
	 * @param companyId the company ID of this sample sb
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this sample sb.
	 *
	 * @return the user ID of this sample sb
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this sample sb.
	 *
	 * @param userId the user ID of this sample sb
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this sample sb.
	 *
	 * @return the user uuid of this sample sb
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this sample sb.
	 *
	 * @param userUuid the user uuid of this sample sb
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this sample sb.
	 *
	 * @return the user name of this sample sb
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this sample sb.
	 *
	 * @param userName the user name of this sample sb
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this sample sb.
	 *
	 * @return the create date of this sample sb
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this sample sb.
	 *
	 * @param createDate the create date of this sample sb
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this sample sb.
	 *
	 * @return the modified date of this sample sb
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this sample sb.
	 *
	 * @param modifiedDate the modified date of this sample sb
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the url title of this sample sb.
	 *
	 * @return the url title of this sample sb
	 */
	@AutoEscape
	public String getUrlTitle();

	/**
	 * Sets the url title of this sample sb.
	 *
	 * @param urlTitle the url title of this sample sb
	 */
	public void setUrlTitle(String urlTitle);

	/**
	 * Returns the samplesb title name of this sample sb.
	 *
	 * @return the samplesb title name of this sample sb
	 */
	@AutoEscape
	public String getSamplesbTitleName();

	/**
	 * Sets the samplesb title name of this sample sb.
	 *
	 * @param samplesbTitleName the samplesb title name of this sample sb
	 */
	public void setSamplesbTitleName(String samplesbTitleName);

	/**
	 * Returns the samplesb summary name of this sample sb.
	 *
	 * @return the samplesb summary name of this sample sb
	 */
	@AutoEscape
	public String getSamplesbSummaryName();

	/**
	 * Sets the samplesb summary name of this sample sb.
	 *
	 * @param samplesbSummaryName the samplesb summary name of this sample sb
	 */
	public void setSamplesbSummaryName(String samplesbSummaryName);

	/**
	 * Returns the status of this sample sb.
	 *
	 * @return the status of this sample sb
	 */
	@Override
	public int getStatus();

	/**
	 * Sets the status of this sample sb.
	 *
	 * @param status the status of this sample sb
	 */
	@Override
	public void setStatus(int status);

	/**
	 * Returns the status by user ID of this sample sb.
	 *
	 * @return the status by user ID of this sample sb
	 */
	@Override
	public long getStatusByUserId();

	/**
	 * Sets the status by user ID of this sample sb.
	 *
	 * @param statusByUserId the status by user ID of this sample sb
	 */
	@Override
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Returns the status by user uuid of this sample sb.
	 *
	 * @return the status by user uuid of this sample sb
	 */
	@Override
	public String getStatusByUserUuid();

	/**
	 * Sets the status by user uuid of this sample sb.
	 *
	 * @param statusByUserUuid the status by user uuid of this sample sb
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Returns the status by user name of this sample sb.
	 *
	 * @return the status by user name of this sample sb
	 */
	@AutoEscape
	@Override
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this sample sb.
	 *
	 * @param statusByUserName the status by user name of this sample sb
	 */
	@Override
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Returns the status date of this sample sb.
	 *
	 * @return the status date of this sample sb
	 */
	@Override
	public Date getStatusDate();

	/**
	 * Sets the status date of this sample sb.
	 *
	 * @param statusDate the status date of this sample sb
	 */
	@Override
	public void setStatusDate(Date statusDate);

	/**
	 * Returns <code>true</code> if this sample sb is approved.
	 *
	 * @return <code>true</code> if this sample sb is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved();

	/**
	 * Returns <code>true</code> if this sample sb is denied.
	 *
	 * @return <code>true</code> if this sample sb is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied();

	/**
	 * Returns <code>true</code> if this sample sb is a draft.
	 *
	 * @return <code>true</code> if this sample sb is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft();

	/**
	 * Returns <code>true</code> if this sample sb is expired.
	 *
	 * @return <code>true</code> if this sample sb is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired();

	/**
	 * Returns <code>true</code> if this sample sb is inactive.
	 *
	 * @return <code>true</code> if this sample sb is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive();

	/**
	 * Returns <code>true</code> if this sample sb is incomplete.
	 *
	 * @return <code>true</code> if this sample sb is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete();

	/**
	 * Returns <code>true</code> if this sample sb is pending.
	 *
	 * @return <code>true</code> if this sample sb is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending();

	/**
	 * Returns <code>true</code> if this sample sb is scheduled.
	 *
	 * @return <code>true</code> if this sample sb is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled();

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(SampleSB sampleSB);

	@Override
	public int hashCode();

	@Override
	public CacheModel<SampleSB> toCacheModel();

	@Override
	public SampleSB toEscapedModel();

	@Override
	public SampleSB toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}