package controller;

import java.util.ArrayList;
import java.util.Set;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.Message;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;
import payrollcasestudy.transactions.add.AddSalariedEmployeeTransaction;
import updatable.Updatable;
import updatable.UpdatableMessage;
import views.EmpleadoView;
import views.MessageView;

public class EmployeeController {
	static public int employeeId=0;
	public static String addHourlyEmployee(String nombre, String apellido, String direccion, double tarifa_por_hora){
		System.out.println("----------REGISTRANDO EMPLEADO POR HORA---------");			
		employeeId++;
		String nombreCompleto = "";
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute();
        return verifyCreation(nombre, apellido, nombreCompleto);        
	}

	private static String verifyCreation(String nombre, String apellido, String nombreCompleto) {
		Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
        String mensajee;
		if(employee.getName() == nombreCompleto)        	
    		return mensajee="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito";
        else
        	return mensajee="Error al registrar el empleado " + employee.getName();
	}
	
	public static String showEmployee()
	{
		Updatable updatable = new EmpleadoView();
		Set<Integer> employeeIds=PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		ArrayList<Integer> employeeIdLista = new ArrayList<>(employeeIds);
		Employee employee;
		String allEmployees="";
		for(int ind=0;ind<employeeIdLista.size();ind++)
		{
			employee=PayrollDatabase.addShow(ind);			
			allEmployees=allEmployees+employee.update(updatable);			
		}
		return allEmployees;	
	}
	
	public static String showAllEmployees(){
		Updatable updatable = new EmpleadoView();		
		String allEmployees="";
		ArrayList<Employee> empleados = new ArrayList<Employee>();
		empleados = PayrollDatabase.getAllEmployees();
		for( Employee emp : empleados)
		{
			allEmployees = allEmployees + emp.update(updatable);
		}
		return allEmployees;	
	}

	public static String addSalariedEmployee(String nombre, String apellido, String direccion,
			double salario) {
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO---------");			
		employeeId++;
		String nombreCompleto = "";
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddSalariedEmployeeTransaction(employeeId, nombreCompleto, direccion, salario);
        addEmployeeTransaction.execute();
        return verifyCreation(nombre, apellido, nombreCompleto);
	}		

}
