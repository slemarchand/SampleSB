package com.liferay.test.web.asset;

import com.liferay.asset.kernel.model.*;
import com.liferay.portal.kernel.exception.*;
import com.liferay.portal.kernel.log.*;
import com.liferay.portal.kernel.portlet.*;
import com.liferay.portal.kernel.security.permission.*;
import com.liferay.portal.kernel.theme.*;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.kernel.workflow.*;
import com.liferay.test.constants.*;
import com.liferay.test.model.*;
import com.liferay.test.service.*;
import com.liferay.test.service.permission.*;
import org.osgi.service.component.annotations.*;

import javax.portlet.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
            groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);

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
        liferayPortletURL.setParameter("fromAsset", "true");

        return liferayPortletURL;
    }

    @Override
    public PortletURL getURLView(
        LiferayPortletResponse liferayPortletResponse,
        WindowState windowState) {

        String portletId = PortletProviderUtil.getPortletId(
            SampleSB.class.getName(), PortletProvider.Action.VIEW);

        LiferayPortletURL liferayPortletURL =
            liferayPortletResponse.createLiferayPortletURL(
                portletId, PortletRequest.RENDER_PHASE);

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
