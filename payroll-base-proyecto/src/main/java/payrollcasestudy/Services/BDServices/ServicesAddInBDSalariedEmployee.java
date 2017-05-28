package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;

public class ServicesAddInBDSalariedEmployee extends DatabaseTypeServices {
	  BaseDeDatos bd = new BaseDeDatos();

		 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
				PreparedStatement pstInsertarCuenta;
				try {
				    
					pstInsertarCuenta=bd.conectar().prepareStatement("INSERT INTO salaried_employee VALUES (?,?,?,?)");;
					SalariedClassification salariedClassification =  (SalariedClassification) employee.getPaymentClassification();
					addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
					pstInsertarCuenta.setDouble(4,salariedClassification.getSalary());
					pstInsertarCuenta.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

}
