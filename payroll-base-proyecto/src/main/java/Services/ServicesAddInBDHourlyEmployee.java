package Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;

public class ServicesAddInBDHourlyEmployee extends DatabaseTypeServices{
    BaseDeDatos bd = new BaseDeDatos();

	 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
			PreparedStatement pstInsertarCuenta;
			try {
				pstInsertarCuenta = bd.conectar().prepareStatement("INSERT INTO hourly_employees VALUES (?,?,?,?)");
				HourlyPaymentClassification hourlyClassification =  (HourlyPaymentClassification) employee.getPaymentClassification();
				addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
				pstInsertarCuenta.setDouble(4,hourlyClassification.getHourlyRate());
				pstInsertarCuenta.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


}
