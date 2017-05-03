package views;

import updatable.Updatable;

public class EmpleadoView implements Updatable {
	
		public String inicioEmpleado() {
			return "<div class='ui basic segment'>" + "<div class='ui horizontal list'>";
			}
	
		public String updateId(String id) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>ID: </div>"
			    + id
			    + "</div>"
				+ "</div>";		
		}
	
		public String updateName(String nombre) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Nombre: </div>"
			    + nombre
			    + "</div>"
				+ "</div>";		
		}

		public String updateAddress(String direccion) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Direccion: </div>"
			    + direccion
			    + "</div>"
			    + "</div>";		
		}
		
		public String updateHourlyRate(String tarifaPorHora) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Tarifa por Hora: </div>"
			    + tarifaPorHora
			    + "</div>"
			    + "</div>";			
		}
		
		public String updateSalary(String salario) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Salario Fijo: </div>"
			    + salario
			    + "</div>"
			    + "</div>";			
		}
		
		public String updateMontlySalary(String salarioMensual) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Salario Mensual: </div>"
			    + salarioMensual
			    + "</div>"
			    + "</div>";			
		}
		public String updateCommission(String comision) {
			return "<div class='item'>"
				+ "<div class='content'>"
			    + "<div class='header'>Comision: </div>"
			    + comision
			    + "</div>"
			    + "</div>";			
		}

		public String finEmpleado() {
		return "</div>" 
				+ "</div>";	
		} 

}
