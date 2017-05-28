package payrollcasestudy.boundaries;



import java.util.ArrayList;
import java.util.Set;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentType;
import payrollcasestudy.Services.BDServices.TypeDatabaseServices;

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
		if( TypeDatabaseServices.serviceHourly.getTypeEmployeeOfBD(employeeId)!=null)
			return employee3=TypeDatabaseServices.serviceHourly.getTypeEmployeeOfBD(employeeId);
		
		if(TypeDatabaseServices.serviceSalaried.getTypeEmployeeOfBD(employeeId)!=null)
			return employee3= TypeDatabaseServices.serviceSalaried.getTypeEmployeeOfBD(employeeId);
		
		if(TypeDatabaseServices.serviceCommissined.getTypeEmployeeOfBD(employeeId)!=null)
          return employee3=TypeDatabaseServices.serviceCommissined.getTypeEmployeeOfBD(employeeId);
		return employee3;
	}
	
	@Override
	public void addEmployee(int employeeId, Employee employee) {
		try {	
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Hourly)
			{		
				TypeDatabaseServices.serviceHourly.addTypeEmployeeInBD(employeeId, employee);
				
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Salaried)
			{
				TypeDatabaseServices.serviceSalaried.addTypeEmployeeInBD(employeeId, employee);
			        
			}
			if(employee.getPaymentClassification().typeOfPayment()==typeEmployee.Comision)
			{
				TypeDatabaseServices.serviceCommissined.addTypeEmployeeInBD(employeeId, employee);
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
		allEmployees.addAll(TypeDatabaseServices.serviceHourly.getAllEmploye());
		allEmployees.addAll(TypeDatabaseServices.serviceSalaried.getAllEmploye());
		allEmployees.addAll(TypeDatabaseServices.serviceCommissined.getAllEmploye());
		return allEmployees;
		
	}
	
	@Override
	public Integer getLastId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
