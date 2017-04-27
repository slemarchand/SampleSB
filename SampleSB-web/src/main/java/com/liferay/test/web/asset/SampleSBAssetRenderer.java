package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.test.model.SampleSB;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleSBAssetRenderer extends BaseJSPAssetRenderer<SampleSB> implements TrashRenderer {

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
		}
		else {
			return null;
		}
	}
	
	@Override
	public String getPortletId() {
		AssetRendererFactory<SampleSB> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}
	
	@Override
	public int getStatus() {
		return _entry.getStatus();
	}
	
	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
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
	public boolean include(HttpServletRequest request, HttpServletResponse response, String template) throws Exception {
		request.setAttribute("sampleSB", _entry);

		return super.include(request, response, template);
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

	private SampleSB _entry;

}
