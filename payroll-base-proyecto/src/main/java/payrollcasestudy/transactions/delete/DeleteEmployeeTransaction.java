package payrollcasestudy.transactions.delete;

import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.transactions.Transaction;

public class DeleteEmployeeTransaction implements Transaction{
    private int employeeId;

    public DeleteEmployeeTransaction(int employeeId) {
        this.employeeId = employeeId;
    }

    public void execute(Repositoory repository) {
        repository.deleteEmployee(employeeId);
    }
}
