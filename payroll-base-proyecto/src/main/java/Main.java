import static spark.Spark.*;

import java.awt.List;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.junit.Rule;

import controller.EmployeeController;

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
	
	static Map<String, Object> map = new HashMap<>();
	static Updatable updatable = new EmpleadoView();		
	
		
	public static void main(String[] args) {
		
		employeeId = 0;		
		
		get("/", (request, response) -> {
		      return new ModelAndView(new HashMap(), "mainPage.vtl");
		    }, velocity.vel());
		
		get("/registrar", (request, response) -> {
		      return new ModelAndView(new HashMap(), "registrarEmpleado.vtl");
		    }, velocity.vel());		                
            	  	      	
    	post("/registrar_Empleado_por_Hora", (request, response) -> employeeController.addHourlyEmployee(request.queryParams("nombre"), request.queryParams("apellido"), request.queryParams("direccion"), Double.parseDouble(request.queryParams("tarifa_por_hora"))));
    	post("/registrar_Empleado_Asalariado", (request, response) -> employeeController.addSalariedEmployee(request.queryParams("nombre2"), request.queryParams("apellido2"), request.queryParams("direccion2"), Double.parseDouble(request.queryParams("salario"))));
    	post("/registrar_Empleado_Comision", (request, response) -> employeeController.addComisionEmployee(request.queryParams("nombre3"), request.queryParams("apellido3"), request.queryParams("direccion3"), Double.parseDouble(request.queryParams("salarioMensual")), Double.parseDouble(request.queryParams("comision"))));
   	    
    	
    	get("/registrarServicio", (request, response) -> {
		      return new ModelAndView(new HashMap(), "registrarServicio.vtl");
		    }, velocity.vel());	
    	
    	
    	
    	post("/agregarCargoPorServicio", (request, response) -> employeeController.addServiceChargeEmployee(Integer.parseInt(request.queryParams("id")), Double.parseDouble(request.queryParams("cargo"))));
    	post("/agregarReciboVenta", (request, response) -> employeeController.addSalesReceiptEmployee(Integer.parseInt(request.queryParams("id2")), Double.parseDouble(request.queryParams("amount"))));
   	    post("/agregarTimeCard", (request, response) -> employeeController.addTimeCardEmployee(Integer.parseInt(request.queryParams("id3")), Double.parseDouble(request.queryParams("hours"))));
   	    
    	
    	get("/pago", (request, response) -> {
			Map<String, Object> map = new HashMap<>();
            map.put("pagos", employeeController.payEmployee());
		      return new ModelAndView(map, "pago.vtl");
		    }, velocity.vel());
    	
    	get("/detalle/:id", (request, response) -> {
    		
    		map.put("empleado",employeeController.showEmployee(Integer.parseInt(request.params(":id"))));
    		map.put("pago",employeeController.payEmployeeSeulement(Integer.parseInt(request.params(":id"))));
    		
		      return new ModelAndView(map, "mostrarUno.vtl");
		    }, velocity.vel());	
    	
    	
    	get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));
          
    	
	
		
		get("/mostrarEmpleados", (request, response) -> {
			
			map.put("empleados",PayrollDatabase.getAllEmployees());
			map.put("updatable",updatable);
		      return new ModelAndView(map, "showAllEmployees.vtl");
		    }, velocity.vel());	
	}		
}
