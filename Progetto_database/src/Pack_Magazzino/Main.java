package Pack_Magazzino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Connection  conn = null;
		Scanner scan = new Scanner(System.in);
		String db_name= null;
		Statement stmt = null;
		String u;
		String p;
		String sql;
		ResultSet rs= null;
		String categoria = "";
		
		
		
		db_name = "Progetto";
		
		System.out.println("Username : ");
		u = scan.nextLine();
		System.out.println("Password : ");
		p = scan.nextLine();
		
		
		try {
			Class.forName ("org.postgresql.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		try {
			conn = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/"+db_name+"?loggerLevel=OFF" , u , p);
			stmt = conn.createStatement();
			
		} catch (SQLException e) {
			System.err.println("Hai inserito i dati sbagliati.");
			System.exit(1);
		}
		

		try {
			sql = "select who_is('"+u+"');";
			rs = stmt.executeQuery(sql);
			rs.next();
			categoria = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Persona aut = null;
		switch (categoria) {
		case "admin":
			aut = new Admin();
			break;
		case "dirigente":
			aut = new Dirigente("","","");
			break;
		case "custode":
			aut = new Custode();
			break;
		case "cliente":
			aut = new Cliente();
			break;
		case "impiegato":
			aut = new Impiegato("","","","");
			break;
		case "fattorino":
			aut = new Fattorino("","","");
			break;
		case "magazziniere":
			aut = new Magazziniere("","","", 0, "");
			break;
			
		default:
			System.out.println("Autenticazione fallita!");
			System.exit(1);
			break;
		}
	
		aut.startOptions(stmt);
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
		


	}
	
	
		
		
	
	
}


