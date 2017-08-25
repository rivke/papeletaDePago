package payrollcasestudy.Services.AddTypesEmployeesToBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.boundaries.DatabaseConnection;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;

public class ServicesAddInBDCommissionedEmployee extends TypeDatabaseServices{
	DatabaseConnection bd = new DatabaseConnection();
	ArrayList<Employee> commissionedEmployees = new ArrayList<Employee>();
	
	public void addTypeEmployeeInBD(int employeeId, Employee employee) {
		try {
			PreparedStatement insertTypeEmployee= bd.conectar().prepareStatement("INSERT INTO comision VALUES (?,?,?,?,?)");
			CommissionedPaymentClassification commissionedClassification =  (CommissionedPaymentClassification) employee.getPaymentClassification();
			addNameAddressInBD(employeeId, employee, insertTypeEmployee);
			insertTypeEmployee.setDouble(4,commissionedClassification.getMonthlySalary());
			insertTypeEmployee.setDouble(5,commissionedClassification.getCommissionRate());
			insertTypeEmployee.executeUpdate();
			insertTypeEmployee.executeUpdate();
			}
		catch (SQLException e) {
			e.printStackTrace();
			}
			
		}

	@Override
	public Employee getTypeEmployeeOfBD(int employeeId) {
		ResultSet rs=bd.connectionWithTableOfEmployees("SELECT * FROM comision WHERE idemployees='"+ employeeId + "';");
		Employee employee = null;
		try {
			while (rs.next()) {
				employee=new Employee(Integer.parseInt(rs.getString("idemployees")), rs.getString("name"), rs.getString("address"));
				PaymentClassification paymentClassification= new CommissionedPaymentClassification(Integer.parseInt(rs.getString("monthlySalary")), Integer.parseInt(rs.getString("commissionRate")));
				employee.setPaymentClassification(paymentClassification);
			  }
			}
		catch (Exception e) {
			System.err.println(e);
		}
		return employee;
	}

	@Override
	public ArrayList<Employee> getAllEmploye() {
		String query= "SELECT * FROM papeletadepago.comision";
		
		try{
			ResultSet results= bd.connectionWithTableOfEmployees(query);
			while(results.next()){
				Employee employee=new Employee(Integer.parseInt(results.getString("idemployees")), results.getString("name"), results.getString("address"));
				commissionedEmployees.add(employee);
				}
			return commissionedEmployees;
			}
			catch(Exception e){
				System.err.println(e);
				return commissionedEmployees;	
			}
	}

}
