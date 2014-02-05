<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>



<style type="text/css">
	.tab-content-container {
		margin: 0px;
		position: relative;
		background-color: #f4f4f4;
		border: 1px solid #1c5a83;
		clear: both;
		/*color: #1c5a83;*/
		font-family: Verdana, Arial, sans-serif;
		font-size: 0.85em;
		}
		.row {
			line-height: 3em;
		}
		.labelCol, .fieldCol {
			float: left;
			padding: 3px 5px;
			padding-left: 2%;
		}
		
		.required:after {
			content: " *";
			color: red;
		}
		
		label {
		cursor: default;
		}
		
		label.labelStyle{
			/* Style for all form labels used in the main content area, for non-required fields */
			font-size: 100%;
			font-weight: normal;
			color:  #336699;
			}
		
</style>

<portlet:resourceURL var="searchCaseBasedOnSacwisNumber" id="searchCaseBasedOnSacwisNumber" ></portlet:resourceURL>

<div class="bootstrap3" id="lccs-request-for-funds-body">
	<div id="server-form-validation-errors"><!-- server form validation errors div --></div>
	<div id="form-validation-errors"><ul><!-- errors --></ul></div>
	
	<div id="lccs-request-for-funds-main-content">
	
		<div class="tab-content-container form-group" id="lccs-request-for-funds-case-worker">
			<div class="row">
				 <div class="labelCol">
					<label class="labelStyle" for="caseWorkerName">Caseworker Requesting:</label>
				 </div>
				   <div class="fieldCol"><c:out value="${requestingCaseWorker}"></c:out> </div>
			<!-- </div>

			<div class="row"> -->
				 <div class="labelCol">
					<label class="labelStyle" for="caseWorkerPhone">Worker Phone:</label>
				 </div>
				   <div class="fieldCol"><c:out value="${workerPhoneNumber}"></c:out> </div>
<!-- 			</div>
			
			<div class="row"> -->
				 <div class="labelCol">
					<label class="labelStyle" for="requestDate">Date:</label> 
				 </div>
				   <div class="fieldCol"><c:out value="${requestedDate}"></c:out> </div>
			</div>
		</div>	<!-- End: lccs-request-for-funds-case-worker -->
		<br/> <!-- Seperator -->
		
		<div id="lccs-request-for-funds-form-content" class="tab-content-container">
			<portlet:actionURL var="searchSACWISURL" name="searchSACWIS"></portlet:actionURL>
			<form action="<%= searchSACWISURL %>" class="form-inline" role="form" id="requestFundsSearchForm" method="post">
		
				<div class="">
					<div class="row">
					 	<div class="labelCol"><label class="labelStyle" for="sacwisId">Sacwis ID</label> </div>
					 	 <div class="fieldCol">
					 	 	<input type="text" id="sacwisId" name="sacwisId" class="form-control" required data-msg-required="Sacwis ID is required."/>
					 	 </div>
					 	 <div class="fieldCol">
					 	 	<button type="submit" class="btn btn-primary" id="requestFundsSearchFormSubmit">Search</button>
					 	 </div>
					</div>
				</div>
			</form>
		</div><!-- End:lccs-request-for-funds-form-content -->
	</div><!--lccs-request-for-funds-main-content -->
	
</div> <!--End: lccs-request-for-funds-body  -->
<div id="lccs-request-for-funds-search-result-content">
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" data-backdrop="static" data-keyboard="true" tabindex="-1" role="modal"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<h1>Thank you for your application.</h1>
				<p>You will know shortly if you have been approved.</p>
				<div class="processing-img"><img src="<%=request.getContextPath()%>/images/processing_indicator.gif"/></div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
$(function() {
	requestFundsSearchObj.init('${searchCaseBasedOnSacwisNumber}');
});
</script>

