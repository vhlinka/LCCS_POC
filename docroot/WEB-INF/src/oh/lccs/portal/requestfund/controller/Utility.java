package oh.lccs.portal.requestfund.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;

import oh.lccs.portal.requestfund.common.DateUtils;
import oh.lccs.portal.requestfund.common.LccsConstants;
import oh.lccs.portal.requestfund.domain.RequestFunds;

import com.liferay.portal.kernel.util.ParamUtil;


public class Utility {

	/**
	 * Utility to pull the RequestFunds attributes from the http request
	 * 
	 * @param actionRequest
	 * @return
	 */
	public static RequestFunds populateRequestFundsFromForm(ActionRequest actionRequest) {
		
		RequestFunds requestFunds = new RequestFunds();
		
		if(actionRequest.getParameter("id") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("id").trim())){
			requestFunds.setId(new BigDecimal( actionRequest.getParameter("id")));
		}
		
		String[] selectedParticipants = ParamUtil.getParameterValues(actionRequest, "rowIds");
		if(selectedParticipants != null && selectedParticipants.length > 0){
			requestFunds.setSelectedCaseParticipants(selectedParticipants);
		}

		requestFunds.setBudgetCenter( actionRequest.getParameter("budgetCenter") );
		if(actionRequest.getParameter("personRespForPurchase") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("personRespForPurchase").trim())){
			requestFunds.setPersonRespForPurchase( actionRequest.getParameter("personRespForPurchase"));
		}
		
		if(actionRequest.getParameter("caseId") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("caseId").trim())){
			requestFunds.setCaseId(new BigDecimal( actionRequest.getParameter("caseId")));
		}
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if(actionRequest.getParameter("requestDate") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("requestDate").trim())){
			requestFunds.setRequestedDate(actionRequest.getParameter("requestDate"));
		}else{
			String currentDate = df.format(new Date());
			Date d = new Date();
			try {
			 d = df.parse(currentDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Date d : "+d);
			requestFunds.setRequestedDate(currentDate);
		}
		System.out.println("Requested Date: "+requestFunds.getRequestedDate());
		
		if(actionRequest.getParameter("requestingCaseWorker") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("requestingCaseWorker").trim())){
			requestFunds.setRequestingCaseWorker(new BigDecimal(actionRequest.getParameter("requestingCaseWorker")));
		}else{
			requestFunds.setRequestingCaseWorker(new BigDecimal(0));
		}
		
		if(actionRequest.getParameter("caseWorker") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("caseWorker").trim())){
			requestFunds.setCaseWorker(actionRequest.getParameter("caseWorker"));
		}else{
			requestFunds.setCaseWorker(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("caseName") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("caseName").trim())){
			requestFunds.setCaseName(actionRequest.getParameter("caseName"));
		}else{
			requestFunds.setCaseName(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("workerPhoneNumber") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("workerPhoneNumber").trim())){
			requestFunds.setWorkerPhoneNumber(actionRequest.getParameter("workerPhoneNumber"));
		}else{
			requestFunds.setWorkerPhoneNumber(LccsConstants.EMPTY_STRING);
		}
		
		//Request Types
		if(actionRequest.getParameter("donation") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("donation").trim())){
			requestFunds.setDonation(actionRequest.getParameter("donation"));
		}else{
			requestFunds.setDonation(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("prePlacement") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("prePlacement").trim())){
			requestFunds.setPrePlacement(actionRequest.getParameter("prePlacement"));
		}else{
			requestFunds.setPrePlacement(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("afterCareIndependence") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("afterCareIndependence").trim())){
			requestFunds.setAfterCareIndependence(actionRequest.getParameter("afterCareIndependence"));
		}else{
			requestFunds.setAfterCareIndependence(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("kinshipCare") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("kinshipCare").trim())){
			requestFunds.setKinshipCare(actionRequest.getParameter("kinshipCare"));
		}else{
			requestFunds.setKinshipCare(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("operating") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("operating").trim())){
			requestFunds.setOperating(actionRequest.getParameter("operating"));
		}else{
			requestFunds.setOperating(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("familyReunification") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("familyReunification").trim())){
			requestFunds.setFamilyReunification(actionRequest.getParameter("familyReunification"));
		}else{
			requestFunds.setFamilyReunification(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("alternativeResponse") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("alternativeResponse").trim())){
			requestFunds.setAlternativeResponse(actionRequest.getParameter("alternativeResponse"));
		}else{
			requestFunds.setAlternativeResponse(LccsConstants.EMPTY_STRING);
		}
		
		//Information filled in by Caseworker for approval
		
		if(actionRequest.getParameter("personRespForPurchase") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("personRespForPurchase").trim())){
			requestFunds.setPersonRespForPurchase(actionRequest.getParameter("personRespForPurchase"));
		}else{
			requestFunds.setPersonRespForPurchase(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("requestPurpose") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("requestPurpose").trim())){
			requestFunds.setRequestPurpose(actionRequest.getParameter("requestPurpose"));
		}else{
			requestFunds.setRequestPurpose(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("ssnTaxId") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("ssnTaxId").trim())){
			requestFunds.setSsnTaxId(actionRequest.getParameter("ssnTaxId"));
		}else{
			requestFunds.setSsnTaxId(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("otherCommResContacted") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("otherCommResContacted").trim())){
			requestFunds.setOtherCommResContacted(actionRequest.getParameter("otherCommResContacted"));
		}else{
			requestFunds.setOtherCommResContacted(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("totalAmtRequested") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("totalAmtRequested").trim())){
			requestFunds.setTotalAmtRequested(actionRequest.getParameter("totalAmtRequested"));
		}else{
			requestFunds.setTotalAmtRequested(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("dateRequired") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("dateRequired").trim())){
			requestFunds.setDateRequired(DateUtils.getMMDDYYYYStringAsDate(actionRequest.getParameter("dateRequired")));
		}else{
			requestFunds.setDateRequired(new Date());
		}
		
		if(actionRequest.getParameter("fundMode") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("fundMode").trim())){
			requestFunds.setFundMode(actionRequest.getParameter("fundMode"));
		}else{
			requestFunds.setFundMode(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("fundDeliveryType") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("fundDeliveryType").trim())){
			requestFunds.setFundDeliveryType(actionRequest.getParameter("fundDeliveryType"));
		}else{
			requestFunds.setFundDeliveryType(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("paymentMadeFor") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("paymentMadeFor").trim())){
			requestFunds.setPaymentMadeFor(actionRequest.getParameter("paymentMadeFor"));
		}else{
			requestFunds.setPaymentMadeFor(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("otherInstructions") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("otherInstructions").trim())){
			requestFunds.setOtherInstructions(actionRequest.getParameter("otherInstructions"));
		}else{
			requestFunds.setOtherInstructions(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("furnitureDeliveryAddress") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("furnitureDeliveryAddress").trim())){
			requestFunds.setFurnitureDeliveryAddress(actionRequest.getParameter("furnitureDeliveryAddress"));
		}else{
			requestFunds.setFurnitureDeliveryAddress(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("budgetCenter") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("budgetCenter").trim())){
			requestFunds.setBudgetCenter(actionRequest.getParameter("budgetCenter"));
		}else{
			requestFunds.setBudgetCenter(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("lineItem") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("lineItem").trim())){
			requestFunds.setLineItem(actionRequest.getParameter("lineItem"));
		}else{
			requestFunds.setLineItem(LccsConstants.EMPTY_STRING);
		}
		
		
		//Workflow Variables
		if(actionRequest.getParameter("statusCode") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("statusCode").trim())){
			requestFunds.setStatusCode(new BigDecimal(actionRequest.getParameter("statusCode")));
		}else{
			requestFunds.setStatusCode(new BigDecimal(0));
		}
		
		if(actionRequest.getParameter("approverName") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("approverName").trim())){
			requestFunds.setApproverName(actionRequest.getParameter("approverName"));
		}else{
			requestFunds.setApproverName(LccsConstants.EMPTY_STRING);
		}
		
		if(actionRequest.getParameter("approver") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("approver").trim())){
			requestFunds.setApprover(new BigDecimal(actionRequest.getParameter("approver")));
		}else{
			requestFunds.setApprover(new BigDecimal(0));
		}
		
		//Audit Info
		if(actionRequest.getParameter("createdBy") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("createdBy").trim())){
			requestFunds.setCreatedBy(new BigDecimal(actionRequest.getParameter("createdBy")));
		}else{
			requestFunds.setCreatedBy(requestFunds.getRequestingCaseWorker());
		}
		
		if(actionRequest.getParameter("createdDate") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("createdDate").trim())){
			requestFunds.setCreatedDate(DateUtils.getMMDDYYYYStringAsDate(actionRequest.getParameter("createdDate")));
		}else{
			requestFunds.setCreatedDate(new Date());
		}
		
		if(actionRequest.getParameter("modifiedby") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("modifiedby").trim())){
			requestFunds.setModifiedby(new BigDecimal(actionRequest.getParameter("modifiedby")));
		}else{
			requestFunds.setModifiedby(requestFunds.getRequestingCaseWorker());
		}
		
		if(actionRequest.getParameter("modifiedDate") != null && 
				!"".equalsIgnoreCase(actionRequest.getParameter("modifiedDate").trim())){
			requestFunds.setModifiedDate(DateUtils.getMMDDYYYYStringAsDate(actionRequest.getParameter("modifiedDate")));
		}else{
			requestFunds.setModifiedDate(new Date());
		}
		
		
		return requestFunds;
	}
}
