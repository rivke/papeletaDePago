package payrollcasestudy.boundaries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.sql.ResultSet;

import payrollcasestudy.entities.Employee;

public class BDrepository implements Repositoory{
	private static final Map<String, Class<?>> Employee = null;
	private static Connection con;
	  private static BDrepository INSTANCE = null;
	    public Connection conexion = null;

	  
	  
	public static Repositoory getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

	private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new BDrepository();
            performConnection();
        }
    }
	
	public static void performConnection() {
		String host = "localhost";
		String user = "root";
		String pass = "rebeca";
		String dtbs = "papeletadepago";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String newConnectionURL = "jdbc:mysql://" + host + "/" + dtbs
					+ "?" + "user=" + user + "&password=" + pass;
					con = DriverManager.getConnection(newConnectionURL);
		}catch (Exception e) {
			System.out.println("Error en l'obertura de la connexió.");
		}
		
	}
	@Override
	public Employee getEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEmployee(int employeeId, Employee employee) throws SQLException {
		
	    PreparedStatement pstInsertarCuenta;
	    conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/papeletadepago","root","rebeca");
	   
	 
	    String sqlNuevaCuenta = "INSERT INTO employees VALUES (?,?,?)";
	    pstInsertarCuenta = conexion.prepareStatement(sqlNuevaCuenta); 
	    pstInsertarCuenta.setLong(1, employeeId);
	    pstInsertarCuenta.setString(2, employee.getName());
	    pstInsertarCuenta.setString(3, employee.getAddress());
	   
	    pstInsertarCuenta.executeUpdate();    
	    
		// TODO Auto-generated method stub
		//INSERT INTO `papeletadepago`.`employees` (`employeeId`, `name`, `address`) VALUES ('1', 'dd', 'ff');
		// query = "INSERT INTO vehiculo(marca, " + "modelo, " + "matricula, " + "anio, "
	     //           + "id_tipo_vehiculo" + ") VALUES ('" + taxi.getMarca() + "', " + "'" + taxi.getModelo()
	    //            + "', " + "'" + taxi.getMatricula() + "', " + taxi.getAnio() + ", " + tipo_vehiculo + ");";
		 

		/*	String seleccio = "INSERT INTO employees(employeeId,name,address)VALUES (?,?,?)";
			PreparedStatement ps = con.prepareStatement(seleccio);
			ps.setLong(0, employeeId); 
			ps.setString(1, employee.getName()); 
			ps.setString(2, employee.getAddress()); 
			ps.executeUpdate();
		*/
	}
	
		
	

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getUnionMember(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUnionMember(int memberId, Employee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUnionMember(int memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> getAllEmployeeIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee addShow(int ind) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ResultSet connectionWithTableOfEmployees()
	{//Connection cn = DriverManager.getConnection("jdbc:mysql://servidor_bd:puerto/nombre_bd", "usuario", "contraseña");
		
		
		ResultSet rs=null;
		try{
			conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/papeletadepago","root","rebeca");
           String query= "SELECT * FROM papeletadepago.employees";
           Statement stmt=(Statement) conexion.createStatement();
           rs=((java.sql.Statement)stmt).executeQuery(query);
           System.out.println("Exito");
           return rs;
			
			
		}catch (Exception e){
			System.err.println(e);
			
			return rs;

		}
		
		
	}
	
	
	
	@Override
	public ArrayList<Employee> getAllEmployees() throws SQLException {
       ArrayList<Employee> ls = new ArrayList<Employee>();
		try{
			ResultSet results= connectionWithTableOfEmployees();
			while(results.next()){
				
				Employee employee=new Employee(Integer.parseInt(results.getString("employeeId")), results.getString("name"), results.getString("address"));
		       ls.add(employee);
			}
			
			return ls;
		}
		catch(Exception e){
			System.err.println(e);
			return ls;
			
		}
		
	}

}
