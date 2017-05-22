package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.Repositoory;
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
		protected void registerAsAMember(Employee employee, Repositoory repository) {
			this.memberId = employee.getUnionAffiliation().getMemberId();
			repository.deleteUnionMember(memberId);
		}  		   				   	
	   	
	
}
