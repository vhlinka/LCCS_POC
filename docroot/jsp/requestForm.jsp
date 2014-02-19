
<%@include file="/jsp/common.jsp" %>


<%
	//RequestFunds requestForm = (RequestFunds) request.getAttribute("fundrequest");
%>
<jsp:useBean id="fundrequest" class="oh.lccs.portal.requestfund.domain.RequestFunds" scope="session">
	<jsp:setProperty name="fundrequest" property="*"/>
</jsp:useBean>

<portlet:actionURL var="submitRequestURL" name="submitFundRequest"></portlet:actionURL>





<h2>Please Complete the Request Form</h2>

<div id="additionalInfo" style="width:800px">

	<aui:form name="fdr1" id="fdr1" action="<%= submitRequestURL %>" method="post">
	<aui:layout> 

 		<aui:fieldset>
 			<aui:input
				name="budgetCenter"
				label="Budget Center"
				value="${fundrequest.budgetCenter}"
				size="15"
				width="60">
				<aui:validator name="required" />
			</aui:input>	

	 		<aui:input
				name="personRespForPurchase"
				label="Person(s) Responsible for Purchase"
				value="${fundrequest.personRespForPurchase}"
				size="50"
				width="30">
				<aui:validator name="required" />
				<aui:validator name="digits" />
			</aui:input>	
			
 			<aui:input
				name="requestPurpose"
				label="Purpose for Request"
				type="textarea"
				rows="5"
				value="${fundrequest.requestPurpose}">
				<aui:validator name="required" />
			</aui:input>	
			
	 		<aui:input
				name="totalAmountRequested"
				label="Total Amount Requested"
				value="${fundrequest.totalAmtRequested}">
				<aui:validator name="required" />
				<aui:validator name="number" />
			</aui:input>	
			
	 		<aui:button-row>
 				<aui:button type="submit"/>
 			
 				<aui:button
					type="cancel"
 					value="Cancel"
 					onClick="<%= cancelRequestURL %>"
 					/>
 				
 			</aui:button-row>

 		</aui:fieldset>

	</aui:layout>
	</aui:form> 

</div>

<div id="sampleCode">
  <input type="text" id="some-input" />
  <span id="counter"></span> character(s) remaining

  <script>
  YUI().use(
    'aui-char-counter',
    function(Y) {
      new Y.CharCounter(
        {
          counter: '#counter',
          input: '#some-input',
          maxLength: 10
        }
      );
    }
  );
  </script>

</div>

