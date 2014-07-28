package oh.lccs.portal.requestfund.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import oh.lccs.portal.requestfund.common.DateUtils;
import oh.lccs.portal.requestfund.common.LCCSException;
import oh.lccs.portal.requestfund.common.LccsConstants;
import oh.lccs.portal.requestfund.common.LucasServiceConstants;
import oh.lccs.portal.requestfund.domain.CaseDetails;
import oh.lccs.portal.requestfund.domain.CaseParticipant;
import oh.lccs.portal.requestfund.domain.RequestFunds;
import oh.lccs.portal.requestfund.service.RequestFundsService;
import oh.lccs.portal.requestfund.service.dao.RequestFundsDAO;
import oh.lccs.portal.requestfund.service.dao.impl.RequestFundsDAOImpl;



public class RequestFundsServiceImpl implements RequestFundsService {
	
	

	private RequestFundsDAO requestFundsDAO;

	private RequestFundsDAO getRequestFundsDAO(){
		if(requestFundsDAO == null){
			requestFundsDAO = new RequestFundsDAOImpl();
		}
		
		return requestFundsDAO;
	}
	
	@Override
	public RequestFunds searchForm(RequestFunds dto) throws LCCSException{
		
		RequestFunds searchResult = new RequestFunds();
		List<Map<String, Object>> caseParticipantsColl = getRequestFundsDAO().searchBasedOnSacwisId(dto.getCaseId());
		
		List<Map<String, Object>> caseDetailsColl = getRequestFundsDAO().retrieveCaseDetails(dto.getCaseId());
		
		if(caseDetailsColl != null && caseDetailsColl.size()> 0 
				&& !caseDetailsColl.isEmpty() ){
			Iterator<Map<String, Object>> caseDetailsIterator = caseDetailsColl.iterator();
			Object caseObj = caseDetailsIterator.next();
			if(!(caseObj instanceof CaseDetails)){
				throw new LCCSException();
			}
		}
		
		CaseDetails caseDetail= (CaseDetails) caseDetailsColl.get(0);
		searchResult.setCaseName(caseDetail.getCaseName());
		searchResult.setCaseWorker(caseDetail.getCaseWorker());
		searchResult.setRequestingCaseWorker(new BigDecimal(caseDetail.getRequestingCaseWorker()));
		if(dto.getRequestedDate()!= null){
			searchResult.setRequestedDate(dto.getRequestedDate());
		}else{
			searchResult.setRequestedDate(caseDetail.getRequestedDate());
		}
		
		searchResult.setWorkerPhoneNumber(caseDetail.getWorkerPhoneNumber());
		if(dto.getWorkerPhoneNumber()!= null){
			searchResult.setWorkerPhoneNumber(dto.getWorkerPhoneNumber());
		}
		searchResult.setCaseId(new BigDecimal(1234));
		if(dto.getCaseId() != null){
			searchResult.setCaseId(dto.getCaseId());	
		}
		
		List<CaseParticipant> requestingPeople = new ArrayList<CaseParticipant>();
		
		if(caseParticipantsColl != null && caseParticipantsColl.size()> 0){
			Iterator<Map<String, Object>> itr = caseParticipantsColl.iterator();
			while(itr.hasNext()){
				CaseParticipant cp = (CaseParticipant) itr.next(); 
				cp.setRequestingPersonCheckbox(LucasServiceConstants.CHECKBOX_ON);
				requestingPeople.add(cp);
			}
			
		}
		searchResult.setRequestingForPeople(requestingPeople);
		searchResult.setCountOfParticipants(requestingPeople.size());
		
		return searchResult;
	}

	@Override
	public boolean saveData(RequestFunds dto) {
		// TODO Auto-generated method stub
		java.util.Date date= new java.util.Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		dto.setModifiedDate(date);
		dto.setCreatedDate(date);
		dto.setRequestedDate(df.format(date));
		dto.setDateRequired(date);
		
		System.out.println("requestFunds === " + dto.toString());
		
		boolean saveFlag = false;
		saveFlag = getRequestFundsDAO().saveFundRequest(dto);
		long selectedCaseParticipant = 0;
		CaseParticipant caseParticipant = null;
		System.out.println("fundRequestId === " + dto.getId());
		
		if(saveFlag){
			//get all participants from sacwis
			List<Map<String, Object>> caseParticipantsColl = getRequestFundsDAO().searchBasedOnSacwisId(dto.getCaseId());
			
			int size = dto.getSelectedCaseParticipants().length;
			for (int i = 0; i < size; i++) {
				selectedCaseParticipant = Long.parseLong(dto.getSelectedCaseParticipants()[i]);
				
				if(caseParticipantsColl != null && caseParticipantsColl.size()> 0){
					Iterator<Map<String, Object>> itr = caseParticipantsColl.iterator();
					while(itr.hasNext()){
						caseParticipant = (CaseParticipant) itr.next(); 
						if(caseParticipant.getSacwisId().longValue() == selectedCaseParticipant){
							caseParticipant.setRequestFundsId(dto.getId());
							
							if(caseParticipant.getCustody() != null){
								String cus = caseParticipant.getCustody().substring(0, 10);
								caseParticipant.setCustody(cus);
							}

							caseParticipant.setModifiedby(dto.getModifiedby());
							caseParticipant.setModifiedDate(new java.sql.Timestamp(date.getTime()));
							caseParticipant.setCreatedDate(new java.sql.Timestamp(date.getTime()));
							caseParticipant.setCreatedBy(dto.getCreatedBy());
							caseParticipant.setIveReimbursable(LccsConstants.EMPTY_STRING);
							saveFlag = requestFundsDAO.saveFundRequestParticipant(caseParticipant);
							
						}
					}
				}
			}
		}
		return saveFlag;

	}
	
	@Override
	public boolean updateData(RequestFunds dto) {
		boolean saveFlag = false;
		saveFlag = getRequestFundsDAO().updateFundRequest(dto);
		return saveFlag;
	}
	

	@Override
	public List<RequestFunds> retrieveRequestFundsRequests(BigDecimal statusCode) {
		
		List<Map<String, Object>> requestFundsColl = getRequestFundsDAO().retrieveRequestFundsRequests(statusCode);
		List<RequestFunds> requestFundsList = new ArrayList<RequestFunds>();

		if(requestFundsColl != null && requestFundsColl.size()> 0){
			Iterator<Map<String, Object>> itr = requestFundsColl.iterator();
			while(itr.hasNext()){
				RequestFunds fundRequests = (RequestFunds) itr.next(); 
				requestFundsList.add(fundRequests);
			}
		}
		
		return requestFundsList;
	}

	@Override
	public RequestFunds retrieveFundRequestForReview(BigDecimal fundRequestId){
		System.out.println("Entering retrieveFundRequestForReview === ");
		
		List<Map<String, Object>> requestFundsColl = getRequestFundsDAO().retrieveFundRequest(fundRequestId);
		RequestFunds requestFund = new RequestFunds();
		if(requestFundsColl != null && requestFundsColl.size()> 0){
			requestFund = (RequestFunds)  requestFundsColl.get(0);
		
		}
		System.out.println("Before calling retrieveFundRequestParticipants === ");
		List<Map<String, Object>> requestFundParticipantsList = getRequestFundsDAO().retrieveFundRequestParticipants(fundRequestId);
		System.out.println("After calling retrieveFundRequestParticipants === ");
		
		List<CaseParticipant> requestingPeople = new ArrayList<CaseParticipant>();
		
		if(requestFundParticipantsList != null && requestFundParticipantsList.size()> 0){
			Iterator<Map<String, Object>> itr = requestFundParticipantsList.iterator();
			while(itr.hasNext()){
				CaseParticipant cp = (CaseParticipant) itr.next(); 
				cp.setRequestingPersonCheckbox(LucasServiceConstants.CHECKBOX_ON);
				requestingPeople.add(cp);
				System.out.println("caseParticipant === "+cp.toString());
			}
			
		}
		System.out.println("requestingPeople === "+requestingPeople.size());
		requestFund.setRequestingForPeople(requestingPeople);
		requestFund.setCountOfParticipants(requestingPeople.size());
		return requestFund;
	}
	
	@Override
	public boolean updateFundRequestStatus(BigDecimal fundRequestId, BigDecimal statusCode) {
		boolean saveFlag = false;
		saveFlag = getRequestFundsDAO().updateFundRequestStatus(fundRequestId, statusCode);
		return saveFlag;
	}

}
