package payrollcasestudy.transactions.change;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;

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
	protected void registerAsAMember(Employee employee) {
		// TODO Auto-generated method stub
		PayrollDatabase.globalPayrollDatabase.addUnionMember(memberId, employee);
		
	}

	
	

}
