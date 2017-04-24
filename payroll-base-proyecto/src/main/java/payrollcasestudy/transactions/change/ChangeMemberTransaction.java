package payrollcasestudy.transactions.change;
import payrollcasestudy.entities.Employee;

public class ChangeMemberTransaction  {

	private int memberId;
	private double weeklyUnionDues;
	
	public ChangeMemberTransaction(int employeeId, int memberId, double weeklyUnionDues) {
		// TODO Auto-generated constructor stub
		
		this.memberId=memberId;
		this.weeklyUnionDues=weeklyUnionDues;
	}
	

}
