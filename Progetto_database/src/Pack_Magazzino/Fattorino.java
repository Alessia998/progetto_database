package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import Pack_Magazzino.Persona;

public class Fattorino extends Persona{
	
	private String data_nascita;
	private Scanner scan  = new Scanner(System.in);
	private String mail;

	public Fattorino(String cf, String nome, String cognome) {
		super(cf,nome,cognome);			
	}
	

	public String getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
}


	@Override
	public void startOptions(Statement stmt) {
		
		int scelta;
		String sql;
		ResultSet rs = null;
		
		do {
			System.out.println("\n---- Opzioni: ----");
			System.out.println("* VISUALIZZA:");
			System.out.println("	1) Spedizioni effettuate");
			System.out.println("	2) Spedizioni da effettuare");
			System.out.println("	3) Trasferimenti effettuati");
			System.out.println("	4) trasfereimenti da effettuare");
			System.out.println("* OPERAZIONI:");
			System.out.println("	5) Contrassegna spedizione come effettuata");
			System.out.println("	6) Contrassegna trasferimento come effettuato");
			System.out.println("0) Esci");
			
			System.out.print("\nScelta: ");
			//scan.nextLine();
			scelta = scan.nextInt();
			
			switch(scelta)
			{
				case 0:
					break;
				case 1:
					sql = "select * from spedizione where stato_consegna = 'In consegna'";
					break;
				case 2:
					sql = "select * from spedizione where stato_consegna = 'Consegnato'";
					break;
				case 3:
					sql = "select * from trasferimenti where stato_consegna = 'In consegna'";
					break;
				case 4:
					sql = "select * from trasferimenti where stato_consegna = 'Consegnato'";
					break;
				case 5:
					sql = "select * from spedizione where stato_consegna = 'In consegna'";
					break;
				case 6:
					sql = "select * from trasferimenti where stato_consegna = 'In consegna'";
					break;
				default:
					scelta = 0;
					break;
			}
		}while(scelta > 0 && scelta < 5);

		
		
	}
}