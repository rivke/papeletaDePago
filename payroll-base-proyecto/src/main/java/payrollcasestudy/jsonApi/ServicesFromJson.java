package payrollcasestudy.jsonApi;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import payrollcasestudy.transactions.add.AddTimeCardTransaction;


public class ServicesFromJson {
	public ArrayList<AddTimeCardTransaction> FromGson(String json) {
		Gson gson = new Gson();
		java.lang.reflect.Type typeListTransactions = new TypeToken<AddTimeCardTransaction>(){}.getType();
		ArrayList<AddTimeCardTransaction> listTransactions = gson.fromJson(json, typeListTransactions);
		return listTransactions;
	}
	

}
