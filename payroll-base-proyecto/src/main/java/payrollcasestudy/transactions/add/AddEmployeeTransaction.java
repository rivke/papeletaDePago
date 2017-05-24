package payrollcasestudy.transactions.add;



import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.boundaries.BDrepository;
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

    public void execute(Repositoory repository)  {
        PaymentClassification paymentClassification = getPaymentClassification();
        PaymentSchedule paymentSchedule = getPaymentSchedule();
        PaymentMethod paymentMethod = new HoldMethod();
        Employee employee = new Employee(employeeId, employeeName, employeeAddress);
        employee.setPaymentClassification(paymentClassification);
        employee.setPaymentSchedule(paymentSchedule);
        employee.setPaymentMethod(paymentMethod);
        repository.addEmployee(employeeId, employee);
			
		
		
    }

    protected abstract PaymentSchedule getPaymentSchedule();

    protected abstract PaymentClassification getPaymentClassification();
}
