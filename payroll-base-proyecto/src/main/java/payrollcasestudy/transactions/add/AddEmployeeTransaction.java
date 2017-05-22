package payrollcasestudy.transactions.add;

import java.sql.SQLException;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentmethods.HoldMethod;
import payrollcasestudy.entities.paymentmethods.PaymentMethod;
import payrollcasestudy.entities.paymentschedule.PaymentSchedule;
import payrollcasestudy.transactions.Transaction;

/**
 * Listing 19-5
 * Listing 19-6
 */
public abstract class AddEmployeeTransaction implements Transaction {
    private int employeeId;
    private String employeeName;
    private String employeeAddress;

    public AddEmployeeTransaction(int employeeId, String employeeName, String employeeAddress){
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
    }

    public void execute(Repositoory repository) {
        PaymentClassification paymentClassification = getPaymentClassification();
        PaymentSchedule paymentSchedule = getPaymentSchedule();
        PaymentMethod paymentMethod = new HoldMethod();
        Employee employee = new Employee(employeeId, employeeName, employeeAddress);
        
        
        employee.setPaymentClassification(paymentClassification);
        employee.setPaymentSchedule(paymentSchedule);
        employee.setPaymentMethod(paymentMethod);
        try {
			repository.addEmployee(employeeId, employee);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //PayrollDatabase.globalPayrollDatabase.addEmployee(employeeId, employee);
    }

    protected abstract PaymentSchedule getPaymentSchedule();

    protected abstract PaymentClassification getPaymentClassification();
}
