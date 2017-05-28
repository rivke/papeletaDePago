package payrollcasestudy.Services.BDServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;

public class ServicesAddInBDHourlyEmployee extends TypeDatabaseServices{
    BaseDeDatos bd = new BaseDeDatos();
    
    public void addTypeEmployeeInBD(int employeeId, Employee employee){
    	try {
    		PreparedStatement pstInsertarCuenta = bd.conectar().prepareStatement("INSERT INTO hourly_employees VALUES (?,?,?,?)");
    		HourlyPaymentClassification hourlyClassification =  (HourlyPaymentClassification) employee.getPaymentClassification();
    		addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
    		pstInsertarCuenta.setDouble(4,hourlyClassification.getHourlyRate());
    		pstInsertarCuenta.executeUpdate();
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
		ArrayList<Employee> listHourlyEmployee = new ArrayList<Employee>();try{
			ResultSet results= bd.connectionWithTableOfEmployees(query);
			while(results.next()){
				Employee employee=new Employee(Integer.parseInt(results.getString("employeeId")), results.getString("name"), results.getString("address"));
				listHourlyEmployee.add(employee);
				}
			return listHourlyEmployee;
			}
			catch(Exception e){
				System.err.println(e);
				return listHourlyEmployee;	
			}
					
	}


}
