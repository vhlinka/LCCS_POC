/**
 * 
 */
package oh.lccs.portal.requestfund.domain;

/**
 * @author Santosh Vellore
 *
 */
public class UserProfile {
	
	public boolean caseWorker;
	public boolean supervisor;
	public boolean manager;
	public boolean financeApprover;
	public boolean isCaseWorker() {
		return caseWorker;
	}
	public void setCaseWorker(boolean caseWorker) {
		this.caseWorker = caseWorker;
	}
	public boolean isSupervisor() {
		return supervisor;
	}
	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public boolean isFinanceApprover() {
		return financeApprover;
	}
	public void setFinanceApprover(boolean financeApprover) {
		this.financeApprover = financeApprover;
	}
	
	
	
}
