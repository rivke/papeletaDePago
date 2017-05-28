package Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;

public class ServicesAddInBDCommissionedEmployee extends DatabaseTypeServices{
	BaseDeDatos bd = new BaseDeDatos();

	 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
			PreparedStatement pstInsertarCuenta;
			try {
				pstInsertarCuenta= bd.conectar().prepareStatement("INSERT INTO comision VALUES (?,?,?,?,?)");
				CommissionedPaymentClassification commissionedClassification =  (CommissionedPaymentClassification) employee.getPaymentClassification();
				addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
				pstInsertarCuenta.setDouble(4,commissionedClassification.getMonthlySalary());
				pstInsertarCuenta.setDouble(5,commissionedClassification.getCommissionRate());
				pstInsertarCuenta.executeUpdate();
				pstInsertarCuenta.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
