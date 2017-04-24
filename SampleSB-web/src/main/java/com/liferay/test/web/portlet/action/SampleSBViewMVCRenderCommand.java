package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Yasuyuki Takeo
 */
@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESBWEB,
        "mvc.command.name=/",
        "mvc.command.name=/samplesb/view"
    },
    service = MVCRenderCommand.class
)
public class SampleSBViewMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(
        RenderRequest request, RenderResponse response)
        throws PortletException {
        
        return "/view.jsp";
    }

}