package com.liferay.test.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
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
import com.liferay.test.service.permission.SampleSBPermissionChecker;
import com.liferay.test.service.permission.SampleSBResourcePermissionChecker;
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
		PermissionChecker permissionChecker = themeDisplay
			.getPermissionChecker();

		long entryId = ParamUtil.getLong(request, "resourcePrimKey", 0L);

		if (entryId > 0) {
			deleteEntryIds = new long[] {
				entryId };
		} else {
			deleteEntryIds = StringUtil
				.split(ParamUtil.getString(request, "deleteEntryIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteEntryId : deleteEntryIds) {
			
			//Permission check
			if (!SampleSBPermissionChecker.contains(permissionChecker,
				deleteEntryId, ActionKeys.DELETE)) {
				SessionErrors.add(request, "permission-error for ID - " + String.valueOf(deleteEntryId));
				continue;
			}

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
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker = themeDisplay
			.getPermissionChecker();

		long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

		SampleSB entry = ActionUtil.getSampleSBFromRequest(primaryKey, request);

		List<String> errors = SampleSBValidator.validateSampleSB(request);

		// Permission check
		if (!SampleSBResourcePermissionChecker.contains(permissionChecker,
			themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {
			errors.add("permission-error");
		}

		if (errors.isEmpty()) {
			try {
				ServiceContext serviceContext = ServiceContextFactory
					.getInstance(SampleSB.class.getName(), request);
				_sampleSBLocalService.addEntry(entry, serviceContext);
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
			throw new InvalidParameterException();
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
		ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker = themeDisplay
			.getPermissionChecker();

		long primaryKey = ParamUtil.getLong(request, "resourcePrimKey", 0);

		SampleSB entry = ActionUtil.getSampleSBFromRequest(primaryKey, request);

		List<String> errors = SampleSBValidator.validateSampleSB(request);

		// Permission check
		if (!SampleSBPermissionChecker.contains(permissionChecker,
			entry.getPrimaryKey(), ActionKeys.UPDATE)) {
			errors.add("permission-error");
		}

		if (errors.isEmpty()) {
			try {
				ServiceContext serviceContext = ServiceContextFactory
					.getInstance(SampleSB.class.getName(), request);
				_sampleSBLocalService.updateEntry(entry, serviceContext);

				SessionMessages.add(request, "samplesb-updated-successfully");

			} catch (Exception cvex) {
				SessionErrors.add(request, "please-enter-a-unique-code");
			}
		} else {
			for (String error : errors) {
				SessionErrors.add(request, error);
			}
			request.setAttribute("sampleSB", entry);
			throw new InvalidParameterException();
			
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