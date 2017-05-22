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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Rule;

import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.BDrepository;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.boundaries.Repositoory;
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
import views.MessageView;



public class EmployeeServices {
	
	private static Repositoory mc = new BDrepository();

	static PayrollDatabase database=PayrollDatabase.globalPayrollDatabase;
	static public int employeeId = 0;
	static public int memberId = 0;
	static String nombreCompleto = "";
	static Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
    static PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
	
	public static String addHourlyEmployee(String nombre, String apellido, String direccion, double tarifa_por_hora) throws SQLException{
		System.out.println("----------REGISTRANDO EMPLEADO POR HORA---------");			
		employeeId+=1;
		
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute(mc);
        return verifyCreation(nombre, apellido, nombreCompleto);        
	}
	

	private static String verifyCreation(String nombre, String apellido, String nombreCompleto) {
		Employee employee = mc.getEmployee(employeeId);
        String mensaje;
		if(employee.getName()!="")
			mensaje="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito ";
		else		
			mensaje="Error al registrar el empleado " + employee.getName();
		
		return MessageView.mostrarMensaje(mensaje);   
	}
	
	
	
     public static String showEmployee(int id){			
		Updatable updatable = new EmpleadoView();		
		Employee empleado = mc.getEmployee(id);		
		return empleado.update(updatable);		
			
	}
	
	
	
	

	public static String addSalariedEmployee(String nombre, String apellido, String direccion,double salario) throws SQLException {
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO---------");			
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddSalariedEmployeeTransaction(employeeId, nombreCompleto, direccion, salario);
        addEmployeeTransaction.execute(mc);
        return verifyCreation(nombre, apellido, nombreCompleto);
	}
	

	
	public static String addComisionEmployee(String nombre, String apellido, String direccion, double salarioMensual,double comision) throws SQLException {		
		employeeId++;
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddCommissionedEmployeeTransaction(employeeId, nombreCompleto, direccion, salarioMensual, comision);
        addEmployeeTransaction.execute(null);
        return verifyCreation(nombre, apellido, nombreCompleto);		
	}
	

	
	public static String addPaySalariedEmployee(int employeeId) {		
        Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
        paydayTransaction.execute(null);
        PayCheck payCheck = paydayTransaction.getPaycheck(employeeId);         
		return ""+payCheck.getNetPay();
	}
	
   public static String payEmployee() {	   
       paydayTransaction.execute(null);
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
		Employee employee = database.getEmployee(eemployeId);
	    memberId++;
        addMemberShip(2, employee);
        Calendar date1 = fechaCorrecta(dia, mes, anio);
         AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date1, cargo, mc);
        addServiceChargeTransaction.execute(null);	        
	      
		return MessageView.mostrarMensaje("Servicio agregado");
	}


	private static void addMemberShip(double cargo, Employee employee) {
		UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
        employee.setUnionAffiliation(unionAffiliation);
        database.addUnionMember(memberId, employee);
	}

	
	
	public static String addSalesReceiptEmployee(int eemployeId, double amount, int dia, int mes, int anio) throws SQLException {		
        Calendar date1 = fechaCorrecta(dia, mes, anio); 
        Transaction salesReceiptTransaction =
                new AddSalesReceiptTransaction(date1, amount, eemployeId);
        salesReceiptTransaction.execute(null); 
		return MessageView.mostrarMensaje("Recibo de venta agregado");
	}
	

	public static String addTimeCardEmployee(int eemployeId, double horas, int dia, int mes, int anio) throws SQLException {		
		 Calendar date1 = fechaCorrecta(dia, mes, anio);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date1, horas,  eemployeId);
        timeCardTransaction.execute(null);
     
		return MessageView.mostrarMensaje("Timecard agregada");
	}


	private static Calendar fechaCorrecta(int dia, int mes, int anio) {
		Calendar date = new GregorianCalendar(anio,mes,dia);
        date.set(Calendar.MONTH, mes-1);
        Calendar date1 = new GregorianCalendar(anio, date.get(Calendar.MONTH),dia);
		return date1;
	}		

}
