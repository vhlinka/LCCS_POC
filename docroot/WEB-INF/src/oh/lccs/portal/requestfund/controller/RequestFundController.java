package oh.lccs.portal.requestfund.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;


import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;






import oh.lccs.portal.requestfund.common.LCCSException;
import oh.lccs.portal.requestfund.common.LccsConstants;
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

	static String SYSTEM_ERROR = "system-error";
	static String RECORD_NOT_FOUND_ERROR = "caseIdNotFound-error";
	
	private RequestFundsService requestFundsService;
	
	private static Log log = LogFactoryUtil.getLog(RequestFundController.class);
    
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
    public void showPendingRequestsSupervisor(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	List<RequestFunds> fundRequestSearchResult = getRequestFundsService().retrieveRequestFundsRequests(LccsConstants.REQUEST_SUBMITED);
	    	actionRequest.setAttribute("pendingFundRequest", fundRequestSearchResult);
	    	actionRequest.setAttribute("pendingFundRequestCount", fundRequestSearchResult.size());
	    	
	    	actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsSupervisor.jsp");
		}catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
		}
    }
    
    /**
     * 
     */
    public void showPendingRequestsManager(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	List<RequestFunds> fundRequestSearchResult = getRequestFundsService().retrieveRequestFundsRequests(LccsConstants.SUPERVISOR_APPROVAL);
	    	actionRequest.setAttribute("pendingFundRequest", fundRequestSearchResult);
	    	actionRequest.setAttribute("pendingFundRequestCount", fundRequestSearchResult.size());
	    	
	    	actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsManager.jsp");
		}catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
		}
    }
    
    /**
     * 
     */
    public void showPendingRequestsFinancial(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	List<RequestFunds> fundRequestSearchResult = getRequestFundsService().retrieveRequestFundsRequests(LccsConstants.MANAGER_APPROVAL);
	    	actionRequest.setAttribute("pendingFundRequest", fundRequestSearchResult);
	    	actionRequest.setAttribute("pendingFundRequestCount", fundRequestSearchResult.size());
	    	
	    	actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsFinancial.jsp");
		}catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
		}
    }
    
    public void searchSACWIS(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	
    		RequestFunds fundRequestInput = Utility.populateRequestFundsFromForm(actionRequest);
	    	
	    	RequestFunds fundRequestSearchResult = getRequestFundsService().searchForm(fundRequestInput);
	    	
	    	actionRequest.setAttribute("fundrequest", fundRequestSearchResult);
	    	
	    	actionResponse.setRenderParameter("jspPage", "/jsp/searchResult.jsp");
    	
    	}catch(LCCSException le){
    		log.error(le);
    		SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
    		actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
    	}catch(Exception e){
    		log.error(e);
    		SessionErrors.add(actionRequest, SYSTEM_ERROR);
    		actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
    	}
    }

    public void submitFundRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
	       
	    	if ( Validator.isNotNull(requestForm)) {
	    		String s = requestForm.getBudgetCenter();
	    		SessionMessages.add(actionRequest, s);
	    	}
	    	
	    	getRequestFundsService().saveData(requestForm);
	   	
	    	actionResponse.setRenderParameter("jspPage", "/jsp/requestConfirmation.jsp"); 
	    
    	}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/searchResult.jsp");
		}
    }
    
    public void reviewFundRequestSupervisor(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	String[] selectedFundRequests = ParamUtil.getParameterValues(actionRequest, "rowIds");
			if(selectedFundRequests != null && selectedFundRequests.length > 0){
				RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(new BigDecimal(selectedFundRequests[0]));
				System.out.println("fundRequestReview === " + fundRequestReview);
				actionRequest.setAttribute("fundrequest", fundRequestReview);
			}
			
	    	actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestSupervisor.jsp");
    	
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsSupervisor.jsp");
		}
    }

    public void reviewFundRequestManager(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	String[] selectedFundRequests = ParamUtil.getParameterValues(actionRequest, "rowIds");
			if(selectedFundRequests != null && selectedFundRequests.length > 0){
				RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(new BigDecimal(selectedFundRequests[0]));
				System.out.println("fundRequestReview === " + fundRequestReview);
				actionRequest.setAttribute("fundrequest", fundRequestReview);
			}
			
	    	actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestManager.jsp");
    	
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsManager.jsp");
		}
    }
    
    public void reviewFundRequestFinancial(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	String[] selectedFundRequests = ParamUtil.getParameterValues(actionRequest, "rowIds");
			if(selectedFundRequests != null && selectedFundRequests.length > 0){
				RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(new BigDecimal(selectedFundRequests[0]));
				System.out.println("fundRequestReview === " + fundRequestReview);
				actionRequest.setAttribute("fundrequest", fundRequestReview);
			}
			
	    	actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestFinancial.jsp");
    	
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/showPendingRequestsFinancial.jsp");
		}
    }

    public void approveFundRequestSupervisor(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
			log.debug("fundRequestApprove === " + requestForm);
	        
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.SUPERVISOR_APPROVAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
    	}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestSupervisor.jsp");
		}
    	
    }
    
    public void declineFundRequestSupervisor(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
	    	log.debug("fundRequestDecline === " + requestForm);
	        
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.SUPERVISOR_DENIAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestSupervisor.jsp");
		}
    }
    
    public void approveFundRequestManager(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
	    	log.debug("fundRequestApprove === " + requestForm);
	        
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.MANAGER_APPROVAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestManager.jsp");
		}
    }
    
    public void declineFundRequestManager(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
	    	log.debug("fundRequestDecline === " + requestForm);
	        
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.MANAGER_DENIAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestManager.jsp");
		}
    }
    
    
    private RequestFundsService getRequestFundsService(){
    	if(requestFundsService == null){
    		requestFundsService = new RequestFundsServiceImpl();
    	}
    	
    	return requestFundsService;
    }

}
