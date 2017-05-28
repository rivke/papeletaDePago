package payrollcasestudy.boundaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import payrollcasestudy.entities.Employee;

public class MemoryRepository implements Repositoory {
	  public static Repositoory repository = new MemoryRepository();	
	  
	  private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
	    public Map<Integer, Employee> unionMembers = new HashMap<Integer, Employee>();

	@Override
	public Employee getEmployee(int employeeId) {
		return employees.get(employeeId);
	}

	@Override
	public void addEmployee(int employeeId, Employee employee) {
		  employees.put(employeeId, employee);
		
	}

	@Override
	public void clear() {
		 employees.clear();
	        unionMembers.clear();
		
	}

	@Override
	public void deleteEmployee(int employeeId) {
		 employees.put(employeeId, null);
		
	}

	@Override
	public Employee getUnionMember(int memberId) {
		  return unionMembers.get(memberId);
	}

	@Override
	public void addUnionMember(int memberId, Employee employee) {
		 unionMembers.put(memberId, employee);
		
	}

	@Override
	public void deleteUnionMember(int memberId) {
		 unionMembers.remove(memberId);
		
	}

	@Override
	public Set<Integer> getAllEmployeeIds() {
		 return employees.keySet();
	}

	@Override
	public Employee addShow(int ind) {
		Set<Integer> employeeIds=Repositoory.repository.getAllEmployeeIds();
		ArrayList<Integer> employeeIdLista = new ArrayList<>(employeeIds);
		Employee employee;				
			employee=Repositoory.repository.getEmployee(employeeIdLista.get(ind));				
		return employee;	
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
    
    	for(int id : repository.getAllEmployeeIds()){
    		employees.add(repository.getEmployee(id));    		
    	}
    	return employees;
	}

	@Override
	public Integer getLastId() {
		// TODO Auto-generated method stub
		return null;
	}

}
