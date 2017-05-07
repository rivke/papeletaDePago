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



public class EmployeeController {
	static PayrollDatabase database=PayrollDatabase.globalPayrollDatabase;
	static public int employeeId=0;
	static public int memberId = 0;
	static String nombreCompleto = "";
	
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
        String mensajee;
		if(employee.getName() == nombreCompleto)        	
    		return mensajee="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito";
        else
        	return mensajee="Error al registrar el empleado " + employee.getName();
	}
	
	
	
     public static String showEmployee(int id){
		
		
		Updatable updatable = new EmpleadoView();		
		String Employees="";
		Employee empleados;
		empleados = database.getEmployee(id);
		
			Employees = Employees + empleados.update(updatable);
		
		return Employees;	
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
	

	
	public static String addComisionEmployee(String nombre, String apellido, String direccion, double salarioMensual,
			double comision) {
		System.out.println("----------REGISTRANDO EMPLEADO POR COMISION---------");			
		employeeId++;
		String nombreCompleto = "";
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
	   Calendar payDate = new GregorianCalendar(2001, NOVEMBER, 30);
       PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
       paydayTransaction.execute();
		Set<Integer> employeeIds=database.getAllEmployeeIds();
		ArrayList<Integer> employeeIdLista = new ArrayList<>(employeeIds);
		String allPay="";
		for(int ind=1;ind<=employeeIdLista.size();ind++)
		{
			
			 PayCheck payCheck = paydayTransaction.getPaycheck(ind);
			allPay=allPay+payCheck.getNetPay()+" --- ";			
		}
		return allPay;		
	}
		
   
   
   public static String payEmployeeSeulement(int id) {
	   Calendar payDate = new GregorianCalendar(2001, NOVEMBER, 30);
       PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
       paydayTransaction.execute();
		
		String Pay="";
		
			
			 PayCheck payCheck = paydayTransaction.getPaycheck(id);
			Pay="Pago total: "+payCheck.getNetPay()+"  ";			
	
		return Pay;		
	}
   
   
	public static String addServiceChargeEmployee(int eemployeId, double cargo) {
		
		Employee employee = database.getEmployee(eemployeId);
	        
		memberId++;
	        UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
	        employee.setUnionAffiliation(unionAffiliation);
	        database.addUnionMember(memberId, employee);
	        
	        Calendar date = new GregorianCalendar(2001, 11, 01);
	        AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date, 12.95);
	        addServiceChargeTransaction.execute();	        
	      
		return "Servicio agregado";
	}

	
	
	public static String addSalesReceiptEmployee(int eemployeId, double amount) {		
        
        Calendar date = new GregorianCalendar(2001,NOVEMBER, 30);
        Transaction salesReceiptTransaction =
                new AddSalesReceiptTransaction(date, amount, eemployeId);
        salesReceiptTransaction.execute(); 
		return "Recibo de venta agregado";
	}
	

	public static String addTimeCardEmployee(int eemployeId, double horas) {		
		
        Calendar date = new GregorianCalendar(2001,NOVEMBER,30);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date, horas,  eemployeId);
        timeCardTransaction.execute();
     
		return "Timecard agregada";
	}		

}
