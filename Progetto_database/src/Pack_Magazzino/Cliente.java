package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Cliente extends Persona{
	private Scanner scan  = new Scanner(System.in);
	private String sql;
	private ResultSet rs;
	
	public Cliente() {
		super();
	}
	
	public Cliente(String u)
	{
		super(u,"","");	
	}

	@Override
	public void startOptions(Statement stmt) {
		printInfo(this.getCf(), stmt);
		int k;
		
		do {
			k = menu();
			switch (k) {
			case 1:
				sql = "select * from contratto where cf_cli = '"+this.getCf()+"';";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Num contratto | Data inizio | Data fine | Numero spazi acquistati");
					this.display(rs, 4);
				} catch (SQLException e) {
					System.err.println("Si è verificato un errore...");
				}
				break;
	
			default:
				break;
			}
			scan.nextLine();
			System.out.println("Premi invio per continuare...");
			scan.nextLine();
		}while(k != 0);
		
	}
	
	
	private void printInfo(String u, Statement stmt)
	{	
		String sql = "select * from cliente where cf_cli = '" + u + "';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println("Nome : " + rs.getString(2));
				System.out.println("Cognome : " + rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("");
	}
	
	
	private int menu()
	{
		int k;
		System.out.println("* OPZIONI : ");
		System.out.println("	1) I miei contratti");
		System.out.println("	2) I miei prodotti");
		System.out.println("	3) Richiedi una spedizione");
		System.out.println("* VISUALIZZA :");
		System.out.println("	4) I contratti scaduti");
		System.out.println("	5) Le spedizioni consegnate");
		System.out.println("	6) Le spedizioni in consegna");
		System.out.println("* ESCI :");
		System.out.println("    0) Esci");			
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k < 0 || k > 6);
		
		return k;
		
		
	
	}
	
}
