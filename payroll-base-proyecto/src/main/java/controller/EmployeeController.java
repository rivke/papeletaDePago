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
import java.util.Set;

import org.junit.Rule;

import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.Message;
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
import updatable.UpdatableMessage;
import views.EmpleadoView;
import views.MessageView;


public class EmployeeController {
	static PayrollDatabase database=PayrollDatabase.globalPayrollDatabase;
	
	static public int employeeId=0;
	
	static public int memberId = 86;
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
		Employee employee = database.getEmployee(employeeId);
        String mensajee;
		if(employee.getName() == nombreCompleto)        	
    		return mensajee="El Empleado "+nombre+ " "+ apellido + "</br> Se ha sido registrado con exito";
        else
        	return mensajee="Error al registrar el empleado " + employee.getName();
	}
	
	public static String showEmployee()
	{
		Updatable updatable = new EmpleadoView();
		Set<Integer> employeeIds=database.getAllEmployeeIds();
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

	public static String addServiceChargeEmployee(int eemployeId, double cargo) {
		// TODO Auto-generated method stub
		//AddHourlyEmployeeTransaction
		Employee employee = database.getEmployee(eemployeId);
	        
	        int memberId = 86; //Maxwell Smart
	        UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
	        employee.setUnionAffiliation(unionAffiliation);
	        database.addUnionMember(memberId, employee);
	        
	        Calendar date = new GregorianCalendar(2001, 11, 01);
	        AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date, 12.95);
	        addServiceChargeTransaction.execute();
	        
	        //ServiceCharge serviceCharge = unionAffiliation.getServiceCharge(date);
	       
	      //  assertThat(serviceCharge.getAmount(), is(closeTo(12.95, FLOAT_ACCURACY)));
		return "Servicio agregado";
	}

	public static String addSalesReceiptEmployee(int eemployeId, double amount) {
		// TODO Auto-generated method stub
		
        //comision
        Calendar date = new GregorianCalendar(2001, NOVEMBER, 31);
        Transaction salesReceiptTransaction =
                new AddSalesReceiptTransaction(date, amount, eemployeId);
        salesReceiptTransaction.execute();

        //Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
        
        
        
		return "Recibo de venta agregado";
	}

	public static String addTimeCardEmployee(int eemployeId, double horas) {
		int employeeId = 2;
		//hora
      

        Calendar date = new GregorianCalendar(2001,10,31);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date, 8.0,  eemployeId);
        timeCardTransaction.execute();

       // Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee( eemployeId);
     
		return "Timecard agregada";
	}		

}
