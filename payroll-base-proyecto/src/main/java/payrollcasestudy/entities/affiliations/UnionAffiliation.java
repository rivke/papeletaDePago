package payrollcasestudy.entities.affiliations;

import java.util.Calendar;

import org.hamcrest.Matcher;

import payrollcasestudy.entities.ServiceCharge;

public class UnionAffiliation {
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
		return null;
	}

	

}
