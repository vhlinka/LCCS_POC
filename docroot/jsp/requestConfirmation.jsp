
<%@include file="/jsp/common.jsp" %>


<p>Your Request has been submitted. An e-mail notification will be sent to you once approved.</p>
<p></p>

<portlet:renderURL  var="requestFundsURL">
	<portlet:param name="jspPage" value="/jsp/view.jsp"/>
</portlet:renderURL>

<aui:form action="<%= requestFundsURL %>">
	<a href="/group/lccs/fundrequest" class="btn btn-cancel"> Done </a>
 	
</aui:form>