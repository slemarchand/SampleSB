package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.web.constants.SampleSBWebPortletKeys;
import com.liferay.test.web.util.SampleSBValidator;

import java.io.IOException;
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
    property =
        {
            "javax.portlet.name=" + SampleSBWebPortletKeys.SAMPLESBWEB,
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

            if (cmd.equals(Constants.ADD) ||
                cmd.equals(Constants.UPDATE)) {

                updateRecord(request, response);

            } else if (
                cmd.equals(Constants.DELETE)) {

                // Fetch primary key
                long samplesbId = ParamUtil.getLong(request, "samplesbId", 0);

                _samplesblocalservice.deleteSampleSB(samplesbId);
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
     * Add / Update Record
     *
     * @param request  Action Request
     * @param response Action Response
     * @throws PortalException
     * @throws IOException
     */
    private void updateRecord(ActionRequest request, ActionResponse response)
        throws Exception {

        SampleSB sampleSB = SampleSBFromRequest(request);
        request.setAttribute("sampleSB", sampleSB);

        //Validate
        List<String> errors = SampleSBValidator.validateSampleSB(request);

        if (!errors.isEmpty()) {
            //Set errors into Sessions
            errors.stream().forEach(error -> SessionErrors.add(request, error));
            throw new InvalidParameterException("Parameter Error");
        }

        if (sampleSB.getPrimaryKey() <= 0) {
            //Add Record
            _samplesblocalservice.addSampleSB(sampleSB);
            SessionMessages.add(request, "samplesb-added-successfully");

        } else {
            //Update Record
            _samplesblocalservice.updateSampleSB(sampleSB);
            SessionMessages.add(request, "samplesb-updated-successfully");
        }
    }

    /**
     * Populate Model with values from a form
     *
     * @param request
     * @return SampleSB Object
     */
    private SampleSB SampleSBFromRequest(ActionRequest request) throws Exception {

        //Create or fetch Primary key
        SampleSB sampleSB = ActionUtil.getRecord(request, false);

        sampleSB.setTitle(ParamUtil.getString(request, "title"));

//        int SampleSBdateAmPm = ParamUtil.getInteger(request, "SampleSBdateAmPm");
//        int SampleSBdateHour = ParamUtil.getInteger(request, "SampleSBdateHour");
//        DateTimeFormatter fSampleSBdate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime SampleSBdate = LocalDateTime.of(
//            ParamUtil.getInteger(request, "SampleSBdateYear"),
//            ParamUtil.getInteger(request, "SampleSBdateMonth") + 1,
//            ParamUtil.getInteger(request, "SampleSBdateDay"),
//            (SampleSBdateAmPm == Calendar.PM) ? SampleSBdateHour + 12 : SampleSBdateHour,
//            ParamUtil.getInteger(request, "SampleSBdateMinute"),
//            0);
//
//        sampleSB.setSamplesbDateTime(Date.from(SampleSBdate.atZone(ZoneId.systemDefault()).toInstant()));
//
//        sampleSB.setSamplesbInteger(ParamUtil.getInteger(request, "SamplesbInteger"));
//        sampleSB.setSamplesbDouble(ParamUtil.getDouble(request, "SamplesbDouble"));

        return sampleSB;
    }

    @Reference(unbind = "-")
    protected void setSampleSBLocalService(
        SampleSBLocalService samplesblocalservice) {
        _samplesblocalservice = samplesblocalservice;
    }

    private SampleSBLocalService _samplesblocalservice;

    private static Log _log = LogFactoryUtil.getLog(SampleSBCrudMVCActionCommand.class);
}