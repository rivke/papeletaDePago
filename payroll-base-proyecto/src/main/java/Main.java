import static spark.Spark.*;

import java.awt.List;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.junit.Rule;

import controller.EmployeeController;
import controller.MessageController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentmethods.HoldMethod;
import payrollcasestudy.entities.paymentmethods.PaymentMethod;
import payrollcasestudy.entities.paymentschedule.PaymentSchedule;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;
import spark.ModelAndView;
import spark.Spark;
import updatable.Updatable;
import velocityy.VelocityTemplateEngine;
import views.EmpleadoView;


public class Main {
	
	public static int employeeId;
	public static String mensajee;
	public static String mensajee2="rebe";
	static EmployeeController employeeController;
	static VelocityTemplateEngine velocity;
	static MessageController messageController;
		
	public static void main(String[] args) {
		
		employeeId = 0;		
		
		get("/registrar", (request, response) -> {
		      return new ModelAndView(new HashMap(), "registrar1.vtl");
		    }, velocity.vel());		                
        
    	post("/registrarEmpleado", (request, response) -> employeeController.createEmployee(request.queryParams("id"), request.queryParams("nombre"), request.queryParams("direccion")));  	      	
    	post("/registrar_Empleado", (request, response) -> employeeController.addHourlyEmployee(request.queryParams("nombre"), request.queryParams("apellido"), request.queryParams("direccion"), Double.parseDouble(request.queryParams("tarifa_por_hora"))));
   	    
    	get("/verResultado", (request, response) -> {
			Map<String, Object> map = new HashMap<>();
            map.put("message", messageController.employeeCreatedSuccessfully());
		      return new ModelAndView(map, "viewMessage.vtl");
		    }, velocity.vel());
    	
		get("/mostrar", (request, response) -> {
			Map<String, Object> map = new HashMap<>();
            map.put("nombre", employeeController.showEmployee());
		      return new ModelAndView(map, "showEmp.vtl");
		    }, velocity.vel());
		
		get("/mostrarEmpleados", (request, response) -> {
			Map<String, Object> map = new HashMap<>();
            map.put("empleados", employeeController.showAllEmployees());
		      return new ModelAndView(map, "showAllEmployees.vtl");
		    }, velocity.vel());	
	}		
}
