package payrollcasestudy.Main;

import payrollcasestudy.Controller.routeController;
import payrollcasestudy.Services.AddPaymentToBD.PaymentServices;
import payrollcasestudy.Services.EmployeeServices.EmployeeServices;
import payrollcasestudy.boundaries.BDrepository;
import payrollcasestudy.boundaries.MemoryRepository;
import payrollcasestudy.boundaries.Repositoory;

public class Main {

	public static void main(String[] args) {
		Repositoory repository = new BDrepository();
		Repositoory repository2 = new MemoryRepository();
		EmployeeServices employeeService = new EmployeeServices(repository);

		PaymentServices paymentServices=new PaymentServices(repository2, repository.getRepo());
		routeController routes = new routeController(employeeService,paymentServices);
		routes.routeControl();
	}

}
