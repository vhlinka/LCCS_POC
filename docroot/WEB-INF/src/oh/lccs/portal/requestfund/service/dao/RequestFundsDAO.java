package oh.lccs.portal.requestfund.service.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import oh.lccs.portal.requestfund.domain.CaseParticipant;
import oh.lccs.portal.requestfund.domain.RequestFunds;

public interface RequestFundsDAO {

	List<Map<String, Object>> searchBasedOnSacwisId(BigDecimal sacwisId);
	
	List<Map<String, Object>> retrieveCaseDetails(BigDecimal sacwisId);
	
	boolean saveFundRequest(RequestFunds requestFunds);
	
	boolean saveFundRequestParticipant(CaseParticipant requestFundsParticipant);
	
	boolean updateFundRequest(RequestFunds requestFunds);
	
	List<Map<String, Object>> retrieveRequestFundsRequests(BigDecimal sacwisId);
	
	List<Map<String, Object>> retrieveFundRequest(BigDecimal fundRequestId);
	
	List<Map<String, Object>> retrieveFundRequestParticipants(BigDecimal fundRequestId);
	
	boolean updateFundRequestStatus(BigDecimal fundRequestId, BigDecimal statusCode);
}
