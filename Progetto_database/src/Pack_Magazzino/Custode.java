package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Pack_Magazzino.Persona;

public class Custode extends Persona{
	private String data_nascita;
	private String mail;
	private Scanner scan  = new Scanner(System.in);

	public Custode(String cf, String nome, String cognome) {
		super(cf,nome,cognome);			
	}
	
	public Custode (String cf) {
		super (cf,"","");
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
		String sql = null;
		int k;
		
		do {
			k = menu();
			switch (k) {
			case 0:
				System.out.println("---EXIT---");
				System.exit(0);
				break;
			case 1:
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu \r\n" + 
						"where tu.cf = cu.cf and tu.data_t between current_date and current_date + 7;";
				break;
			case 2:
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu \r\n" + 
						"where tu.cf = cu.cf and tu.data_t between current_date and current_date + 30;";
				break;
			case 3:
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu \r\n" + 
						"where tu.cf = cu.cf and tu.data_t between current_date - 7 and current_date;";
				break;
			case 4:
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu \r\n" + 
						"where tu.cf = cu.cf and tu.data_t between current_date - 30 and current_date;";
				break;
			case 5:
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu\r\n" + 
						"where tu.cf = cu.cf and\r\n" + 
						"extract(year from tu.data_t) = extract(year from current_date);";			
				break;	
			case 6:
				System.out.println("Inserisci la data(aaaa-mm-gg): ");
				scan.nextLine();
				sql = "select cu.cf, cu.nome, cu.cognome, tu.data_t from turno as tu, custode as cu \r\n" + 
						"where tu.cf = cu.cf and tu.data_t = '" + scan.nextLine() + "';";
				break;		
			default:
				break;
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("Risultati:");
			System.out.println("-----------------------------------------------------");
			try {
				ResultSet rs = stmt.executeQuery(sql);
				this.display(rs, 4);
			} catch (SQLException e) {
				System.out.println("Non trovo niente... premi invio");
				scan.nextLine();
			}
			System.out.println("-----------------------------------------------------");		
		}while(k != 0);			
	}
	
	private int menu()
	{
		int k;
		
		System.out.println("Opzioni");
		System.out.println("* VISUALIZZA I TURNI : ");
		System.out.println("    1) Prossimi 7 giorni");
		System.out.println("    2) Prossimi 30 giorni");
		System.out.println("    3) Scorsi 7 giorni");
		System.out.println("    4) Scorsi 30 giorni");
		System.out.println("    5) L'anno in corso");
		System.out.println("    6) Data specifica");
		System.out.println("* ESCI");
		System.out.println("    0) Esci");	
		
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k < 0 || k > 6);
		
		return k;
	}
	


}
