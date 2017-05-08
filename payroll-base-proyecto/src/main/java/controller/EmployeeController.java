package controller;

import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.APRIL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static payrollcasestudy.TestConstants.FLOAT_ACCURACY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Rule;

import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.SalesReceipt;
import payrollcasestudy.entities.ServiceCharge;
import payrollcasestudy.entities.TimeCard;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
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
import views.UI;



public class EmployeeController {
	static PayrollDatabase database=PayrollDatabase.globalPayrollDatabase;
	static public int employeeId=0;
	static public int memberId = 0;
	static String nombreCompleto = "";
	static Calendar payDate = new GregorianCalendar(2001, NOVEMBER, 30);
    static PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
	
	public static String addHourlyEmployee(String nombre, String apellido, String direccion, double tarifa_por_hora){
		System.out.println("----------REGISTRANDO EMPLEADO POR HORA---------");			
		employeeId++;
		
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute();
        return verifyCreation(nombre, apellido, nombreCompleto);        
	}
	

	private static String verifyCreation(String nombre, String apellido, String nombreCompleto) {
		Employee employee = database.getEmployee(employeeId);
        String mensaje;
		if(employee.getName() == nombreCompleto)
			mensaje="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito ";
				
			mensaje="Error al registrar el empleado " + employee.getName();
		
		return UI.mostrarMensaje(mensaje);   
	}
	
	
	
     public static String showEmployee(int id){			
		Updatable updatable = new EmpleadoView();		
		Employee empleado = database.getEmployee(id);		
		return empleado.update(updatable);		
			
	}
	
	
	
	

	public static String addSalariedEmployee(String nombre, String apellido, String direccion,double salario) {
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO---------");			
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddSalariedEmployeeTransaction(employeeId, nombreCompleto, direccion, salario);
        addEmployeeTransaction.execute();
        return verifyCreation(nombre, apellido, nombreCompleto);
	}
	

	
	public static String addComisionEmployee(String nombre, String apellido, String direccion, double salarioMensual,double comision) {		
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddCommissionedEmployeeTransaction(employeeId, nombreCompleto, direccion, salarioMensual, comision);
        addEmployeeTransaction.execute();
        return verifyCreation(nombre, apellido, nombreCompleto);		
	}
	

	
	public static String addPaySalariedEmployee(int employeeId) {		
        Calendar payDate = new GregorianCalendar(2001, NOVEMBER, 30);
        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
        paydayTransaction.execute();
        PayCheck payCheck = paydayTransaction.getPaycheck(employeeId);         
		return ""+payCheck.getNetPay();
	}
	
   public static String payEmployee() {	   
       paydayTransaction.execute();
       return UI.mostrarMensaje("Pagados");
	}
		
   
   	public static String showPayment(int id) {	  	
	
   		String Pay="no pagado";					
	    PayCheck payCheck = paydayTransaction.getPaycheck(id);
		if (payCheck!=null)			 
		     Pay="Pago total: "+payCheck.getNetPay()+"  ";	
		return Pay;		
	}
   
  
	public static String addServiceChargeEmployee(int eemployeId, double cargo, int dia, int mes, int anio) {		
		Employee employee = database.getEmployee(eemployeId);
	    memberId++;
        addMemberShip(2, employee);
        Calendar date1 = fechaCorrecta(dia, mes, anio);
         AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date1, cargo);
        addServiceChargeTransaction.execute();	        
	      
		return UI.mostrarMensaje("Servicio agregado");
	}


	private static void addMemberShip(double cargo, Employee employee) {
		UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
        employee.setUnionAffiliation(unionAffiliation);
        database.addUnionMember(memberId, employee);
	}

	
	
	public static String addSalesReceiptEmployee(int eemployeId, double amount, int dia, int mes, int anio) {		
        Calendar date1 = fechaCorrecta(dia, mes, anio); 
        Transaction salesReceiptTransaction =
                new AddSalesReceiptTransaction(date1, amount, eemployeId);
        salesReceiptTransaction.execute(); 
		return UI.mostrarMensaje("Recibo de venta agregado");
	}
	

	public static String addTimeCardEmployee(int eemployeId, double horas, int dia, int mes, int anio) {		
		 Calendar date1 = fechaCorrecta(dia, mes, anio);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date1, horas,  eemployeId);
        timeCardTransaction.execute();
     
		return UI.mostrarMensaje("Timecard agregada");
	}


	private static Calendar fechaCorrecta(int dia, int mes, int anio) {
		Calendar date = new GregorianCalendar(anio,mes,dia);
        date.set(Calendar.MONTH, mes-1);
        Calendar date1 = new GregorianCalendar(anio, date.get(Calendar.MONTH),dia);
		return date1;
	}		

}
