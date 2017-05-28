package payrollcasestudy.Services.AddTypesEmployeesToBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.boundaries.BaseDeDatos;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;

public class ServicesAddInBDSalariedEmployee extends TypeDatabaseServices {
	  BaseDeDatos bd = new BaseDeDatos();

	  public void addTypeEmployeeInBD(int employeeId, Employee employee) {
		  try {
			  PreparedStatement pstInsertarCuenta=bd.conectar().prepareStatement("INSERT INTO salaried_employee VALUES (?,?,?,?)");;
			  SalariedClassification salariedClassification =  (SalariedClassification) employee.getPaymentClassification();
			  addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
			  pstInsertarCuenta.setDouble(4,salariedClassification.getSalary());
			  pstInsertarCuenta.executeUpdate();
			  }
		  catch (SQLException e){
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
			}
		  catch (Exception e){
			  System.err.println(e);
			} 
			return employee;
	  }

	  @Override
	  public ArrayList<Employee> getAllEmploye() {
		  String query= "SELECT * FROM papeletadepago.salaried_employee";
		  ArrayList<Employee> listSalariedEmployee = new ArrayList<Employee>();
		  try{
			  ResultSet results= bd.connectionWithTableOfEmployees(query);
			  while(results.next()){
				  Employee employee=new Employee(Integer.parseInt(results.getString("idSalariedEmployee")), results.getString("name"), results.getString("address"));
				  listSalariedEmployee.add(employee);
				  }
			  return listSalariedEmployee;
			}
			catch(Exception e){
				System.err.println(e);
				return listSalariedEmployee;	
			}
		}

}
