package payrollcasestudy.entities.affiliations;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.ServiceCharge;
import static payrollcasestudy.entities.paymentclassifications.PaymentClassification.isInPayPeriod;
public class UnionAffiliation {
	private Map<Calendar, ServiceCharge> serviceCharges = new HashMap<Calendar, ServiceCharge>();
	private int memberId;
	private double dues;

	public UnionAffiliation(int memberId,double dues) {
		this.memberId=memberId;
		this.dues=dues;
		// TODO Auto-generated constructor stub
	}

	public static final UnionAffiliation NO_AFFILIATION = new UnionAffiliation(0,0);

	public ServiceCharge getServiceCharge(Calendar date) {
		// TODO Auto-generated method stub
		return serviceCharges.get(date);
	}

	public void addServiceChargeTransaction(Calendar payDate, double serviceCharge) {
		// TODO Auto-generated method stub
		serviceCharges.put(payDate, new ServiceCharge(payDate,serviceCharge));
		
	}

	public Double getDues() {
		// TODO Auto-generated method stub
		return dues;
	}

	public int getMemberId() {		
		return memberId;
	}

	public double calculateDeduction(PayCheck payCheck) {
		// TODO Auto-generated method stub				
	    double totalDeductions = 0;
	    totalDeductions += numberOfFridaysInPayPeriod(payCheck.getPayPeriodStart(), payCheck.getPayPeriodEnd()) * dues;
        for (ServiceCharge serviceCharge : serviceCharges.values()){
            if (isInPayPeriod(serviceCharge.getDate(), payCheck)){
                totalDeductions += serviceCharge.getAmount();
            }
        }
        return totalDeductions;	    
	}
	

	private double numberOfFridaysInPayPeriod(Calendar payPeriodStart, Calendar payPeriodEnd) {
		// TODO Auto-generated method stub
		int numberOfFridays = 0;
        while (!payPeriodStart.after(payPeriodEnd)){
            if (payPeriodStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                numberOfFridays++;
            }
            payPeriodStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return numberOfFridays;
	}

	

}
