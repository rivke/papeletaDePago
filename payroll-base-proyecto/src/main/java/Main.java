import static spark.Spark.*;

import java.io.StringWriter;

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
		
	public static void main(String[] args) {
		employeeId = 0;	
		final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        
        Spark.get("/regi", (request, response) -> {
       	 
            StringWriter writer = new StringWriter();
 
            try {
                Template formTemplate = configuration.getTemplate("registrar1.ftl");
 
                formTemplate.process(null, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
 
            return writer;
        });
        
        
				    
		
		get("/", (request, response) -> hola());		
		
		post("/hola", (request, response) -> responder_saludo(request.queryParams("nombre_saludo"), request.queryParams("nombre_saludo2")));
		
		get("/Arquitectura", (request, response) -> "Hola Arquitectura");
		
		//get("/registroEmpleados", (request, response) -> registrar());
		
		post("/registrar", (request, response) -> registrar_Empleado(request.queryParams("nombre"), request.queryParams("apellido"), request.queryParams("direccion"), Double.parseDouble(request.queryParams("tarifa_por_hora"))));
	}

	private static String responder_saludo(String nombre,String apellido) {
		System.out.println("----------RESPONDIENDO---------");
		return "Hola "+nombre+ " "+ apellido ;
	}

	private static String hola() {
		return "<html>"
				+ "<body>"
				+ "<form method='post' action='/hola'>" 
				+ "<label>Nombre:</label>"
				+ "<input type='text' name='nombre_saludo'><br>"
				+ "<label>Apellido:</label>"
				+ "<input type='text' name='nombre_saludo2'><br>"
				+ "<input type='submit' value='Saluda'>"
				+ "</body>"
				+ "</html>";
		
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
    		return mostrar_Mensaje_de_Registro( "Empleado con "+nombre+ " "+ apellido + "</br></br> Se ha sido registrado con exito");
        else
        	return mostrar_Mensaje_de_Registro("Error al registrar el empleado " + employee.getName());        
	}
	
	
	
	private static String mostrar_Mensaje_de_Registro(String mensaje){
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
	}
}
