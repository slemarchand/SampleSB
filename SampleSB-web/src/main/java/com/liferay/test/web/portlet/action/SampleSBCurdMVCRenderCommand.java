package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.*;
import com.liferay.portal.kernel.portlet.bridges.mvc.*;
import com.liferay.portal.kernel.util.*;
import com.liferay.test.constants.*;
import com.liferay.test.model.*;
import com.liferay.test.service.*;
import com.liferay.test.web.constants.*;
import com.liferay.test.web.upload.*;
import org.osgi.service.component.annotations.*;

import javax.portlet.*;

/**
 * @author Yasuyuki Takeo
 */
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

		String renderJSP = SampleSBWebKeys.VIEW_JSP;

		// Fetch command
		String cmd = ParamUtil.getString(request, Constants.CMD);

		// Fetch primary key
		long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

		try {
			if (cmd.equalsIgnoreCase(Constants.UPDATE)) {

				SampleSB record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
				request.setAttribute("folderDLId", String.valueOf(record.getFolderDLId()));
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBWebKeys.EDIT_JSP;

			} else if (cmd.equalsIgnoreCase(Constants.VIEW)) {

				SampleSB record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBWebKeys.VIEW_RECORD_JSP;

			} else {

				SampleSB record = ActionUtil.getNewObject(primaryKey);

				if (Validator.isNull(request.getParameter("addErrors"))) {

					record = ActionUtil.SampleSBInitialize(primaryKey,request);
				} else {

					record = ActionUtil.SampleSBFromRequest(primaryKey, request);
				}
				request.setAttribute("sampleSB", record);
				renderJSP = SampleSBWebKeys.EDIT_JSP;
			}
		} catch (PortalException e) {
			throw new PortletException(e.getMessage());
		}

        request.setAttribute(SampleSBWebKeys.SAMPLESB_ITEM_SELECTOR_HELPER, _sampleSBItemSelectorHelper);

		return renderJSP;
	}

    @Reference(unbind = "-")
    public void setItemSelectorHelper(
        SampleSBItemSelectorHelper sampleSBItemSelectorHelper) {

        _sampleSBItemSelectorHelper = sampleSBItemSelectorHelper;
    }

    private SampleSBItemSelectorHelper _sampleSBItemSelectorHelper;
}