import static spark.Spark.*;

import java.awt.List;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.junit.Rule;

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
import spark.Spark;


public class Main {
	
	public static int employeeId;
	public static String mensajee;
		
	public static void main(String[] args) {
		employeeId = 0;	
		final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        
        Spark.get("/registrarEmpleadoHora", (request, response) -> {
       	 
            StringWriter writer = new StringWriter();
 
            try {
                Template formTemplate = configuration.getTemplate("registrar1.ftl");
 
                formTemplate.process(null, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
 
            return writer;
        });
        
    	post("/create", (request, response) -> createEmployee(request.queryParams("id"), request.queryParams("nombre"), request.queryParams("direccion")));
    	
        
		
		post("/registrar", (request, response) -> registrar_Empleado(request.queryParams("nombre"), request.queryParams("apellido"), request.queryParams("direccion"), Double.parseDouble(request.queryParams("tarifa_por_hora"))));
	
		get("/mostrar2", (request, response) ->showEmployee());
		
	
		Spark.post("/mostrar", (request, response) -> {
	       	 
            StringWriter writer = new StringWriter();
 
            try {
            	
                Template formTemplate = configuration.getTemplate("result.ftl");
 
                formTemplate.process(null, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
 
            return writer;
        });
        

	
	}

	


	
	private static String registrar_Empleado(String nombre, String apellido, String direccion, double tarifa_por_hora){
		System.out.println("----------REGISTRANDO EMPLEADO ASALARIADO POR HORA---------");		
		
		employeeId++;
		String nombreCompleto = "";
		nombreCompleto = nombre + " " + apellido;
        Transaction addEmployeeTransaction =
                new AddHourlyEmployeeTransaction(employeeId, nombreCompleto, direccion, tarifa_por_hora);
        addEmployeeTransaction.execute();
        Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
        if(employee.getName() == nombreCompleto)
        	
    		return mensajee="Empleado con "+nombre+ " "+ apellido + "</br></br> Se ha sido registrado con exito";
        else
        	return mensajee="Error al registrar el empleado " + employee.getName();        
	}
	
	public static String showEmployee()
	{
		Set<Integer> employeeIds=PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		ArrayList<Integer> employeeIdLista = new ArrayList<>(employeeIds);
		Employee employee;
		String allEmployees="";
		for(int ind=0;ind<employeeIdLista.size();ind++)
		{
			employee=PayrollDatabase.globalPayrollDatabase.getEmployee(employeeIdLista.get(ind));
			allEmployees=allEmployees+employee.getName().toString()+" Vive en... "+employee.getAddress()+"<br>";
		}
		return allEmployees;
		
	
	}
	public static String createEmployee(String employeeId, String nombre,String direccion){
		int employeeIdInt=Integer.parseInt(employeeId);
		Employee employee = new Employee(employeeIdInt,nombre,direccion);
		PayrollDatabase.globalPayrollDatabase.addEmployee(employeeIdInt,employee);
		return "exito";
		
		
	}
	
	
	
	
	/*private static String mostrar_Mensaje_de_Registro(String mensaje){
		return "<html>"
				+ "<head>"
				+ "<style>"
				+ "body{ font-family: 'Oswald', 'Helvetica Neue', Arial, Helvetica, sans-serif; }"
				+ "</style>"
				+ "<link rel='stylesheet' type='text/css' href='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.css' media='screen' title='no title' charset='utf-8'>"		  		  
				+ "<script src='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.js'></script>"
				+ "<meta charset='utf-8'>"
				+ "<meta name='viewport' content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>"
				+ "<title>Registro de empleado por horas</title>"		    
				+ "</head>"
				+ "<body>"
				+ "<div class='ui container' style='padding-top: 30px;'>"
				+ "<div class='ui blue segment' >"
				+ "<h3 class='ui teal centered header'>" + mensaje + "</h3>"
				+ "</div>"
				+ "</div>"
				+ "<div class='ui segment center aligned basic'>"
				+ "<form action='/regi'>"
				+ "<input class='ui orange button' type='submit' value='Volver a Registro de Empleados' />"
				+ "</form>"
				+ "</div>"
				+ "</body>"
				+ "</html>";	
	}*/
}
