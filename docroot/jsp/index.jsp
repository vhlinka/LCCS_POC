
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/jsp/common.jsp" %>

<liferay-ui:success key="success" message="Greeting saved successfully!"/>
<liferay-ui:error key="error" message="Sorry, an error prevented saving your greeting" />
<liferay-ui:error key="email_error" message="Sorry, email notification not implemented yet" />

<liferay-ui:panel-container id="bootstrapview">
<liferay-ui:panel title="Lucas County Children's Services: Fund Request Form (LCCS #4695, Rev 10/02) " id="request-for-funds-form-content">


<portlet:actionURL var="validateUserURL" name="validateUser"></portlet:actionURL>

<br>

<p> <aui:a label="Go to Request for Funds" href="<%= validateUserURL %>" /></p>
</liferay-ui:panel>
</liferay-ui:panel-container>				


<%-- 

<p><a href="<%= sampleFormURL %>">Sample AlloutUI Form</a></p>
 --%>