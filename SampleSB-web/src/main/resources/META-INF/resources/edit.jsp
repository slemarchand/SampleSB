<%@ include file="/init.jsp" %>

<%
    String CMD = ParamUtil.getString(request, Constants.CMD, Constants.UPDATE);
    SampleSB sampleSB = (SampleSB) request.getAttribute("sampleSB");
    String redirect = ParamUtil.getString(request, "redirect");
%>

<portlet:actionURL name="/samplesb/crud" var="samplesbEditURL">
    <portlet:param name="<%= Constants.CMD %>" value="<%= CMD %>"/>
    <portlet:param name="redirect" value="<%= currentURL %>"/>
</portlet:actionURL>

<aui:form name="samplesbEdit" action="<%= samplesbEditURL %>" method="post">
    <aui:model-context bean="<%= sampleSB %>" model="<%= SampleSB.class %>"/>
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= CMD %>"/>
    <aui:input type="hidden" name="samplesbId" value="<%=sampleSB.getPrimaryKey() %>"/>

    <aui:fieldset>

        <liferay-ui:error key="samplesb-title-required" message="samplesb-title-required"/>
        <aui:input name="title" label='<%= LanguageUtil.get(request, "samplesb-title")%>' />

        <aui:button-row>
            <aui:button cssClass="btn-lg" type="submit" value="add"/>
            &nbsp;&nbsp;&minus; or &minus;&nbsp;&nbsp;
            <aui:a href="<%= redirect %>"><liferay-ui:message key="cancel"/></aui:a>
        </aui:button-row>
    </aui:fieldset>
</aui:form>
