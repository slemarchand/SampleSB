package com.liferay.test.web.portlet.action;

import com.google.common.collect.Lists;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.test.model.SampleSB;
import com.liferay.test.web.constants.PagenationWebKeys;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;
import com.liferay.test.web.util.EntryListContainer;
import com.liferay.test.web.util.EntryListContainerImpl;
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
        "javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESB_WEB,
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

        //Search Key
        String searchFilter = ParamUtil.getString(request, PagenationWebKeys.SEARCH_FILTER);
        
    	// Fetch page context
        PagenationContext<SampleSB> pagenationContext = PagenationFactory.create(request);

        // Fetch list data
        EntryListContainer<SampleSB> entryList = new EntryListContainerImpl<SampleSB>();
        entryList.setResults(Lists.newArrayList());
        
        if(searchFilter.equalsIgnoreCase("")) {
        	entryList = ActionUtil.getListFromDB(request, pagenationContext);
        } else {
        	try {
				entryList = ActionUtil.getListFromIndex(request, pagenationContext);
			} catch (SearchException e) {
				_log.error(e.getMessage());
			}
        }
        
        pagenationContext.setResult(entryList.getResults());
        pagenationContext.setTotal(entryList.getTotal());

        request.setAttribute(PagenationWebKeys.PAGENATION_CONTEXT, pagenationContext);
        
        return "/view.jsp";
    }

	private static final Log _log = LogFactoryUtil.getLog(
			SampleSBViewMVCRenderCommand.class);
}