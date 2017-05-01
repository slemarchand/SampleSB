package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalServiceUtil;
import com.liferay.test.web.constants.SampleSBjspKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB,
        "mvc.command.name=/samplesb/crud"
    },
    service = MVCRenderCommand.class
)
public class SampleSBCurdMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {

		String renderJSP = SampleSBjspKeys.VIEW_JSP;

		// Fetch command
		String cmd = ParamUtil.getString(request, Constants.CMD);

		// Fetch primary key
		long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

		try {
			if (cmd.equalsIgnoreCase(Constants.UPDATE)) {

				SampleSB record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
				request.setAttribute("folderDLId", String.valueOf(record.getFolderDLId()));
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBjspKeys.EDIT_JSP;

			} else if (cmd.equalsIgnoreCase(Constants.VIEW)) {

				SampleSB record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBjspKeys.VIEW_SAMPLESB_JSP;

			} else {

				SampleSB record = ActionUtil.getNewObject(primaryKey);

				if (Validator.isNull(request.getParameter("addErrors"))) {
					
					record = ActionUtil.SampleSBInitialize(primaryKey,request);
				} else {
					
					record = ActionUtil.SampleSBFromRequest(primaryKey, request);
				}
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBjspKeys.EDIT_JSP;
			}
		} catch (PortalException e) {
			throw new PortletException(e.getMessage());
		}

		return renderJSP;
	}
}