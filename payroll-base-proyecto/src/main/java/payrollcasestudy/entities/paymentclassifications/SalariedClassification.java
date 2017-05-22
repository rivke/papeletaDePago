package payrollcasestudy.entities.paymentclassifications;

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

	public double update2() {
		// TODO Auto-generated method stub
		return salary;
	}

	

	@Override
	public String updateQuery() {

	    return "INSERT INTO salaried_employee VALUES (?,?,?,?)";
		
	}
	
}
