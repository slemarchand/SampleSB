<%@ include file="/init.jsp"%>
<%
	SampleSB sampleSB = (SampleSB) request.getAttribute("sampleSB");
	String redirect = ParamUtil.getString(request, "redirect");
%>

<div class="container-fluid-1280">
	<aui:fieldset>
		<div class="form-group">
			<h3><liferay-ui:message key="asset-title" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbTitleName()%></p>
		</div>
		<div class="form-group">
			<h3><liferay-ui:message key="asset-summary-name" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbSummaryName()%></p>
		</div>			
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-title" /></h3>
			<p class="form-control"><%=sampleSB.getTitle()%></p>
		</div>
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-startdate" /></h3>
			<p class="form-control"><%=sampleSB.getStartDate()%></p>
		</div>		
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-enddate" /></h3>
			<p class="form-control"><%=sampleSB.getEndDate()%></p>
		</div>				
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbbooleanstat" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbBooleanStat()%></p>
		</div>			
		
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbdatetime" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbDateTime()%></p>
		</div>	
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbdocumentlibrary" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbDocumentLibrary()%></p>
		</div>
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbdouble" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbDouble()%></p>
		</div>		
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbinteger" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbInteger()%></p>
		</div>	
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbrichtext" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbRichText()%></p>
		</div>
		<div class="form-group">
			<h3><liferay-ui:message key="samplesb-samplesbtext" /></h3>
			<p class="form-control"><%=sampleSB.getSamplesbText()%></p>
		</div>										
		<aui:button-row>
			<aui:button cssClass="btn-lg" href="<%=redirect%>" name="backToList"
				primary="<%=true%>" value="back-to-list" />
		</aui:button-row>
	</aui:fieldset>
</div>
