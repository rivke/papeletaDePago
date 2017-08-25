package payrollcasestudy.Services.AddTypesEmployeesToBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.boundaries.DatabaseConnection;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;

public class ServicesAddInBDHourlyEmployee extends TypeDatabaseServices{
    DatabaseConnection bd = new DatabaseConnection();
    
    public void addTypeEmployeeInBD(int employeeId, Employee employee){
    	try {
    		PreparedStatement insertTypeEmployee = bd.conectar().prepareStatement("INSERT INTO hourly_employees VALUES (?,?,?,?)");
    		HourlyPaymentClassification hourlyClassification =  (HourlyPaymentClassification) employee.getPaymentClassification();
    		addNameAddressInBD(employeeId, employee, insertTypeEmployee);
    		insertTypeEmployee.setDouble(4,hourlyClassification.getHourlyRate());
    		insertTypeEmployee.executeUpdate();
			}
    	catch (SQLException e){
    		e.printStackTrace();
			}
    }

	@Override
	public Employee getTypeEmployeeOfBD(int employeeId) {
		ResultSet rs=bd.connectionWithTableOfEmployees("SELECT * FROM hourly_employees WHERE employeeId='"+ employeeId + "';");
		Employee employee = null;
		try {
			while (rs.next()) {
				employee=new Employee(Integer.parseInt(rs.getString("employeeId")), rs.getString("name"), rs.getString("address"));
				PaymentClassification paymentClassification= new HourlyPaymentClassification(Integer.parseInt(rs.getString("tarifa_por_hora")));
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
		String query= "SELECT * FROM papeletadepago.hourly_employees";
		ArrayList<Employee> hourlyEmployees = new ArrayList<Employee>();try{
			ResultSet results= bd.connectionWithTableOfEmployees(query);
			while(results.next()){
				Employee employee=new Employee(Integer.parseInt(results.getString("employeeId")), results.getString("name"), results.getString("address"));
				hourlyEmployees.add(employee);
				}
			return hourlyEmployees;
			}
			catch(Exception e){
				System.err.println(e);
				return hourlyEmployees;	
			}
					
	}


}
