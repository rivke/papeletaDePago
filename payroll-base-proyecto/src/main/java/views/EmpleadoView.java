package views;

import updatable.Updatable;

public class EmpleadoView implements Updatable {
	
	public String inicioEmpleado() {
		return "<div>";

		}

		public String updateNombre(String nombre) {
		return "<div>"
		+ "<h3 class='ui teal centered header'>Nombre: </h3>"
		+ "<h2 class='ui teal centered header'>"+nombre+"</h2>"
		+"</div>";
		}

		public String updateAddress(String direccion) {
		return "<div>"
		+ "<h3 class='ui teal centered header'>Direccion: </h3>"
		+ "<h2 class='ui teal centered header'>"+direccion+"</h2>"
		+"</div>";
		}


		public String finEmpleado() {
		return "</div>";	
		} 

}
