<%@ include file="/init.jsp"%>
<%
	boolean fromAsset = (request.getAttribute("fromAsset") != null
		? (Boolean) request.getAttribute("fromAsset")
		: false);
	String CMD = ParamUtil.getString(request, Constants.CMD,
		Constants.UPDATE);
	SampleSB sampleSB = (SampleSB) request.getAttribute("sampleSB");
	String redirect = ParamUtil.getString(request, "redirect");
%>

<portlet:actionURL name="/samplesb/crud" var="samplesbEditURL">
	<portlet:param name="<%=Constants.CMD%>" value="<%=CMD%>" />
	<portlet:param name="redirect" value="<%=currentURL%>" />
</portlet:actionURL>

<aui:fieldset>
	<aui:form name="samplesbEdit" action="<%=samplesbEditURL%>"
		method="post">
		<aui:model-context bean="<%=sampleSB%>" model="<%=SampleSB.class%>" />
		<aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=CMD%>" />
		<aui:input type="hidden" name="resourcePrimKey"
			value="<%=sampleSB.getPrimaryKey()%>" />
		<c:if test='<%=Constants.ADD.equals(CMD)%>'>
			<aui:input type="hidden" name="addGuestPermissions" value="true" />
			<aui:input type="hidden" name="addGroupPermissions" value="true" />
		</c:if>
		<aui:input name="samplesbTitleName" label="title" />
		<aui:input name="samplesbSummaryName" label="summary" />

		<%
			String requiredLabel = "";
		%>

		<%
			requiredLabel = "*";
		%>
		<liferay-ui:error key="samplesb-title-required"
			message="samplesb-title-required" />
		<aui:input name="title"
			label='<%=LanguageUtil.get(request, "samplesb-title")
							+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="startDate" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-startdate")
							+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="endDate" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-enddate")
							+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbBooleanStat" disabled="false"
			label='<%=LanguageUtil.get(request,
						"samplesb-samplesbbooleanstat") + requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbDateTime" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-samplesbdatetime")
								+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbDocumentLibrary" disabled="false"
			label='<%=LanguageUtil.get(request,
						"samplesb-samplesbdocumentlibrary") + requiredLabel%>' />
		<%
		String samplesbDocumentLibraryClick = renderResponse.getNamespace() + "dlBrowse('samplesbDocumentLibrary Files select','" + 
		renderResponse.getNamespace()+"samplesbDocumentLibrary')";
		%>
						
		<aui:button onClick="<%=samplesbDocumentLibraryClick%>" value="select" />						

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbDouble" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-samplesbdouble")
								+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbInteger" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-samplesbinteger")
								+ requiredLabel%>' />

		<%
			requiredLabel = "";
		%>
		<aui:field-wrapper
			label="<%=LanguageUtil.get(request,\"samplesb-samplesbrichtext\") + requiredLabel%>">
			<liferay-ui:input-editor name="samplesbRichTextEditor"
				initMethod="initsamplesbRichTextEditor" />
		</aui:field-wrapper>
		<aui:input name="samplesbRichText" type="hidden" />

		<%
			requiredLabel = "";
		%>
		<aui:input name="samplesbText" disabled="false"
			label='<%=LanguageUtil.get(request, "samplesb-samplesbtext")
							+ requiredLabel%>' />

		<%
			if (sampleSB.getPrimaryKey() != 0) {
		%>
		<liferay-ui:ratings className="<%=SampleSB.class.getName()%>"
			classPK="<%=sampleSB.getPrimaryKey()%>" type="stars" />
		<%
			}
		%>
		<aui:input name="categories" type="assetCategories" />
		<aui:input name="tags" type="assetTags" />

		<liferay-ui:panel defaultState="closed" extended="<%=false%>"
			id="sampleSBEntryAssetLinksPanel" persistState="<%=true%>"
			title="related-assets">
			<aui:fieldset>
				<liferay-ui:input-asset-links
					className="<%=SampleSB.class.getName()%>"
					classPK="<%=sampleSB.getPrimaryKey()%>" />
			</aui:fieldset>
		</liferay-ui:panel>

		<aui:button-row>
			<%
				String publishButtonLabel = "submit";
			%>

			<%
				if (WorkflowDefinitionLinkLocalServiceUtil
								.hasWorkflowDefinitionLink(
									themeDisplay.getCompanyId(), scopeGroupId,
									SampleSB.class.getName())) {
								publishButtonLabel = "submit-for-publication";
							}
			%>
			<aui:button cssClass="btn-lg"
				onClick="<%=renderResponse.getNamespace() +\"saveEditors()\"%>"
				value="<%=publishButtonLabel%>" />
				&nbsp;&nbsp;&minus; or &minus;
			<%
				if (!fromAsset) {
			%>
			<aui:button onClick="<%=redirect%>" type="cancel" />
			<%
				}
			%>
		</aui:button-row>
	</aui:form>
	<%
		if (sampleSB.getPrimaryKey() != 0) {
	%>
	<liferay-ui:panel-container extended="<%=false%>"
		id="sampleSBCommentsPanelContainer" persistState="<%=true%>">

		<liferay-ui:panel collapsible="<%=true%>" extended="<%=true%>"
			id="sampleSBCommentsPanel" persistState="<%=true%>"
			title='<%=LanguageUtil.get(request, "comments")%>'>

			<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

			<liferay-ui:discussion className="<%=SampleSB.class.getName()%>"
				classPK="<%=sampleSB.getPrimaryKey()%>" formName="fm2"
				ratingsEnabled="<%=true%>" redirect="<%=currentURL%>"
				userId="<%=sampleSB.getUserId()%>" />
		</liferay-ui:panel>

	</liferay-ui:panel-container>
	<%
		}
	%>
</aui:fieldset>


<aui:script>
     function <portlet:namespace />initsamplesbRichTextEditor() {
         return '<%=UnicodeFormatter.toString(sampleSB.getSamplesbRichText())%>';
    }

     function <portlet:namespace />saveEditors() {
          document.<portlet:namespace />samplesbEdit.<portlet:namespace />samplesbRichText.value = 
          window.<portlet:namespace />samplesbRichTextEditor.getHTML();
          submitForm(document.<portlet:namespace />samplesbEdit);
     }
</aui:script>

<%
	SampleSBItemSelectorHelper sampleSBItemSelectorHelper = (SampleSBItemSelectorHelper) request
		.getAttribute(SampleSBWebKeys.SAMPLESB_ITEM_SELECTOR_HELPER);
	RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil
		.create(liferayPortletRequest);
	String selectItemName = liferayPortletResponse.getNamespace()
			+ "selectItem";
%>

<aui:script>
    function <portlet:namespace />dlBrowse (title, inputField) {
       	event.preventDefault();
       	var itemSrc = $('#'+inputField);
		AUI().use(
			'liferay-item-selector-dialog',
			function(A) {
				var itemSelectorDialog = new A.LiferayItemSelectorDialog(  
				    {
				        eventName: '<%=selectItemName%>',
				        on: {
			                selectedItemChange: function(event) {
			                    var selectedItem = event.newVal;
			
			                    if (selectedItem) {
			                        var itemValue = JSON.parse(
			                        	selectedItem.value
			                        );
			                        itemSrc.val(itemValue.url);
			                    }
			                }
				        },
				        title: title,
				        url: '<%=sampleSBItemSelectorHelper.getItemSelectorURL(
						requestBackedPortletURLFactory, themeDisplay,
						selectItemName)%>'
				    }
				);
				itemSelectorDialog.open();
			}
		);       	
    }
</aui:script>