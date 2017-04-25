<%@include file="/init.jsp"%>

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	SampleSB sampleSB = (SampleSB) row.getObject();

	long groupId = sampleSB.getGroupId();
	String name = SampleSB.class.getName();
	String primKey = String.valueOf(sampleSB.getPrimaryKey());
%>
<liferay-ui:icon-menu
	cssClass="<%=row == null ? "entry-options inline" : StringPool.BLANK%>"
	direction="left-side" icon="<%=StringPool.BLANK%>"
	markupView="lexicon" message="<%=StringPool.BLANK%>"
	showWhenSingleIcon="<%=true%>">

	<portlet:actionURL name="editSampleSB" var="editSampleSBURL">
		<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
	</portlet:actionURL>

	<portlet:actionURL name="/samplesb/crud" var="editSampleSBURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
		<portlet:param name="redirect" value="<%=currentURL%>" />
		<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
	</portlet:actionURL>	
	<liferay-ui:icon image="edit" url="<%=editSampleSBURL.toString()%>" />

	<portlet:actionURL name="/samplesb/crud" var="deleteSampleSBURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="resourcePrimKey" value="<%=primKey%>" />
	</portlet:actionURL>	
    <liferay-ui:icon image="delete" url="<%=deleteSampleSBURL.toString()%>" />
    
</liferay-ui:icon-menu>
