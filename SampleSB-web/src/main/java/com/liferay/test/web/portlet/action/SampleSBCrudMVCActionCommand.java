package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.web.util.SampleSBValidator;
import com.liferay.trash.kernel.util.TrashUtil;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
        "javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB,
        "mvc.command.name=/samplesb/crud"
    },
    service = MVCActionCommand.class
)
public class SampleSBCrudMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected
		void doProcessAction(ActionRequest request, ActionResponse response) {

		try {
			// Fetch command
			String cmd = ParamUtil.getString(request, Constants.CMD);

			if (cmd.equals(Constants.ADD)) {
				addEntry(request, response);

			} else if (cmd.equals(Constants.UPDATE)) {
				updateEntry(request, response);

			} else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(request, response, false);
//				// Fetch primary key
//				long resourcePrimKey = ParamUtil.getLong(request,
//					"resourcePrimKey", 0);
//
//				_sampleSBLocalService.deleteSampleSB(resourcePrimKey);
//				SessionMessages.add(request, "samplesb-deleted-successfully");
//
//				// Fetch redirect
//				String redirect = ParamUtil.getString(request, "redirect");
//				redirect = PortalUtil.escapeRedirect(redirect);
//
//				sendRedirect(request, response, redirect);
			} else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntry(request, response, true);
			}
		} catch (InvalidParameterException e) {
			response.setRenderParameter("mvcRenderCommandName",
				"/samplesb/crud");
			hideDefaultSuccessMessage(request);
		} catch (Throwable t) {

			_log.error(t, t);
			SessionErrors.add(request, PortalException.class);
			hideDefaultSuccessMessage(request);
		}
	}

	/**
	 * Delte Entry
	 *
	 * @param request
	 * @param response
	 * @param moveToTrash true to move to trush.
	 * @throws PortalException
	 * @throws Exception
	 */
	public void deleteEntry(
		ActionRequest request, ActionResponse response, boolean moveToTrash)
		throws PortalException {
		long[] deleteEntryIds = null;
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(request, "resourcePrimKey", 0L);

		if (entryId > 0) {
			deleteEntryIds = new long[] { entryId };
		} else {
			deleteEntryIds = StringUtil
				.split(ParamUtil.getString(request, "deleteEntryIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteEntryId : deleteEntryIds) {
			if (moveToTrash) {
				SampleSB entry = _sampleSBLocalService
					.moveEntryToTrash(themeDisplay.getUserId(), deleteEntryId);

				trashedModels.add(entry);
			} else {
				_sampleSBLocalService.deleteEntry(deleteEntryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(request, trashedModels);

			SessionMessages.add(request,
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		} else {
			SessionMessages.add(request, "samplesb-deleted-successfully");
		}
	}

	/**
	 * Add Entry
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void addEntry(ActionRequest request, ActionResponse response)
		throws Exception {
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
				ServiceContext serviceContext = ServiceContextFactory
					.getInstance(SampleSB.class.getName(), request);
				_sampleSBLocalService.addEntry(sampleSB, serviceContext);
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
	 * Update Entry
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void updateEntry(ActionRequest request, ActionResponse response)
		throws Exception {
		// boolean isMultipart = PortletFileUpload.isMultipartContent(request);
		// if (isMultipart) {
		// uploadManager = new SampleSBUpload();
		// request = extractFields(request, true);
		// }
		long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

		SampleSB sampleSB = ActionUtil.SampleSBFromRequest(primaryKey, request);
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
				ServiceContext serviceContext = ServiceContextFactory
					.getInstance(SampleSB.class.getName(), request);
				_sampleSBLocalService.updateEntry(sampleSB, serviceContext);

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
	protected void setSampleSBLocalService(
		SampleSBLocalService samplesblocalservice) {
		_sampleSBLocalService = samplesblocalservice;
	}

	private SampleSBLocalService _sampleSBLocalService;

	private static Log _log = LogFactoryUtil
		.getLog(SampleSBCrudMVCActionCommand.class);
}