package payrollcasestudy.boundaries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDatos {
	
	
	
	
	    public Connection conexion = null;

	
	    public Connection conectar(){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/papeletadepago","root","rebeca");
	            if (conexion != null) {
	                System.out.println("¡Conexión Exitosa!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            return conexion;
	        }
	    }
	    
	    public String getStatusConnection() {
			try{
				conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/papeletadepago", "root","rebeca");
				return "Connection success";
			}catch (Exception e){
				return "Connection failed";
			}
			
		}
	    
	    public void close()
	    {
	    	
	    	try {
				conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

}




