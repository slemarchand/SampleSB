<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.UnicodeFormatter" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.trash.kernel.util.TrashUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="javax.portlet.PortletException" %>
<%@ page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@ page import="com.liferay.trash.kernel.util.TrashUtil"%>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>

<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<%@ page import="com.liferay.test.model.SampleSB" %>
<%@ page import="com.liferay.test.service.SampleSBLocalServiceUtil"%>
<%@ page import="com.liferay.test.web.constants.PagenationWebKeys" %>
<%@ page import="com.liferay.test.web.util.PagenationContext" %>
<%@ page import="com.liferay.test.web.portlet.action.SampleSBConfiguration" %>

<liferay-frontend:defineObjects/>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<liferay-theme:defineObjects />

<%
SampleSBConfiguration sampleSBConfiguration =
        (SampleSBConfiguration)
        renderRequest.getAttribute(SampleSBConfiguration.class.getName());

    String prefsViewType = StringPool.BLANK;
    
    if (Validator.isNotNull(sampleSBConfiguration)) {
        prefsViewType =
            HtmlUtil.escape(
            portletPreferences.getValue(
                "prefsViewType", String.valueOf(sampleSBConfiguration.prefsViewType())));
    }

%>