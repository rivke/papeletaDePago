package payrollcasestudy.transactions.change;

import org.junit.Rule;
import org.junit.Test;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentschedule.MonthlyPaymentSchedule;
import payrollcasestudy.transactions.add.AddEmployeeTransaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;
import static payrollcasestudy.TestConstants.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ChangeCommissionedTransactionTest {
	private static final Repositoory Repository = new MemoryRepository();

 
    @Test
    public void changeCommissionedTransactionTest() throws Exception {
        int employeeId = 3;
        AddEmployeeTransaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, "Lance", "Home", 24.3);
        addEmployeeTransaction.execute(Repository);

        ChangeCommissionedTransaction changeCommissionedTransaction = new ChangeCommissionedTransaction(employeeId, 200, 20.0);
        changeCommissionedTransaction.execute(Repository);

        Employee employee = Repository.getEmployee(employeeId);
        assertThat(employee.getPaymentClassification(), is(instanceOf(CommissionedPaymentClassification.class)));
        CommissionedPaymentClassification paymentClassification =
                (CommissionedPaymentClassification) employee.getPaymentClassification();
        assertThat(paymentClassification.getMonthlySalary(), is(closeTo(200.0, FLOAT_ACCURACY)));
        assertThat(paymentClassification.getCommissionRate(), is(closeTo(20.0, FLOAT_ACCURACY)));

        assertThat(employee.getPaymentSchedule(), is(instanceOf(MonthlyPaymentSchedule.class)));
    }
}
