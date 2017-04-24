package payrollcasestudy.transactions.change;

import payrollcasestudy.entities.Employee;

public abstract class changeAffiliationTransactionn extends ChangeEmployeeTransaction{

	public changeAffiliationTransactionn(int employeeId) {
		super(employeeId);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	 public void changeEmployee(Employee employee) {
		 registerAsAMember(employee);
	        
	    }

	protected abstract void registerAsAMember(Employee employee);

}
