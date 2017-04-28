package com.liferay.test.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.test.model.SampleSB;

/**
 * @author Yasuyuki Takeo
 */
@OSGiBeanProperties(
	property = {
		"resource.name=" + SampleSBPermission.RESOURCE_NAME })
public class SampleSBPermission
	extends BaseResourcePermissionChecker {

	public static final String RESOURCE_NAME = "com.liferay.test";

	public static void check(
		PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long classPK, String actionId) {
		String portletId = PortletProviderUtil.getPortletId(
			SampleSB.class.getName(), PortletProvider.Action.EDIT);

		return contains(permissionChecker, RESOURCE_NAME, portletId, classPK,
			actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {
		return contains(permissionChecker, classPK, actionId);
	}
}