<%@ include file="/init.jsp"%>
<%
	String iconChecked = "checked";
	String iconUnchecked = "unchecked";
	PortletPreferences prefs = renderRequest.getPreferences();
 	SimpleDateFormat dateFormat = new SimpleDateFormat(prefs.getValue("dateFormat", "yyyy/MM/dd"));
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(prefs.getValue("datetimeFormat","yyyy/MM/dd HH:mm"));

	PortletURL navigationPortletURL = renderResponse.createRenderURL();
	PortletURL portletURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);

	String keywords = ParamUtil.getString(request, DisplayTerms.KEYWORDS);
	int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
	int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
	String orderByCol = ParamUtil.getString(request, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "samplesbId");
	String orderByType = ParamUtil.getString(request, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	String[] orderColumns = new String[] { "samplesbId", "title", "startDate", "endDate" };
	
	navigationPortletURL.setParameter(DisplayTerms.KEYWORDS, keywords);
	navigationPortletURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, String.valueOf(cur));
	navigationPortletURL.setParameter("mvcRenderCommandName", "/samplesb/view");
	navigationPortletURL.setParameter(SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, orderByCol);
	navigationPortletURL.setParameter(SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, orderByType);
%>

<portlet:renderURL var="samplesbAddURL">
	<portlet:param name="mvcRenderCommandName" value="/samplesb/crud" />
	<portlet:param name="<%=Constants.CMD%>" value="<%=Constants.ADD%>" />
	<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
</portlet:renderURL>

<div class="container-fluid-1280">
	<aui:button-row>
		<aui:button href="<%=samplesbAddURL%>" cssClass="btn btn-default"
			icon="icon-plus" value="add-samplesb" />
	</aui:button-row>
</div>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:form action="<%=portletURL.toString()%>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-frontend:management-bar 
	includeCheckBox="<%=true%>"
	searchContainerId="entryList">

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort orderByCol="<%=orderByCol%>"
			orderByType="<%=orderByType%>"
			orderColumns='<%=orderColumns%>'
			portletURL="<%=navigationPortletURL%>" />
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%="javascript:" + renderResponse.getNamespace() + "deleteEntries();"%>'
			icon='<%=TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times"%>'
			label='<%=TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete"%>' />
	</liferay-frontend:management-bar-action-buttons>

</liferay-frontend:management-bar>

<div class="container-fluid-1280"
	id="<portlet:namespace />formContainer">

	<aui:form action="<%=navigationPortletURL.toString()%>" method="get" name="fm">
		<aui:input name="<%=Constants.CMD%>" type="hidden" />
		<aui:input name="redirect" type="hidden"
			value="<%=navigationPortletURL.toString()%>" />
		<aui:input name="deleteEntryIds" type="hidden" />

		<liferay-ui:success key="samplesb-added-successfully"
			message="samplesb-added-successfully" />

		<liferay-ui:error exception="<%=PortletException.class%>"
			message="there-was-an-unexpected-error.-please-refresh-the-current-page" />

		<liferay-ui:search-container 
			id="entryList" 
			deltaConfigurable="true"
			rowChecker="<%=new EmptyOnClickRowChecker(renderResponse)%>"
			searchContainer='<%=new SearchContainer(renderRequest,
							PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse), null,
							"no-recodes-were-found")%>'>

			<liferay-ui:search-container-results>
				<%@ include file="/search_results.jspf"%>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.test.model.SampleSB" 
				escapedModel="<%= true %>"
				keyProperty="samplesbId"
				rowIdProperty="samplesbId"
				modelVar="sampleSB">

				<liferay-ui:search-container-column-text name="SampleSB Id"
					property="samplesbId" align="left" />

				<liferay-ui:search-container-column-text
					name="Title"
				    property="title"
					orderable="true"
					orderableProperty="title"
					align="left"
				/>
					
 				<liferay-ui:search-container-column-text
					name="Start Date"
					value="<%= dateFormat.format(sampleSB.getStartDate()) %>"
					orderable="true"
					orderableProperty="startDate"
					align="left"
				/> 
				
				<liferay-ui:search-container-column-text
					name="End Date"
					value="<%= dateFormat.format(sampleSB.getEndDate()) %>"
					orderable="true"
					orderableProperty="endDate"
					align="left"
				/>
				
				<liferay-ui:search-container-column-text name="SamplesbBoolean Stat"
					property="samplesbBooleanStat" align="left" />		
		
				<liferay-ui:search-container-column-text
					name="Samplesb DateTime"
					value="<%= dateTimeFormat.format(sampleSB.getSamplesbDateTime()) %>"
					orderable="true"
					orderableProperty="samplesbDateTime"
					align="left"
				/>
									
				<liferay-ui:search-container-column-text
					name="SampleSB Double"
				    property="samplesbDouble"
					align="left"
				/>		
				
				<liferay-ui:search-container-column-text
					name="SampleSB Ingeger"
				    property="samplesbInteger"
					align="left"
				/>		
				
				<liferay-ui:search-container-column-text name="SampleSB richtext" align="center">
					<%
		 				String samplesbRichTextIcon = iconUnchecked;
						String samplesbRichText = sampleSB.getSamplesbRichText();
						if (!samplesbRichText.equals("")) {
							samplesbRichTextIcon= iconChecked;
		 				}
		 			  %>
		 			  <liferay-ui:icon image="<%= samplesbRichTextIcon %>"/>
				</liferay-ui:search-container-column-text>	
				
				<liferay-ui:search-container-column-text name="SampleSB richtext" align="center">
					<%
		 				String samplesbTextIcon = iconUnchecked;
						String samplesbText = sampleSB.getSamplesbText();
						if (!samplesbText.equals("")) {
							samplesbTextIcon= iconChecked;
		 				}
		 			  %>
		 			  <liferay-ui:icon image="<%= samplesbTextIcon %>"/>
				</liferay-ui:search-container-column-text>																	

				<liferay-ui:search-container-column-jsp align="right"
					path="/edit_actions.jsp" />

			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%=TrashUtil.isTrashEnabled(scopeGroupId)%> || confirm('<%=UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries")%>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%=Constants.CMD%>').val('<%=TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE%>');
			form.fm('deleteEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/samplesb/crud" />');
		}
	}
</aui:script>