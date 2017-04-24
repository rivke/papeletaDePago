package payrollcasestudy.entities.affiliations;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

import payrollcasestudy.entities.ServiceCharge;

public class UnionAffiliation {
	private Map<Calendar, ServiceCharge> serviceCharges = new HashMap<Calendar, ServiceCharge>();
	private int memberId;
	private double dues;

	public UnionAffiliation(int memberId,double dues) {
		this.memberId=memberId;
		this.dues=dues;
		// TODO Auto-generated constructor stub
	}

	public static final UnionAffiliation NO_AFFILIATION = null;

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

	

}
