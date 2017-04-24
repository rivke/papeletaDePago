package payrollcasestudy.transactions.add;

import java.util.Calendar;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public class AddServiceChargeTransaction implements Transaction {

	
	public AddServiceChargeTransaction(int memberId, Calendar payDate, double serviceCharge) {
		// TODO Auto-generated constructor stub
		Employee member=PayrollDatabase.globalPayrollDatabase.getUnionMember(memberId);
		
		
		
		
	}

	
	public void execute() {
		// TODO Auto-generated method stub

	}

}
