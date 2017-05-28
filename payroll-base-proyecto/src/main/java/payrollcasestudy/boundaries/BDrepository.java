package payrollcasestudy.boundaries;



import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Set;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentType;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;
import payrollcasestudy.Services.BDServices.DatabaseTypeServices;
import payrollcasestudy.Services.BDServices.ServicesAddInBDCommissionedEmployee;
import payrollcasestudy.Services.BDServices.ServicesAddInBDHourlyEmployee;
import payrollcasestudy.Services.BDServices.ServicesAddInBDSalariedEmployee;


public class BDrepository  implements Repositoory{
	public  PaymentType typeEmployee;
	private DatabaseTypeServices dataser;

	private ServicesAddInBDHourlyEmployee servicioHourly ;

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
		getHourlyEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectHourlyEmployee));
		  getSalariedEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectSalariedEmployee));
		  getCommissionedEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectCommissionedEmployee));
         
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
		
		try {	
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Hourly)
			{		dataser = new ServicesAddInBDHourlyEmployee();
				    dataser.addTypeEmployeeInBD(employeeId, employee);
				
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Salaried)
			{
				dataser = new ServicesAddInBDSalariedEmployee();
				dataser.addTypeEmployeeInBD(employeeId, employee);
			        
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Comision)
			{
				dataser = new ServicesAddInBDCommissionedEmployee();
			
				dataser.addTypeEmployeeInBD(employeeId, employee);
			}
			
		} catch (Exception e) {
			
				System.err.println(e);
			
		} 
	   
	    
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
	           rs= bd.connectionWithTableOfEmployees(querySelectHourlyEmployee);
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
			ResultSet results= bd.connectionWithTableOfEmployees(query);
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
			ResultSet results= bd.connectionWithTableOfEmployees(query);
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
			ResultSet results= bd.connectionWithTableOfEmployees(query);
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
