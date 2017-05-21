package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {

    PayrollDatabase database = PayrollDatabase.globalPayrollDatabase;
    private int employeeId;

    public ChangeEmployeeTransaction(int employeeId) {
        this.employeeId = employeeId;
    }

    public void execute(Repositoory repository) {
        Employee employee = database.getEmployee(employeeId);
        changeEmployee(employee);
    }

    public abstract void changeEmployee(Employee employee);
}
