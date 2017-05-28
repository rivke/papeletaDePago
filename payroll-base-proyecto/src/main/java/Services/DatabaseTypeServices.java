package Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import payrollcasestudy.entities.Employee;
import updatable.Updatable;

public abstract class DatabaseTypeServices {
	
	 public abstract void addTypeEmployeeInBD(int employeeId, Employee employee);
	
	public static void addNameAddressInBD(int employeeId, Employee employee, PreparedStatement pstInsertarCuenta)
			throws SQLException {
		pstInsertarCuenta.setLong(1, employeeId);
		pstInsertarCuenta.setString(2, employee.getName());
		pstInsertarCuenta.setString(3, employee.getAddress());
	}

}
