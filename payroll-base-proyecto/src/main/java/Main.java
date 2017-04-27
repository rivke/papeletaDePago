import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		get("/", (request, response) -> hola());
		
		
		post("/hola", (request, response) -> responder_saludo(request.queryParams("nombre_saludo"), request.queryParams("nombre_saludo2")));
		
		get("/Arquitectura", (request, response) -> "Hola Arquitectura");
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
}
