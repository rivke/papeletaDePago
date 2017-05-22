package payrollcasestudy.boundaries;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import payrollcasestudy.entities.Employee;

public interface Repositoory {
	


	public Employee getEmployee(int employeeId);
    public void addEmployee(int employeeId, Employee employee) throws SQLException;
    public void clear();

    public void deleteEmployee(int employeeId);

    public Employee getUnionMember(int memberId);
    public void addUnionMember(int memberId, Employee employee);

    public void deleteUnionMember(int memberId);

    public Set<Integer> getAllEmployeeIds();
    
    public Employee addShow(int ind);     
    
    public  ArrayList<Employee> getAllEmployees() throws SQLException;
    
    public Integer getLastId();

}
