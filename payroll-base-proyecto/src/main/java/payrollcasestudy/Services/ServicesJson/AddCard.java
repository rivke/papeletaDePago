package payrollcasestudy.Services.ServicesJson;
import java.util.ArrayList;

import payrollcasestudy.boundaries.Repositoory;
import payrollcasestudy.jsonApi.ServicesFromJson;
import payrollcasestudy.transactions.add.AddTimeCardTransaction;



public class AddCard implements ServicesJson{
	@Override
	public void addServicesFromJson(Repositoory repository, String json) {
		ServicesFromJson service = new ServicesFromJson();
		ArrayList<AddTimeCardTransaction> list = service.FromGson(json);
		for(AddTimeCardTransaction addTimeCard : list){
			addTimeCard.execute(repository);
		}	
	}

}
