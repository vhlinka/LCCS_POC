
<%@include file="/jsp/common.jsp" %>

<%@ page import="com.liferay.portal.kernel.dao.search.RowChecker" %>

<liferay-ui:success key="success" message="Greeting saved successfully!"/>
<liferay-ui:error key="error" message="Sorry, an error prevented saving your greeting" />
<liferay-ui:error key="email_error" message="Sorry, email notification not implemented yet" />

<portlet:actionURL var="reviewFundRequestManagerURL" name="reviewFundRequestManager">
</portlet:actionURL>

<liferay-ui:panel-container id="bootstrap31">
	<aui:form name="pendingFundRequestsForm" method="post" action="${reviewFundRequestManagerURL}">
		<liferay-ui:panel title=" Pending Fund Requests" extended="true">
	 		<liferay-ui:search-container delta="10" 
	 			emptyResultsMessage="No Pending Fund Requests"
	 			rowChecker="<%= new RowChecker(renderResponse) %>" >
				<liferay-ui:search-container-results results="${pendingFundRequest}" total="${pendingFundRequestCount}"/>
		
					    <liferay-ui:search-container-row className="oh.lccs.portal.requestfund.domain.RequestFunds" keyProperty="id" modelVar="requestFunds">
					        <liferay-ui:search-container-column-text name="Sacwis ID" property="caseId"/>
					        <liferay-ui:search-container-column-text name="Case Name" property="caseName"/>
							<liferay-ui:search-container-column-text name="Case Worker Name" property="caseWorker"/>
							<liferay-ui:search-container-column-text name="Submitted Date and Time" property="createdDate"/>
							<%-- <liferay-ui:search-container-column-text name="Type Of Request" property="requestType[0]"/> --%>
				         
					    </liferay-ui:search-container-row>
					
					    <liferay-ui:search-iterator/>
				</liferay-ui:search-container>
		</liferay-ui:panel>
		<aui:button-row>
				<aui:button type="submit" value="Review Selected Fund Request"/>
				<%-- <aui:button name="new" value="Submit a New Request for Funds" onClick="<%= requestFundsURL %>"/> --%>
				<aui:button type="cancel" value="Cancel" onClick="<%= cancelRequestURL %>"/>
		</aui:button-row>
	</aui:form>
</liferay-ui:panel-container>

<%-- <p><a href="<%= requestFundsURL %>">Submit a New Request for Funds</a></p>
<p><a href="<%= approveFundsURL %>">Review Pending Requests for Funds</a></p> --%>
