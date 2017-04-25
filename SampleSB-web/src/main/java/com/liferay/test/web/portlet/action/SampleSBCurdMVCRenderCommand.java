package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESB_WEB,
        "mvc.command.name=/samplesb/crud"
    },
    service = MVCRenderCommand.class
)
public class SampleSBCurdMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(
        RenderRequest request, RenderResponse response)
        throws PortletException {

        try {
            ActionUtil.getRecord(request, true);
        } catch (Exception e) {
            throw new PortletException(e);
        }

        return "/edit.jsp";
    }

}