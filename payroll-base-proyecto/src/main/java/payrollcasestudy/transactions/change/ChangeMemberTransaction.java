package payrollcasestudy.transactions.change;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public class ChangeMemberTransaction  extends changeAffiliationTransactionn {

	private int memberId;
	private double weeklyUnionDues;
	
	public ChangeMemberTransaction(int employeeId, int memberId, double weeklyUnionDues) {
		// TODO Auto-generated constructor stub
		super(employeeId);
		this.memberId=memberId;
		this.weeklyUnionDues=weeklyUnionDues;
		
	}

	@Override
	protected void registerAsAMember(Employee employee, Repositoory repository) {
		// TODO Auto-generated method stub
		repository.addUnionMember(memberId, employee);
		
	}

	@Override
	protected UnionAffiliation getUnionAffiliation() {
		return new UnionAffiliation(memberId, weeklyUnionDues);
		
	}

	
	

}
