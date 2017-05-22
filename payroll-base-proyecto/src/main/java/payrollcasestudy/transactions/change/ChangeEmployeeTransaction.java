package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {

   PayrollDatabase database = PayrollDatabase.globalPayrollDatabase;
    //Repositoory database1 = Repositoory.repository2;

    private int employeeId;

    public ChangeEmployeeTransaction(int employeeId) {
        this.employeeId = employeeId;
    }

    public void execute(Repositoory repository) {
        Employee employee = repository.getEmployee(employeeId);
        changeEmployee(employee);
    }

    public abstract void changeEmployee(Employee employee);
}
