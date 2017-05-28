package payrollcasestudy.Services.AddTypesEmployeesToBD;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.entities.Employee;

public abstract class TypeDatabaseServices {
	public static TypeDatabaseServices serviceHourly = new ServicesAddInBDHourlyEmployee();	
	public static TypeDatabaseServices serviceSalaried = new ServicesAddInBDSalariedEmployee();	
	public static TypeDatabaseServices serviceCommissined = new ServicesAddInBDCommissionedEmployee();	
	public abstract void addTypeEmployeeInBD(int employeeId, Employee employee);
	public abstract Employee getTypeEmployeeOfBD(int employeeId);
	public abstract ArrayList<Employee> getAllEmploye();
	
	public static void addNameAddressInBD(int employeeId, Employee employee, PreparedStatement pstInsertarCuenta){
		try {
			pstInsertarCuenta.setLong(1, employeeId);
			pstInsertarCuenta.setString(2, employee.getName());
			pstInsertarCuenta.setString(3, employee.getAddress());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


}
