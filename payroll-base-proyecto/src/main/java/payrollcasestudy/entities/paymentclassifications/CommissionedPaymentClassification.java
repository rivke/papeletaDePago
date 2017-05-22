package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.SalesReceipt;
import updatable.Updatable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CommissionedPaymentClassification extends PaymentClassification {
    private Map<Calendar, SalesReceipt> salesReceiptMap = new HashMap<Calendar, SalesReceipt>();
    private double commissionRate;
    private double monthlySalary;

    public CommissionedPaymentClassification(double monthlySalary, double commissionRate) {
        this.commissionRate = commissionRate;
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public SalesReceipt getSalesReceipt(Calendar date) {
        return salesReceiptMap.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceiptMap.put(salesReceipt.getDate(), salesReceipt);
    }

    @Override
    public double calculatePay(PayCheck payCheck) {
        double  totalPay = monthlySalary;
        for (SalesReceipt receipt: salesReceiptMap.values()){
            if (isInPayPeriod(receipt.getDate(), payCheck)){
                totalPay += receipt.getAmount() * commissionRate;
            }
        }
        return totalPay;
    }
    
    public String update(Updatable updatable) {
    		String result="";
    		result += updatable.updateMontlySalary(""+monthlySalary);
    		result += updatable.updateCommission(""+commissionRate);
    		return	result;
    		
			
	
		
	}

	public double update2() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void update3(double a) {
		// TODO Auto-generated method stub
		a=1;
		
	}

	@Override
	public String updateQuery() {
		// TODO Auto-generated method stub
		return "a";
	}
	
    
}
