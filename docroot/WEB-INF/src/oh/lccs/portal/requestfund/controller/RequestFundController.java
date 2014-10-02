package oh.lccs.portal.requestfund.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletResponse;

import oh.lccs.portal.requestfund.common.LCCSException;
import oh.lccs.portal.requestfund.common.LccsConstants;
import oh.lccs.portal.requestfund.common.LiferayUtil;
import oh.lccs.portal.requestfund.common.PropertiesLoader;
import oh.lccs.portal.requestfund.domain.CaseParticipant;
import oh.lccs.portal.requestfund.domain.RequestFunds;
import oh.lccs.portal.requestfund.domain.UserProfile;
import oh.lccs.portal.requestfund.email.EmailSender;
import oh.lccs.portal.requestfund.service.RequestFundsService;
import oh.lccs.portal.requestfund.service.impl.RequestFundsServiceImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
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
	static String PARTICIPANTS_NOT_SELECTED_ERROR = "participantsNotSelected-error";
	static String RECORD_NOT_SELECTED_ERROR = "recordsNotSelected-error";
				
	private RequestFundsService requestFundsService;
	
	private static Log log = LogFactoryUtil.getLog(RequestFundController.class);
    
	/**
	 * @throws SystemException 
	 * @throws PortalException 
     */
    public void validateUser(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException, SystemException, PortalException
    {
//    	User user = (User) actionRequest.getAttribute(WebKeys.USER);
    	User user =PortalUtil.getUser(actionRequest);
    	LiferayUtil liferayUtil = LiferayUtil.instance();
    	UserProfile userProfile = new UserProfile();
    	userProfile = liferayUtil.convertUserGroups(user);
    	actionRequest.setAttribute("userProfile", userProfile);
    	// set the next jsp in the response parameter
        actionResponse.setRenderParameter("jspPage", "/jsp/view.jsp"); 
    }
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
    		long recordId = ParamUtil.getLong(actionRequest, "recordId");
    		log.info("Record id is:" + recordId);
    		requestForm.setId(new BigDecimal(recordId));
    		log.info("Request Id: "+requestForm.getId());
    		RequestFunds fundRequestReview = getRequestFundsService().retrieveFundRequestForReview(requestForm.getId());
	    	
	    	HttpServletResponse response = PortalUtil.getHttpServletResponse(actionResponse);
	    	//response.setContentType("application/pdf");
	    	// step 1
	    	log.info("Before creating the document");
	        Document document = new Document();
	        Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		    String s = "";
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
	        // step 2
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            Font headingFont = FontFactory.getFont("Times-Roman", 12);
            Font contentFont = FontFactory.getFont("Calibri", 10);
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
		      cell = new PdfPCell(new Paragraph("Date 01/01/2014", contentFont));
		      cell.setColspan(2);
		      table.addCell(cell);
		      table.addCell(new Paragraph("LCCS#4695\nRev:10/02", contentFont));
		      cell = new PdfPCell(new Paragraph("ORIGINAL TO FISCAL, COPIES TO PLACEMENT AND CASE FILE"));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		      cell.setPadding(10.0f);
		      cell.setBackgroundColor(Color.LIGHT_GRAY);
		      table.addCell(cell);
		      log.info("Case Worker: " + fundRequestReview.getCaseWorker());
		      table.addCell(new Paragraph("Caseworker:"+ fundRequestReview.getCaseWorker(), contentFont));
		      table.addCell(new Paragraph("Worker Phone:"+ fundRequestReview.getWorkerPhoneNumber(), contentFont));
		      
		      
//		      if(fundRequestReview.getRequestedDate()!= null){
//		    	  s = formatter.format(fundRequestReview.getRequestedDate());
//		      }
		      table.addCell(new Paragraph("Date: "+ fundRequestReview.getRequestedDate(), contentFont));
		      
		      table.addCell(new Paragraph("Case Name:"+ fundRequestReview.getCaseName(), contentFont));
		      table.addCell(new Paragraph("Case/Log Number:"+ fundRequestReview.getCaseId(), contentFont));
		      table.addCell(new Paragraph("Status:", contentFont));
		      cell = new PdfPCell(new Paragraph("SACWIS ID: "+fundRequestReview.getCaseId(), contentFont));
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
		     
		      boolean foundRequestType = false;
		      String requestType = "";
		      if(fundRequestReview.getDonation() != null && "true".equals(fundRequestReview.getDonation())){
		    	  requestType = "Donation";
		    	  foundRequestType = true;
		      }
		      if(fundRequestReview.getPrePlacement() != null && "true".equals(fundRequestReview.getPrePlacement())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Preplacement Prevention";
		    	  }else{
		    		  requestType = "Preplacement Prevention";
		    		  foundRequestType = true;
		    	  }
		      }
		      if(fundRequestReview.getAfterCareIndependence() != null && "true".equals(fundRequestReview.getAfterCareIndependence())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Aftercare Independence";
		    	  }else{
		    		  requestType = "Aftercare Independence";
		    		  foundRequestType = true;
		    	  }
		      }
		      if(fundRequestReview.getKinshipCare() != null && "true".equals(fundRequestReview.getKinshipCare())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Kinship Care";
		    	  }else{
		    		  requestType = "Kinship Care";
		    		  foundRequestType = true;
		    	  }
		      }
		      if(fundRequestReview.getOperating() != null && "true".equals(fundRequestReview.getOperating())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Operating";
		    	  }else{
		    		  requestType = "Operating";
		    		  foundRequestType = true;
		    	  }
		      }
		      if(fundRequestReview.getFamilyReunification() != null && "true".equals(fundRequestReview.getFamilyReunification())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Family Reunification";
		    	  }else{
		    		  requestType = "Family Reunification";
		    		  foundRequestType = true;
		    	  }
		      }
		      if(fundRequestReview.getAlternativeResponse() != null && "true".equals(fundRequestReview.getAlternativeResponse())){
		    	  if(foundRequestType){
		    		  requestType = requestType +" , " + "Alternative Response";
		    	  }else{
		    		  requestType = "Alternative Response";
		    		  foundRequestType = true;
		    	  }
		      }
		      cell = new PdfPCell(new Paragraph("Type of Request: "+requestType, contentFont));
		      cell.setColspan(3);
		      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		      cell.setPadding(10.0f);
		      requestTypeTable.addCell(cell);
		      
		      PdfPTable table1 = new PdfPTable(8);
		      if(fundRequestReview.getRequestingForPeople()!= null && fundRequestReview.getRequestingForPeople().size()> 0){
		    	  
		    	  cell = new PdfPCell(new Paragraph("Requesting for"));
			      cell.setColspan(8);
			      table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Person's Full Name", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Client/Sacwis ID", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("DOB", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Type", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Custody With", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Child Placement", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("Custody Date", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  cell = new PdfPCell(new Paragraph("IV-E Reimbursable", contentFont));
		    	  cell.setBackgroundColor(Color.LIGHT_GRAY);
		    	  table1.addCell(cell);
		    	  
		    	  for(CaseParticipant participant: fundRequestReview.getRequestingForPeople()){
		    		  table1.addCell(new Paragraph(participant.getPersonFullName(), contentFont));
		    		  table1.addCell(new Paragraph(" "+participant.getSacwisId(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getDob(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getType(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getCustody(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getServiceDesc(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getCustodyDate(), contentFont));
		    		  table1.addCell(new Paragraph(participant.getIveReimbursable(), contentFont));
		    	  }
		    	  		    	  
		      }
		      
		   PdfPTable  infoTable = new  PdfPTable(2);
		   
		   cell = new PdfPCell(new Paragraph("INFORMATION FILLED IN BY CASE WORKER FOR APPROVAL"));
		   cell.setColspan(2);
		   cell.setBackgroundColor(Color.LIGHT_GRAY);
		   infoTable.addCell(cell);
		   
		   
		   cell = new PdfPCell(new Paragraph("Person(s) Responsible for Making purchase: "+fundRequestReview.getPersonRespForPurchase(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Purpose of Request: "+fundRequestReview.getRequestPurpose(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Other Community Resources Request: "+fundRequestReview.getOtherCommResContacted(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Total Amount Requested: "+fundRequestReview.getTotalAmtRequested(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
//		   s = "";
//	      if(fundRequestReview.getDateRequired()!= null){
//	    	  s = formatter.format(fundRequestReview.getDateRequired());
//	      }
		   cell = new PdfPCell(new Paragraph("Date Required: "+fundRequestReview.getDateRequired(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   infoTable.addCell(new Paragraph("Fund Type: "+fundRequestReview.getFundMode(), contentFont));
		   infoTable.addCell(new Paragraph("Fund Pickup: "+fundRequestReview.getFundDeliveryType(), contentFont));
		   infoTable.addCell(new Paragraph("Made Payable To: "+fundRequestReview.getPaymentMadeFor(), contentFont));
		   infoTable.addCell(new Paragraph("Furniture/Appliances Delivery to: "+fundRequestReview.getFurnitureDeliveryAddress(), contentFont));
		   
		   cell = new PdfPCell(new Paragraph("SS#/Tax Id of Payee: "+fundRequestReview.getSsnTaxId(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   cell = new PdfPCell(new Paragraph("Other Instructions: "+fundRequestReview.getOtherInstructions(), contentFont));
		   cell.setColspan(2);
		   infoTable.addCell(cell);
		   
		   infoTable.addCell(new Paragraph("Charge to budget Center: "+fundRequestReview.getBudgetCenter(), contentFont));
		   infoTable.addCell(new Paragraph("Line Item: "+fundRequestReview.getLineItem(), contentFont));
		      
	        document.open();
	        // step 4
	        
	        document.add(new Paragraph("Preview to PDF File", headingFont));
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
	    	
	    	actionRequest.setAttribute("casePartList", fundRequestSearchResult.getRequestingForPeople());
	    	
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
	    	if(requestForm.getSelectedCaseParticipants() == null){
	    		SessionErrors.add(actionRequest, PARTICIPANTS_NOT_SELECTED_ERROR);
	    		RequestFunds fundRequestInput = Utility.populateRequestFundsFromForm(actionRequest);
		    	RequestFunds fundRequestSearchResult = getRequestFundsService().searchForm(fundRequestInput);
		    	actionRequest.setAttribute("fundrequest", fundRequestSearchResult);
		    	actionResponse.setRenderParameter("jspPage", "/jsp/searchResult.jsp");
	    		return;
	    	}
	       
	    	if ( Validator.isNotNull(requestForm)) {
	    		String s = requestForm.getBudgetCenter();
	    		SessionMessages.add(actionRequest, s);
	    	}
	    	
	    	getRequestFundsService().saveData(requestForm);
	    	
	    	//Send email notification
	    	//TODO: The application is currently retrieving hard code email ids from lccs-portal.proprties file. It has to be 
	    	//changed as per the requirement.
	    	Properties prop = PropertiesLoader.getPropertiesInstance();
	    	String emailId = prop.getProperty("supervisor.email");
            EmailSender mailSender = new EmailSender();
            mailSender.sendEmail(emailId, "Request Funds - CaseWorker", "A case worker has submitted a request for funds.");
	   	
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
			}else{
				SessionErrors.add(actionRequest, RECORD_NOT_SELECTED_ERROR);
				showPendingRequestsSupervisor(actionRequest, actionResponse);
				return;
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
			}else{
				SessionErrors.add(actionRequest, RECORD_NOT_SELECTED_ERROR);
				showPendingRequestsManager(actionRequest, actionResponse);
				return;
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
			}else{
				SessionErrors.add(actionRequest, RECORD_NOT_SELECTED_ERROR);
				showPendingRequestsFinancial(actionRequest, actionResponse);
				return;
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
			
			//Send email notification
			Properties prop = PropertiesLoader.getPropertiesInstance();
	    	String emailId = prop.getProperty("manager.email");
            EmailSender mailSender = new EmailSender();
            mailSender.sendEmail(emailId, "Request Funds - Supervisor", "Please review the fund request and approve it");
	    	
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
			
			//Send email notification
			Properties prop = PropertiesLoader.getPropertiesInstance();
	    	String emailId = prop.getProperty("finance.email");
            EmailSender mailSender = new EmailSender();
            mailSender.sendEmail(emailId, "Request Funds - Manager", "Please review the fund request and approve it.");
	    	
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
    
    public void approveFundRequestFinance(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
			log.debug("fundRequestApprove === " + requestForm);
	        
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.FINANCE_APPROVAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
    	}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestFinancial.jsp");
		}
    	
    }
    
    public void declineFundRequestFinance(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException
    {
    	try{
	    	RequestFunds requestForm = Utility.populateRequestFundsFromForm(actionRequest);
	    	log.debug("fundRequestDecline === " + requestForm);
	    	long recordId = ParamUtil.getLong(actionRequest, "recordId");
    		log.info("Record id is:" + recordId);
    		requestForm.setId(new BigDecimal(recordId));
			getRequestFundsService().updateFundRequestStatus(requestForm.getId(), LccsConstants.FINANCE_DENIAL);
	    	
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestConfirmation.jsp");
		}/*catch(LCCSException le){
			log.error(le);
			SessionErrors.add(actionRequest, RECORD_NOT_FOUND_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/requestFunds.jsp");
		}*/catch(Exception e){
			log.error(e);
			SessionErrors.add(actionRequest, SYSTEM_ERROR);
			actionResponse.setRenderParameter("jspPage", "/jsp/reviewFundRequestFinancial.jsp");
		}
    }
    
    
    private RequestFundsService getRequestFundsService(){
    	if(requestFundsService == null){
    		requestFundsService = new RequestFundsServiceImpl();
    	}
    	
    	return requestFundsService;
    }

}
