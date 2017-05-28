package payrollcasestudy.CheckPayment;

public class CheckPayment {
	private int employeeId;
	private String name;
	private String netPay;
	public CheckPayment(int employeeId, String name,String payCheck) {
		this.employeeId = employeeId;
		this.name = name;
		this.netPay = payCheck;
		
	}
	
}
