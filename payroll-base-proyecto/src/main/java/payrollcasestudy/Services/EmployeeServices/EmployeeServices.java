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
	

	////Pagos
	public static String addPaySalariedEmployee(int employeeId) {		
        Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
        paydayTransaction.execute(repository);
        PayCheck payCheck = paydayTransaction.getPaycheck(employeeId);         
		return ""+payCheck.getNetPay();
	}
	
   public static String payEmployee() {	   
       paydayTransaction.execute(repository);
       return MessageView.mostrarMensaje("Pagados");
	}
		
   
   	public static String showPayment(int id) {	  	
	
   		String Pay="no pagado";					
	    PayCheck payCheck = paydayTransaction.getPaycheck(id);
		if (payCheck!=null)			 
		     Pay="Pago total: "+payCheck.getNetPay()+"  ";	
		return Pay;		
	}
   
  
	public static String addServiceChargeEmployee(int eemployeId, double cargo, int dia, int mes, int anio) {		
		Employee employee = repository.getEmployee(eemployeId);
	    memberId++;
        addMemberShip(2, employee);
        Calendar date1 = fechaCorrecta(dia, mes, anio);
         AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date1, cargo, repository);
        addServiceChargeTransaction.execute(repository);	        
	      
		return MessageView.mostrarMensaje("Servicio agregado");
	}


	private static void addMemberShip(double cargo, Employee employee) {
		UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
        employee.setUnionAffiliation(unionAffiliation);
        repository.addUnionMember(memberId, employee);
	}

	
	
	public static String addSalesReceiptEmployee(int eemployeId, double amount, int dia, int mes, int anio)  {		
        Calendar date1 = fechaCorrecta(dia, mes, anio); 
        Transaction salesReceiptTransaction =
                new AddSalesReceiptTransaction(date1, amount, eemployeId);
        salesReceiptTransaction.execute(repository); 
		return MessageView.mostrarMensaje("Recibo de venta agregado");
	}
	

	public static String addTimeCardEmployee(int eemployeId, double horas, int dia, int mes, int anio) {		
		 Calendar date1 = fechaCorrecta(dia, mes, anio);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date1, horas,  eemployeId);
        timeCardTransaction.execute(repository);
     
		return MessageView.mostrarMensaje("Timecard agregada");
	}


	private static Calendar fechaCorrecta(int dia, int mes, int anio) {
		Calendar date = new GregorianCalendar(anio,mes,dia);
        date.set(Calendar.MONTH, mes-1);
        Calendar date1 = new GregorianCalendar(anio, date.get(Calendar.MONTH),dia);
		return date1;
	}		

}
