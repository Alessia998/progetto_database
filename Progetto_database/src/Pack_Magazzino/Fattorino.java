package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
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
			System.out.println("");
			
			
			switch(scelta)
			{
				case 0:
					break;
				case 1:
					// TODO : da chiarire Nuova - In consegna - Consegnata
					sql = "select * from spedizione where stato_consegna = 'Consegnata'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next()) 
						{
							System.out.println("\n ---- Spedizione : " + rs.getString("num_sped") + " ---- ");
							System.out.println("Destinazione: ");
							System.out.println("    Paese  : " + rs.getString("paese"));
							System.out.println("    Città  : " + rs.getString("citta"));
							System.out.println("    Via    : " + rs.getString("via"));
							System.out.println("    Civico : " + rs.getString("numero"));
							System.out.println("Codice Fiscale cliente : " + rs.getString("cf_cli"));
							System.out.println("Data  : " + rs.getString("data_sp"));
							System.out.println("Targa del veicolo : " + rs.getString("targa"));
							System.out.println("Numero di telefono: " + rs.getString("tel")); // Di chi è il numero di telefono?
							System.out.println("Codice Fiscale fattorino : " + rs.getString("cf"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
				case 2:
					
					sql = "select * from spedizione where stato_consegna = 'In consegna'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next()) 
						{
							System.out.println("\n ---- Spedizione : " + rs.getString("num_sped") + " ---- ");
							System.out.println("Destinazione: ");
							System.out.println("    Paese  : " + rs.getString("paese"));
							System.out.println("    Città  : " + rs.getString("citta"));
							System.out.println("    Via    : " + rs.getString("via"));
							System.out.println("    Civico : " + rs.getString("numero"));
							System.out.println("Codice Fiscale cliente : " + rs.getString("cf_cli"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
					
				case 3:
					sql = "select * from trasferimenti where stato_consegna = 'Consegnato'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next()) 
						{
							System.out.println("\n ---- Trasferimento : " + rs.getString("num_sped") + " ---- ");
							System.out.println("Magazzino di partenza (Num Filiale - Num Magazzino): "
									+ rs.getString("num1") + " - " + rs.getString("cod1"));
							System.out.println("Magazzino di arrivo (Num Filiale - Num Magazzino): "
									+ rs.getString("num2") + " - " + rs.getString("cod2"));
							System.out.println("Data  : " + rs.getString("data_sp"));
							System.out.println("Targa del veicolo : " + rs.getString("targa"));
							System.out.println("Codice Fiscale fattorino : " + rs.getString("cf"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
					
				case 4:
					sql = "select * from trasferimenti where stato_consegna = 'In consegna'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next()) 
						{
							System.out.println("\n ---- Trasferimento : " + rs.getString("num_sped") + " ---- ");
							System.out.println("Magazzino di partenza (Num Filiale - Num Magazzino): "
									+ rs.getString("num1") + " - " + rs.getString("cod1"));
							System.out.println("Magazzino di arrivo (Num Filiale - Num Magazzino): "
									+ rs.getString("num2") + " - " + rs.getString("cod2"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
					
				case 5: // Andrea : A mio parere bisogna fare una distinzione tra NUOVA - IN CONSEGNA - CONSEGNATA
					Boolean s_printed = false; // Usata per controllare se il fattorino ha almeno una spedizione in carico
					sql = "select num_sped from spedizione where stato_consegna = 'In consegna' and cf = '"+ this.getCf() +"'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next())
						{
							s_printed = true;
							System.out.println("Spedizione: " + rs.getString("num_sped"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(s_printed)
					{
						System.out.print("Inserisci il numero di spedizione: ");
						int n_sped = scan.nextInt();
						
						sql = "update spedizione set stato_consegna = 'Consegnato' where num_sped = "+ n_sped +"";
						try {
							stmt.executeUpdate(sql);
							System.out.println("Aggiornamento effettuato con successo");
						} catch (SQLException e) {
							System.err.println("Errore durante l'aggiornamento");
							e.printStackTrace();
						}
					}else {
						System.out.println("Non hai in carico alcuna spedizione");
					}
					
					
					break;
				case 6:
					Boolean printed = false; // Usata per controllare se il fattorino ha almeno un trasferimento in carico
					sql = "select * from trasferimenti where stato_consegna = 'In consegna'and cf = '"+ this.getCf() +"'";
					try {
						rs = stmt.executeQuery(sql);
						while(rs.next())
						{
							printed = true;
							System.out.println("trasferimento: " + rs.getString("num_sped"));
						}
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(printed)
					{
						System.out.print("Inserisci il numero di trasferimento: ");
						int n_tr = scan.nextInt();
						
						sql = "update trasferimenti set stato_consegna = 'Consegnato' where num_sped = "+ n_tr +"";
						try {
							stmt.executeUpdate(sql);
							System.out.println("Aggiornamento effettuato con successo");
						} catch (SQLException e) {
							System.err.println("Errore durante l'aggiornamento");
							e.printStackTrace();
						}
					}else {
						System.out.println("Non hai in carico alcun trasferimento");
					}
					break;
				default:
					scelta = 0;
					break;
			}
		}while(scelta > 0 && scelta < 7);

		
		
	}
}