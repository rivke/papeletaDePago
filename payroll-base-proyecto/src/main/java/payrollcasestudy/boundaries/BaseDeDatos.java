package payrollcasestudy.boundaries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDatos {
	
	
	
	
	    public Connection conexion = null;

	
	    @SuppressWarnings("finally")
		public Connection conectar(){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/papeletadepago","root","rebeca");
	            if (conexion != null) {
	                System.out.println("¡Conexión Exitosa!");
	            }
	        } catch (SQLException e) {
			
				System.err.println(e);
	        } finally {
	            return conexion;
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




