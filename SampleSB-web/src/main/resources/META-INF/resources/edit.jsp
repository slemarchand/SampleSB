<%@ include file="/init.jsp"%>

<%
    String CMD = ParamUtil.getString(request, Constants.CMD, Constants.UPDATE);
    SampleSB sampleSB = (SampleSB) request.getAttribute("sampleSB");
    String redirect = ParamUtil.getString(request, "redirect");
%>

<portlet:actionURL name="/samplesb/crud" var="samplesbEditURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= CMD %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form name="samplesbEdit" action="<%= samplesbEditURL %>"
	method="post">
	<aui:model-context bean="<%= sampleSB %>" model="<%= SampleSB.class %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= CMD %>" />
	<aui:input type="hidden" name="resourcePrimKey"
		value="<%=sampleSB.getPrimaryKey() %>" />

	<aui:fieldset>
		<% String requiredLabel=""; %>

		<% requiredLabel ="*"; %>
		<liferay-ui:error key="samplesb-title-required"
			message="samplesb-title-required" />
		<aui:input name="title"
			label='<%= LanguageUtil.get(request, "samplesb-title") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="startDate" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-startdate") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="endDate" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-enddate") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="samplesbBooleanStat" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-samplesbbooleanstat") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="samplesbDateTime" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-samplesbdatetime") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="samplesbDouble" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-samplesbdouble") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:input name="samplesbInteger" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-samplesbinteger") + requiredLabel %>' />

		<% requiredLabel =""; %>
		<aui:field-wrapper
			label="<%= LanguageUtil.get(request, \"samplesb-samplesbrichtext\") + requiredLabel %>">
			<liferay-ui:input-editor name="samplesbRichTextEditor"
				initMethod="initsamplesbRichTextEditor" />
		</aui:field-wrapper>
		<aui:input name="samplesbRichText" type="hidden" />

		<% requiredLabel =""; %>
		<aui:input name="samplesbText" disabled="false"
			label='<%= LanguageUtil.get(request, "samplesb-samplesbtext") + requiredLabel %>' />

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="add" />
            &nbsp;&nbsp;&minus; or &minus;&nbsp;&nbsp;
            <aui:a href="<%= redirect %>">
				<liferay-ui:message key="cancel" />
			</aui:a>
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
     function <portlet:namespace />initsamplesbRichTextEditor() {
         return '<%=UnicodeFormatter.toString(sampleSB.getSamplesbRichText()) %>';
    }

     function <portlet:namespace />saveEditors() {
          document.<portlet:namespace />samplesbEdit.<portlet:namespace />samplesbRichText.value = window.<portlet:namespace />samplesbRichTextEditor.getHTML();
          submitForm(document.<portlet:namespace />samplesbEdit);
     }
</aui:script>