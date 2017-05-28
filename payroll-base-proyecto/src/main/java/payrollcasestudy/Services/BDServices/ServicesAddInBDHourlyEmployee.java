package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;

public class ServicesAddInBDHourlyEmployee extends DatabaseTypeServices{
    BaseDeDatos bd = new BaseDeDatos();
    


	 public void addTypeEmployeeInBD(int employeeId, Employee employee) {
			try {
				PreparedStatement pstInsertarCuenta = bd.conectar().prepareStatement("INSERT INTO hourly_employees VALUES (?,?,?,?)");
				HourlyPaymentClassification hourlyClassification =  (HourlyPaymentClassification) employee.getPaymentClassification();
				addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
				pstInsertarCuenta.setDouble(4,hourlyClassification.getHourlyRate());
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
			 employee=new Employee(Integer.parseInt(rs.getString("employeeId")), rs.getString("name"), rs.getString("address"));
			 PaymentClassification paymentClassification= new HourlyPaymentClassification(Integer.parseInt(rs.getString("tarifa_por_hora")));
			employee.setPaymentClassification(paymentClassification);
			   }
		} catch (Exception e) {
			
			System.err.println(e);
		} 
		return employee;
	}


}
