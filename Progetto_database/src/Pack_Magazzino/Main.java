package Pack_Magazzino;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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
		Option o;
		System.out.println("ciao");
		
		
		
		try {
			db_name = args[0];
		} catch (Exception e) {
			System.err.println("Il programma prende in input nome del database");
			System.exit(1);
		}
		
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
		
		
		/*CallableStatement proc=null;
		try {
			proc = conn.prepareCall("{ ? = call who_is('"+u+"') }");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			proc.registerOutParameter(1, Types.OTHER);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			proc.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet results = (ResultSet) proc.getObject(1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		

		
		//sql = "select who_is('postgres');";
		try {
		sql = "select who_is('"+u+"');";
		rs = stmt.executeQuery(sql);
		rs.next();
		categoria = rs.getString(1);
		System.out.println(categoria);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
			//rs.next();
			//categoria = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


		
	
		
		System.out.print("Sei ");
		o = new Option(stmt, conn);
		switch (categoria) {
		case "admin":
			o.admin_options();
			
			break;
		case "dirigente":
			System.out.println("dirigente");
			break;
		case "custode":
			System.out.println("custode");
			break;
		case "cliente":
			System.out.println("cliente");
			break;
		case "impiegato":
			System.out.println("impiegato");
			break;
		case "fattorino":
			System.out.println("fattorino");
			break;
		case "magazziniere":
			System.out.println("magazziniere");
			break;



		default:
			System.out.println("Autenticazione fallita!");
			System.exit(1);
			break;
		}
	
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
		


	}
	
	
		
		
	
	
}


