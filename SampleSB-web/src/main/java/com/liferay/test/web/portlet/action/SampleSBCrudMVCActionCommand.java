package com.liferay.test.web.portlet.action;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;
import com.liferay.test.web.util.SampleSBValidator;

import java.security.InvalidParameterException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * CRUD Actions for SampleSB
 *
 * @author Yasuyuki Takeo
 */
@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESB_WEB,
        "mvc.command.name=/samplesb/crud"
    },
    service = MVCActionCommand.class
)
public class SampleSBCrudMVCActionCommand extends BaseMVCActionCommand {

    @Override
    protected void doProcessAction(ActionRequest request, ActionResponse response) {

        try {
            // Fetch command
            String cmd = ParamUtil.getString(request, Constants.CMD);

            if (cmd.equals(Constants.ADD)) {
                addSampleSB(request, response);

            } else if (cmd.equals(Constants.UPDATE)) {
                updateSampleSB(request, response);

            } else if (cmd.equals(Constants.DELETE)) {

                // Fetch primary key
                long resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

                _samplesblocalservice.deleteSampleSB(resourcePrimKey);
                SessionMessages.add(request, "samplesb-deleted-successfully");

                // Fetch redirect
                String redirect = ParamUtil.getString(request, "redirect");
                redirect = PortalUtil.escapeRedirect(redirect);

                sendRedirect(request, response, redirect);
            }
        } catch (InvalidParameterException e) {
            response.setRenderParameter("mvcRenderCommandName", "/samplesb/crud");
            hideDefaultSuccessMessage(request);
        } catch (Throwable t) {

            _log.error(t, t);
            SessionErrors.add(request, PortalException.class);
            hideDefaultSuccessMessage(request);
        }

    }

    /**
     * Add SampleSB
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void addSampleSB(ActionRequest request, ActionResponse response) throws Exception {
        // boolean isMultipart = PortletFileUpload.isMultipartContent(request);
        // if (isMultipart) {
        // uploadManager = new SampleSBUpload();
        // request = extractFields(request, false);
        // }
    	long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);
    	
        SampleSB sampleSB = ActionUtil.SampleSBFromRequest(primaryKey, request);
        // ThemeDisplay themeDisplay = (ThemeDisplay)
        // request.getAttribute(WebKeys.THEME_DISPLAY);
        // PermissionChecker permissionChecker =
        // themeDisplay.getPermissionChecker();
        //
        // if (!SampleSBPermission.contains(permissionChecker,
        // themeDisplay.getScopeGroupId(), "ADD_SAMPLESB")) {
        // SampleSBUtil.addParametersForDefaultView(response);
        // SessionErrors.add(request, "permission-error");
        // return;
        // }
        List<String> errors = SampleSBValidator.validateSampleSB(request);

        if (errors.isEmpty()) {
            // sampleSB = uploadManager.uploadFiles(request, sampleSB);
            try {
                ServiceContext serviceContext = ServiceContextFactory.getInstance(SampleSB.class.getName(), request);
                _samplesblocalservice.addSampleSB(sampleSB, serviceContext);
                SessionMessages.add(request, "samplesb-added-successfully");

            } catch (Exception cvex) {
                SessionErrors.add(request, "please-enter-a-unique-code");
                PortalUtil.copyRequestParameters(request, response);
            }
        } else {
            for (String error : errors) {
                SessionErrors.add(request, error);
            }
            PortalUtil.copyRequestParameters(request, response);
        }

    }

    /**
     * Update SampleSB
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void updateSampleSB(ActionRequest request, ActionResponse response) throws Exception {
        // boolean isMultipart = PortletFileUpload.isMultipartContent(request);
        // if (isMultipart) {
        // uploadManager = new SampleSBUpload();
        // request = extractFields(request, true);
        // }
    	long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);
    	
        SampleSB sampleSB = ActionUtil.SampleSBFromRequest(primaryKey,request);
        // ThemeDisplay themeDisplay = (ThemeDisplay)
        // request.getAttribute(WebKeys.THEME_DISPLAY);
        // PermissionChecker permissionChecker =
        // themeDisplay.getPermissionChecker();
        //
        // if (!SampleSBEntryPermission.contains(permissionChecker, sampleSB,
        // ActionKeys.UPDATE)) {
        // SampleSBUtil.addParametersForDefaultView(response);
        // SessionErrors.add(request, "permission-error");
        // return;
        // }

        List<String> errors = SampleSBValidator.validateSampleSB(request);

        // boolean fromAsset = SampleSBUtil.isFromAsset(request);
        //
        // sampleSB = uploadManager.uploadFiles(request, sampleSB);
        if (errors.isEmpty()) {
            try {
                ServiceContext serviceContext = ServiceContextFactory.getInstance(SampleSB.class.getName(), request);
                _samplesblocalservice.updateSampleSB(sampleSB, serviceContext);

                SessionMessages.add(request, "samplesb-updated-successfully");

            } catch (Exception cvex) {
                SessionErrors.add(request, "please-enter-a-unique-code");
            }
        } else {
            for (String error : errors) {
                SessionErrors.add(request, error);
            }
            request.setAttribute("sampleSB", sampleSB);
        }
    }

    @Reference(unbind = "-")
    protected void setSampleSBLocalService(SampleSBLocalService samplesblocalservice) {
        _samplesblocalservice = samplesblocalservice;
    }
    
    @Reference(unbind = "-")
    protected void setCounterLocalService(CounterLocalService counterLocalService) {
    	_counterLocalService = counterLocalService;
    }

    private SampleSBLocalService _samplesblocalservice;
    private CounterLocalService _counterLocalService;

    private static Log _log = LogFactoryUtil.getLog(SampleSBCrudMVCActionCommand.class);
}