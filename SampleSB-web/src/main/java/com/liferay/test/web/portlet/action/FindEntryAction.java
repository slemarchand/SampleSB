package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.test.constants.SampleSBPortletKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
    property = {
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB,
        "path=" + SampleSBPortletKeys.SAMPLESB_FIND_ENTRY
    },
    service = StrutsAction.class
)
public class FindEntryAction extends BaseStrutsAction {

    @Override
    public String execute(
            HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        _findActionHelper.execute(request, response);

        return null;
    }

    @Reference(
        target = "(model.class.name=com.liferay.test.model.SampleSB)",
        unbind = "-"
    )
    protected void setFindActionHelper(FindActionHelper findActionHelper) {
        _findActionHelper = findActionHelper;
    }

    private FindActionHelper _findActionHelper;

}