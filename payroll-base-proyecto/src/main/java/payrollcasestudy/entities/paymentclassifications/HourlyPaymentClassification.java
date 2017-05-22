package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.TimeCard;
import updatable.Updatable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HourlyPaymentClassification extends PaymentClassification{

    private Map<Calendar, TimeCard> timeCardMap = new HashMap<Calendar, TimeCard>();
    private double hourlyRate;

    public HourlyPaymentClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public HourlyPaymentClassification() {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public TimeCard getTimeCard(Calendar date) {
        return timeCardMap.get(date);
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCardMap.put(timeCard.getDate() ,timeCard);
    }

    @Override
    public double calculatePay(PayCheck payCheck) {
        double totalPay = 0;
        for(TimeCard timeCard: timeCardMap.values()){
            if(isInPayPeriod(timeCard.getDate(), payCheck)){
                totalPay += calculatePayForTimeCard(timeCard);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        double hours = timeCard.getHours();
        double overtime = Math.max(0.0, hours-8.0);
        double straightTime = hours - overtime;
        return straightTime * hourlyRate + overtime * hourlyRate * 1.5;
    }

	public String update(Updatable updatable) {
		
			
		return	 updatable.updateHourlyRate(""+hourlyRate);
			
			
		
		
	}

	public double update2() {
		// TODO Auto-generated method stub
		return hourlyRate;
	}

	@Override
	public void update3(double a) {
		// TODO Auto-generated method stub
		hourlyRate=a;
		
	}

	public String updateQuery() {
		
	    return  "INSERT INTO hourly_employees VALUES (?,?,?,?)";
		
	}
	
	
	
}
