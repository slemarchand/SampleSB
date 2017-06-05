package com.liferay.test.web.portlet.action;

import com.google.common.collect.Lists;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.constants.SampleSBPortletKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;

/**
 * Configuraion Aciton
 * 
 * Determine the scope of the portlet configuration in DS properties of Portlet
 * class The default of this generator is Portlet Instance scope.
 * 
 * Look at the elements preferences-company-wide, preferences-unique-per-layout
 * and preferences-owned-by-group to determine the right scope. The following
 * table maps out the scopes:
 * 
 * liferay-portlet.xml Scope preferences-company-wide=true Company
 * preferences-owned-by-group=true AND preferences-unique-per-layout=false Group
 * preferences-owned-by-group=true AND preferences-unique-per-layout=true
 * Portlet Instance
 * 
 * @author Yasuyuki Takeo
 *
 */
@Component(
    immediate = true,
    configurationPid = SampleSBPortletKeys.SAMPLESB_CONFIG,
    configurationPolicy = ConfigurationPolicy.OPTIONAL,
    property = {
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB,
    }, service = ConfigurationAction.class
)
public class SampleSBConfigrationAction
	extends DefaultConfigurationAction {

	@Override
	public void processAction(
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse) throws Exception {

		int prefsViewType = ParamUtil.getInteger(actionRequest,
			SampleSBConfiguration.CONF_PREFS_VIEW_TYPE,
			Integer.valueOf(SampleSBConfiguration.PREFS_VIEW_TYPE_DEFAULT));
		String dateFormat = ParamUtil.getString(actionRequest,
			SampleSBConfiguration.CONF_DATE_FORMAT);
		String datetimeFormat = ParamUtil.getString(actionRequest,
			SampleSBConfiguration.CONF_DATETIME_FORMAT);

		if (_log.isDebugEnabled()) {
			_log.debug("Prefs View Type :" + prefsViewType);
			_log.debug("Date Format     :" + dateFormat);
			_log.debug("Date Time Format:" + datetimeFormat);
		}

		List<String> errors = Lists.newArrayList();
		if (validate(dateFormat, datetimeFormat,
			errors)) {
			setPreference(actionRequest,
				SampleSBConfiguration.CONF_PREFS_VIEW_TYPE,
				String.valueOf(prefsViewType));
			setPreference(actionRequest, SampleSBConfiguration.CONF_DATE_FORMAT,
				dateFormat);
			setPreference(actionRequest,
				SampleSBConfiguration.CONF_DATETIME_FORMAT, datetimeFormat);

			SessionMessages.add(actionRequest, "prefs-success");
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public void include(
		PortletConfig portletConfig, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("SampleSB Portlet configuration include");
		}

		httpServletRequest.setAttribute(SampleSBConfiguration.class.getName(),
			_sampleSBConfiguration);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_sampleSBConfiguration = Configurable
			.createConfigurable(SampleSBConfiguration.class, properties);
	}

	/**
	 * Validate Preference
	 * 
	 * @param dateFormat
	 *            Date Format
	 * @param datetimeFormat
	 *            Date Time Format
	 * @param errors
	 * @return boolean
	 */
	protected boolean validate(
		String dateFormat, String datetimeFormat, List<String> errors) {
		boolean valid = true;

		if (Validator.isNull(dateFormat)) {
			errors.add("date-format-required");
			valid = false;
		} else if (Validator.isNull(datetimeFormat)) {
			errors.add("datetime-format.required");
			valid = false;
		}
		return valid;
	}

	private static final Log _log = LogFactoryUtil
		.getLog(SampleSBConfigrationAction.class);

	private volatile SampleSBConfiguration _sampleSBConfiguration;
}
