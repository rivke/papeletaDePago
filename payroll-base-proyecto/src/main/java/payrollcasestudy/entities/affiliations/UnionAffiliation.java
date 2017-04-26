package payrollcasestudy.entities.affiliations;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.ServiceCharge;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;


public class UnionAffiliation {
	private Map<Calendar, ServiceCharge> serviceCharges = new HashMap<Calendar, ServiceCharge>();
	private int memberId;
	private double dues;
	public static final UnionAffiliation NO_AFFILIATION = new UnionAffiliation(0,0);
	private static PaymentClassification paymentClassification;
	
	public UnionAffiliation(int memberId,double dues) {
		this.memberId=memberId;
		this.dues=dues;
		// TODO Auto-generated constructor stub
	}

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

	public double calculateDeductions(PayCheck payCheck){
		double totalDeductions = 0;
        totalDeductions = dues * quantityOfFridays(payCheck.getPayPeriodStart(), payCheck.getPayPeriodEnd());
        for (ServiceCharge serviceCharge : serviceCharges.values()){
            if (paymentClassification.isInPayPeriod(serviceCharge.getDate(), payCheck)){
                totalDeductions += serviceCharge.getAmount();
            }
        }
        return totalDeductions;
		
	}

	private int quantityOfFridays(Calendar periodForPayStart, Calendar periodForPayEnd) {
        int numberOfFridays = 0;
        while (periodForPayStart != periodForPayEnd){
            if (periodForPayStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                numberOfFridays++;
            }
            periodForPayStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return numberOfFridays;
    }

	

}
