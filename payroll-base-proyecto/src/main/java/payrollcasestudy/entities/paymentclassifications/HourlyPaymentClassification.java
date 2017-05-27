package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.TimeCard;
import updatable.Updatable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HourlyPaymentClassification extends PaymentClassification{

	
	public  PaymentType category;
   // private static final PaymentType Hourly = null;
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

	



	public String updateQuery() {
		
	    return  "INSERT INTO hourly_employees VALUES (?,?,?,?)";
		
	}

	@Override
	public ArrayList<Double> updatePayment() {
		ArrayList list = new ArrayList<Double>();
		list.add(hourlyRate);
		return list;
	}

	public PaymentType typeOfPayment() {
		// TODO Auto-generated method stub
		return category.Hourly;
	}
	
	
	
}
