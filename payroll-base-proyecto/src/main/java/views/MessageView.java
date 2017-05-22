package views;

public class MessageView {
	static public String inicioPagina(){
		return "<html>"
	+ "<head>"
	+	"<link rel='stylesheet' type='text/css' href='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.css' media='screen' title='no title' charset='utf-8'>" 		  		
	+	"<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>"
	+	"<script src='https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.4/semantic.min.js'></script>"
	+	"<meta charset='utf-8'>"
	+	"<meta name='viewport' content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>"
	+	"<title>Resultado</title>"    
	+ "</head>"
	+ "<body>"	
	+ "<div class='ui basic padded segment'>"
	+ "<div class='ui container'>";
	}
	
	static public String finPagina(){
		return "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
	}
	
	static public String getVolverAlMenuButton() {
		return "<div class='ui segment center aligned basic'>"
				+ "<form action='/'><input class='ui grey center aligned button' type='submit' value='Volver a la Pagina Principal' /></form>"
				+ "</div>";
	}
	
	static public String mostrarMensaje(String mensaje){
		return inicioPagina() + "<div class='ui basic center aligned teal segment'>" + mensaje + "</div>" + getVolverAlMenuButton() + finPagina();
	}	
}
