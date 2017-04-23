package payrollcasestudy.entities.paymentschedule;

import java.util.Calendar;

public class WeeklyPaymentSchedule implements PaymentSchedule {
    public boolean isPayDate(Calendar payDate) {
        return payDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    public Calendar getPayPeriodStartDate(Calendar payDate) {
        Calendar monday = (Calendar) payDate.clone();
        monday.roll(Calendar.DAY_OF_MONTH, -5);
        return monday;
    }
}
