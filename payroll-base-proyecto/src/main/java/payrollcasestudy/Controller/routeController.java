package payrollcasestudy.Controller;
import static spark.Spark.*;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import spark.ModelAndView;

import updatable.Updatable;
import velocityy.VelocityTemplateEngine;
import views.EmpleadoView;
import payrollcasestudy.Services.EmployeeServices.EmployeeServices;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.jsonApi.JsonUtil;


public class routeController {
	
	public static int employeeId;
	private EmployeeServices employeeService;
	static VelocityTemplateEngine velocity;
	static Map<String, Object> map = new HashMap<>();
	static Updatable updatable = new EmpleadoView();		
	
	public routeController(EmployeeServices employeeController) {
		this.employeeService = employeeController;
	}

		
	public void routeControl() {
		map.put("updatable",updatable);
		employeeId = 0;		
		
		get("/", (request, response) -> {
		      return new ModelAndView(new HashMap(), "mainPage.vtl");
		      }, velocity.vel());
		
		get("/registrar", (request, response) -> {
		      return new ModelAndView(new HashMap(), "registrarEmpleado.vtl");
		      }, velocity.vel());		                
            	  	      	
    	post("/registrar_Empleado_por_Hora", (request, response) -> employeeService.addHourlyEmployee(request.queryParams("nombre"), request.queryParams("apellido"), request.queryParams("direccion"), Double.parseDouble(request.queryParams("tarifa_por_hora"))));
    	
    	post("/registrar_Empleado_Asalariado", (request, response) -> employeeService.addSalariedEmployee(request.queryParams("nombre2"), request.queryParams("apellido2"), request.queryParams("direccion2"), Double.parseDouble(request.queryParams("salario"))));
    	
    	post("/registrar_Empleado_Comision", (request, response) -> employeeService.addComisionEmployee(request.queryParams("nombre3"), request.queryParams("apellido3"), request.queryParams("direccion3"), Double.parseDouble(request.queryParams("salarioMensual")), Double.parseDouble(request.queryParams("comision"))));
   	    
    	get("/registrarServicio", (request, response) -> {  
    		map.put("empleados",employeeService.getAllEmployees());
		      return new ModelAndView(map, "registrarServicio.vtl");
		    }, velocity.vel());    	
    	
    	post("/agregarCargoPorServicio", (request, response) -> employeeService.addServiceChargeEmployee(Integer.parseInt(request.queryParams("id")), Double.parseDouble(request.queryParams("cargo")),Integer.parseInt(request.queryParams("dia_pago1")),Integer.parseInt(request.queryParams("mes_pago1")),Integer.parseInt(request.queryParams("anio_pago1"))));
    	
    	post("/agregarReciboVenta", (request, response) -> employeeService.addSalesReceiptEmployee(Integer.parseInt(request.queryParams("id2")), Double.parseDouble(request.queryParams("amount")),Integer.parseInt(request.queryParams("dia_pago2")),Integer.parseInt(request.queryParams("mes_pago2")),Integer.parseInt(request.queryParams("anio_pago2"))));
   	    
    	post("/agregarTimeCard", (request, response) -> employeeService.addTimeCardEmployee(Integer.parseInt(request.queryParams("id3")), Double.parseDouble(request.queryParams("hours")),Integer.parseInt(request.queryParams("dia_pago3")),Integer.parseInt(request.queryParams("mes_pago3")),Integer.parseInt(request.queryParams("anio_pago3"))));
   	   
   	    get("/pago", (request, response) -> employeeService.payEmployee());	       	 
   	    
   	    get("/detalle/:id", (request, response) -> {
    		map.put("empleado",employeeService.showEmployee(Integer.parseInt(request.params(":id"))));        			
    		 return new ModelAndView(map, "mostrarUno.vtl");
		    }, velocity.vel());	
    	
    	
		get("/mostrarEmpleados", (request, response) -> {
			map.put("empleados",employeeService.getAllEmployees());
			return new ModelAndView(map, "showAllEmployees.vtl");
		    }, velocity.vel());	
		
		get("/api/AllEmployees", (req, res) -> employeeService.getAllEmployees(), JsonUtil.json());
		
		

		
		
	}		
}
