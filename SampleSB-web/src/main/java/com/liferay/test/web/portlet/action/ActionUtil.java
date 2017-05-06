package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalServiceUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class ActionUtil {

	/**
	 * Converte Date Time into Date()
	 * 
	 * @param request PortletRequest
	 * @param prefix Prefix of the parameter
	 * @return Date object
	 */
	public static
		Date getDateTimeFromRequest(PortletRequest request, String prefix) {
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
	 */
	public static SampleSB getSampleSBFromRequest(
		long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB sampleSB;
		if (primaryKey <= 0) {
			sampleSB = SampleSBLocalServiceUtil.getNewObject(primaryKey);
		} else {
			sampleSB = SampleSBLocalServiceUtil.fetchSampleSB(primaryKey);
		}

		sampleSB.setSamplesbId(primaryKey);
		sampleSB.setTitle(ParamUtil.getString(request, "title"));
		sampleSB.setStartDate(
			ActionUtil.getDateTimeFromRequest(request, "startDate"));
		sampleSB
			.setEndDate(ActionUtil.getDateTimeFromRequest(request, "endDate"));
		sampleSB.setSamplesbBooleanStat(
			ParamUtil.getBoolean(request, "samplesbBooleanStat"));
		sampleSB.setSamplesbDateTime(
			ActionUtil.getDateTimeFromRequest(request, "samplesbDateTime"));
		sampleSB.setSamplesbDocument(
			ParamUtil.getLong(request, "samplesbDocument"));
		sampleSB.setFolderDLId(ParamUtil.getLong(request, "folderDLId"));
		sampleSB.setSamplesbDocumentLibrary(
			ParamUtil.getString(request, "samplesbDocumentLibrary"));
		sampleSB
			.setSamplesbDouble(ParamUtil.getDouble(request, "samplesbDouble"));
		sampleSB.setSamplesbInteger(
			ParamUtil.getInteger(request, "samplesbInteger"));
		sampleSB.setSamplesbRichText(
			ParamUtil.getString(request, "samplesbRichText"));
		sampleSB.setSamplesbText(ParamUtil.getString(request, "samplesbText"));
		sampleSB.setSamplesbTitleName(
			ParamUtil.getString(request, "samplesbTitleName"));
		sampleSB.setSamplesbSummaryName(
			ParamUtil.getString(request, "samplesbSummaryName"));

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
	public static SampleSB getInitializedSampleSB(
		long primaryKey, PortletRequest request) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		// Create or fetch existing data
		SampleSB sampleSB = SampleSBLocalServiceUtil.getNewObject(primaryKey);

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
		sampleSB.setSamplesbTitleName("");
		sampleSB.setSamplesbSummaryName("");

		sampleSB.setCompanyId(themeDisplay.getCompanyId());
		sampleSB.setGroupId(themeDisplay.getScopeGroupId());
		sampleSB.setUserId(themeDisplay.getUserId());

		return sampleSB;
	}

	private static Log _log = LogFactoryUtil.getLog(ActionUtil.class);
}
