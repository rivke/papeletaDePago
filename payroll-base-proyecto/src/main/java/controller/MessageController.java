package controller;

import java.util.ArrayList;
import java.util.Set;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.Message;
import updatable.Updatable;
import updatable.UpdatableMessage;
import views.EmpleadoView;
import views.MessageView;

public class MessageController {
	
	public static String showMessage(){		
		String resp="";
		UpdatableMessage updatableMessage = new MessageView();
		Message message = new Message("Hola este es un mensaje de prueba");
		resp += message.update(updatableMessage);
		return resp;
	}
	
	public static String employeeCreatedSuccessfully(){
		String resp="";
		UpdatableMessage updatableMessage = new MessageView();
		Message message = new Message("Usuario creado Satisfactoriamente");
		resp += message.update(updatableMessage);
		return resp;
	}

}
