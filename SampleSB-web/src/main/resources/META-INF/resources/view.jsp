<%@ include file="/init.jsp"%>
<%
	PortletURL navigationPortletURL = renderResponse.createRenderURL();
	PortletURL portletURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);
	
	String keywords = ParamUtil.getString(request, "keywords");
	int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
	int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
	String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
	String orderByType = ParamUtil.getString(request, "orderByType", "asc");
	
	navigationPortletURL.setParameter("keywords", keywords);
	navigationPortletURL.setParameter("cur", String.valueOf(cur));  
	navigationPortletURL.setParameter("mvcRenderCommandName", "/samplesb/view");
	navigationPortletURL.setParameter("orderByCol", orderByCol);
	navigationPortletURL.setParameter("orderByType", orderByType);
%>

<portlet:renderURL var="samplesbAddURL">
	<portlet:param name="mvcRenderCommandName" value="/samplesb/crud" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
	<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
</portlet:renderURL>

<div class="container-fluid-1280" >
	<aui:button-row>
		<aui:button href="<%= samplesbAddURL %>" cssClass="btn btn-default"
			icon="icon-plus" value="add-samplesb" />
	</aui:button-row>
</div>
	
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

		<liferay-frontend:management-bar-sort 
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title", "display-date"} %>'
			portletURL="<%= navigationPortletURL %>" />
	</liferay-frontend:management-bar-filters>
	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='#' icon='times'
			label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280"
	id="<portlet:namespace />formContainer">
	<liferay-ui:success key="samplesb-added-successfully"
		message="samplesb-added-successfully" />

	<liferay-ui:error exception="<%= PortletException.class %>"
		message="there-was-an-unexpected-error.-please-refresh-the-current-page" />

	<liferay-ui:search-container 
		deltaConfigurable="true"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		searchContainer='<%= new SearchContainer(renderRequest, PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse), null, "no-recodes-were-found") %>'>

		<liferay-ui:search-container-results>
			<%@ include file="/search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.test.model.SampleSB" keyProperty="sampleSBId"
			modelVar="sampleSB">

			<liferay-ui:search-container-column-text name="SampleSB Id"
				property="samplesbId" align="left" />

			<liferay-ui:search-container-column-text name="Title"
				property="title" align="left" />
				
			<liferay-ui:search-container-column-jsp align="right"
				path="/edit_actions.jsp" />
				
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>

</div>