package com.liferay.test.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import com.liferay.test.web.constants.SampleSBWebPortletKeys;
import org.osgi.service.component.annotations.Component;

/**
 * SampleSB Portlet
 *
 * @author yasuflatland
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-samplesb",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SampleSB-web Portlet",
		"javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.test.web.portlet.action" + SampleSBWebPortletKeys.SAMPLESBWEB,
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESBWEB, 
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SampleSBWebPortlet extends MVCPortlet {
}