package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;

public class ServicesAddInBDCommissionedEmployee extends DatabaseTypeServices{
	BaseDeDatos bd = new BaseDeDatos();

	 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
			
			try {
				PreparedStatement pstInsertarCuenta= bd.conectar().prepareStatement("INSERT INTO comision VALUES (?,?,?,?,?)");
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

	@Override
	public Employee getTypeEmployeeOfBD(ResultSet rs) {
		Employee employee = null;
		try {
			while (rs.next()) {
			 employee=new Employee(Integer.parseInt(rs.getString("idemployees")), rs.getString("name"), rs.getString("address"));
			 PaymentClassification paymentClassification= new CommissionedPaymentClassification(Integer.parseInt(rs.getString("monthlySalary")), Integer.parseInt(rs.getString("commissionRate")));
      employee.setPaymentClassification(paymentClassification);
			  }
		} catch (Exception e) {
			
			System.err.println(e);
		}
		return employee;
	}

}
