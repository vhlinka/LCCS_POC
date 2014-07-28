package oh.lccs.portal.requestfund.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import oh.lccs.portal.requestfund.common.LucasServiceConstants;

public class RequestFunds {
	
	private BigDecimal id;
	
	private String name;
	
	private BigDecimal caseId;
	
	private String requestedDate;
	private BigDecimal requestingCaseWorker;
	private String caseWorker;
	private String caseName;
	private String workerPhoneNumber;
	
	//Request Types
	private String donation;
	private String prePlacement;
	private String afterCareIndependence;
	private String kinshipCare;
	private String operating;
	private String familyReunification;
	private String alternativeResponse;
	
	//Check Person for which request is being made section
	private List<RequestType> requestTypes;
	private String[] requestType;
	private List<CaseParticipant> requestingForPeople;
	private int countOfParticipants;
	private String[] selectedCaseParticipants;
	
	//Information filed in by Caseworker for approval
	private String personRespForPurchase;
	private String requestPurpose;
	private String otherCommResContacted;
	private String totalAmtRequested;
	private String dateRequired;
	private String fundMode;
	private String fundDeliveryType;
	private String paymentMadeFor;
	private String otherInstructions;
	private String furnitureDeliveryAddress;
	private String budgetCenter;
	private String lineItem;
	private String ssnTaxId;
	
	//Variables for the workflow
	private BigDecimal statusCode;
	private String approverName;
	private BigDecimal approver;
	
	//Audit Info
	private BigDecimal createdBy;
	private Date createdDate;
	private BigDecimal modifiedby;
	private Date modifiedDate;
	

	
	// ================== Constants =====================
	public static final double MAX_REQUEST_AMOUNT = 5000.0;
	
	public RequestFunds(){
		//default values
		this.setDonation(LucasServiceConstants.CHECKBOX_OFF);
		this.setPrePlacement(LucasServiceConstants.CHECKBOX_OFF);
		this.setAfterCareIndependence(LucasServiceConstants.CHECKBOX_OFF);
		this.setKinshipCare(LucasServiceConstants.CHECKBOX_OFF);
		this.setOperating(LucasServiceConstants.CHECKBOX_OFF);
		this.setFamilyReunification(LucasServiceConstants.CHECKBOX_OFF);
		this.setAlternativeResponse(LucasServiceConstants.CHECKBOX_OFF);
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCaseId() {
		return caseId;
	}

	public void setCaseId(BigDecimal caseId) {
		this.caseId = caseId;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public BigDecimal getRequestingCaseWorker() {
		return requestingCaseWorker;
	}

	public void setRequestingCaseWorker(BigDecimal requestingCaseWorker) {
		this.requestingCaseWorker = requestingCaseWorker;
	}

	public String getCaseWorker() {
		return caseWorker;
	}

	public void setCaseWorker(String caseWorker) {
		this.caseWorker = caseWorker;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getWorkerPhoneNumber() {
		return workerPhoneNumber;
	}

	public void setWorkerPhoneNumber(String workerPhoneNumber) {
		this.workerPhoneNumber = workerPhoneNumber;
	}

	public String getDonation() {
		return donation;
	}

	public void setDonation(String donation) {
		this.donation = donation;
	}

	public String getPrePlacement() {
		return prePlacement;
	}

	public void setPrePlacement(String prePlacement) {
		this.prePlacement = prePlacement;
	}

	public String getAfterCareIndependence() {
		return afterCareIndependence;
	}

	public void setAfterCareIndependence(String afterCareIndependence) {
		this.afterCareIndependence = afterCareIndependence;
	}

	public String getKinshipCare() {
		return kinshipCare;
	}

	public void setKinshipCare(String kinshipCare) {
		this.kinshipCare = kinshipCare;
	}

	public String getOperating() {
		return operating;
	}

	public void setOperating(String operating) {
		this.operating = operating;
	}

	public String getFamilyReunification() {
		return familyReunification;
	}

	public void setFamilyReunification(String familyReunification) {
		this.familyReunification = familyReunification;
	}

	public String getAlternativeResponse() {
		return alternativeResponse;
	}

	public void setAlternativeResponse(String alternativeResponse) {
		this.alternativeResponse = alternativeResponse;
	}

	public String getPersonRespForPurchase() {
		return personRespForPurchase;
	}

	public void setPersonRespForPurchase(String personRespForPurchase) {
		this.personRespForPurchase = personRespForPurchase;
	}

	public String getRequestPurpose() {
		return requestPurpose;
	}

	public void setRequestPurpose(String requestPurpose) {
		this.requestPurpose = requestPurpose;
	}

	public String getOtherCommResContacted() {
		return otherCommResContacted;
	}

	public void setOtherCommResContacted(String otherCommResContacted) {
		this.otherCommResContacted = otherCommResContacted;
	}

	public String getTotalAmtRequested() {
		return totalAmtRequested;
	}

	public void setTotalAmtRequested(String totalAmtRequested) {
		this.totalAmtRequested = totalAmtRequested;
	}

	public String getDateRequired() {
		return dateRequired;
	}

	public void setDateRequired(String dateRequired) {
		this.dateRequired = dateRequired;
	}

	public String getFundMode() {
		return fundMode;
	}

	public void setFundMode(String fundMode) {
		this.fundMode = fundMode;
	}

	public String getFundDeliveryType() {
		return fundDeliveryType;
	}

	public void setFundDeliveryType(String fundDeliveryType) {
		this.fundDeliveryType = fundDeliveryType;
	}

	public String getPaymentMadeFor() {
		return paymentMadeFor;
	}

	public void setPaymentMadeFor(String paymentMadeFor) {
		this.paymentMadeFor = paymentMadeFor;
	}

	public String getOtherInstructions() {
		return otherInstructions;
	}

	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}

	public String getFurnitureDeliveryAddress() {
		return furnitureDeliveryAddress;
	}

	public void setFurnitureDeliveryAddress(String furnitureDeliveryAddress) {
		this.furnitureDeliveryAddress = furnitureDeliveryAddress;
	}

	public String getBudgetCenter() {
		return budgetCenter;
	}

	public void setBudgetCenter(String budgetCenter) {
		this.budgetCenter = budgetCenter;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(BigDecimal statusCode) {
		this.statusCode = statusCode;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public BigDecimal getApprover() {
		return approver;
	}

	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(BigDecimal modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<CaseParticipant> getRequestingForPeople() {
		return requestingForPeople;
	}
	public int getCountOfRequestingForPeople() {
		return requestingForPeople.size();
	}
	
	public void setRequestingForPeople(List<CaseParticipant> requestingPeople) {
		this.requestingForPeople = requestingPeople;
	}
	public String[] getSelectedCaseParticipants() {
		return selectedCaseParticipants;
	}

	public void setSelectedCaseParticipants(String[] selectedCaseParticipants) {
		this.selectedCaseParticipants = selectedCaseParticipants;
	}
	
	public String[] getRequestType() {
		return requestType;
	}
	public void setRequestType(String[] requestType) {
		this.requestType = requestType;
	}

	public List<RequestType> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(List<RequestType> requestTypes) {
		this.requestTypes = requestTypes;
	}


	public int getCountOfParticipants() {
		return countOfParticipants;
	}


	public void setCountOfParticipants(int countOfParticipants) {
		this.countOfParticipants = countOfParticipants;
	}


	public BigDecimal getId() {
		return id;
	}


	public void setId(BigDecimal id) {
		this.id = id;
	}


	public String getSsnTaxId() {
		return ssnTaxId;
	}


	public void setSsnTaxId(String ssnTaxId) {
		this.ssnTaxId = ssnTaxId;
	}


	@Override
	public String toString() {
		return "RequestFunds [id=" + id + ", name=" + name + ", caseId="
				+ caseId + ", requestedDate=" + requestedDate
				+ ", requestingCaseWorker=" + requestingCaseWorker
				+ ", caseWorker=" + caseWorker + ", caseName=" + caseName
				+ ", workerPhoneNumber=" + workerPhoneNumber + ", donation="
				+ donation + ", prePlacement=" + prePlacement
				+ ", afterCareIndependence=" + afterCareIndependence
				+ ", kinshipCare=" + kinshipCare + ", operating=" + operating
				+ ", familyReunification=" + familyReunification
				+ ", alternativeResponse=" + alternativeResponse
				+ ", requestTypes=" + requestTypes + ", requestType="
				+ Arrays.toString(requestType) + ", requestingForPeople="
				+ requestingForPeople + ", countOfParticipants="
				+ countOfParticipants + ", selectedCaseParticipants="
				+ Arrays.toString(selectedCaseParticipants)
				+ ", personRespForPurchase=" + personRespForPurchase
				+ ", requestPurpose=" + requestPurpose
				+ ", otherCommResContacted=" + otherCommResContacted
				+ ", totalAmtRequested=" + totalAmtRequested
				+ ", dateRequired=" + dateRequired + ", fundMode=" + fundMode
				+ ", fundDeliveryType=" + fundDeliveryType
				+ ", paymentMadeFor=" + paymentMadeFor + ", otherInstructions="
				+ otherInstructions + ", furnitureDeliveryAddress="
				+ furnitureDeliveryAddress + ", budgetCenter=" + budgetCenter
				+ ", lineItem=" + lineItem + ", statusCode=" + statusCode
				+ ", ssnTaxId=" + ssnTaxId
				+ ", approverName=" + approverName + ", approver=" + approver
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedby=" + modifiedby + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
	
}
