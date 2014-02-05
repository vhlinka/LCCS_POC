
<%@include file="/jsp/common.jsp" %>


<liferay-ui:success key="success" message="Greeting saved successfully!"/>
<liferay-ui:error key="error" message="Sorry, an error prevented saving your greeting" />
<liferay-ui:error key="email_error" message="Sorry, email notification not implemented yet" />

<%
PortletPreferences prefs = renderRequest.getPreferences();
String greeting = (String)prefs.getValue(
"greeting", "Hello! Welcome to our portal.");
%>

<p>Lucas County Children's Services: PENDING Fund Requests</p>

<p></p>
<p></p>
<p>Case 1</p>
<p>Case 2</p>
<p>Case 3</p>
<p>Case 4</p>


<portlet:actionURL var="requestFundsURL" name="setupSearch">
    <portlet:param name="mvcPath" value="/jsp/requestFunds.jsp" />
</portlet:actionURL>

<portlet:actionURL var="approveFundsURL" name="showPendingRequests">
    <portlet:param name="mvcPath" value="/jsp/approveFunds.jsp" />
</portlet:actionURL>


<p><a href="<%= requestFundsURL %>">Submit a New Request for Funds</a></p>
<p><a href="<%= approveFundsURL %>">Review Pending Requests for Funds</a></p>
