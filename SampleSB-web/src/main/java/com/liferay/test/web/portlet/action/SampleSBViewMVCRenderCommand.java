package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.web.constants.SampleSBWebKeys;
import com.liferay.test.web.util.SampleSBViewHelper;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB,
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
    	  
    	request.setAttribute(SampleSBWebKeys.SAMPLESB_VIEW_HELPER, _sampleSBViewHelper);
    	   
        return "/view.jsp";
    }

    @Reference(unbind = "-")
    public void setViewHelper(
    	SampleSBViewHelper sampleSBViewHelper) {
        _sampleSBViewHelper = sampleSBViewHelper;
    }
    
    private SampleSBViewHelper _sampleSBViewHelper;      
}