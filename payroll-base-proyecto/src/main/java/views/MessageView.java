package views;

import updatable.UpdatableMessage;

public class MessageView implements UpdatableMessage{
	
	@Override
	public String inicioMensaje() {
		return "<div class='ui basic segment'>";
	}
	
	@Override
	public String updateMessage(String message) {		
		return "<h2 class='ui teal center aligned header'>" + message + "</h2>";
	}	

	@Override
	public String finMensaje() {
		return "</div>";
	}
	
}
