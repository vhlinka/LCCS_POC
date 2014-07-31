
<%@include file="/jsp/common.jsp" %>
<p> An e-mail notification on Fund Status update will be sent to CaseWorker.</p>
<p></p>

<portlet:renderURL  var="requestFundsURL">
	<portlet:param name="jspPage" value="/group/lccs/fundrequest"/>
</portlet:renderURL>

<aui:form action="<%= requestFundsURL %>" method="post">
	<aui:fieldset>
 		<a href="/group/lccs/fundrequest" class="btn btn-cancel"> Done </a>
 	</aui:fieldset>
 	
</aui:form>