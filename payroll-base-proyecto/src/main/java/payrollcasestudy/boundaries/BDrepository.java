package payrollcasestudy.boundaries;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Set;


import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentType;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;


public class BDrepository implements Repositoory{
	public  PaymentType category;

	Employee employee = null;
    BaseDeDatos bd = new BaseDeDatos();
   
   
	    
	@Override
	public Employee getEmployee(int employeeId)
	{
    String querySelectHourlyEmployee= "SELECT * FROM hourly_employees WHERE employeeId='"+ employeeId + "';";
    String querySelectSalariedEmployee= "SELECT * FROM salaried_employee WHERE idSalariedEmployee='"+ employeeId + "';";
    String querySelectCommissionedEmployee= "SELECT * FROM comision WHERE idemployees='"+ employeeId + "';";

		try{     
        
           getAllEmployeesBD(querySelectHourlyEmployee, querySelectSalariedEmployee, querySelectCommissionedEmployee);
			
           return employee;

		}catch (Exception e){
			System.err.println(e);
			return employee;
			
		}
		   
	}


	private void getAllEmployeesBD(String querySelectHourlyEmployee, String querySelectSalariedEmployee, String querySelectCommissionedEmployee)	{
		getHourlyEmployeeOfBD(connectionWithTableOfEmployees(querySelectHourlyEmployee));
		  getSalariedEmployeeOfBD(connectionWithTableOfEmployees(querySelectSalariedEmployee));
		  getCommissionedEmployeeOfBD(connectionWithTableOfEmployees(querySelectCommissionedEmployee));
         
	}


	private void getHourlyEmployeeOfBD(ResultSet rs)  {
		try {
			while (rs.next()) {
			 employee=new Employee(Integer.parseInt(rs.getString("employeeId")), rs.getString("name"), rs.getString("address"));
			 PaymentClassification paymentClassification= new HourlyPaymentClassification(Integer.parseInt(rs.getString("tarifa_por_hora")));
			employee.setPaymentClassification(paymentClassification);
			   }
		} catch (Exception e) {
			
			System.err.println(e);
		} 
	}
	private void getSalariedEmployeeOfBD(ResultSet rs) {
		try {
			while (rs.next()) {
			 employee=new Employee(Integer.parseInt(rs.getString("idSalariedEmployee")), rs.getString("name"), rs.getString("address"));
			 PaymentClassification paymentClassification= new SalariedClassification(Integer.parseInt(rs.getString("salary")));
      employee.setPaymentClassification(paymentClassification);
			  }
		} catch (Exception e) {
			
			System.err.println(e);
		} 
	}
	
	


	private void getCommissionedEmployeeOfBD(ResultSet rs) {
		try {
			while (rs.next()) {
			 employee=new Employee(Integer.parseInt(rs.getString("idemployees")), rs.getString("name"), rs.getString("address"));
			 PaymentClassification paymentClassification= new CommissionedPaymentClassification(Integer.parseInt(rs.getString("monthlySalary")), Integer.parseInt(rs.getString("commissionRate")));
      employee.setPaymentClassification(paymentClassification);
			  }
		} catch (Exception e) {
			
			System.err.println(e);
		}
	}
	
	
	public String createQueryByTypeEmployee(Employee employee)
	{
      
		String query="";
        if(employee.getPaymentClassification().typeOfPayment()==category.Hourly)
		{
				
        	query="INSERT INTO hourly_employees VALUES (?,?,?,?)";
			
		}
		if(employee.getPaymentClassification().typeOfPayment()==category.Salaried)
		{
			query="INSERT INTO salaried_employee VALUES (?,?,?,?)";
			
		    
		}
		if(employee.getPaymentClassification().typeOfPayment()==category.Comision)
		{
   
			query="INSERT INTO comision VALUES (?,?,?,?,?)";
			
		}
		return query;

		
	}
	
	
	@Override
	public void addEmployee(int employeeId, Employee employee) {
		
		try {	
			if(employee.getPaymentClassification().typeOfPayment()==category.Hourly)
			{		
				addHourlyPaymentInBD(employeeId, employee);
			    
			}
			if(employee.getPaymentClassification().typeOfPayment()==category.Salaried)
			{
				addSalariedPaymentInBD(employeeId, employee);
			        
			}
			if(employee.getPaymentClassification().typeOfPayment()==category.Comision)
			{
				addCommissionedInBD(employeeId, employee);    
			}
			
		} catch (Exception e) {
			
				System.err.println(e);
			
		} 
	   
	    
	}


	private void addCommissionedInBD(int employeeId, Employee employee) throws SQLException {
		PreparedStatement pstInsertarCuenta= bd.conectar().prepareStatement(createQueryByTypeEmployee(employee));
		CommissionedPaymentClassification commissionedClassification =  (CommissionedPaymentClassification) employee.getPaymentClassification();
		addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
		pstInsertarCuenta.setDouble(4,commissionedClassification.getMonthlySalary());
		pstInsertarCuenta.setDouble(5,commissionedClassification.getCommissionRate());
		pstInsertarCuenta.executeUpdate();
	}


	private void addNameAddressInBD(int employeeId, Employee employee, PreparedStatement pstInsertarCuenta)
			throws SQLException {
		pstInsertarCuenta.setLong(1, employeeId);
		pstInsertarCuenta.setString(2, employee.getName());
		pstInsertarCuenta.setString(3, employee.getAddress());
	}


	private void addSalariedPaymentInBD(int employeeId, Employee employee) throws SQLException {
		PreparedStatement pstInsertarCuenta=bd.conectar().prepareStatement(createQueryByTypeEmployee(employee));;
		SalariedClassification salariedClassification =  (SalariedClassification) employee.getPaymentClassification();
		addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
		pstInsertarCuenta.setDouble(4,salariedClassification.getSalary());
		pstInsertarCuenta.executeUpdate();
	}


	private void addHourlyPaymentInBD(int employeeId, Employee employee) throws SQLException {
		PreparedStatement pstInsertarCuenta= bd.conectar().prepareStatement(createQueryByTypeEmployee(employee));
		HourlyPaymentClassification hourlyClassification =  (HourlyPaymentClassification) employee.getPaymentClassification();
		addNameAddressInBD(employeeId, employee, pstInsertarCuenta);
		pstInsertarCuenta.setDouble(4,hourlyClassification.getHourlyRate());
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
		ResultSet rs=null;
	    String querySelectHourlyEmployee= "SELECT * FROM hourly_employees WHERE memberId='"+ memberId + "';";
	    
			try{     
	           rs= connectionWithTableOfEmployees(querySelectHourlyEmployee);
	           getHourlyEmployeeOfBD(rs);
	         
				
	           return employee;

			}catch (Exception e){
				System.err.println(e);
				return employee;
				
			}
		
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
	public ArrayList<Employee> getAllEmployees() {
		

       ArrayList<Employee> allEmployees = new ArrayList<Employee>();
       allEmployees.addAll(getAllEmployeesHourly());
       allEmployees.addAll(getAllEmployeesSalaried());
       allEmployees.addAll(getAllEmployeesCommissioned());
       return allEmployees;
		
	}
	
	public ArrayList<Employee> getAllEmployeesHourly(){
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
	
	public ArrayList<Employee> getAllEmployeesSalaried(){
		String query= "SELECT * FROM papeletadepago.salaried_employee";

       ArrayList<Employee> ls = new ArrayList<Employee>();
		try{
			ResultSet results= connectionWithTableOfEmployees(query);
			while(results.next()){
				
				Employee employee=new Employee(Integer.parseInt(results.getString("idSalariedEmployee")), results.getString("name"), results.getString("address"));
		       ls.add(employee);
			}
			
			return ls;
		}
		catch(Exception e){
			System.err.println(e);
			return ls;
			
		}
		
	}

	public ArrayList<Employee> getAllEmployeesCommissioned() {
		String query= "SELECT * FROM papeletadepago.comision";

       ArrayList<Employee> ls = new ArrayList<Employee>();
		try{
			ResultSet results= connectionWithTableOfEmployees(query);
			while(results.next()){
				
				Employee employee=new Employee(Integer.parseInt(results.getString("idemployees")), results.getString("name"), results.getString("address"));
		       ls.add(employee);
			}
			
			return ls;
		}
		catch(Exception e){
			System.err.println(e);
			return ls;
			
		}
		
	}


	@Override
	public Integer getLastId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
