package payrollcasestudy.transactions.add;

import java.util.Calendar;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.Transaction;

public class AddServiceChargeTransaction implements Transaction {

	
	public AddServiceChargeTransaction(int memberId, Calendar payDate, double serviceCharge) {
		// TODO Auto-generated constructor stub
		Employee member=PayrollDatabase.globalPayrollDatabase.getUnionMember(memberId);
		UnionAffiliation unionAffiliation= member.getUnionAffiliation();
		unionAffiliation.addServiceChargeTransaction(payDate,serviceCharge);
		
		
		
	}

	
	public void execute(Repositoory repository) {
		// TODO Auto-generated method stub

	}

}
