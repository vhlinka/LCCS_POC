package oh.lccs.portal.requestfund.service.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import oh.lccs.portal.requestfund.common.DateUtils;
import oh.lccs.portal.requestfund.domain.CaseDetails;
import oh.lccs.portal.requestfund.domain.CaseParticipant;
import oh.lccs.portal.requestfund.domain.RequestFunds;
import oh.lccs.portal.requestfund.service.dao.RequestFundsDAO;
import oh.lccs.portal.requestfund.service.db.RequestFundMybatisDML;



/**
 * Represents the DATA layer for the RequestFunds.
 * 
 * @author vinodh.srinivasan@compuware.com
 *
 */

public class RequestFundsDAOImpl implements RequestFundsDAO {

	private static final String FUNDS_REQUEST_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.searchBasedOnSacwisId";
	private static final String CASE_INFORMATION_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.retrieveCaseDetails";
	private static final String FUNDS_REQUEST_INSERT_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.insertFundRequest";
	private static final String FUNDS_REQUEST_PARTICIPANT_INSERT_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.insertFundRequestParticipant";
	private static final String FUNDS_REQUEST_UPDATE_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.updateFundRequest";
	private static final String RETRIEVE_FUNDS_REQUEST_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.retrieveRequestFundsRequests";
	private static final String RETRIEVE_FUND_REQUEST_BY_ID_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.retrieveFundsRequestById";
	private static final String RETRIEVE_FUND_REQUEST_PARTICIPANTS_BY_ID = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.retrieveFundsRequestParticipantsById";
	private static final String FUNDS_REQUEST_UPDATE_STATUS_MAPPER = "oh.lccs.portal.requestfund.service.db.mapper.RequestFundsMapper.updateFundRequestStatus";


	@Override
	public List<Map<String, Object>> searchBasedOnSacwisId(BigDecimal sacwisId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("caseId",String.valueOf(sacwisId));
		
		List<Map<String, Object>> userInfo = new RequestFundMybatisDML().performSawisSelect(FUNDS_REQUEST_MAPPER, parameters );
		System.out.println(userInfo);
		if(userInfo!= null && !userInfo.isEmpty()){
			CaseParticipant caseParticipant= (CaseParticipant) userInfo.get(0);
			if(caseParticipant!= null){
				return userInfo;
			}
		}
		
		return userInfo;
	}

	@Override
	public List<Map<String, Object>> retrieveCaseDetails(BigDecimal sacwisId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("caseId",String.valueOf(sacwisId));
		
		List<Map<String, Object>> userInfo = new RequestFundMybatisDML().performSawisSelect(CASE_INFORMATION_MAPPER, parameters );
		System.out.println(userInfo);
		if(userInfo!= null && !userInfo.isEmpty()){
			CaseDetails caseDetails= (CaseDetails) userInfo.get(0);
			if(caseDetails!= null){
				return userInfo;
			}
		}
		List<Map<String, Object>> caseDetailsColl = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> caseDetailsMap = new HashMap<String, Object>();
		caseDetailsColl.add(caseDetailsMap);
		
		return caseDetailsColl;
	}
	
	@Override
	public boolean saveFundRequest(RequestFunds requestFunds) {
		int rowsInserted = 0;
		rowsInserted = new RequestFundMybatisDML().performLCCSInsert(FUNDS_REQUEST_INSERT_MAPPER, requestFunds);

		if(rowsInserted > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean saveFundRequestParticipant(CaseParticipant requestFundsParticipant) {
		int rowsInserted = 0;
		rowsInserted = new RequestFundMybatisDML().performLCCSInsert(FUNDS_REQUEST_PARTICIPANT_INSERT_MAPPER, requestFundsParticipant);

		if(rowsInserted > 0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean updateFundRequest(RequestFunds requestFunds) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		rowsUpdated = new RequestFundMybatisDML().performLCCSUpdate(FUNDS_REQUEST_UPDATE_MAPPER, requestFunds);
		if(rowsUpdated > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> retrieveRequestFundsRequests(
			BigDecimal sacwisId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("caseId",sacwisId);
		
		List<Map<String, Object>> userInfo = new RequestFundMybatisDML().performLCCSSelect(RETRIEVE_FUNDS_REQUEST_MAPPER, parameters );
		System.out.println(userInfo);
		if(userInfo!= null && !userInfo.isEmpty()){
			RequestFunds requestFunds= (RequestFunds) userInfo.get(0);
			if(requestFunds!= null){
				return userInfo;
			}
		}
		
		return null;
	}

	@Override
	public List<Map<String, Object>> retrieveFundRequest(BigDecimal fundRequestId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id",String.valueOf(fundRequestId));
		
		List<Map<String, Object>> userInfo = new RequestFundMybatisDML().performLCCSSelect(RETRIEVE_FUND_REQUEST_BY_ID_MAPPER, parameters );
		System.out.println(userInfo);
		if(userInfo!= null && !userInfo.isEmpty()){
			RequestFunds requestFunds= (RequestFunds) userInfo.get(0);
			if(requestFunds!= null){
				return userInfo;
			}
		}
		
		return null;
	}

	@Override
	public List<Map<String, Object>> retrieveFundRequestParticipants(BigDecimal fundRequestId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestFundsId",String.valueOf(fundRequestId));
		
		List<Map<String, Object>> userInfo = new RequestFundMybatisDML().performLCCSSelect(RETRIEVE_FUND_REQUEST_PARTICIPANTS_BY_ID, parameters );
		System.out.println(userInfo);
		if(userInfo!= null && !userInfo.isEmpty()){
			CaseParticipant requestFunds= (CaseParticipant) userInfo.get(0);
			if(requestFunds!= null){
				return userInfo;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean updateFundRequestStatus(BigDecimal fundRequestId, BigDecimal statusCode) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id",fundRequestId);
		parameters.put("modifiedDate",new java.util.Date());
		parameters.put("statusCode", statusCode);
		
		int rowsUpdated = 0;
		rowsUpdated = new RequestFundMybatisDML().performLCCSUpdate(FUNDS_REQUEST_UPDATE_STATUS_MAPPER, parameters);
		if(rowsUpdated > 0){
			return true;
		}else{
			return false;
		}
	}
}
