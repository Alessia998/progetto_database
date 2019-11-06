package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
			case 2:
				sql = "select spazio.id_spazio, spazio.num, spazio.cod, contiene.codice , contiene.quantita from contiene, spazio_contratto, contratto, spazio\n" + 
						"where contiene.cod = spazio.cod and contiene.num = spazio.num and contiene.id_spazio = spazio.id_spazio\n" + 
						"	  and  spazio_contratto.cod = spazio.cod and spazio_contratto.num = spazio.num and spazio_contratto.id_spazio = spazio.id_spazio\n" + 
						"	  and spazio_contratto.num_c = contratto.num_c and contratto.cf_cli = '"+this.getCf()+"';";
				
				try {
					
					System.out.println("SPAZIO | NUM MAGAZZINO | COD PRODOTTO | QUANTITA' ");
					rs = stmt.executeQuery(sql);
					display(rs,rs.getMetaData().getColumnCount());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
	
			case 3:
				if(chiediSpedizione(stmt, scan) == 1)
				{
					System.out.println("Errore di inserimento spedizione!");
					break;
				}
				System.out.println("L'ordine  di spedizione registrato correttamente!");
				//stato_consegna In consegna viene messo in automatico
				
				break;
			case 4:
				//Contratti scaduti
				sql = "select * from contratto\n" + 
						"where data_fine < current_date\n" + 
						"	  and cf_cli = '"+this.getCf()+"'";
				
				System.out.println("");
				
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Numero contratto | Inizio data | Fine data | Numero di spazi ");
					this.display(rs, 4);
				} catch (SQLException e) {
					System.out.println("Errore di esecuzione query!");
					//e.printStackTrace();
				}
				
				System.out.println("");
	
				break;
			case 5:
				//da finire
			    sql = "select * from spedizione\n" + 
			    		"where stato_consegna = 'In consegna'\n" + 
			    		"     and cf_cli = '"+this.getCf()+"';";
				
				
				break;
			case 6:
				//da finire 
				sql = "select * from spedizione\n" + 
						"where stato_consegna = 'Consegnato'\n" + 
						"     and cf_cli = '"+this.getCf()+"';";
				
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
	
		//non funziona. Da lavorare - Niko
		public String getCfCliente(Statement stmt, Scanner scan)
		{
			int i = 0,k;
			ResultSet rs=null;
			String sql = "select cf_cli from cliente;";
			System.out.println(i++ + ") Per uscire");
			try {
				rs = stmt.executeQuery(sql);		
				while(rs.next())
				{
					System.out.println(i + ") " + rs.getString(1));
					i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			do {
				System.out.print("Scelta : ");
				k = scan.nextInt();
			}while(k<0 || k>i-1);
			
			if (k == 0) return "";
			
			sql = "select cf_cli from cliente "
					+"limit "+k+" offset "+(k-1)+";";
			try {
				rs = stmt.executeQuery(sql);
				if(rs.next())
					return rs.getString(1);	
			} catch (SQLException e) {
				e.getMessage();
			}	
			return "";
		}
		
		
		//return 0 = ok, return 1 = error;
		private int chiediSpedizione(Statement stmt, Scanner scan)
		{
			scan.nextLine();
			System.out.println("Inserisci la data di spedizione : ");
			String data = scan.nextLine();
			
			sql = "select targa from veicolo ORDER BY random() limit 1;";
			String targa = "";
			try {
				rs = stmt.executeQuery(sql);
				rs.next();
				targa = rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore di selezione veicolo!");
				return 1;
			}
			
			String[] infolist = new String[5];
			
			System.out.println("Inserisci il paese di destinazione : ");
			infolist[0] = scan.nextLine();
			if(infolist[0] == "" ) infolist[0] = "Italia";
			
			System.out.println("Inserisci la città : ");
			infolist[1] = scan.nextLine();
			
			System.out.println("Inserisci la via : ");
			infolist[2] = scan.nextLine();
			
			System.out.println("Inserisci il numero civico : ");
			infolist[3] = scan.nextLine();
			
			System.out.println("Inserisci il numero di telefono : ");
			infolist[4] = scan.nextLine();
			
			//codice_cli cliente this.getCf();
			
			String fattorino = "";
			sql = "select fattorino from veicolo ORDER BY random() limit 1;";
			try {
				rs = stmt.executeQuery(sql);
				rs.next();
				targa = rs.getString(1);
			} catch (SQLException e) {
				System.out.println("Errore di selezione fattorino!");
				return 1;
			}
			
			System.out.println("Inserisci il codice del prodotto : ");
			sql = "select codice from prodotto";
			String cod = this.chooseInfo(sql, stmt, scan, "prodotto", "codice").toString();
			
			System.out.println("Inserisci la quantità che si vuole mandare : ");
			int q = scan.nextInt();
			
			//Calcolo filiale, magazzino e spazio
			
			//sto lavorando Niko
			sql = " select spazio.cod, spazio.num, spazio.id_spazio from contratto co, spazio_contratto sc, spazio, contiene\n" + 
					"		where co.num_c = sc.num_c and\n" + 
					"	  sc.cod = spazio.cod and\n" + 
					"	  sc.num = spazio.num and\n" + 
					"	  sc.id_spazio = spazio.id_spazio and\n" + 
					"	  sc.cod = contiene.cod and\n" + 
					"	  sc.num = contiene.num and\n" + 
					"	  sc.id_spazio = contiene.id_spazio and\n" + 
					"	  co.cf_cli = '"+this.getCf()+"' and \n" + 
					"	  contiene.codice = '"+cod+"'";
			
			String fil = null;
			int mag=0;
			int spa=0;
			
			
			try {
				rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					fil = rs.getString(1);
				    mag = rs.getInt(2);
					spa = rs.getInt(3);
				}
			} catch (SQLException e) {
				System.out.println("Errore sql!");
				e.printStackTrace();
				return 1;
			}
			
			sql = "select spedisci('"+fattorino+"','"+data+"','"+targa+"','"+infolist[0]+"','"+infolist[1]+"','"+infolist[2]+"','"+infolist[3]+"','"+infolist[4]+"','"+this.getCf()+"',"+q+",'"+cod+"','"+fil+"',"+mag+","+spa+")";
			System.out.println(sql);
			
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				System.out.println("Errore di inserimento spedizione nel DB!");
				e.printStackTrace();
				return 1;
			}
			
			return 0;
		}
	
}
