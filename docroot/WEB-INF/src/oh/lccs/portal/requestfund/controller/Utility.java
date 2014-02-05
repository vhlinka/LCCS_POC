package oh.lccs.portal.requestfund.controller;

import java.math.BigDecimal;
import javax.portlet.ActionRequest;
import oh.lccs.portal.requestfund.domain.*;


public class Utility {

	/**
	 * Utility to pull the RequestFunds attributes from the http request
	 * 
	 * @param actionRequest
	 * @return
	 */
	public RequestFunds populateRequestFundsFromForm(ActionRequest actionRequest) {
		
		RequestFunds requestForm = new RequestFunds();
				
		requestForm.setBudgetCenter( actionRequest.getParameter("budgetCenter") );
		requestForm.setPersonRespForPurchase( new BigDecimal( actionRequest.getParameter("personRespForPurchase")));
		

		
		return requestForm;
	}
}
