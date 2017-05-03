<%@include file="../init.jsp" %>
<%@ page import="com.liferay.test.model.SampleSB" %>

<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>


<jsp:useBean id="sampleSB" type="com.liferay.test.model.SampleSB" scope="request"/>

<portlet:defineObjects />

<%= sampleSB.getSamplesbTitleName() %>