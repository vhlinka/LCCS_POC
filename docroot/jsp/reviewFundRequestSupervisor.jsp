<%@include file="/jsp/common.jsp" %>

<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.liferay.portal.kernel.dao.search.RowChecker" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page contentType="text/html" isELIgnored="false"%>
<portlet:defineObjects />

<liferay-ui:error key="system-error" message="System failure. Please try again later." />

<jsp:useBean id="fundrequest" class="oh.lccs.portal.requestfund.domain.RequestFunds" scope="session">
	<%-- <jsp:setProperty name="fundrequest" property="*"/> --%>
</jsp:useBean>

<portlet:actionURL var="approveRequestSupervisorURL" name="approveFundRequestSupervisor"></portlet:actionURL>
<portlet:actionURL var="declineRequestSupervisorURL" name="declineFundRequestSupervisor"></portlet:actionURL>

<%RowChecker rowCh = new RowChecker(renderResponse); %>
<liferay-ui:panel-container id="bootstrap31">
	<aui:form name="SacwisForm" method="post" action="${approveRequestSupervisorURL}">

		<liferay-ui:panel title="CASE WORKER DETAILS" extended="true">
 			<fieldset>
 				<aui:input  inlineField="true" type = "hidden" label="Request ID: "  name="id" value="${fundrequest.id}"></aui:input>
				<aui:input  inlineField="true" label="Sacwis ID: "  name="caseId" value="${fundrequest.caseId}"></aui:input>
				<aui:input  inlineField="true" label="Request Date: "  name="requestedDate" value="${fundrequest.requestedDate}"></aui:input>
			</fieldset>
			<fieldset>
				<aui:input  inlineField="true" label="Caseworker Requesting: " name="requestingCaseWorker" value="${fundrequest.requestingCaseWorker}"></aui:input> 
	       		<aui:input  inlineField="true" label="Caseworker : " name="caseWorker" value="${fundrequest.caseWorker}"></aui:input> 
	       		<aui:input  type = "hidden" inlineField="true" label="CaseName : " name="caseName" value="${fundrequest.caseName}"></aui:input>
	       		<aui:input  inlineField="true" label="Worker Phone:" name="workerPhoneNumber" value="${fundrequest.workerPhoneNumber}"></aui:input>
			</fieldset>
       	</liferay-ui:panel>

		<liferay-ui:panel title="LIST OF PEOPLE FOR WHOM FUND REQUEST IS MADE" extended="true">
			<aui:field-wrapper label="Type of Request *: (Select atleast one option) ">
				
				<aui:column columnWidth="25" first="true" > 
					<aui:input name="donation" label="Donation" type="checkbox" value="${fundrequest.donation}"></aui:input>
					<aui:input  name="operating" label="Operating" type="checkbox" value="${fundrequest.operating}"></aui:input>
				</aui:column>
				<aui:column columnWidth="25" > 
					<aui:input  name="prePlacement" label="Preplacement Prevention" type="checkbox" value="${fundrequest.prePlacement}"></aui:input>
					<aui:input  name="familyReunification" label="Family Reunification" type="checkbox" value="${fundrequest.familyReunification}"></aui:input>
		        	</aui:column>
		        <aui:column columnWidth="25" > 
					<aui:input name="afterCareIndependence" label="Aftercare Independence" type="checkbox" value="${fundrequest.afterCareIndependence}"></aui:input>
					<aui:input  name="childWelfare" label="TANF / Child Welfare" type="checkbox" value=""></aui:input>
		        	</aui:column>
		        <aui:column columnWidth="25" last="true"> 
		        	<aui:input  name="kinshipCare" label="Kinship Care" type="checkbox" value="${fundrequest.kinshipCare}"></aui:input>
					<aui:input  name="alternativeResponse" label="Alternative Response" type="checkbox" value="${fundrequest.alternativeResponse}"></aui:input>
		        </aui:column>

			</aui:field-wrapper>

		  	<br>
			
			  <aui:field-wrapper first="true" label="Requesting for *: (select atleast one option) ">
			  
				  <liferay-ui:search-container
				  	delta="10" emptyResultsMessage="No Participants for Case">
				    <liferay-ui:search-container-results results="${fundrequest.requestingForPeople}" total="${fundrequest.countOfParticipants}"/>
	
				    <liferay-ui:search-container-row className="oh.lccs.portal.requestfund.domain.CaseParticipant" keyProperty="sacwisId" modelVar="requestingForPeople">
				        <liferay-ui:search-container-column-text name="Person's Full Name" property="personFullName">
				        	<%-- <aui:input inlineField="true"  name="personFullName" label="personFullName" type="checkx" value=""></aui:input> --%>
				        </liferay-ui:search-container-column-text>
				        <liferay-ui:search-container-column-text name="Client/Sacwis ID" property="sacwisId"/>
				        <liferay-ui:search-container-column-text name="DOB" property="dob"/>
						<liferay-ui:search-container-column-text name="Type" property="type"/>
						<liferay-ui:search-container-column-text name="Custody With" property="custody"/>
						<liferay-ui:search-container-column-text name="Child Placement" property="placement"/>
						<liferay-ui:search-container-column-text name="Custody Date" property="custodyDate"/>
						<liferay-ui:search-container-column-text name="IV-E Reimbursable" property="iveReimbursable"/>
				         
				    </liferay-ui:search-container-row>
				
				    <liferay-ui:search-iterator/>
				</liferay-ui:search-container>
			  </aui:field-wrapper>	
		</liferay-ui:panel>

		<liferay-ui:panel title="INFORMATION FILLED IN BY CASE WORKER FOR APPROVAL" extended="true">
			<aui:fieldset column="true"  size="260" >
				<aui:input inlineField="false" style="width: 60%;" maxlength="250" rows="1" label="Person(s) responsible for making purchase*:" name="personRespForPurchase" value="${fundrequest.personRespForPurchase}" type="textarea" required="true"></aui:input>
				<aui:input inlineField="false" style="width: 60%;" maxlength="250" rows="6" label="Purpose of Request*: " name="requestPurpose"  value="${fundrequest.requestPurpose}" type="textarea" required="true"></aui:input>
		        <aui:input inlineField="false" style="width: 60%;" maxlength="250" rows="1" label="Other Community Resources Contacted:" name="otherCommResContacted" value="${fundrequest.otherCommResContacted}" type="textarea" ></aui:input>
				
				<aui:column columnWidth="50" first="true" > 
					<aui:input  label="Total Amount Requested: "  name="totalAmtRequested" value="${fundrequest.totalAmtRequested}"></aui:input>
					
					<aui:select   label="Fund type: " name="fundMode" onchange="<%= renderResponse.getNamespace() + \"chooseSelectionStyle();\" %>">
					  	<aui:option selected="<%= \"VOUCHER\".equalsIgnoreCase(fundrequest.getFundMode()) %>" value="VOUCHER"><liferay-ui:message key="Voucher" /></aui:option>
						<aui:option selected="<%= \"CHECK\".equalsIgnoreCase(fundrequest.getFundMode()) %>" value="CHECK"><liferay-ui:message key="Check" /></aui:option>
					</aui:select>
					<aui:input maxlength="250" rows="6" label="Made Payable To:* " name="paymentMadeFor" type="textarea" required="true" value="${fundrequest.paymentMadeFor}"></aui:input>
					<aui:input maxlength="250" rows="6" label="Other Instructions: " name="otherInstructions" type="textarea" value="${fundrequest.otherInstructions}"></aui:input>
		        	
				</aui:column>    			
		        
		        <aui:column columnWidth="50" last="true" > 
		        	<aui:input  label="Date Required*: "  name="dateRequired" value="${fundrequest.dateRequired}"></aui:input>
					
					<aui:select   label="Fund Pickup: " name="fundMode" onchange="<%= renderResponse.getNamespace() + \"chooseSelectionStyle();\" %>">
					  	<aui:option selected="<%= \"PICKUP\".equalsIgnoreCase(fundrequest.getFundDeliveryType()) %>" value="PICKUP"><liferay-ui:message key="To be picked up" /></aui:option>
						<aui:option selected="<%= \"FUNDSREADY\".equalsIgnoreCase(fundrequest.getFundDeliveryType()) %>" value="FUNDSREADY"><liferay-ui:message key="Call when funds are ready" /></aui:option>
						<aui:option selected="<%= \"FURNITURE\".equalsIgnoreCase(fundrequest.getFundDeliveryType()) %>" value="FURNITURE"><liferay-ui:message key="(For furniture/appliances)" /></aui:option>
					</aui:select>
					<aui:input maxlength="250" rows="6" label="Furniture/Appliances Delivery to: " name="furnitureDeliveryAddress" type="textarea" value="${fundrequest.furnitureDeliveryAddress}"></aui:input>
		        	<aui:input label="Charge to budget Center: "  name="budgetCenter" value="${fundrequest.budgetCenter}"></aui:input>
					<aui:input label="Line Item: "  name="lineItem" value="${fundrequest.lineItem}"></aui:input>
					
				
				</aui:column>    			
	         </aui:fieldset>   			
		
		</liferay-ui:panel>
		
		
		<aui:button-row>
			<aui:button type="cancel" value="Decline" onClick="${declineRequestSupervisorURL}" />
			<aui:button type="submit" value="Approve"/>
		</aui:button-row>
		
		
	</aui:form>
</liferay-ui:panel-container>

