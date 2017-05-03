package com.liferay.test.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.web.portlet.action.SampleSBConfiguration;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;

/**
 * SampleSB Portlet
 *
 * @author Yasuyuki Takeo
 */
@Component(
    configurationPid =SampleSBPortletKeys.SAMPLESB_CONFIG,	 		
    immediate = true,
    property = {
        "com.liferay.portlet.preferences-unique-per-layout=true",           
        "com.liferay.portlet.preferences-owned-by-group=true",        
        "com.liferay.portlet.css-class-wrapper=portlet-samplesb",
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.instanceable=true",
        "com.liferay.portlet.use-default-template=true",
        "javax.portlet.display-name=SampleSB-web Portlet",
        "javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.test.web.portlet.action" + SampleSBPortletKeys.SAMPLESB,
    	"javax.portlet.init-param.always-display-default-configuration-icons=true",    		
        "javax.portlet.expiration-cache=0",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB, 
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user",
        "javax.portlet.supports.mime-type=text/html"
    },
    service = Portlet.class
)
public class SampleSBWebPortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		renderRequest.setAttribute(SampleSBConfiguration.class.getName(), _sampleSBConfiguration);

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_sampleSBConfiguration = Configurable.createConfigurable(SampleSBConfiguration.class, properties);
	}

	private volatile SampleSBConfiguration _sampleSBConfiguration;
}