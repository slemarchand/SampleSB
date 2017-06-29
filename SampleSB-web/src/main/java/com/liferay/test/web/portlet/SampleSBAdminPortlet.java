package com.liferay.test.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.test.constants.SampleSBPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * SampleSB Admin Portlet
 * 
 * TODO: This portlet merely delegate all requests and actions to
 * SampleSBWebPortlet for an edit link of Asset Publisher portlet. You'll have a
 * link in a product menu (the menus in the left side pane on Liferay) but it's
 * same one you have in a SampleSBWebPortlet. If you need to implement a decent
 * Admin Portlet, please refer jsp files under the following folder.
 * liferay-portal/modules/apps/collaboration/blogs/blogs-web/src/main/resources/
 * META-INF/resources/blogs_admin/
 *
 * @author Yasuyuki Takeo
 */
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.preferences-unique-per-layout=true",           
        "com.liferay.portlet.preferences-owned-by-group=true",        
        "com.liferay.portlet.css-class-wrapper=portlet-samplesb-admin",
        "com.liferay.portlet.display-category=category.hidden",
        // TODO : If com.liferay.portlet.instanceable is true, Edit for Asset Publisher doesn't work.
        "com.liferay.portlet.instanceable=false", 
        "com.liferay.portlet.render-weight=50",
        "com.liferay.portlet.scopeable=true",           
        "com.liferay.portlet.struts-path=samplesb-admin",
        "com.liferay.portlet.use-default-template=true",
        "javax.portlet.display-name=SampleSB-Admin Portlet",
        "javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.test.web.portlet.action" + SampleSBPortletKeys.SAMPLESB_ADMIN,
        "javax.portlet.init-param.always-display-default-configuration-icons=true",         
        "javax.portlet.expiration-cache=0",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB_ADMIN, 
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=administrator",
        "javax.portlet.supports.mime-type=text/html"
    },
    service = Portlet.class
)
public class SampleSBAdminPortlet extends MVCPortlet {

}
