package payrollcasestudy.boundaries;

import static java.util.Calendar.NOVEMBER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;
import java.sql.ResultSet;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;
import payrollcasestudy.transactions.PaydayTransaction;
import payrollcasestudy.transactions.Transaction;

public class BDrepository implements Repositoory{
	Employee employee = null;
	static Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
    static PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
	
    BaseDeDatos bd = new BaseDeDatos();

	    
	@Override
	public Employee getEmployee(int employeeId)
	{ResultSet rs=null;
    String querySelectHourlyEmployee= "SELECT * FROM hourly_employees WHERE employeeId='"+ employeeId + "';";
    String querySelectSalariedEmployee= "SELECT * FROM salaried_employee WHERE idSalariedEmployee='"+ employeeId + "';";

		try{     
           rs= connectionWithTableOfEmployees(querySelectHourlyEmployee);
           getHourlyEmployeeOfBD(rs);
           rs= connectionWithTableOfEmployees(querySelectSalariedEmployee);
           getSalariedEmployeeOfBD(rs);
			
           return employee;

		}catch (Exception e){
			System.err.println(e);
			return employee;
			
		}
		   
	}


	private void getHourlyEmployeeOfBD(ResultSet rs) throws SQLException {
		while (rs.next()) {
		 employee=new Employee(Integer.parseInt(rs.getString("employeeId")), rs.getString("name"), rs.getString("address"));
		 PaymentClassification paymentClassification= new HourlyPaymentClassification(Integer.parseInt(rs.getString("tarifa_por_hora")));
        employee.setPaymentClassification(paymentClassification);
           }
	}


	private void getSalariedEmployeeOfBD(ResultSet rs) throws SQLException {
		while (rs.next()) {
		 employee=new Employee(Integer.parseInt(rs.getString("idSalariedEmployee")), rs.getString("name"), rs.getString("adresse"));
		 PaymentClassification paymentClassification= new SalariedClassification(Integer.parseInt(rs.getString("salary")));
       employee.setPaymentClassification(paymentClassification);
          }
	}
	
	
	@Override
	public void addEmployee(int employeeId, Employee employee) throws SQLException {
		PaymentClassification a=employee.getPaymentClassification();
		PreparedStatement pstInsertarCuenta; 
	    String sqlNewHourlyEmployee =a.updateQuery();

	    pstInsertarCuenta = bd.conectar().prepareStatement(sqlNewHourlyEmployee); 
	    pstInsertarCuenta.setLong(1, employeeId);
	    pstInsertarCuenta.setString(2, employee.getName());
	    pstInsertarCuenta.setString(3, employee.getAddress());
	    pstInsertarCuenta.setLong(4, (long) a.update2());
	    pstInsertarCuenta.executeUpdate();    
	    
	}
	
	
		
	

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getUnionMember(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUnionMember(int memberId, Employee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUnionMember(int memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> getAllEmployeeIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee addShow(int ind) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ResultSet connectionWithTableOfEmployees(String query)
	{
		
		ResultSet rs=null;
		try{
		
          // String query= "SELECT * FROM papeletadepago.hourly_employees";
           Statement stmt=(Statement) bd.conectar().createStatement();
           rs=((java.sql.Statement)stmt).executeQuery(query);
           System.out.println("Exito");
           return rs;
			
			
		}catch (Exception e){
			System.err.println(e);
			
			return rs;

		}
		
		
	}
	
	
	
	
	
	@Override
	public ArrayList<Employee> getAllEmployees() throws SQLException {
		

       ArrayList<Employee> ls = new ArrayList<Employee>();
       ls.addAll(getAllEmployeesHourly());
       ls.addAll(getAllEmployeesSalaried());
		return ls;
		
	}
	
	public ArrayList<Employee> getAllEmployeesHourly() throws SQLException {
		String query= "SELECT * FROM papeletadepago.hourly_employees";
       ArrayList<Employee> ls = new ArrayList<Employee>();
		try{
			ResultSet results= connectionWithTableOfEmployees(query);
			while(results.next()){
				
				Employee employee=new Employee(Integer.parseInt(results.getString("employeeId")), results.getString("name"), results.getString("address"));
		       ls.add(employee);
			}
			
			return ls;
		}
		catch(Exception e){
			System.err.println(e);
			return ls;
			
		}
		
	}
	
	public ArrayList<Employee> getAllEmployeesSalaried() throws SQLException {
		String query= "SELECT * FROM papeletadepago.salaried_employee";

       ArrayList<Employee> ls = new ArrayList<Employee>();
		try{
			ResultSet results= connectionWithTableOfEmployees(query);
			while(results.next()){
				
				Employee employee=new Employee(Integer.parseInt(results.getString("idSalariedEmployee")), results.getString("name"), results.getString("adresse"));
		       ls.add(employee);
			}
			
			return ls;
		}
		catch(Exception e){
			System.err.println(e);
			return ls;
			
		}
		
	}
	
	
	
	

}
