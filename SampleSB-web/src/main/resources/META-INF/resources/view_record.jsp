<%@ include file="/init.jsp"%>
<%
	SampleSB sampleSB = (SampleSB) request.getAttribute("sampleSB");
	String redirect = ParamUtil.getString(request, "redirect");
%>

<div class="container-fluid-1280">
	<aui:fieldset>
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-title" /></h3>
			<p class="form-control"><%=sampleSB.getTitle()%></p>
		</div>
		<aui:button-row>
			<aui:button cssClass="btn-lg" href="<%=redirect%>" name="backToList"
				primary="<%=true%>" value="back-to-list" />
		</aui:button-row>
	</aui:fieldset>
</div>
