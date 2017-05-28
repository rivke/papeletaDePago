package payrollcasestudy.Services.AddPaymentToBD;

import static java.util.Calendar.NOVEMBER;

import java.util.Calendar;
import java.util.GregorianCalendar;

import payrollcasestudy.boundaries.BDrepository;
import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.PaydayTransaction;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddSalesReceiptTransaction;
import payrollcasestudy.transactions.add.AddServiceChargeTransaction;
import payrollcasestudy.transactions.add.AddTimeCardTransaction;
import views.MessageView;

public class PaymentServices {
private static Repositoory memoryrepository;

static Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
static PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
static public int memberId = 0;
public PaymentServices(Repositoory repository) {
	this.memoryrepository=repository;
}


public static String addPaySalariedEmployee(int employeeId) {		
    Calendar payDate = new GregorianCalendar(2017, NOVEMBER, 24);
    PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
    paydayTransaction.execute(memoryrepository);
    PayCheck payCheck = paydayTransaction.getPaycheck(employeeId);         
	return ""+payCheck.getNetPay();
}

public static String payEmployee() {	   
   paydayTransaction.execute(memoryrepository);
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
	Employee employee = memoryrepository.getEmployee(eemployeId);
    memberId++;
    addMemberShip(2, employee);
    Calendar date1 = fechaCorrecta(dia, mes, anio);
     AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(memberId, date1, cargo, memoryrepository);
    addServiceChargeTransaction.execute(memoryrepository);	        
      
	return MessageView.mostrarMensaje("Servicio agregado");
}


private static void addMemberShip(double cargo, Employee employee) {
	UnionAffiliation unionAffiliation = new UnionAffiliation(memberId,cargo);
    employee.setUnionAffiliation(unionAffiliation);
    memoryrepository.addUnionMember(memberId, employee);
}



public static String addSalesReceiptEmployee(int eemployeId, double amount, int dia, int mes, int anio)  {		
    Calendar date1 = fechaCorrecta(dia, mes, anio); 
    Transaction salesReceiptTransaction =
            new AddSalesReceiptTransaction(date1, amount, eemployeId);
    salesReceiptTransaction.execute(memoryrepository); 
	return MessageView.mostrarMensaje("Recibo de venta agregado");
}


public static String addTimeCardEmployee(int eemployeId, double horas, int dia, int mes, int anio) {		
	 Calendar date1 = fechaCorrecta(dia, mes, anio);
    Transaction timeCardTransaction = new AddTimeCardTransaction(date1, horas,  eemployeId);
    timeCardTransaction.execute(memoryrepository);
 
	return MessageView.mostrarMensaje("Timecard agregada");
}


private static Calendar fechaCorrecta(int dia, int mes, int anio) {
	Calendar date = new GregorianCalendar(anio,mes,dia);
    date.set(Calendar.MONTH, mes-1);
    Calendar date1 = new GregorianCalendar(anio, date.get(Calendar.MONTH),dia);
	return date1;
}		


}
