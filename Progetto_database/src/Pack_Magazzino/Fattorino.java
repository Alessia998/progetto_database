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
					sql = "select * from spedizione where stato_consegna = 'Consegnato'";
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
							System.out.println("Numero di telefono del destinatario: " + rs.getString("tel"));
							System.out.println("Codice Fiscale fattorino : " + rs.getString("cf"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
				case 2:
					
					sql = "select * from spedizione where stato_consegna = 'In consegna' and cf = '"+ this.getCf() +"'";
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
							System.out.println("Telefono del destinatario: "+ rs.getString("tel"));
							System.out.println("Veicolo da utilizzare: "+ rs.getString("targa"));
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
					sql = "select * from trasferimenti where stato_consegna = 'In consegna' and cf = '"+ this.getCf() +"'";
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
							System.out.println("Veicolo da utilizzare : " + rs.getString("targa"));
						}
					} catch (SQLException e) {
						System.err.println("Errore esecuzione query");
						e.printStackTrace();
					}
					break;
					
				case 5://errore
					System.out.println("Scegli l'indice del numero spedizione :");
					sql = "select num_sped from spedizione where stato_consegna = 'In consegna' and cf = '"+ this.getCf() +"'";
					
					
					Integer res = (Integer) this.chooseInfo(sql,stmt,scan,"spedizione","num_sped");
					
					System.out.println("Res: " + res);
					
					if(res != null)
					{
						
						sql = "update spedizione set stato_consegna = 'Consegnato' where num_sped = "+ res +"";
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
				case 6:	//errore
					sql = "select num_sped from trasferimenti where stato_consegna = 'In consegna' and cf = '"+ this.getCf() +"'";
					Integer t_res = (Integer)chooseInfo(sql,stmt,scan,"trasferimenti","num_sped");
					
					if(t_res != null) 
					{
						sql = "update trasferimenti set stato_consegna = 'Consegnato' where num_sped = "+ t_res +"";
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
	
	public Object chooseInfo(String sql, Statement stmt, Scanner scan, String nomeTab, String nomeKey) {
		
		ResultSet rs = null;
		int i = 1, k;
		
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Dati Sbagliati!");
			e.printStackTrace();
			return null;
		}
		
		
		try {
			System.out.println(" ---- OPZIONI ----");
			while(rs.next()){
				System.out.println(i + ") " + rs.getObject(nomeKey));
				i ++;
			}
		} catch (SQLException e) {
			System.out.println("SQL result error!");
			e.printStackTrace();
			return null;
		}
		
		do {
			System.out.print("Inserire indice dell'elenco (0 per uscire) :");
			k = scan.nextInt();
		}while(k<0 || k>i-1);
		
		if(k == 0)
			return null;
		
		sql += " limit "+k+" offset "+(k-1)+";";
		
		System.out.println(sql);
		
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getObject(nomeKey);
	
		} catch (SQLException e) {
			System.out.println("SQL result error!");
			e.printStackTrace();
		}	
		
		
		return null;
		
	}
}