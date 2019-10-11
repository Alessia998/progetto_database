package db_init;

import java.sql.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.*;
import java.util.*;

public class db_init_main {
	

	public static void main(String[] args) {
		String user=null;
		String password=null;
		String db_name= null;
		Statement stmt=null;
		String filePath[] = new String[3];
		Connection conn=null;
		
		try {
			db_name = args[0];
			user = args[1];
			password = args[2];
		} catch (Exception e) {
			System.err.println("Il programma prende in input nome del database username e password.");
			System.exit(1);
		}		
		
		try {
			Class.forName ("org.postgresql.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		
		try {
		    conn = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/"+db_name+"?loggerLevel=OFF", user,password);
			stmt = conn.createStatement();
					
		} catch (SQLException e) {
			System.err.println("Hai inserito i dati sbagliati.");
			System.exit(1);
		}
		  
		Scanner in = new Scanner(System.in);
		int k;
		System.out.println("OPZIONI :");
		System.out.println("1) Inizializza un nuovo DataBase.");
		System.out.println("2) Pulisci DataBase " + db_name);
		System.out.println("3) Chiudi.");
		
		do {
		k = in.nextInt();	
		switch (k) {
		case 1:
			filePath[0]="create_tables.sql";
			filePath[1]="all_functions.sql";
			filePath[2]="script_insert.sql";
			 
			for(int i=0;i<3;i++)
			{
			String sql = readLine( filePath[i] );
			try {			
				 stmt.execute(sql);
			} catch (SQLException e) {
				System.err.println("Query non eseguita! (Forse DataBase esiste già.)");		
				System.exit(0);
			}
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("Connessione non chiusa!");
			}
			
			System.out.println("DB creato con successo!");
			break;
		case 2:
			elimina_tutto(stmt);	
			break;
		case 3:
			System.out.println("EXIT");
			System.exit(0);	
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("Connessione non chiusa!");
			}
			break;
			
		default:
			System.out.println("Valore non ammissibile");
			break;
		}
		}while(k<1 || k>3 || k == 2);
		
		
		
      
		in.close();
	}
	
	private static String readLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    }
	
	
	private static void elimina_tutto(Statement stmt)
	{
		String sql = "DROP SCHEMA public CASCADE; CREATE SCHEMA public";
		try {
			
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("Query non eseguita!");		
			return;
		}
		
		
		System.out.println("DataBase pulito.");
		
	}

}
