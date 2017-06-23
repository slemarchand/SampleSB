package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.*;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.portlet.*;
import com.liferay.portal.kernel.security.permission.*;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.trash.*;
import com.liferay.portal.kernel.util.*;
import com.liferay.test.constants.*;
import com.liferay.test.model.*;
import com.liferay.test.service.permission.*;

import javax.portlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * @author Yasuyuki Takeo
 */
public class SampleSBAssetRenderer
	extends BaseJSPAssetRenderer<SampleSB>
	implements TrashRenderer {

	public SampleSBAssetRenderer(
		SampleSB entry) {
		_entry = entry;
	}

	@Override
	public SampleSB getAssetObject() {
		return _entry;
	}

	@Override
	public String getClassName() {
		return SampleSB.class.getName();
	}

	@Override
	public long getClassPK() {
		return _entry.getPrimaryKey();
	}

	@Override
	public String getDiscussionPath() {
		return null;
	}

	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) || 
			template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("sampleSB", _entry);
			return "/asset/" + template + ".jsp";
		} else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<SampleSB> assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _entry.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {
		return HtmlUtil.stripHtml(_entry.getSamplesbSummaryName());
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO : This need to be customized 
		return _entry.getSamplesbTitleName();
	}

	@Override
	public String getType() {
		return SampleSBAssetRendererFactory.TYPE;
	}

	@Override
	public String getUrlTitle() {
		// TODO : This need to be customized 
		return _entry.getSamplesbTitleName();
	}
	
	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, SampleSBPortletKeys.SAMPLESB_FIND_ENTRY,
			"resourcePrimKey", _entry.getPrimaryKey());
	}
	
	@Override
	public long getUserId() {
		return _entry.getUserId();
	}

	@Override
	public String getUserName() {
		return _entry.getUserName();
	}

	@Override
	public String getUuid() {
		return _entry.getUuid();
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker) {
		return SampleSBPermissionChecker.contains(permissionChecker, _entry,
			ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return SampleSBPermissionChecker.contains(permissionChecker, _entry,
			ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return SampleSBPermissionChecker.contains(permissionChecker, _entry,
			ActionKeys.VIEW);
	}

	@Override
	public boolean include(
		HttpServletRequest request, HttpServletResponse response,
		String template) throws Exception {
		request.setAttribute("sampleSB", _entry);

		return super.include(request, response, template);
	}
	
	@Override
	public boolean isPrintable() {
		return true;
	}
	
	private final SampleSB _entry;

}
