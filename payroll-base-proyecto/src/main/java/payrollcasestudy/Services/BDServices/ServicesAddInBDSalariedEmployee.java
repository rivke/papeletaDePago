package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;

public class ServicesAddInBDSalariedEmployee extends DatabaseTypeServices {
	  BaseDeDatos bd = new BaseDeDatos();

		 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
				;
				try {
				    
					PreparedStatement pstInsertarCuenta=bd.conectar().prepareStatement("INSERT INTO salaried_employee VALUES (?,?,?,?)");;
					SalariedClassification salariedClassification =  (SalariedClassification) employee.getPaymentClassification();
					addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
					pstInsertarCuenta.setDouble(4,salariedClassification.getSalary());
					pstInsertarCuenta.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		@Override
		public Employee getTypeEmployeeOfBD(int employeeId) {
			ResultSet rs=bd.connectionWithTableOfEmployees("SELECT * FROM salaried_employee WHERE idSalariedEmployee='"+ employeeId + "';");

			Employee employee = null;
			try {
				while (rs.next()) {
				 employee=new Employee(Integer.parseInt(rs.getString("idSalariedEmployee")), rs.getString("name"), rs.getString("address"));
				 PaymentClassification paymentClassification= new SalariedClassification(Integer.parseInt(rs.getString("salary")));
	      employee.setPaymentClassification(paymentClassification);
				  }
			} catch (Exception e) {
				
				System.err.println(e);
			} 
			return employee;
		}

}
