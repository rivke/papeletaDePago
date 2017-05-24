package payrollcasestudy.entities.paymentclassifications;

import java.util.ArrayList;

import payrollcasestudy.entities.PayCheck;
import updatable.Updatable;

public class SalariedClassification extends PaymentClassification {
    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double calculatePay(PayCheck payCheck) {
        return salary;
    }
    
	public String update(Updatable updatable) {
		
			
		return updatable.updateSalary(""+salary);
		
		
		
	}


	

	@Override
	public String updateQuery() {

	    return "INSERT INTO salaried_employee VALUES (?,?,?,?)";
		
	}

	@Override
	public ArrayList<Double> updatePayment() {
		ArrayList list = new ArrayList<Double>();
		list.add(salary);
		return list;
	}
	
}
