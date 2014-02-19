/**
 * 
 */
package oh.lccs.portal.requestfund.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author BCMSZV0
 *
 */
public class CaseParticipant {
	private BigDecimal id;
	
	private BigDecimal requestFundsId;
	
	private String personFullName;
	private BigDecimal sacwisId;
	private BigDecimal caseId;
	private Timestamp dob;
	private String type;
	private String requestingPersonCheckbox;
	
	private String custody;
	private BigDecimal custodyAgencyId;
	private BigDecimal placement;
	private Timestamp custodyDate;
	private String iveReimbursable;
	
	//Audit Info
	private BigDecimal createdBy;
	private Timestamp createdDate;
	private BigDecimal modifiedby;
	private Timestamp modifiedDate;
	
	public String getPersonFullName() {
		return personFullName;
	}
	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}
	public BigDecimal getSacwisId() {
		return sacwisId;
	}
	public void setSacwisId(BigDecimal sacwisId) {
		this.sacwisId = sacwisId;
	}
	public Timestamp getDob() {
		return dob;
	}
	public void setDob(Timestamp dob) {
		this.dob = dob;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestingPersonCheckbox() {
		return requestingPersonCheckbox;
	}
	public void setRequestingPersonCheckbox(String requestingPersonCheckbox) {
		this.requestingPersonCheckbox = requestingPersonCheckbox;
	}
	public BigDecimal getCaseId() {
		return caseId;
	}
	public void setCaseId(BigDecimal caseId) {
		this.caseId = caseId;
	}
	public String getCustody() {
		return custody;
	}
	public void setCustody(String custody) {
		this.custody = custody;
	}
	public BigDecimal getCustodyAgencyId() {
		return custodyAgencyId;
	}
	public void setCustodyAgencyId(BigDecimal custodyAgencyId) {
		this.custodyAgencyId = custodyAgencyId;
	}
	public BigDecimal getPlacement() {
		return placement;
	}
	public void setPlacement(BigDecimal placement) {
		this.placement = placement;
	}
	public Timestamp getCustodyDate() {
		return custodyDate;
	}
	public void setCustodyDate(Timestamp custodyDate) {
		this.custodyDate = custodyDate;
	}
	public String getIveReimbursable() {
		return iveReimbursable;
	}
	public void setIveReimbursable(String iveReimbursable) {
		this.iveReimbursable = iveReimbursable;
	}
	public BigDecimal getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public BigDecimal getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(BigDecimal modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getRequestFundsId() {
		return requestFundsId;
	}
	public void setRequestFundsId(BigDecimal requestFundsId) {
		this.requestFundsId = requestFundsId;
	}
	@Override
	public String toString() {
		return "CaseParticipant [id=" + id + ", requestFundsId="
				+ requestFundsId + ", personFullName=" + personFullName
				+ ", sacwisId=" + sacwisId + ", caseId=" + caseId + ", dob="
				+ dob + ", type=" + type + ", requestingPersonCheckbox="
				+ requestingPersonCheckbox + ", custody=" + custody
				+ ", custodyAgencyId=" + custodyAgencyId + ", placement="
				+ placement + ", custodyDate=" + custodyDate
				+ ", iveReimbursable=" + iveReimbursable + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedby="
				+ modifiedby + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
}
