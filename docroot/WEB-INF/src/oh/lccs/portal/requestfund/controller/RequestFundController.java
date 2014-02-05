package oh.lccs.portal.requestfund.controller;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;


import com.liferay.portal.kernel.util.Validator;

import oh.lccs.portal.requestfund.domain.*;

/**
 * 
 * @author lccs
 *
 *	The following always gets called
 */
public class RequestFundController extends MVCPortlet {
    
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
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/approveFunds.jsp"); 
    }
    
    
    public void searchSACWIS(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErrors.add(actionRequest, "email_error");    	
    	
    	RequestFunds fundRequest = new RequestFunds();
    	
  	
    	fundRequest.setBudgetCenter("BudgetCenter001");
    	actionRequest.setAttribute("fundrequest", fundRequest);
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/requestForm.jsp"); 
    }

    public void submitFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
//    	SessionErequestFormrrors.add(actionRequest, "email_error");    	
    	
    	Utility util = new Utility();
    	RequestFunds requestForm = util.populateRequestFundsFromForm(actionRequest);
    	
    	if ( Validator.isNotNull(requestForm)) {
    		String s = requestForm.getBudgetCenter();
    		SessionMessages.add(actionRequest, s);
    	}
    	
    	//String  = (RequestFunds) actionRequest.getAttribute("fundrequest");
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/requestConfirmation.jsp"); 
    }

    /**
     * 
     * @param actionRequest
     * @param actionResponse
     * @throws IOException
     * @throws PortletException
     * 
     * 
     */
    public void runSampleForm(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	
    	actionResponse.setRenderParameter("jspPage", "/jsp/sampleForm.jsp"); 
    }

}
