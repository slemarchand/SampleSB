package com.liferay.test.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.service.permission.SampleSBPermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author yasuflatland
 */
@Component(
	property = {"model.class.name=com.liferay.test.model.SampleSB"},
	service = TrashHandler.class
)
public class SampleSBTrashHandler
	extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_sampleSBLocalService.deleteEntry(classPK);
	}

	@Override
	public String getClassName() {
		return SampleSB.class.getName();
	}

	@Override
	public String getRestoreContainedModelLink(
		PortletRequest portletRequest, long classPK)
		throws PortalException {

		SampleSB entry = _sampleSBLocalService.getSampleSB(classPK);
		
		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		portletURL.setParameter(Constants.CMD, Constants.UPDATE);
		portletURL.setParameter("resourcePrimKey", String.valueOf(entry.getPrimaryKey()));
		
		return portletURL.toString();
	}

	@Override
	public String getRestoreContainerModelLink(
		PortletRequest portletRequest, long classPK)
		throws PortalException {
		PortletURL portletURL = getRestoreURL(portletRequest, classPK, true);

		return portletURL.toString();
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long classPK) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.translate("SampleSB");
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		SampleSB entry = _sampleSBLocalService.getSampleSB(classPK);

		return entry.isInTrash();
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_sampleSBLocalService.restoreEntryFromTrash(userId, classPK);
	}

	protected PortletURL getRestoreURL(
			PortletRequest portletRequest, long classPK, boolean containerModel)
		throws PortalException {

		PortletURL portletURL = null;

		SampleSB entry = _sampleSBLocalService.getSampleSB(classPK);

		long plid = _portal.getPlidFromPortletId(entry.getGroupId(), SampleSBPortletKeys.SAMPLESB);

		portletURL = PortletURLFactoryUtil.create(
			portletRequest, SampleSBPortletKeys.SAMPLESB, plid,
			PortletRequest.RENDER_PHASE);
		
		if (!containerModel) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/samplesb/crud");
		} else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/samplesb/view");			
		}

		return portletURL;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return SampleSBPermissionChecker.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setSampleSBLocalService(
		SampleSBLocalService samplesblocalservice) {
		_sampleSBLocalService = samplesblocalservice;
	}

	private SampleSBLocalService _sampleSBLocalService;

	@Reference
	private Portal _portal;
}
