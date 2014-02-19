package oh.lccs.portal.requestfund.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;


import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;


import oh.lccs.portal.requestfund.domain.*;
import oh.lccs.portal.requestfund.service.RequestFundsService;
import oh.lccs.portal.requestfund.service.impl.RequestFundsServiceImpl;

/**
 * 
 * @author lccs
 *
 *	The following always gets called
 */
public class RequestFundController extends MVCPortlet {
	
	private RequestFundsService requestFundsService;
    
    /**
     * the reset need explicit calls
     */
    public void setupSearch(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	
    	// set the next jsp in the response parameter
        actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp"); 
    }
    
    /**
     * 
     */
    public void showPendingRequests(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	RequestFunds dto = new RequestFunds();
    	dto.setCaseId(new BigDecimal(23233));
    	List<RequestFunds> fundRequestSearchResult = getRequestFundsService().retrieveRequestFundsRequests(dto);
    	actionRequest.setAttribute("pendingFundRequest", fundRequestSearchResult);
    	actionRequest.setAttribute("pendingFundRequestCount", fundRequestSearchResult.size());
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequests.jsp"); 
    }
    
    
    public void searchSACWIS(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	RequestFunds fundRequestInput = Utility.populateRequestFundsFromForm(actionRequest);
    	
    	RequestFunds fundRequestSearchResult = getRequestFundsService().searchForm(fundRequestInput);

    	actionRequest.setAttribute("fundrequest", fundRequestSearchResult);
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/searchResult.jsp");//requestForm.jsp"); 
    }

    public void submitFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErequestFormrrors.add(actionRequest, "email_error");    	
    	//PortletSession ps = actionRequest.getPortletSession();
        
    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
       
    	if ( Validator.isNotNull(requestForm)) {
    		String s = requestForm.getBudgetCenter();
    		SessionMessages.add(actionRequest, s);
    	}
    	
    	getRequestFundsService().saveData(requestForm);
   	
    	actionResponse.setRenderParameter("jspPage", "/jsp/requestConfirmation.jsp"); 
    }
    
    public void reviewFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErequestFormrrors.add(actionRequest, "email_error");    	
    	//PortletSession ps = actionRequest.getPortletSession();
        
    	String[] selectedFundRequests = ParamUtil.getParameterValues(actionRequest, "rowIds");
		if(selectedFundRequests != null && selectedFundRequests.length > 0){
			RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(new BigDecimal(selectedFundRequests[0]));
			System.out.println("fundRequestReview === " + fundRequestReview);
			actionRequest.setAttribute("fundrequest", fundRequestReview);
		}
		
    	actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequest.jsp"); 
    }

    public void approveFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErequestFormrrors.add(actionRequest, "email_error");    	
    	//PortletSession ps = actionRequest.getPortletSession();
    	
    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
		System.out.println("fundRequestApprove === " + requestForm);
        
		getRequestFundsService().updateFundRequestStatus(requestForm.getId(), new BigDecimal(1));
    	
		actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp"); 
    }
    
    public void declineFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErequestFormrrors.add(actionRequest, "email_error");    	
    	//PortletSession ps = actionRequest.getPortletSession();
        
    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
		System.out.println("fundRequestDecline === " + requestForm);
        
		getRequestFundsService().updateFundRequestStatus(requestForm.getId(), new BigDecimal(2));
    	
		actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp"); 
    }
    
    private RequestFundsService getRequestFundsService(){
    	if(requestFundsService == null){
    		requestFundsService = new RequestFundsServiceImpl();
    	}
    	
    	return requestFundsService;
    }

}