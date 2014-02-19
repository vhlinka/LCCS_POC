package oh.lccs.portal.requestfund.service;

import java.math.BigDecimal;
import java.util.List;

import oh.lccs.portal.requestfund.domain.RequestFunds;



public interface RequestFundsService {

	RequestFunds searchForm(RequestFunds dto);
	
	boolean saveData(RequestFunds dto);
	
	boolean updateData(RequestFunds dto);
	
	List<RequestFunds> retrieveRequestFundsRequests(RequestFunds dto);
	
	RequestFunds retrieveFundRequestForReview(BigDecimal fundRequestId);
	
	boolean updateFundRequestStatus(BigDecimal fundRequestId, BigDecimal statusCode);

}
