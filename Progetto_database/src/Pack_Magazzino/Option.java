package Pack_Magazzino;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Option {
	private String sql;
	Statement stmt= null;
	Connection conn = null;
	
	public Option(Statement stmt, Connection conn)
	{
		this.stmt = stmt;
		this.conn = conn;
	}
	
	public void admin_options()
	{
		System.out.println("Connected as ADMIN!");
		System.out.println("Opzioni a disposizione: ");
		System.out.println("1) Inserisci un nuovo dirigente");
		System.out.println("2) Inserisci una nuova filiale");
		System.out.println("3) Stampa tutti dirigenti");
		System.out.println("4) Stampa tutte le filiali");
		System.out.println("5) Elimina un dirigente (solo se non gestisce nessuna filiale)");
		System.out.println("0) Exit");
		int k;
		Scanner scan = new Scanner(System.in);
		k = scan.nextInt();
		
		do {
		switch (k) {
		case 0:
			System.out.println("---EXIT---");
			break;
		case 1:
			clr();
			String cf,nome,cognome,temp="";
			boolean ok = false;
			

			System.out.println("Inserimento nuovo dirigente. ");
			System.out.println("Codice fiscale :");
			scan.nextLine();
			cf = scan.nextLine();
			System.out.println("Nome: ");
			nome = scan.nextLine();
			System.out.println("Congome");
			cognome = scan.nextLine();
			Dirigente dir = new Dirigente(cf, nome, cognome);
			System.out.println("Inserisci password per il dirigente");
			dir.setPass(scan.nextLine());
			System.out.println("Data di nascita (aaaa-mm-gg) :");
			dir.setData_nascita(scan.nextLine());
			System.out.println("Telefono: ");
			dir.setTel(scan.nextLine());
			System.out.println("Mail :");
			dir.setMail(scan.nextLine());
			sql = "insert into dirigente values ('"+dir.getCf()+"','"+dir.getNome()+"','"+dir.getCognome()+"',"+dir.getData_nascita(1)+","+dir.getTel(1)+","+dir.getMail(1)+")";
			try {
				stmt.execute(sql);
				ok = true;
			} catch (SQLException e) {
				System.err.println("Formato dati è incoretto!... )");
				e.printStackTrace();
				return;	
			}
			sql = "create user "+dir.getCf()+" with password '"+dir.getPass()+"';"+
					"GRANT USAGE ON SCHEMA public TO "+dir.getCf()+";\r\n" + 
					"GRANT SELECT ON dirigente to "+dir.getCf()+";"+
					"GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO "+dir.getCf()+";";

			//System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
				System.out.println("Nuovo dirigente registrato!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		default:
			break;
		}
		}while(k<0 || k >5);
	}
	
	
	public void custode_options()
	{
		//da mettere a posto. solo per test
		System.out.println("1) prossima settimana");
		sql = "select * from custode";
		ResultSet rs;
		/*try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	
	public void clr()
	{
		try {
			  if( System.getProperty( "os.name" ).startsWith( "Window" ) ) {		
			     Runtime.getRuntime().exec("cls");	
			  } else {	
			     Runtime.getRuntime().exec("clear");		
			  }
			} catch (IOException e) {		
			  for(int i = 0; i < 200; i++) {			
			    System.out.println();		
			  }
	}
	}
}
