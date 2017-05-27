package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {


    private int employeeId;

    public ChangeEmployeeTransaction(int employeeId) {
        this.employeeId = employeeId;
    }

    public void execute(Repositoory repository) {
        Employee employee = repository.getEmployee(employeeId);
        changeEmployee(employee, repository);
    }

    public abstract void changeEmployee(Employee employee, Repositoory repository);
}
