package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction{
    private String newName;

    public ChangeNameTransaction(int employeeId, String newName) {
        super(employeeId);
        this.newName = newName;
    }

    @Override
    public void changeEmployee(Employee employee, Repositoory repository) {
        employee.setName(newName);
    }
}
