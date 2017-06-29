package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.service.SampleSBLocalServiceUtil;
import com.liferay.test.web.constants.SampleSBWebKeys;
import com.liferay.test.web.upload.SampleSBItemSelectorHelper;

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
		"javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB_ADMIN,
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

				SampleSB record = _sampleSBLocalService.getNewObject(primaryKey);

				if (Validator.isNull(request.getParameter("addErrors"))) {

					record = _sampleSBLocalService.getInitializedSampleSB(primaryKey, request);
				} else {

					record = _sampleSBLocalService.getSampleSBFromRequest(primaryKey, request);
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
	protected void setSampleSBLocalService(
		SampleSBLocalService samplesblocalservice) {
		_sampleSBLocalService = samplesblocalservice;
	}

    @Reference(unbind = "-")
    public void setItemSelectorHelper(
        SampleSBItemSelectorHelper sampleSBItemSelectorHelper) {
        _sampleSBItemSelectorHelper = sampleSBItemSelectorHelper;
    }
 
	private SampleSBLocalService _sampleSBLocalService;
    private SampleSBItemSelectorHelper _sampleSBItemSelectorHelper;

}