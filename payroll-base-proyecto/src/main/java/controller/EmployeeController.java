package controller;

import java.util.ArrayList;
import java.util.Set;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.Message;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;
import updatable.Updatable;
import updatable.UpdatableMessage;
import views.EmpleadoView;
import views.MessageView;

public class EmployeeController {
	public static String addHourlyEmployee(String nombre, String apellido, String direccion, double tarifa_por_hora){
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO POR HORA---------");		
		int employeeId = 0;
		employeeId++;
		String nombreCompleto = "";
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute();
        Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
        String mensajee;
		if(employee.getName() == nombreCompleto)
        	
    		return mensajee="<div class='ui basic segment'>El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito</div>";
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
	
	public static String createEmployee(String employeeId, String nombre,String direccion){
		String resultado="";
		int employeeIdInt=Integer.parseInt(employeeId);
		Employee employee = new Employee(employeeIdInt,nombre,direccion);
		PayrollDatabase.globalPayrollDatabase.addEmployee(employeeIdInt,employee);
		MessageController messageController = new MessageController();
		UpdatableMessage updatableMessage = new MessageView();
		Message message = new Message("Empleado creado suc");
		resultado += message.update(updatableMessage);
		return resultado;				
	}

}
