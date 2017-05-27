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
	
	
	
	
	@Override
	public void addEmployee(int employeeId, Employee employee) {
		String salariado="INSERT INTO salaried_employee VALUES (?,?,?,?)";
	   String hourlyy="INSERT INTO hourly_employees VALUES (?,?,?,?)";
		String commission= "INSERT INTO comision VALUES (?,?,?,?,?)";
		
		PaymentClassification pay=employee.getPaymentClassification();
		PreparedStatement pstInsertarCuenta; 
        

		
		try {	
	
	   
			if(employee.getPaymentClassification().typeOfPayment()==category.Hourly)
			{
	   
			pstInsertarCuenta = bd.conectar().prepareStatement(hourlyy);
			 addNameAddressEmployee(employeeId, employee, pstInsertarCuenta);
			  //addHourlyPaymentABD(pay, pstInsertarCuenta);    
			 
			 addHourlyPaymentABD(pay,pstInsertarCuenta);
			    
			}
			if(employee.getPaymentClassification().typeOfPayment()==category.Salaried)
			{
	   
			pstInsertarCuenta = bd.conectar().prepareStatement(salariado);
			 addNameAddressEmployee(employeeId, employee, pstInsertarCuenta);
			  //addHourlyPaymentABD(pay, pstInsertarCuenta);    
			 
			 addHourlyPaymentABD(pay,pstInsertarCuenta);
			    
			}
			if(employee.getPaymentClassification().typeOfPayment()==category.Comision)
			{
	   
			pstInsertarCuenta = bd.conectar().prepareStatement(commission);
			 addNameAddressEmployee(employeeId, employee, pstInsertarCuenta);
			 addHourlyPaymentABD(pay,pstInsertarCuenta);
			    
			    	
			    
			    
			    
			}
			
		} catch (Exception e) {
			
				System.err.println(e);
			
		} 
	   
	    
	}


	private void addHourlyPaymentABD(PaymentClassification pay, PreparedStatement pstInsertarCuenta)
			throws SQLException {
		int colum=4;
		    if(pay.updatePayment().size()>0)
		    {
		    	for(int ind=0;ind<pay.updatePayment().size();ind++) {
			    	 pstInsertarCuenta.setDouble(colum, pay.updatePayment().get(ind));
			    	  colum++;
			    	}
		    	
		    }
		    else
		    pstInsertarCuenta.setDouble(4,pay.updatePayment().get(0));
		    pstInsertarCuenta.executeUpdate();
	}


	private void addNameAddressEmployee(int employeeId, Employee employee, PreparedStatement pstInsertarCuenta)
			throws SQLException {
		pstInsertarCuenta.setLong(1, employeeId);
		    pstInsertarCuenta.setString(2, employee.getName());
		    pstInsertarCuenta.setString(3, employee.getAddress());
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
