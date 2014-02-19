
<%@include file="/jsp/common.jsp" %>
<p> An e-mail notification on Fund Status update will be sent to CaseWorker.</p>
<p></p>

<portlet:renderURL  var="requestFundsURL">
	<portlet:param name="jspPage" value="/jsp/view.jsp"/>
</portlet:renderURL>

<aui:form action="<%= requestFundsURL %>" method="post">
	<aui:fieldset>
 		<aui:button-row>
    		<aui:button type="done" value="Done" />
     	</aui:button-row>
 	</aui:fieldset>
 	
</aui:form>