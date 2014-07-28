package oh.lccs.portal.requestfund.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletResponse;

import oh.lccs.portal.requestfund.common.LCCSException;
import oh.lccs.portal.requestfund.common.LccsConstants;
import oh.lccs.portal.requestfund.domain.CaseParticipant;
import oh.lccs.portal.requestfund.domain.RequestFunds;
import oh.lccs.portal.requestfund.service.RequestFundsService;
import oh.lccs.portal.requestfund.service.impl.RequestFundsServiceImpl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


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
    
    /**
     * 
     */
    public void previewPdf(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
    		RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
    		RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(requestForm.getId());
	    	
	    	HttpServletResponse response = PortalUtil.getHttpServletResponse(actionResponse);
	    	//response.setContentType("application/pdf");
	    	// step 1
	    	log.info("Before creating the document");
	        Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
	        // step 2
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
//            PdfWriter.getInstance(document, baos);
//	        PdfWriter.getInstance(document, new FileOutputStream("c:/junk/requestforFunds.pdf"));
//	        PdfWriter.getInstance(document, response.getOutputStream());
//	        PrintWriter writer = null;
	        
            // step 3
            document.addTitle("Request for Funds");
            
            //Create a table
            PdfPTable table=new PdfPTable(3);
            PdfPCell cell = new PdfPCell (new Paragraph ("REQUEST FOR FUNDS"));

		      cell.setColspan (3);
		      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
//		      cell.setPadding (10.0f);
		      table.addCell(cell);
		      cell = new PdfPCell(new Paragraph("Date 01/01/2014"));
		      cell.setColspan(2);
		      table.addCell(cell);
		      table.addCell("LCCS#4695\nRev:10/02");
		      cell = new PdfPCell(new Paragraph("ORIGINAL TO FISCAL, COPIES TO PLACEMENT AND CASE FILE"));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		      cell.setPadding(10.0f);
		      cell.setBackgroundColor(Color.LIGHT_GRAY);
		      table.addCell(cell);
		      log.info("Case Worker: " + fundRequestReview.getCaseWorker());
		      table.addCell("Caseworker:"+ fundRequestReview.getCaseWorker());
		      table.addCell("Worker Phone:"+ fundRequestReview.getWorkerPhoneNumber());
		      table.addCell("Date:"+ fundRequestReview.getRequestedDate());
		      table.addCell("Case Name:"+ fundRequestReview.getCaseName());
		      table.addCell("Case/Log Number:"+ fundRequestReview.getWorkerPhoneNumber());
		      table.addCell("Status:"+ fundRequestReview.getRequestedDate());
		      cell = new PdfPCell(new Paragraph("SACWIS ID: "+fundRequestReview.getCaseId()));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		      cell.setPadding(10.0f);
		      table.addCell(cell);
//		      document.add(Chunk.NEWLINE); 
//		      PdfFormField checkboxGroupField = PdfFormField.createCheckBox(pdfWriter);
//		      cell = new PdfPCell(table.getDefaultCell());
//		      cell.setCellEvent(new CellField(writer1, checkboxGroupField, true));
//		      cell = new PdfPCell(new Paragraph("checkbox3"));
//		      PdfPCellEvent cellEvent = new PdfPCellEvent();
//		      cell.setCellEvent(new PdfPCellEvent(pdfWriter, checkboxGroupField, true));
		      PdfPTable requestTypeTable=new PdfPTable(3);
		      cell = new PdfPCell(new Paragraph("List of People For Whom Request is Made"));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		      cell.setPadding(10.0f);
		      cell.setBackgroundColor(Color.LIGHT_GRAY);
		      requestTypeTable.addCell(cell);
		      cell = new PdfPCell(new Paragraph("Type of Request:"));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		      cell.setPadding(10.0f);
		      requestTypeTable.addCell(cell);
		      requestTypeTable.addCell("Donation");
		      requestTypeTable.addCell("Preplacment Prevention");
		      requestTypeTable.addCell("Aftercare Independence");
		      requestTypeTable.addCell("Kinship Care");
		      requestTypeTable.addCell("Operating");
		      requestTypeTable.addCell("Family Reunification");
		      requestTypeTable.addCell("TANF / Child Welfare");
		      requestTypeTable.addCell("Alternative Response");
		      requestTypeTable.addCell("");
//		      cell = new PdfPCell(new Paragraph("Requesting for:*"));
//		      cell.setColspan(3);
//		      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		      cell.setPadding(10.0f);
//		      table.addCell(cell);
		      
		      PdfPTable table1 = new PdfPTable(8);
		      if(fundRequestReview.getRequestingForPeople()!= null && fundRequestReview.getRequestingForPeople().size()> 0){
		    	  
		    	  cell = new PdfPCell(new Paragraph("Requesting for"));
			      cell.setColspan(8);
			      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			      cell.setPadding(10.0f);
			      table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Person's Full Name"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Client/Sacwis ID"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("DOB"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Type"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Custody With"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Child Placement"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Custody Date"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("IV-E Reimbursable"));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  for(CaseParticipant participant: fundRequestReview.getRequestingForPeople()){
		    		  table1.addCell(participant.getPersonFullName());
		    		  table1.addCell(" "+participant.getSacwisId());
		    		  table1.addCell(participant.getDob());
		    		  table1.addCell(participant.getType());
		    		  table1.addCell(participant.getCustody());
		    		  table1.addCell(participant.getServiceDesc());
		    		  table1.addCell(participant.getCustodyDate());
		    		  table1.addCell(participant.getIveReimbursable());
		    	  }
		    	  		    	  
		      }
		      
		   PdfPTable  infoTable = new  PdfPTable(2);
		   
		   cell = new PdfPCell(new Paragraph("INFORMATION FILLED IN BY CASE WORKER FOR APPROVAL"));
		   cell.setColspan(2);
		   cell.setBackgroundColor(Color.LIGHT_GRAY);
		   infoTable.addCell(cell);
		   
		   
		   cell = new PdfPCell(new Paragraph("Person(s) Responsible for Making purchase: "+fundRequestReview.getPersonRespForPurchase()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Purpose of Request: "+fundRequestReview.getRequestPurpose()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Other Community Resources Request: "+fundRequestReview.getOtherCommResContacted()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Total Amount Requested: "+fundRequestReview.getTotalAmtRequested()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Date Required: "+fundRequestReview.getDateRequired()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   infoTable.addCell("Fund Type: "+fundRequestReview.getFundMode());
		   infoTable.addCell("Fund Pickup: "+fundRequestReview.getFundDeliveryType());
		   infoTable.addCell("Made Payable To: "+fundRequestReview.getPaymentMadeFor());
		   infoTable.addCell("Furniture/Appliances Delivery to: "+fundRequestReview.getFurnitureDeliveryAddress());
		   
		   cell = new PdfPCell(new Paragraph("SS#/Tax Id of Payee: "+fundRequestReview.getSsnTaxId()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Other Instructions: "+fundRequestReview.getOtherInstructions()));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   infoTable.addCell("Charge to budget Center: "+fundRequestReview.getBudgetCenter());
		   infoTable.addCell("Line Item: "+fundRequestReview.getLineItem());
		      
	        document.open();
	        // step 4
	        
	        document.add(new Paragraph("Preview to PDF File"));
	        document.add(Chunk.NEWLINE); 
	        document.add(table);
	        
	        document.add(Chunk.NEWLINE); 
	        document.add(requestTypeTable);
	         
	        if(fundRequestReview.getRequestingForPeople()!= null && fundRequestReview.getRequestingForPeople().size()> 0){
	        	document.add(table1);
	        }
	        document.add(Chunk.NEWLINE);
	        document.add(infoTable);
	        document.newPage();
	        // step 5
	        
	        document.close();
	        
	        log.info("Before setting the header");
			response.setContentType("application/pdf");
//			response.addHeader("Content-Disposition", "attachment; filename=" + "requestForFunds.pdf");
			
			//response.setContentLength((int) pdfFile.length());

			//FileInputStream fileInputStream = new FileInputStream(pdfFile);
			
			response.setContentLength(baos.size());
			log.info("Before setting the output stream");
			response.flushBuffer();
			OutputStream responseOutputStream = response.getOutputStream();
			log.info("Before writing the output stream");
			baos.writeTo(responseOutputStream);
			log.info("After writing the output stream");
			responseOutputStream.flush();
			log.info("After flushing the output stream");
			responseOutputStream.close();
			log.info("After closing the output stream");
//			int bytes;
			
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
