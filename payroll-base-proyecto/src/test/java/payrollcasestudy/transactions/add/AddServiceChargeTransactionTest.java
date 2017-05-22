package payrollcasestudy.transactions.add;

import org.junit.Rule;
import org.junit.Test;
import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.ServiceCharge;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.Transaction;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static payrollcasestudy.TestConstants.*;

public class AddServiceChargeTransactionTest {
	private static final Repositoory repository = new MemoryRepository();

   

    @Test
    public void testAddServiceCharge() throws Exception {
        int employeeId = 2;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, "Bill", "Home", 15.25);
        addEmployeeTransaction.execute(repository);

        Employee employee = repository.getEmployee(employeeId);
        assertThat(employee, is(notNullValue()));

        int memberId = 86; //Maxwell Smart
        UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,12.5);
        employee.setUnionAffiliation(unionAffiliation);
        repository.addUnionMember(memberId, employee);
        assertThat(repository.getUnionMember(memberId), is(notNullValue()));

        Calendar date = new GregorianCalendar(2001, 11, 01);
        AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date, 12.95);
        addServiceChargeTransaction.execute(repository);
        
        ServiceCharge serviceCharge = unionAffiliation.getServiceCharge(date);
        assertThat(serviceCharge, is(notNullValue()));
        assertThat(serviceCharge.getAmount(), is(closeTo(12.95, FLOAT_ACCURACY)));
    }
}
