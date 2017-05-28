package payrollcasestudy.boundaries;



import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Set;

import payrollcasestudy.entities.Employee;

import payrollcasestudy.entities.paymentclassifications.PaymentType;

import payrollcasestudy.Services.BDServices.DatabaseTypeServices;



public class BDrepository  implements Repositoory{
	public  PaymentType typeEmployee;
	Employee employee = null;
    BaseDeDatos bd = new BaseDeDatos();
   
    
	@Override
	public Employee getEmployee(int employeeId)
	{
		try{     
			return findAnEmployee(employeeId);
			}
		catch (Exception e){
			System.err.println(e);
			return employee;
			}
	}


	private Employee findAnEmployee(int employeeId)	{
		Employee employee3 = null;
		if( DatabaseTypeServices.serviceHourly.getTypeEmployeeOfBD(employeeId)!=null)
			return employee3=DatabaseTypeServices.serviceHourly.getTypeEmployeeOfBD(employeeId);
		
		if(DatabaseTypeServices.serviceSalaried.getTypeEmployeeOfBD(employeeId)!=null)
			return employee3= DatabaseTypeServices.serviceSalaried.getTypeEmployeeOfBD(employeeId);
		
		if(DatabaseTypeServices.serviceCommissined.getTypeEmployeeOfBD(employeeId)!=null)
          return employee3=DatabaseTypeServices.serviceCommissined.getTypeEmployeeOfBD(employeeId);
		return employee3;
	}


	@Override
	public void addEmployee(int employeeId, Employee employee) {
		try {	
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Hourly)
			{		
				DatabaseTypeServices.serviceHourly.addTypeEmployeeInBD(employeeId, employee);
				
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Salaried)
			{
				DatabaseTypeServices.serviceSalaried.addTypeEmployeeInBD(employeeId, employee);
			        
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Comision)
			{
				DatabaseTypeServices.serviceCommissined.addTypeEmployeeInBD(employeeId, employee);
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
		return employee;
		
		
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
