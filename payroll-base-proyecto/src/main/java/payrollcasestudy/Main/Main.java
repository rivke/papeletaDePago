package payrollcasestudy.Main;

import payrollcasestudy.Controller.routeController;
import payrollcasestudy.Services.AddPaymentToBD.PaymentServices;
import payrollcasestudy.Services.EmployeeServices.EmployeeServices;
import payrollcasestudy.boundaries.BDrepository;
import payrollcasestudy.boundaries.Repositoory;

public class Main {

	public static void main(String[] args) {
		Repositoory repository = new BDrepository();
		EmployeeServices employeeService = new EmployeeServices(repository);
		PaymentServices paymentServices=new PaymentServices(repository.getRepo());
		
		routeController routes = new routeController(employeeService,paymentServices);
		routes.routeControl();
	}

}
