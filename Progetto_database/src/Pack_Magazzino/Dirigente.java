package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Pack_Magazzino.Persona;

public class Dirigente extends Persona{

	private String data_nascita;
	private String mail;
	private Scanner scan  = new Scanner(System.in);

	public Dirigente(String cf, String nome, String cognome) {
		super(cf,nome,cognome);	
	}
	

	public String getData_nascita() {
		
		return data_nascita;
	}
	
	public String getData_nascita(int i) {
		if(data_nascita.length()==0) return "null";
		
		return "'"+data_nascita+"'";
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}


	public String getMail() {
		return mail;
	}

	public String getMail(int i) {
		if (this.mail.length()==0) return "''";
		
		return "'"+mail+"'";
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
		System.out.println("Opzioni: ");
		System.out.println("* AGGIUNGI PERSONALE:");
		System.out.println("	1) Custode");
		System.out.println("	2) Magazziniere");
		System.out.println("	3) Impiegato");
		System.out.println("	4) Fattorino");
		System.out.println("	5) Veicolo");
		System.out.println("* VISUALIZZA ORGANICO:");
		System.out.println("	6) Custodi");
		System.out.println("	7) Magazzinieri");
		System.out.println("	8) Impiegati");
		System.out.println("	9) Veicoli");
		System.out.println("	10) Magazzini e relativi spazi");
		System.out.println("	11) Tutti i clienti");
		System.out.println("	12) Clienti della tua filiale");
		System.out.println("0) Esci");
		
		System.out.print("\nScelta: ");
		scan.nextLine();
		scelta = scan.nextInt();
		
		switch(scelta)
		{
			case 1:
				
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				sql = "select * from custode";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale | Nome | Cognome | Data di nascita | Telefono | Mail");
					this.display(rs, 6);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 7:
				sql = "select * from magazziniere";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale | Nome | Cognome | Data di nascita | Telefono | Mail");
					this.display(rs, 6);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 8:
				sql = "select * from impiegato";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale | Nome | Cognome | Data di nascita | Telefono | Mail");
					this.display(rs, 6);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				sql = "select * from veicolo";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Targa | Marca | Modello | Capacita'");
					this.display(rs, 4);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			default:
				break;
		}
		
		}while(scelta != 0 && scelta < 13);
		
	}

}