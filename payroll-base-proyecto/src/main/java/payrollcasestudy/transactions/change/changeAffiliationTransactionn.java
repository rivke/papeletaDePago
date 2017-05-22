package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public abstract class changeAffiliationTransactionn extends ChangeEmployeeTransaction{

	public changeAffiliationTransactionn(int employeeId) {
		super(employeeId);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	 public void changeEmployee(Employee employee, Repositoory repository) {
		 registerAsAMember(employee, repository);
		 employee.setUnionAffiliation(getUnionAffiliation());  
	    }

	protected abstract void registerAsAMember(Employee employee, Repositoory repository);
	
	protected abstract UnionAffiliation getUnionAffiliation();		


}
