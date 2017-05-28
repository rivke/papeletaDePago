package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import payrollcasestudy.entities.Employee;

public abstract class DatabaseTypeServices {
	  public static DatabaseTypeServices s;	

	  public static DatabaseTypeServices serviceHourly = new ServicesAddInBDHourlyEmployee();	
	  public static DatabaseTypeServices serviceSalaried = new ServicesAddInBDSalariedEmployee();	
      public static DatabaseTypeServices serviceCommissined = new ServicesAddInBDCommissionedEmployee();	


	 public abstract void addTypeEmployeeInBD(int employeeId, Employee employee);
	 public abstract Employee getTypeEmployeeOfBD(int employeeId);

	
	public static void addNameAddressInBD(int employeeId, Employee employee, PreparedStatement pstInsertarCuenta)
			throws SQLException {
		pstInsertarCuenta.setLong(1, employeeId);
		pstInsertarCuenta.setString(2, employee.getName());
		pstInsertarCuenta.setString(3, employee.getAddress());
	}
	


}
