<%@ include file="/init.jsp"%>
<%
    PortletURL portletURL = renderResponse.createRenderURL();
    PortletURL navigationPortletURL = renderResponse.createRenderURL();
    PortletURL sortURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);
    
%>
<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
    <aui:form action="<%= portletURL.toString() %>" name="searchFm">
        <aui:nav-bar-search>
            <liferay-ui:input-search markupView="lexicon" />
        </aui:nav-bar-search>
    </aui:form>
</aui:nav-bar>

<liferay-frontend:management-bar includeCheckBox="<%= true %>"
    searchContainerId="samplesbRecords">

    <liferay-frontend:management-bar-filters>

        <liferay-frontend:management-bar-sort orderByCol=""
            orderByType=""
            orderColumns='<%= new String[] {"title", "display-date"} %>'
            portletURL="<%= sortURL %>" />
    </liferay-frontend:management-bar-filters>
    <liferay-frontend:management-bar-action-buttons>
        <liferay-frontend:management-bar-button href='#' icon='times'
            label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />
    </liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:renderURL var="samplesbAddURL">
    <portlet:param name="mvcRenderCommandName" value="/samplesb/crud" />
    <portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
    <portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<div class="container-fluid-1280"
    id="<portlet:namespace />formContainer">
    <liferay-ui:success key="samplesb-added-successfully"
        message="samplesb-added-successfully" />
		
    <liferay-ui:error exception="<%= PortletException.class %>"
        message="there-was-an-unexpected-error.-please-refresh-the-current-page" />
    <aui:button-row>
        <aui:button href="<%= samplesbAddURL %>" cssClass="btn btn-default"
            icon="icon-plus" value="add-samplesb" />
    </aui:button-row>
    

</div>