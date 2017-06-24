package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.service.permission.SampleSBPermissionChecker;
import com.liferay.test.service.permission.SampleSBResourcePermissionChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
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

    public static final String TYPE = "samplesb";

    public SampleSBAssetRendererFactory() {
        setClassName(SampleSB.class.getName());
        setPortletId(SampleSBPortletKeys.SAMPLESB);
        setLinkable(true);
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
    public AssetRenderer<SampleSB> getAssetRenderer(
        long groupId, String urlTitle)
        throws PortalException {

        SampleSB entry = _sampleSBLocalService.getSampleSBByUrlTitle(
            groupId, urlTitle, WorkflowConstants.STATUS_ANY);

        if(Validator.isNull(entry)) {
        	return null;
        }
        
        return new SampleSBAssetRenderer(entry);
    }

    @Override
    public String getClassName() {
        return SampleSB.class.getName();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public PortletURL getURLAdd(
        LiferayPortletRequest liferayPortletRequest,
        LiferayPortletResponse liferayPortletResponse, long classTypeId) {

        LiferayPortletURL liferayPortletURL =
            liferayPortletResponse.createLiferayPortletURL(
                SampleSBPortletKeys.SAMPLESB, PortletRequest.RENDER_PHASE);

        liferayPortletURL.setParameter("mvcRenderCommandName", "/samplesb/crud");
        liferayPortletURL.setParameter(Constants.CMD, Constants.ADD);

        return liferayPortletURL;
    }

    @Override
    public PortletURL getURLView(
        LiferayPortletResponse liferayPortletResponse,
        WindowState windowState) {

        LiferayPortletURL liferayPortletURL =
            liferayPortletResponse.createLiferayPortletURL(
                SampleSBPortletKeys.SAMPLESB, PortletRequest.RENDER_PHASE);

        liferayPortletURL.setParameter("mvcRenderCommandName", "/samplesb/view");
        liferayPortletURL.setParameter(Constants.CMD, Constants.VIEW);

        try {
            liferayPortletURL.setWindowState(windowState);
        }
        catch (WindowStateException wse) {
            _log.error("Windos state is not valid. Skip.");
        }

        return liferayPortletURL;
    }

    @Override
    public boolean hasAddPermission(
        PermissionChecker permissionChecker, long groupId, long classTypeId)
        throws Exception {

        if( !SampleSBResourcePermissionChecker.contains(
                permissionChecker, groupId, ActionKeys.VIEW) ) {
        	return false;
        }
        
        return SampleSBResourcePermissionChecker.contains(
            permissionChecker, groupId, ActionKeys.ADD_ENTRY);
    }

    @Override
    public boolean hasPermission(
        PermissionChecker permissionChecker, long classPK, String actionId)
        throws Exception {

        return SampleSBPermissionChecker.contains(
            permissionChecker, classPK, actionId);
    }

    @Reference(
        target = "(osgi.web.symbolicname=com.liferay.test.web)", unbind = "-"
    )
    public void setServletContext(ServletContext servletContext) {
        _servletContext = servletContext;
    }

    @Reference
    private Portal _portal;
    @Reference
    private SampleSBLocalService _sampleSBLocalService;

    private ServletContext       _servletContext;

    private static final Log _log = LogFactoryUtil.getLog(
        SampleSBAssetRendererFactory.class);
}
