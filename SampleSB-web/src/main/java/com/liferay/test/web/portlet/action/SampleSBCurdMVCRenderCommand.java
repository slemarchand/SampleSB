package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.test.model.SampleSB;
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
    public String render(RenderRequest request, RenderResponse response)
        throws PortletException {

        String renderJSP = SampleSBWebPortletKeys.VIEW_JSP;

        // Fetch command
        String cmd = ParamUtil.getString(request, Constants.CMD);
        
        // Fetch primary key
        long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

        SampleSB record = null;

        try {
            record = ActionUtil.getRecord(primaryKey, true);

        } catch (Exception e) {
            throw new PortletException(e);
        }

        if (cmd.equalsIgnoreCase(Constants.EDIT)) {
//            if (!SampleSBEntryPermission.contains(
//                permissionChecker, sampleSB, ActionKeys.UPDATE)) {
//                SessionErrors.add(renderRequest, "permission-error");
//                return;
//            }
            request.setAttribute("sampleSB", record);
            renderJSP = SampleSBWebPortletKeys.EDIT_JSP;

        } else if (cmd.equalsIgnoreCase(Constants.VIEW)) {
//            if (!SampleSBEntryPermission.contains(
//                permissionChecker, sampleSB, ActionKeys.VIEW)){
//                SessionErrors.add(renderRequest, "permission-error");
//                return;
//            }
            request.setAttribute("sampleSB", record);
            renderJSP = SampleSBWebPortletKeys.VIEW_SAMPLESB_JSP;

        } else {
//            if (!SampleSBPermission.contains(
//                permissionChecker, themeDisplay.getScopeGroupId(), "ADD_SAMPLESB")){
//                SessionErrors.add(renderRequest, "permission-error");
//                return;
//            }
            request.setAttribute("sampleSB", record);
            renderJSP = SampleSBWebPortletKeys.EDIT_JSP;
        }

        return renderJSP;
    }

}