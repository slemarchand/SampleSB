package com.liferay.test.web.portlet;

import com.liferay.portal.kernel.portlet.BasePortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.test.constants.SampleSBPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.test.model.SampleSB"},
	service = PortletLayoutFinder.class
)
public class SampleSBPortletLayoutFinder extends BasePortletLayoutFinder {

	@Override
	protected String[] getPortletIds() {
		return _PORTLET_IDS;
	}

	private static final String[] _PORTLET_IDS = {SampleSBPortletKeys.SAMPLESB};

}
