package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.SalesReceipt;
import updatable.Updatable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CommissionedPaymentClassification extends PaymentClassification {
	public  PaymentType category;
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


	
	@Override
	public String updateQuery() {
		
		return  "INSERT INTO comision VALUES (?,?,?,?,?)";
	}

	@Override
	public ArrayList<Double> updatePayment() {
		ArrayList list = new ArrayList<Double>();
		list.add(monthlySalary);
		list.add(commissionRate);

		return list;
	}

	public PaymentType typeOfPayment() {
		// TODO Auto-generated method stub
		return category.Comision;
	}
	
    
}
