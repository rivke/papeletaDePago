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
    
	public String queTipoDeEmpleado(Updatable updatable, String result, PaymentClassification paymentClassification) {
		if(paymentClassification instanceof SalariedClassification)
		{
			double salary;
			SalariedClassification h = (SalariedClassification) paymentClassification;		
			salary = h.getSalary();
			result += updatable.updateSalary(""+salary);
		//	return result;
			
		}
		return result;
	}
	
}
