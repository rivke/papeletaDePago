package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import updatable.Updatable;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class PaymentClassification {
    public abstract double calculatePay(PayCheck payCheck);
    
    public abstract String update(Updatable updatable );
    public abstract String updateQuery();
    public abstract ArrayList<Double> updatePayment();

    
    public static boolean isInPayPeriod(Calendar date, PayCheck payCheck) {
        Calendar payPeriodStart = payCheck.getPayPeriodStart();
        Calendar payPeriodEnd = payCheck.getPayPeriodEnd();
        return date.equals(payPeriodEnd) || date.equals(payPeriodStart) ||
                (date.after(payPeriodStart) && date.before(payPeriodEnd));
    }
}
