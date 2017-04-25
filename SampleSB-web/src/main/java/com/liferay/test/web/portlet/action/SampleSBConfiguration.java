package com.liferay.test.web.portlet.action;

import com.liferay.test.web.constants.SampleSBWebPortletKeys;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Yasuyuki Takeo
 */
@Meta.OCD(id = SampleSBWebPortletKeys.SAMPLESB_CONFIG)
public interface SampleSBConfiguration {

	public static final String CONF_PREFS_VIEW_TYPE = "prefsViewType";

	@Meta.AD(deflt = "0", required = false)
	public int prefsViewType();
}
