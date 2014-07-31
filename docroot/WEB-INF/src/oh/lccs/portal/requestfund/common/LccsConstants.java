/**
 * 
 */
package oh.lccs.portal.requestfund.common;

import java.math.BigDecimal;

/**
 * @author BCMSZV0
 *
 */
public class LccsConstants {
	
	public static final String CHECKBOX_ON = "on";
	public static final String CHECKBOX_OFF = "off";
	
	public static final String EMPTY_STRING = " ";
	
	public static final BigDecimal REQUEST_SUBMITED = new BigDecimal(0);
	public static final BigDecimal SUPERVISOR_APPROVAL = new BigDecimal(1);
	public static final BigDecimal SUPERVISOR_DENIAL = new BigDecimal(2);
	public static final BigDecimal MANAGER_APPROVAL = new BigDecimal(3);
	public static final BigDecimal MANAGER_DENIAL = new BigDecimal(4);
	public static final BigDecimal FINANCE_APPROVAL = new BigDecimal(5);
	public static final BigDecimal FINANCE_DENIAL = new BigDecimal(6);

}
