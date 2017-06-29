package com.liferay.test.web.portlet;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.test.constants.SampleSBPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100", //TODO : this number determin the order in the panel
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT
	},
	service = PanelApp.class
)
public class SampleSBPanelApp extends BasePanelApp  {

	@Override
	public String getPortletId() {
		return SampleSBPortletKeys.SAMPLESB_ADMIN;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}	
}
