package payrollcasestudy.entities;

import updatable.UpdatableMessage;

public class Message {
	private String mensaje;
	
	public Message(String mensaje){
		this.mensaje = mensaje;
	}
	
	public String update(UpdatableMessage updateMessage) {
		String result = "";
		result += updateMessage.inicioMensaje();
		result += updateMessage.updateMessage(mensaje);		
		result += updateMessage.finMensaje();
		return result;
		}
}
