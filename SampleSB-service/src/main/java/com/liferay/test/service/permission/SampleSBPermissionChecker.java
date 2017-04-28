package com.liferay.test.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.test.model.SampleSB" }
)
public class SampleSBPermissionChecker
	implements BaseModelPermissionChecker {

	public static void check(
		PermissionChecker permissionChecker, SampleSB entry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException.MustHavePermission(permissionChecker,
				SampleSB.class.getName(), entry.getSamplesbId(), actionId);
		}
	}

	public static void check(
		PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException.MustHavePermission(permissionChecker,
				SampleSB.class.getName(), entryId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SampleSB entry, String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			SampleSB.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, entry.getGroupId(), SampleSB.class.getName(),
			entry.getSamplesbId(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (entry.isDraft() || entry.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW)
					&& !contains(permissionChecker, entry, ActionKeys.UPDATE)) {

				return false;
			}
		} else if (entry.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, entry.getGroupId(), SampleSB.class.getName(),
				entry.getSamplesbId(), actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (permissionChecker.hasOwnerPermission(entry.getCompanyId(),
			SampleSB.class.getName(), entry.getSamplesbId(), entry.getUserId(),
			actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(entry.getGroupId(),
			SampleSB.class.getName(), entry.getSamplesbId(), actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		SampleSB entry = SampleSBLocalServiceUtil.getSampleSB(entryId);

		return contains(permissionChecker, entry, actionId);
	}

	@Override
	public void checkBaseModel(
		PermissionChecker permissionChecker, long groupId, long primaryKey,
		String actionId) throws PortalException {
		check(permissionChecker, primaryKey, actionId);

	}
}
