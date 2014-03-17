<%@include file="/jsp/common.jsp" %>

<liferay-ui:error key="caseIdNotFound-error" message="No Records Found. Please try with valid Sacwis Id." />
<liferay-ui:error key="system-error" message="System failure. Please try again later." />

<jsp:useBean id="fundrequest" class="oh.lccs.portal.requestfund.domain.RequestFunds" scope="session">
	<jsp:setProperty name="fundrequest" property="*"/>
</jsp:useBean>

<portlet:actionURL var="searchSACWISURL" name="searchSACWIS"></portlet:actionURL>

<liferay-ui:panel-container id="lccs-request-for-funds-body">
	<div id="server-form-validation-errors"><!-- server form validation errors div --></div>
	<div id="form-validation-errors"><ul><!-- errors --></ul></div>
	
	<div id="lccs-request-for-funds-main-content">
	
		<liferay-ui:panel title=" CASE SEARCH " id="lccs-request-for-funds-form-content">
			<aui:form name="requestFundsSearchForm" id="requestFundsSearchForm" action="<%= searchSACWISURL %>" method="post">
				<aui:layout> 		
					<aui:fieldset>
						<aui:input
								name="caseId"
								label="Sacwis ID">
								<aui:validator name="required" />
								<aui:validator name="number" />
								<aui:validator name="min">5</aui:validator>
								
						</aui:input>
					 
						<aui:button-row>
			 				<aui:button type="submit" value="Search"/>
			 				<aui:button
								type="cancel"
			 					value="Cancel"
			 					onClick="<%= cancelRequestURL %>"
			 					/>
			 			</aui:button-row>
					</aui:fieldset>
				</aui:layout>
			</aui:form>
		</liferay-ui:panel><!-- End:lccs-request-for-funds-form-content -->
	</div><!--lccs-request-for-funds-main-content -->
	
</liferay-ui:panel-container> <!--End: lccs-request-for-funds-body  -->
<div id="lccs-request-for-funds-search-result-content">
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" data-backdrop="static" data-keyboard="true" tabindex="-1" role="modal"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<h1>Thank you for your application.</h1>
				<p>You will know shortly if you have been approved.</p>
				<div class="processing-img"><img src="<%=request.getContextPath()%>/images/processing_indicator.gif"/></div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
$(function() {
	requestFundsSearchObj.init('${searchCaseBasedOnSacwisNumber}');
});
</script>

