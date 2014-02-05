package oh.lccs.portal.requestfund.validators;

import java.awt.List;
import java.math.BigDecimal;

import com.liferay.portal.kernel.util.Validator;

import oh.lccs.portal.requestfund.domain.RequestFunds;

/**
 * 
 * @author lccs developer
 * 
 * Example of using LifeRay Validator - simple/brute force server-side validation approach
 *
 */
public class RequestFundsValidator {

	public static boolean validateRequestFundsObject(RequestFunds fundRequest, List errors) {
		boolean valid = true;
		
		// ensure total amount request is a valid number and lies in valid value range
		// NOTE - multiple nested if statements and BigDecimal/double mixed types
		//        Comparisons of BigDecimal values could have been performed using "compareTo"
		//		  but double were used as "simpler" example
		if (Validator.isNull(fundRequest.getTotalAmtRequested())) {
			errors.add("Total Amount Requested can not be empty");
			valid = false;
		} else {
			if( !Validator.isDigit(fundRequest.getTotalAmtRequested())) {
				errors.add("Total Amount Requested can not be empty");
				valid = false;				
			} else {
				BigDecimal requestedAmount = new BigDecimal(fundRequest.getTotalAmtRequested());
				double amountValue = requestedAmount.doubleValue();				
				if ( amountValue < 0 ) {
					errors.add("Total Amount Requested must be greater than 0");
					valid = false;									
				}
				if ( amountValue < 0 ) {
					errors.add("Total Amount Requested must be greater than 0");
					valid = false;									
				}
				if ( amountValue > RequestFunds.MAX_REQUEST_AMOUNT ) {
					errors.add("Total Amount Requested must be greater than 0");
					valid = false;									
				}				
			}
		}
		
		// --- now on to the next field ...
		
		
		return valid;
	}
}
