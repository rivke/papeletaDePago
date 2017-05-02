package payrollcasestudy.boundaries;

import payrollcasestudy.entities.Employee;
import updatable.Updatable;
import views.EmpleadoView;

import java.util.*;

/**
 * Listing 19-3
 * Listing 19-4
 */
public class PayrollDatabase {
    public static PayrollDatabase globalPayrollDatabase = new PayrollDatabase();

    private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
    public Map<Integer, Employee> unionMembers = new HashMap<Integer, Employee>();


    public Employee getEmployee(int employeeId) {
        return employees.get(employeeId);
    }

    public void addEmployee(int employeeId, Employee employee) {
        employees.put(employeeId, employee);
    }

    public void clear(){
        employees.clear();
        unionMembers.clear();
    }

    public void deleteEmployee(int employeeId) {
        employees.put(employeeId, null);
    }

    public Employee getUnionMember(int memberId) {
        return unionMembers.get(memberId);
    }

    public void addUnionMember(int memberId, Employee employee) {
        unionMembers.put(memberId, employee);
    }

    public void deleteUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }

    public Set<Integer> getAllEmployeeIds() {
        return employees.keySet();
    }
    
    public static Employee addShow(int ind)
	{
		Set<Integer> employeeIds=PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		ArrayList<Integer> employeeIdLista = new ArrayList<>(employeeIds);
		Employee employee;				
			employee=PayrollDatabase.globalPayrollDatabase.getEmployee(employeeIdLista.get(ind));				
		return employee;	
	}        
    
    public static ArrayList<Employee> getAllEmployees(){
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	String resp = " ";
    	Employee emp;
    	for(int id : globalPayrollDatabase.getAllEmployeeIds()){
    		employees.add(globalPayrollDatabase.getEmployee(id));    		
    	}
    	return employees;
    }      
    
}
