package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.test.model.SampleSB;
import com.liferay.test.web.constants.PagenationWebKeys;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;
import com.liferay.test.web.util.EntryListContainer;
import com.liferay.test.web.util.EntryListContainerFactory;
import com.liferay.test.web.util.PagenationContext;
import com.liferay.test.web.util.PagenationFactory;

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

    	// Fetch page context
        PagenationContext<SampleSB> pagenationContext = PagenationFactory.create(request);

        // Fetch data
        EntryListContainer<SampleSB> entryList = EntryListContainerFactory.create(request,pagenationContext);
        
        pagenationContext.setResult(entryList.getResults());
        pagenationContext.setTotal(entryList.getTotal());

        request.setAttribute(PagenationWebKeys.PAGENATION_CONTEXT, pagenationContext);
        
        return "/view.jsp";
    }

}