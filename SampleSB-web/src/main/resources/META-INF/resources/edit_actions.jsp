<%@include file="/init.jsp"%>

<%
	PortletURL navigationPortletURL = renderResponse.createRenderURL();
	PortletURL portletURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);
	
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	SampleSB sampleSB = (SampleSB) row.getObject();

	long groupId = sampleSB.getGroupId();
	String name = SampleSB.class.getName();
	String primKey = String.valueOf(sampleSB.getPrimaryKey());
%>
<liferay-ui:icon-menu
	cssClass="<%=row == null ? "entry-options inline" : StringPool.BLANK%>"
	direction="left-side" icon="<%=StringPool.BLANK%>" markupView="lexicon"
	message="<%=StringPool.BLANK%>" showWhenSingleIcon="<%=true%>">

	<c:if test="<%= SampleSBPermissionChecker.contains(permissionChecker, sampleSB, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="com.liferay.test.model.SampleSB"
			modelResourceDescription="SampleSB"
			resourcePrimKey="<%=String.valueOf(primKey)%>"
			var="permissionsEntryURL" />
	
		<liferay-ui:icon image="permissions" url="<%=permissionsEntryURL%>" />
	</c:if>

	<c:if test="<%= SampleSBPermissionChecker.contains(permissionChecker, sampleSB, ActionKeys.VIEW) %>">
		<portlet:renderURL var="viewSampleSBURL">
			<portlet:param name="mvcRenderCommandName" value="/samplesb/crud" />
			<portlet:param name="<%=Constants.CMD%>"
				value="<%=Constants.VIEW%>" />
			<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
			<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
		</portlet:renderURL>
		<liferay-ui:icon image="view" url="<%=viewSampleSBURL.toString()%>" />
	</c:if>

	<c:if test="<%= SampleSBPermissionChecker.contains(permissionChecker, sampleSB, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editSampleSBURL">
			<portlet:param name="mvcRenderCommandName" value="/samplesb/crud" />
			<portlet:param name="<%=Constants.CMD%>"
				value="<%=Constants.UPDATE%>" />
			<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
			<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
		</portlet:renderURL>
		<liferay-ui:icon image="edit" url="<%=editSampleSBURL.toString()%>" />
	</c:if>

	<c:if test="<%= SampleSBPermissionChecker.contains(permissionChecker, sampleSB, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/samplesb/crud" var="deleteSampleSBURL">
			<portlet:param name="<%=Constants.CMD%>"
				value="<%=Constants.DELETE%>" />
			<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
			<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
		</portlet:actionURL>
		<liferay-ui:icon image="delete" url="<%=deleteSampleSBURL.toString()%>" />
	</c:if>
	
	<c:if test="<%= SampleSBPermissionChecker.contains(permissionChecker, sampleSB, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/samplesb/crud" var="moveToTrashSampleSBURL">
			<portlet:param name="<%=Constants.CMD%>"
				value="<%=Constants.MOVE_TO_TRASH%>" />
			<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
			<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
		</portlet:actionURL>
		<liferay-ui:icon image="trash" url="<%=moveToTrashSampleSBURL.toString()%>" />	
	</c:if>

</liferay-ui:icon-menu>
