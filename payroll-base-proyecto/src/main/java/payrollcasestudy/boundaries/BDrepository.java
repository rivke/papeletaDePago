package payrollcasestudy.boundaries;



import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Set;

import payrollcasestudy.entities.Employee;

import payrollcasestudy.entities.paymentclassifications.PaymentType;

import payrollcasestudy.Services.BDServices.DatabaseTypeServices;

import payrollcasestudy.Services.BDServices.ServicesAddInBDHourlyEmployee;



public class BDrepository  implements Repositoory{
	public  PaymentType typeEmployee;
	
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
		    //employee=DatabaseTypeServices.typeser.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectHourlyEmployee));
		   
			employee=getAllEmployeesBD( querySelectHourlyEmployee, querySelectSalariedEmployee, querySelectCommissionedEmployee);
           return employee;

		}catch (Exception e){
			System.err.println(e);
			return employee;
			
		}
		   
	}


	private Employee getAllEmployeesBD(String querySelectHourlyEmployee, String querySelectSalariedEmployee, String querySelectCommissionedEmployee)	{
		Employee employee3 = null;
		if( DatabaseTypeServices.serviceHourly.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectHourlyEmployee))!=null)
			 return employee3=DatabaseTypeServices.serviceHourly.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectHourlyEmployee));
		
		if(DatabaseTypeServices.serviceSalaried.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectSalariedEmployee))!=null)
			return employee3= DatabaseTypeServices.serviceSalaried.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectSalariedEmployee));
		
		if(DatabaseTypeServices.serviceCommissined.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectCommissionedEmployee))!=null)
          return employee3=DatabaseTypeServices.serviceCommissined.getTypeEmployeeOfBD(bd.connectionWithTableOfEmployees(querySelectCommissionedEmployee));
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
