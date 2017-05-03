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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class SampleSBSoap implements Serializable {
	public static SampleSBSoap toSoapModel(SampleSB model) {
		SampleSBSoap soapModel = new SampleSBSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setSamplesbId(model.getSamplesbId());
		soapModel.setTitle(model.getTitle());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setSamplesbBooleanStat(model.getSamplesbBooleanStat());
		soapModel.setSamplesbDateTime(model.getSamplesbDateTime());
		soapModel.setSamplesbDocument(model.getSamplesbDocument());
		soapModel.setFolderDLId(model.getFolderDLId());
		soapModel.setSamplesbDocumentLibrary(model.getSamplesbDocumentLibrary());
		soapModel.setSamplesbDouble(model.getSamplesbDouble());
		soapModel.setSamplesbInteger(model.getSamplesbInteger());
		soapModel.setSamplesbRichText(model.getSamplesbRichText());
		soapModel.setSamplesbText(model.getSamplesbText());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setUrlTitle(model.getUrlTitle());
		soapModel.setSamplesbTitleName(model.getSamplesbTitleName());
		soapModel.setSamplesbSummaryName(model.getSamplesbSummaryName());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static SampleSBSoap[] toSoapModels(SampleSB[] models) {
		SampleSBSoap[] soapModels = new SampleSBSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SampleSBSoap[][] toSoapModels(SampleSB[][] models) {
		SampleSBSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SampleSBSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SampleSBSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SampleSBSoap[] toSoapModels(List<SampleSB> models) {
		List<SampleSBSoap> soapModels = new ArrayList<SampleSBSoap>(models.size());

		for (SampleSB model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SampleSBSoap[soapModels.size()]);
	}

	public SampleSBSoap() {
	}

	public long getPrimaryKey() {
		return _samplesbId;
	}

	public void setPrimaryKey(long pk) {
		setSamplesbId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSamplesbId() {
		return _samplesbId;
	}

	public void setSamplesbId(long samplesbId) {
		_samplesbId = samplesbId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public boolean getSamplesbBooleanStat() {
		return _samplesbBooleanStat;
	}

	public boolean isSamplesbBooleanStat() {
		return _samplesbBooleanStat;
	}

	public void setSamplesbBooleanStat(boolean samplesbBooleanStat) {
		_samplesbBooleanStat = samplesbBooleanStat;
	}

	public Date getSamplesbDateTime() {
		return _samplesbDateTime;
	}

	public void setSamplesbDateTime(Date samplesbDateTime) {
		_samplesbDateTime = samplesbDateTime;
	}

	public long getSamplesbDocument() {
		return _samplesbDocument;
	}

	public void setSamplesbDocument(long samplesbDocument) {
		_samplesbDocument = samplesbDocument;
	}

	public long getFolderDLId() {
		return _folderDLId;
	}

	public void setFolderDLId(long folderDLId) {
		_folderDLId = folderDLId;
	}

	public String getSamplesbDocumentLibrary() {
		return _samplesbDocumentLibrary;
	}

	public void setSamplesbDocumentLibrary(String samplesbDocumentLibrary) {
		_samplesbDocumentLibrary = samplesbDocumentLibrary;
	}

	public double getSamplesbDouble() {
		return _samplesbDouble;
	}

	public void setSamplesbDouble(double samplesbDouble) {
		_samplesbDouble = samplesbDouble;
	}

	public int getSamplesbInteger() {
		return _samplesbInteger;
	}

	public void setSamplesbInteger(int samplesbInteger) {
		_samplesbInteger = samplesbInteger;
	}

	public String getSamplesbRichText() {
		return _samplesbRichText;
	}

	public void setSamplesbRichText(String samplesbRichText) {
		_samplesbRichText = samplesbRichText;
	}

	public String getSamplesbText() {
		return _samplesbText;
	}

	public void setSamplesbText(String samplesbText) {
		_samplesbText = samplesbText;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		_urlTitle = urlTitle;
	}

	public String getSamplesbTitleName() {
		return _samplesbTitleName;
	}

	public void setSamplesbTitleName(String samplesbTitleName) {
		_samplesbTitleName = samplesbTitleName;
	}

	public String getSamplesbSummaryName() {
		return _samplesbSummaryName;
	}

	public void setSamplesbSummaryName(String samplesbSummaryName) {
		_samplesbSummaryName = samplesbSummaryName;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private String _uuid;
	private long _samplesbId;
	private String _title;
	private Date _startDate;
	private Date _endDate;
	private boolean _samplesbBooleanStat;
	private Date _samplesbDateTime;
	private long _samplesbDocument;
	private long _folderDLId;
	private String _samplesbDocumentLibrary;
	private double _samplesbDouble;
	private int _samplesbInteger;
	private String _samplesbRichText;
	private String _samplesbText;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _urlTitle;
	private String _samplesbTitleName;
	private String _samplesbSummaryName;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
}