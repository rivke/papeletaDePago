package payrollcasestudy.Services.EmployeeServices;

import static java.util.Calendar.NOVEMBER;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.PaydayTransaction;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddCommissionedEmployeeTransaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;
import payrollcasestudy.transactions.add.AddSalariedEmployeeTransaction;
import payrollcasestudy.transactions.add.AddSalesReceiptTransaction;
import payrollcasestudy.transactions.add.AddServiceChargeTransaction;
import payrollcasestudy.transactions.add.AddTimeCardTransaction;
import updatable.Updatable;

import views.EmpleadoView;
import views.MessageView;



public class EmployeeServices {
	private static Repositoory repository;
	static public int employeeId = 0;
	static public int memberId = 0;
	static String nombreCompleto = "";
	static Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
    static PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
	
	public EmployeeServices(Repositoory repository) {
		this.repository = repository;
	}
	
	
	
	public static String addHourlyEmployee(String nombre, String apellido, String direccion, double tarifa_por_hora) throws SQLException{
		System.out.println("----------REGISTRANDO EMPLEADO POR HORA---------");			
		employeeId+=1;
		
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute(repository);
       
        return verifyCreation(nombre, apellido, nombreCompleto);        
	}
	

	private static String verifyCreation(String nombre, String apellido, String nombreCompleto) {
		Employee employee = repository.getEmployee(employeeId);
        String mensaje;
		if(employee.getName()!="")
			mensaje="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito ";
		else		
			mensaje="Error al registrar el empleado " + employee.getName();
		
		return MessageView.mostrarMensaje(mensaje);   
	}
	
	
	
     public static String showEmployee(int id){			
		Updatable updatable = new EmpleadoView();		
		Employee empleado = repository.getEmployee(id);		
		return empleado.update(updatable);		
			
	}
     
     public Employee getAnEmployee(int employeeId) {
 		return repository.getEmployee(employeeId);
 	}
     
	
     public ArrayList<Employee> getAllEmployees() {
		return repository.getAllEmployees();
     
     }
	


	public static String addSalariedEmployee(String nombre, String apellido, String direccion,double salario) throws SQLException {
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO---------");			
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddSalariedEmployeeTransaction(employeeId, nombreCompleto, direccion, salario);
        addEmployeeTransaction.execute(repository);
        return verifyCreation(nombre, apellido, nombreCompleto);
	}
	

	
	public static String addComisionEmployee(String nombre, String apellido, String direccion, double salarioMensual,double comision) throws SQLException {		
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddCommissionedEmployeeTransaction(employeeId, nombreCompleto, direccion, salarioMensual, comision);
        addEmployeeTransaction.execute(repository);
        return verifyCreation(nombre, apellido, nombreCompleto);		
	}
	

	
   	


	
	

	

}
