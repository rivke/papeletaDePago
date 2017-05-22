package payrollcasestudy.transactions.change;

import org.junit.Rule;
import org.junit.Test;
import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ChangeNameTransactionTest {

	private static final Repositoory Repository = new MemoryRepository();
    
    @Test
    public void testChangeNameTransaction() throws Exception {
        int employeeId = 2;
        AddHourlyEmployeeTransaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, "Bill", "Home", 15.25);
        addEmployeeTransaction.execute(Repository);

        ChangeNameTransaction changeNameTransaction = new ChangeNameTransaction(employeeId, "Bob");
        changeNameTransaction.execute(Repository);

        Employee employee = Repository.getEmployee(employeeId);
      //  Employee employee = Repository.getEmployee(employeeId);

        assertThat(employee.getName(), is("Bob"));
    }
}
