package payrollcasestudy.transactions.change;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public class ChangeNoMemberTransaction extends changeAffiliationTransactionn {

	private int memberId;
	public ChangeNoMemberTransaction(int employeeId) {
		super(employeeId);		
	}
	
	 	@Override
	    protected UnionAffiliation getUnionAffiliation() {
	        return UnionAffiliation.NO_AFFILIATION;
	    }

	   	@Override
		protected void registerAsAMember(Employee employee) {
			this.memberId = employee.getUnionAffiliation().getMemberId();
			database.deleteUnionMember(memberId);
		}  		   				   	
	   	
	
}
