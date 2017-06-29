package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.permission.SampleSBPermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yasuyuki Takeo
 */
public class SampleSBAssetRenderer
	extends BaseJSPAssetRenderer<SampleSB>
	implements TrashRenderer {

	public SampleSBAssetRenderer(SampleSB entry) {
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
		return _entry.getSamplesbTitleName();
	}

	@Override
	public String getType() {
		return SampleSBAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_entry.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, SampleSBPortletKeys.SAMPLESB_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/samplesb/crud");
		portletURL.setParameter("fromAsset", StringPool.TRUE);
		portletURL.setParameter(Constants.CMD, Constants.UPDATE);
		portletURL.setParameter("resourcePrimKey", String.valueOf(_entry.getPrimaryKey()));

		return portletURL;		
	}
	
	@Override
	public String getUrlTitle() {
		return _entry.getUrlTitle();
	}
	
	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<SampleSB> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter("mvcRenderCommandName", "/samplesb/crud");
		portletURL.setParameter("fromAsset", StringPool.TRUE);
		portletURL.setParameter(Constants.CMD, Constants.VIEW);
		portletURL.setWindowState(windowState);
		portletURL.setParameter("resourcePrimKey", String.valueOf(_entry.getPrimaryKey()));

		return portletURL.toString();
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
