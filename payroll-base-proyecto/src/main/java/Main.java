import static spark.Spark.*;

import org.junit.Rule;

import payrollcasestudy.DatabaseResource;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentmethods.HoldMethod;
import payrollcasestudy.entities.paymentmethods.PaymentMethod;
import payrollcasestudy.entities.paymentschedule.PaymentSchedule;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.AddHourlyEmployeeTransaction;


public class Main {
	
	public static int employeeId;
		
	public static void main(String[] args) {
		employeeId = 0;			    
		
		//get("/", (request, response) -> hola());		
		
		//post("/hola", (request, response) -> responder_saludo(request.queryParams("nombre_saludo"), request.queryParams("nombre_saludo2")));
		
		get("/Arquitectura", (request, response) -> "Hola Arquitectura");
		
		get("/", (request, response) -> registrar());
		
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
				+ "<input type='submit' value='Saluda'"
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
    		return "Empleado con Nombre: "+nombre+ " Apellido: "+ apellido + " ha sigo registrado con exito";
        else
        	return nombreCompleto +" Error al registrar el empleado " + employee.getName();        
	}
	
	private static String registrar(){
		return "<html>"
				+ "<head>"
				+ "<link rel='stylesheet' type='text/css' href='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.css' media='screen' title='no title' charset='utf-8'>"		  		  
				+ "<script src='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.js'></script>"
				+ "<meta charset='utf-8'>"
				+ "<meta name='viewport' content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>"
				+ "<title>Registro de empleado por horas</title>"		    
				+ "</head>"
				+ "<body>"
				+ "<div class='ui container'>"
				+ "<div class='ui basic padded segment'>"
				+ "<h1 class='ui blue centered header'>Registro de Empleado por Horas</h1>"
				+ "</div>"
				+ "<form class='ui form' method='post' action='/registrar'>" 
				+ "<label>Nombre:</label>"
				+ "<input type='text' name='nombre'><br>"
				+ "<label>Apellido:</label>"
				+ "<input type='text' name='apellido'><br>"
				+ "<label>Direccion:</label>"
				+ "<input type='text' name='direccion'><br>"
				+ "<label>Tarifa por hora:</label>"
				+ "<input type='number' name='tarifa_por_hora'><br>"
				+ "</form>"
				+ "<div class='ui segment center aligned  basic'>"
				+ "<button class='ui button' type='submit'> Registrar Empleado</button>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
	
	}
}
