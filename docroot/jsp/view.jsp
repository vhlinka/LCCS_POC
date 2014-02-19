

<%@include file="/jsp/common.jsp" %>

<liferay-ui:success key="success" message="Greeting saved successfully!"/>
<liferay-ui:error key="error" message="Sorry, an error prevented saving your greeting" />
<liferay-ui:error key="email_error" message="Sorry, email notification not implemented yet" />

<%
PortletPreferences prefs = renderRequest.getPreferences();
String greeting = (String)prefs.getValue(
"greeting", "Hello! Welcome to our portal.");
%>

<p>Lucas County Children's Services: Fund Request Form (LCCS #4695, Rev 10/02)</p>


<portlet:actionURL var="requestFundsURL" name="setupSearch"></portlet:actionURL>

<portlet:actionURL var="showPendingRequestsURL" name="showPendingRequests"></portlet:actionURL>

<portlet:actionURL var="sampleFormURL" name="runSampleForm"></portlet:actionURL>

<br>
<p><aui:a label="Submit a New Request for Funds" href="<%= requestFundsURL %>" /></p>
<p><aui:a label="Review Pending Requests for Funds" href="<%= showPendingRequestsURL %>" /></p>
				


<%-- 

<p><a href="<%= sampleFormURL %>">Sample AlloutUI Form</a></p>
 --%>