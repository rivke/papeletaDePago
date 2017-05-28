package payrollcasestudy.Main;

import payrollcasestudy.Controller.routeController;
import payrollcasestudy.Services.EmployeeServices.EmployeeServices;
import payrollcasestudy.boundaries.BDrepository;
import payrollcasestudy.boundaries.Repositoory;

public class Main {

	public static void main(String[] args) {
		Repositoory repository = new BDrepository();
		EmployeeServices employeeService = new EmployeeServices(repository);
		routeController routes = new routeController(employeeService);
		routes.routeControl();
	}

}
