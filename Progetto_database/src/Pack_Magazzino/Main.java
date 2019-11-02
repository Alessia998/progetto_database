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
		//db_name = args[0];
		
		System.out.print("Username : ");
		u = scan.nextLine();
		System.out.print("Password : ");
		p = scan.nextLine();
		
		//lower case per accedere al pgadmin database  
		u = u.toLowerCase();
		
		try {
			Class.forName ("org.postgresql.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		try {
			conn = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/"+db_name+"?loggerLevel=OFF", u , p);
			stmt = conn.createStatement();
			
		} catch (SQLException e) {
			System.err.println("Hai inserito i dati sbagliati.");
			System.exit(1);
		}
		
		
		//Controllo se l'utente � admin
		//altrimenti faccio Upper case
		sql = "select who_is('"+u+"');";		
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			rs.getString(1);
			if (! rs.getString(1).equals("admin")) {
				u = u.toUpperCase();	}
			
			rs.close();
		} catch (SQLException e1) {
			System.err.println("Errore di accesso al DB");
			e1.printStackTrace();
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
			aut = new Admin(u);
			break;
		case "dirigente":
			System.out.println("Ruolo: DIRIGENTE");
			aut = new Dirigente(u);
			break;
		case "custode":
			System.out.println("Ruolo: CUSTODE");
			aut = new Custode(u);
			break;
		case "cliente":
			System.out.println("CLIENTE ");
			aut = new Cliente(u);
			break;
		case "impiegato":
			System.out.println("Ruolo: IMPIEGATO");
			aut = new Impiegato(u);
			break;
		case "fattorino":
			aut = new Fattorino(u,"","");
			break;
		case "magazziniere":
			aut = new Magazziniere(u,"","", 0, "");
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
			e.printStackTrace();
		}
		
		scan.close();
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Non si riesce a chiudere la connessione");
		}
		System.out.println("---- Chiusura ----");
		
	}
	
	
		
		
	
	
}


