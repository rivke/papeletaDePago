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
		
		try{
			
           String query= "SELECT * FROM employees WHERE employeeId='"+ employeeId + "';";
           Statement stmt=(Statement) bd.conectar().createStatement();
           rs=((java.sql.Statement)stmt).executeQuery(query);
          // System.out.println("Exito");
           while (rs.next()) {
		 employee=new Employee(Integer.parseInt(rs.getString("employeeId")), rs.getString("name"), rs.getString("address"));
		 PaymentClassification paymentClassification= new HourlyPaymentClassification(Integer.parseInt(rs.getString("tarifa_por_hora")));
		// employee.setPaymentClassification(paymentClassification).update3(Integer.parseInt(rs.getString("tarifa_por_hora")));;
        employee.setPaymentClassification(paymentClassification);
           }
			
           return employee;

		}catch (Exception e){
			System.err.println(e);
			return employee;
			
		}
		
		
	   

	      

	   
	}
	
	public ResultSet connectionWithTableOfEmployees2()
	{
		ResultSet rs=null;
		try{
			
           String query= "SELECT * FROM papeletadepago.employees";
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
	public void addEmployee(int employeeId, Employee employee) throws SQLException {
		
	    PreparedStatement pstInsertarCuenta;
	   
	   
	    String sqlNuevaCuenta = "INSERT INTO employees VALUES (?,?,?,?)";
	    pstInsertarCuenta = bd.conectar().prepareStatement(sqlNuevaCuenta); 
	    pstInsertarCuenta.setLong(1, employeeId);
	    pstInsertarCuenta.setString(2, employee.getName());
	    pstInsertarCuenta.setString(3, employee.getAddress());
	    
	    PaymentClassification a=employee.getPaymentClassification();
	     
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
	
	
	public ResultSet connectionWithTableOfEmployees()
	{
		
		ResultSet rs=null;
		try{
		
           String query= "SELECT * FROM papeletadepago.employees";
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
		try{
			ResultSet results= connectionWithTableOfEmployees();
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

}
