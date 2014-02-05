/**
 * 
 */
package oh.lccs.portal.requestfund.domain;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author BCMSZV0
 *
 */
public class RequestFundsParticipant {
	
	private BigDecimal requestFundsId;
	
	private String personFullName;
	private BigDecimal personId;
	private Date dob;
	private String relationShipTypeCode;
	
	private String custody;
	private BigDecimal custodyAgencyId;
	private String placement;
	private Date custodyDate;
	private String iveReimbursable;
	
	//Audit Info
	private BigDecimal createdBy;
	private Date createdDate;
	private BigDecimal modifiedby;
	private Date modifiedDate;
	
	public BigDecimal getRequestFundsId() {
		return requestFundsId;
	}
	public void setRequestFundsId(BigDecimal requestFundsId) {
		this.requestFundsId = requestFundsId;
	}
	public String getPersonFullName() {
		return personFullName;
	}
	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}
	public BigDecimal getPersonId() {
		return personId;
	}
	public void setPersonId(BigDecimal personId) {
		this.personId = personId;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getRelationShipTypeCode() {
		return relationShipTypeCode;
	}
	public void setRelationShipTypeCode(String relationShipTypeCode) {
		this.relationShipTypeCode = relationShipTypeCode;
	}
//	public BigDecimal getCaseId() {
//		return caseId;
//	}
//	public void setCaseId(BigDecimal caseId) {
//		this.caseId = caseId;
//	}
	public String getCustody() {
		return custody;
	}
	public void setCustody(String custody) {
		this.custody = custody;
	}
	public String getPlacement() {
		return placement;
	}
	public void setPlacement(String placement) {
		this.placement = placement;
	}
	public Date getCustodyDate() {
		return custodyDate;
	}
	public void setCustodyDate(Date custodyDate) {
		this.custodyDate = custodyDate;
	}
	public String getIveReimbursable() {
		return iveReimbursable;
	}
	public void setIveReimbursable(String iveReimbursable) {
		this.iveReimbursable = iveReimbursable;
	}
	public BigDecimal getCustodyAgencyId() {
		return custodyAgencyId;
	}
	public void setCustodyAgencyId(BigDecimal custodyAgencyId) {
		this.custodyAgencyId = custodyAgencyId;
	}
	public BigDecimal getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public BigDecimal getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(BigDecimal modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	


}
