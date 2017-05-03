package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB
	},
	service = AssetRendererFactory.class
)
public class SampleSBAssetRendererFactory extends BaseAssetRendererFactory<SampleSB> {

	public static final String TYPE = "sampleSB";

	public SampleSBAssetRendererFactory() {
		setClassName(SampleSB.class.getName());
		setLinkable(true);
		setPortletId(SampleSBPortletKeys.SAMPLESB);
		setSearchable(true);
	}
	
	@Override
	public AssetRenderer<SampleSB> getAssetRenderer(long classPK, int type) throws PortalException {
		SampleSB entry = _sampleSBLocalService.getSampleSB(classPK);

		SampleSBAssetRenderer sampleSBAssetRenderer =
			new SampleSBAssetRenderer(entry);

		sampleSBAssetRenderer.setAssetRendererType(type);
		sampleSBAssetRenderer.setServletContext(_servletContext);

		return sampleSBAssetRenderer;
	}

	@Override
	public String getClassName() {
		return SampleSB.class.getName();
	}
	
	@Override
	public String getIconCssClass() {
		return TYPE;
	}
	
	@Override
	public String getType() {
		return TYPE;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.test.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}
	
	@Reference(unbind = "-")
	protected void setSampleSBLocalService(SampleSBLocalService sampleSBLocalService) {
		_sampleSBLocalService = sampleSBLocalService;
	}

	private SampleSBLocalService _sampleSBLocalService;	
	private ServletContext _servletContext;
}
