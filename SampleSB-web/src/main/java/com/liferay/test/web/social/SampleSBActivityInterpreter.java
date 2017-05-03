package com.liferay.test.web.social;

import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	property = {"javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB},
	service = SocialActivityInterpreter.class
)
public class SampleSBActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}
	
	@Reference(
		target = "(bundle.symbolic.name=com.liferay.test.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private ResourceBundleLoader _resourceBundleLoader;	
	private static final String[] _CLASS_NAMES = {SampleSB.class.getName()};

}
