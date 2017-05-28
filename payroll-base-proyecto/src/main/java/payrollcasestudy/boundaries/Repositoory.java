package payrollcasestudy.boundaries;


import java.util.ArrayList;
import java.util.Set;

import payrollcasestudy.entities.Employee;

public interface Repositoory {
	



	public static Repositoory repository = new MemoryRepository();
	public static Repositoory repository2 = new BDrepository();

	public Repositoory getRepo();
	public Employee getEmployee(int employeeId);
    public void addEmployee(int employeeId, Employee employee);
    public void clear();

    public void deleteEmployee(int employeeId);

    public Employee getUnionMember(int memberId);
    public void addUnionMember(int memberId, Employee employee);

    public void deleteUnionMember(int memberId);

    public Set<Integer> getAllEmployeeIds();
    
    public Employee addShow(int ind);     
    
    public  ArrayList<Employee> getAllEmployees();
    
    public Integer getLastId();

}
