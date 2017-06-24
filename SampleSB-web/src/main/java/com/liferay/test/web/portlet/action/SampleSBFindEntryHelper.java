package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.test.model.SampleSB",
	service = FindActionHelper.class
)
public class SampleSBFindEntryHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		SampleSB entry = _sampleSBLocalService.getSampleSB(primaryKey);

		return entry.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "resourcePrimKey";
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter("resourcePrimKey", String.valueOf(primaryKey));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		portletURL.setParameter("mvcRenderCommandName", "/samplesb/crud");
		portletURL.setParameter(Constants.CMD, Constants.VIEW);
		portletURL.setParameter("redirect", portletURL.toString());
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletLayoutFinder;
	}

	@Reference(
		target = "(model.class.name=com.liferay.test.model.SampleSB)",
		unbind = "-"
	)
	protected void setPortletLayoutFinder(
		PortletLayoutFinder portletPageFinder) {

		_portletLayoutFinder = portletPageFinder;
	}

	@Reference
	private SampleSBLocalService _sampleSBLocalService;
	private PortletLayoutFinder _portletLayoutFinder;

}
