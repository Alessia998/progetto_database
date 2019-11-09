package Pack_Magazzino;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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
			
				int stato = chiediSpedizione(stmt, scan);
				if(stato == 1)
				{
					System.out.println("Errore di inserimento spedizione!");
					break;
				}
				else if(stato == 2)
					break;
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
			    sql = "select sp.num_sped, sp.data_sp, paese, citta, via, numero, tel, codice, quantita, cf from spedizione as sp, prod_sped as ps\n" + 
			    		"where sp.num_sped = ps.num_sped\n" + 
			    		"and stato_consegna = 'Consegnato'\n" + 
			    		"     and cf_cli = '"+this.getCf()+"';";
			    
			    System.out.println("");
				
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Numero spedizione | Data spedizione | Paese | Città | Numero civico | Tel | Codice Prodotto | Quantità | CF corriere ");
					this.display(rs, rs.getMetaData().getColumnCount());
				} catch (SQLException e) {
					System.out.println("Errore di esecuzione query!");
					//e.printStackTrace();
				}
				
				
				break;
			case 6:
				sql = "select sp.num_sped, sp.data_sp, paese, citta, via, numero, tel, codice, quantita, cf from spedizione as sp, prod_sped as ps\n" + 
						"where sp.num_sped = ps.num_sped\n" + 
						"and stato_consegna = 'In consegna'\n" + 
						"     and cf_cli = '"+this.getCf()+"';";

				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Numero spedizione | Data spedizione | Paese | Città | Numero civico | Tel | Codice Prodotto | Quantità | CF corriere");
					this.display(rs, rs.getMetaData().getColumnCount());
				} catch (SQLException e) {
					System.out.println("Errore di esecuzione query!");
					//e.printStackTrace();
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
		
		
		//return 0 = ok, return 1 = error, 2 = uscita voluta;
		private int chiediSpedizione(Statement stmt, Scanner scan)
		{
			scan.nextLine();
			//System.out.println("Inserisci la data di spedizione : ");
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
			String data = formatter.format(date).toString();
			
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
			sql = "select cf from fattorino ORDER BY random() limit 1;";
			try {
				rs = stmt.executeQuery(sql);
				rs.next();
				fattorino = rs.getString(1);
			} catch (SQLException e) {
				System.out.println("Errore di selezione fattorino!");
				return 1;
			}
			
			String cod = null;
			int cont=0,scelta;
			System.out.println("Inserisci indice del codice del prodotto (0 per uscire) : ");// serve anche quantità
			ArrayList <Cod_quan> iter = this.getOwnedItems(stmt, this.getCf());
			
			for (Cod_quan temp : iter) {
				System.out.println(++cont + ") "+temp.codice_prodotto + " Quantità : " + temp.quantita);
			}
			//cont--;
			System.out.println("cont adesso è : " + cont);
			
			do {
				System.out.print("Scelta : ");
				scelta = scan.nextInt();
			}while(scelta < 0);
			
			if(scelta == 0) return 2;
			
			cont=0;
			int temp_quantita = 0;
			for (Cod_quan temp : iter) {
				cont+=1;
				if(cont == scelta) 
					{
					cod = temp.codice_prodotto; 
					temp_quantita = temp.quantita;
					}
			}
			
			int q;
			System.out.println("Inserisci la quantità che si vuole mandare : ");
			do {
				q = scan.nextInt();
			}while(q<1);
			
			//Calcolo filiale, magazzino e spazio
			
			sql = " select spazio.cod, spazio.num, spazio.id_spazio, contiene.quantita from contratto co, spazio_contratto sc, spazio, contiene\n" + 
					"		where co.num_c = sc.num_c and\n" + 
					"	  sc.cod = spazio.cod and\n" + 
					"	  sc.num = spazio.num and\n" + 
					"	  sc.id_spazio = spazio.id_spazio and\n" + 
					"	  sc.cod = contiene.cod and\n" + 
					"	  sc.num = contiene.num and\n" + 
					"	  sc.id_spazio = contiene.id_spazio and\n" + 
					"	  co.cf_cli = '"+this.getCf()+"' and \n" + 
					"	  contiene.codice = '"+cod+"' and \n" +
					"	  quantita = "+temp_quantita+";";
			
			if(q > temp_quantita)
			{
				System.out.println("Nello spazio sono presenti " + temp_quantita + " prodotti. Quindi non è possibile mandarne "+ q);
				return 1;
			}
				
			
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
			
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				System.out.println("Errore di inserimento spedizione nel DB!");
				e.getMessage();
				return 1;
			}
			
			return 0;
		}
		
		
		//prende in input il codice fiscale del cliente e ritorna ArrayList set che contiene i suoi prodotti
		//Nel caso di errore ritorna null
		public ArrayList<Cod_quan> getOwnedItems(Statement stmt, String cod_fis) 
		{
			ArrayList<Cod_quan> list = new ArrayList<Cod_quan>();
			
			String sql = "select prodotto.codice, contiene.quantita from contratto, spazio_contratto sp_co, spazio, contiene, prodotto\n" + 
					"	where cf_cli = '"+ cod_fis +"' and\n" + 
					"	sp_co.num_c = contratto.num_c and\n" + 
					"	spazio.cod = sp_co.cod and\n" + 
					"	spazio.num = sp_co.num and\n" + 
					"	spazio.id_spazio = sp_co.id_spazio and\n" + 
					"	spazio.cod = contiene.cod and\n" + 
					"	spazio.num = contiene.num and\n" + 
					"	spazio.id_spazio = contiene.id_spazio and\n" + 
					"	contiene.codice = prodotto.codice;";
			
			ResultSet rs = null;
			
			try {
				rs = stmt.executeQuery(sql);
				while(rs.next())
				{
					list.add(new Cod_quan(rs.getString(1),rs.getInt(2)));
				}
			} catch (SQLException e) {
				return null;
			}

			return list;
		}
		
		class Cod_quan{
			String codice_prodotto;
			int quantita;
			
			public Cod_quan(String s, int q)
			{
				codice_prodotto = s;
				quantita = q;
			}
			
		}
	
}
