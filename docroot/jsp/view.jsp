
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/jsp/common.jsp" %>

<liferay-ui:success key="success" message="Greeting saved successfully!"/>
<liferay-ui:error key="error" message="Sorry, an error prevented saving your greeting" />
<liferay-ui:error key="email_error" message="Sorry, email notification not implemented yet" />

<liferay-ui:panel-container id="bootstrapview">
<liferay-ui:panel title="Lucas County Children's Services: Fund Request Form (LCCS #4695, Rev 10/02) " id="request-for-funds-form-content">


<portlet:actionURL var="requestFundsURL" name="setupSearch"></portlet:actionURL>

<portlet:actionURL var="showPendingRequestsSupervisorURL" name="showPendingRequestsSupervisor"></portlet:actionURL>
<portlet:actionURL var="showPendingRequestsManagerURL" name="showPendingRequestsManager"></portlet:actionURL>
<portlet:actionURL var="showPendingRequestsFinancialURL" name="showPendingRequestsFinancial"></portlet:actionURL>

<portlet:actionURL var="sampleFormURL" name="runSampleForm"></portlet:actionURL>

<br>
<c:if test="${userProfile.caseWorker}">
	<p> <aui:a label="Submit a New Request for Funds - Case Worker" href="<%= requestFundsURL %>" /></p>
</c:if>
<c:if test="${userProfile.supervisor}">
	<p><aui:a label="Review Pending Requests for Funds - Supervisor" href="<%= showPendingRequestsSupervisorURL %>" /></p>
</c:if>
<c:if test="${userProfile.manager}">
	<p><aui:a label="Review Pending Requests for Funds - Manager" href="<%= showPendingRequestsManagerURL %>" /></p>
</c:if>
<c:if test="${userProfile.financeApprover}">
	<p><aui:a label="Review Pending Requests for Funds - Financial" href="<%= showPendingRequestsFinancialURL %>" /></p>
</c:if>
</liferay-ui:panel>
</liferay-ui:panel-container>				


<%-- 

<p><a href="<%= sampleFormURL %>">Sample AlloutUI Form</a></p>
 --%>