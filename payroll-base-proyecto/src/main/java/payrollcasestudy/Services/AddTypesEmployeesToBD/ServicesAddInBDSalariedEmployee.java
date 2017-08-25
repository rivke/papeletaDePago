package payrollcasestudy.Services.AddTypesEmployeesToBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import payrollcasestudy.boundaries.DatabaseConnection;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;

public class ServicesAddInBDSalariedEmployee extends TypeDatabaseServices {
	  DatabaseConnection bd = new DatabaseConnection();

	  public void addTypeEmployeeInBD(int employeeId, Employee employee) {
		  try {
			  PreparedStatement insertTypeEmployee=bd.conectar().prepareStatement("INSERT INTO salaried_employee VALUES (?,?,?,?)");;
			  SalariedClassification salariedClassification =  (SalariedClassification) employee.getPaymentClassification();
			  addNameAddressInBD(employeeId, employee, insertTypeEmployee);
			  insertTypeEmployee.setDouble(4,salariedClassification.getSalary());
			  insertTypeEmployee.executeUpdate();
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
		  ArrayList<Employee> salariedEmployees = new ArrayList<Employee>();
		  try{
			  ResultSet results= bd.connectionWithTableOfEmployees(query);
			  while(results.next()){
				  Employee employee=new Employee(Integer.parseInt(results.getString("idSalariedEmployee")), results.getString("name"), results.getString("address"));
				  salariedEmployees.add(employee);
				  }
			  return salariedEmployees;
			}
			catch(Exception e){
				System.err.println(e);
				return salariedEmployees;	
			}
		}
	  public Employee convertToObject(ResultSet results, Employee employeeBd)
	  {
		  Employee employee=new Employee(Integer.parseInt(results.getString("idSalariedEmployee")), results.getString("name"), results.getString("address"));  
		  
	  }

	  //hacer apartito un nuevo empleado
	  //cambiar a varible global
	  //enviar listade empleados?? a un devolver lista, y query eviar como parametros para no hacer cada rato en todos y no repetir codigo
	  //asi solo mando resulset??
	  //conversion objeto relacional, exite herramientos, coversion llevar a un metodo
	  //emplyee convertToObject(resultset employeeBd)
	  //employee=new Employee(integer.parset...(id))
}
