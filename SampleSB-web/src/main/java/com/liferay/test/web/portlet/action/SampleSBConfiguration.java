package com.liferay.test.web.portlet.action;

import com.liferay.test.constants.SampleSBPortletKeys;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Yasuyuki Takeo
 */
@Meta.OCD(id = SampleSBPortletKeys.SAMPLESB_CONFIG)
public interface SampleSBConfiguration {

	public static final String CONF_PREFS_VIEW_TYPE = "prefsViewType";
	public static final String CONF_DATE_FORMAT = "dateFormat";
	public static final String CONF_DATETIME_FORMAT = "datetimeFormat";
	
	public static final String PREFS_VIEW_TYPE_DEFAULT = "0";
	public static final String PREFS_VIEW_TYPE_USER = "1";
	public static final String PREFS_VIEW_TYPE_USER_GROUP = "2";
	
	@Meta.AD(deflt = PREFS_VIEW_TYPE_DEFAULT, required = false)
	public int prefsViewType();

	@Meta.AD(deflt = "yyyy/MM/dd", required = false)
	public String dateFormat();

	@Meta.AD(deflt = "yyyy/MM/dd HH:mm", required = false)
	public String datetimeFormat();
}
