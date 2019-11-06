package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


import Pack_Magazzino.Persona;

public class Impiegato extends Persona {
	private String data_nascita;
	private String mail;
	private String cod;
	private Scanner scan  = new Scanner(System.in);


	public Impiegato(String cf, String nome, String cognome,String cod) {
		super(cf,nome,cognome);
	}

	public Impiegato(String u)
	{
		super(u,"","");	
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
	public String getMail(int i) {
		if (this.mail.length()==0) return "''";
		
		return "'"+mail+"'";
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}


	@Override
	public void startOptions(Statement stmt) {
		String sql = null;
		int k;
		ResultSet rs = null;
		String cod, nom, desc, fil, prod;
		int spa, mag, q;
		//boolean check;
		
		printInfo(this.getCf(), stmt);
		
		do {
			k = menu();
			switch (k) {
			case 0:
				System.out.println("---EXIT---");
				System.exit(0);
				break;
			case 1:
				//check = false;
				scan.nextLine();
				System.out.print("Inserisci il codice fiscale : ");
				Cliente cli = new Cliente(scan.nextLine().toUpperCase());
				//this.setCf(scan.nextLine().toUpperCase());
				System.out.print("Inserisci la password : ");
				String pass = scan.nextLine();
				System.out.print("Inserisci il nome : ");
				cli.setNome(scan.nextLine());
				System.out.print("Inserisci il cognome : ");
				cli.setCognome(scan.nextLine());
				System.out.print("Insersci il numero di telefono :");
				cli.setTel(scan.nextLine());
				
				sql = "select crea_cliente('"+cli.getCf()+"','"+cli.getNome()+"','"+cli.getCognome()+"','"+cli.getTel()+"');";
				try {
					stmt.executeQuery(sql);
				} catch (SQLException e1) {
					System.err.println((e1).getMessage());
					System.out.println("Erroe di inserimento nel DB.");
					break;
				}	
				sql = "create user "+cli.getCf()+" with password '"+pass+"';\r\n" + 
						"grant usage on schema public to "+cli.getCf()+" with grant option;\r\n" + 
						"grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to "+cli.getCf()+" with grant option;\r\n" + 
						"grant all on spedizione, prod_sped, my_seq1 to "+cli.getCf()+" with grant option;\r\n" + 
						"grant execute on all functions in schema public to "+cli.getCf()+" with grant option;";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("--- Cliente inserito. ---");
				} catch (SQLException e1) {
					System.err.println((e1).getMessage());
					System.out.println("Errore di inserimento in PgAdmin");
					break;
				}
		
				break;
			case 2:
				Contratto con = new Contratto();
				scan.nextLine();
				System.out.println("Insersci il codice fiscale del cliente : ");
				con.setCf_cli(stmt);
				if(con.getCf_cli() == "")
				{
					System.out.println("Operazione annullata ! ");
					break;
				}
				System.out.println("Inserisci il codice contratto (max 10 caratteri) : ");
				con.setNum_c(scan.nextLine());
				System.out.println("Inserisci la data di inizio contratto (aaaa-mm-gg): ");
				con.setData_inizio(scan.nextLine());
				System.out.println("Inserisci la data di fine contratto (aaaa-mm-gg): ");
				con.setData_fine(scan.nextLine());
				System.out.println("Inersci il numero degli spazi che si vuole acquistare : ");
				con.setNum_spazi(scan.nextInt());
				
				sql = "select nuovo_contratto('"+con.getNum_c()+"','"+con.getData_inizio()+"','"+con.getData_fine()+
						"','"+con.getNum_spazi()+"','"+this.getCf()+"','"+con.getCf_cli()+"');";
				try {
					stmt.executeQuery(sql);
				} catch (SQLException e1) {
					System.out.println(((SQLException)e1).getMessage());
					System.out.println(e1.getErrorCode());
					scan.nextLine();
					System.out.println("Premi invio per continuare...");
					scan.nextLine();
					break;
				}	
				System.out.println("--- Contratto inserito. ---");
				break;
			case 3:
				scan.nextLine();
				System.out.println("Inserisci il codice prodotto : ");
				cod = scan.nextLine();
				System.out.println("Inserisci il nome : ");
				nom = scan.nextLine();
				System.out.println("Inserisci la descrizione");
				desc = scan.nextLine();
				
				sql = "insert into prodotto values ('"+cod+"','"+nom+"','"+desc+"');";
				try {
					stmt.executeUpdate(sql);
					System.out.println("Prodotto inserito!");
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
					System.err.println("Prodotto non inserito!");
					scan.nextLine();
				}
				break;
			case 4:
				scan.nextLine();
				mag = this.getNumMagazzino(stmt, scan, this.getMySubsidiary(this.getCf(), stmt)); 
				if(mag == 0) break;
				int id_spazio = this.getIdSpazio(stmt, scan, this.getMySubsidiary(this.getCf(), stmt), mag);
				if(id_spazio == 0) break;
				sql = "select num_c from contratto";
				System.out.println("Scegli il codice del contratto : ");
				String num_c = this.chooseInfo(sql, stmt, scan, "contratto", "num_c").toString();
				if(num_c == "") break;
				
				sql = "select assegna_spazio('"+this.getMySubsidiary(this.getCf(), stmt)+"','"+mag+"','"+id_spazio+"','"+num_c+"')";
				try {
					stmt.execute(sql);
					System.out.println("Spazio assegnato al contratto "+num_c +"!");
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
					System.err.println("Errore di assegnamento spazio!");
					scan.nextLine();
				}

				break;
			case 5:
				
				//TODO : Da correggere
				/*
				 * 	Workflow :	* Chiedo Il Cliente
				 * 				* Chiedo Magazzino e spazio (Relativi al cliente)
				 * 				* Scelgo il codice prodotto
				 * 				* Chiedo quantità
				 * 				* Query :)
				 * */
				
				sql = "select cf_cli from cliente";
				String cf_cli = this.chooseInfo(sql, stmt, scan, "cliente", "cf_cli").toString();
				
				
				/*fil = this.getMySubsidiary(this.getCf(), stmt);
				mag = this.getNumMagazzino(stmt, scan, fil);
				spa = this.getIdSpazio(stmt, scan, fil, mag);
				System.out.println("Inserisci il codice del prodotto : ");
				sql = "select codice from prodotto";
				cod = this.chooseInfo(sql, stmt, scan, "prodotto", "codice").toString();
				do {
					System.out.println("Inserisci la quantità : ");
					q = scan.nextInt();
				}while(q < 1 );
				
				sql = "call insert_contiene("+spa+","+mag+",'"+fil+"','"+cod+"',"+q+");";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Prodotto inserito!");
				} catch (SQLException e2) {
					System.out.println("Errore di inserimento prodotto!");
					e2.printStackTrace();
				}
				
				*/
				break;
			case 6:
				scan.nextLine();
				System.out.println("Scegli il codice fiscale del cliente : ");
				sql = "select cf_cli from cliente;";
				String cf = this.chooseInfo(sql, stmt, scan, "cliente", "cf_cli").toString();	
				if(cf == null) break;
				scan.nextLine();
				System.out.println("Inserisci la data di trasferimento (aaaa-mm-gg): ");
				String data = scan.nextLine();
				String cf_fat = this.getCfWorker(stmt, scan, "fattorino");
				if(cf_fat == null) break;
				System.out.println("Inserisci la targa del veicolo : ");
				sql = "select targa from veicolo;";
				String targa = this.chooseInfo(sql, stmt, scan, "veicolo", "targa").toString();
				System.out.println("Inserisci il paese di destinazione (predefinito: Italia) : ");
				String paese = scan.nextLine();
				System.out.println("Inserisci il codice prodotto che si vuole mandare : ");
				sql = "select codice from prodotto;";
				String cProd = this.chooseInfo(sql, stmt, scan, "prodotto", "codice").toString();
				System.out.println("Insersci la quantità che deve essere trasferita :"); 
				q = scan.nextInt();
				System.out.println("Scegli filiale di partenza : ");
				String c1 = this.getCodFiliale(stmt, scan);
				System.out.println("Scegli magazzino di partenza : ");
				int n1 = this.getNumMagazzino(stmt, scan, c1); 
				System.out.println("Scegli filiale di arrivo : ");
				String c2 = this.getCodFiliale(stmt, scan);
				System.out.println("Scegli magazzino di arrivo : ");
				int n2 = this.getNumMagazzino(stmt, scan, c2);
				sql = "select trasferisci('"+cf+"','"+data+"','"+cf_fat+"','"+targa+"','"+
				      paese+"','"+n1+"','"+c1+"','"+n2+"','"+c2+"','"+cProd+"','"+q+"');";
				System.out.println(sql);
				try {
					stmt.execute(sql);
					System.out.println("Il trasferimento è stato registrato");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();			
				}
				break;
			case 7:
				sql = "select * from cliente;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					((SQLException)e).printStackTrace();
				}
				System.out.println("Codice fiscale | Nome | Cognome | Telefono | Piano");
				this.display(rs, 5);		
				break;	
			case 8:
				sql = "select * from contratto;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					((SQLException)e).printStackTrace();
				}
				System.out.println("Codice contratto | Data inizio | Data fine | N Spazi | CF impiegato | CF cliente");
				this.display(rs, 6);
				break;	
			case 9:
				sql = "select * from prodotto;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					((SQLException)e).printStackTrace();
				}
				System.out.println("Codice prodotto | Nome prodotto | Descrizione ");
				this.display(rs, 3);
				break;	
			case 10:
				sql = "select * from trasferimenti;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Numero trasferimento | Data sped. | CF fattorino | Veicolo | Stato consegna | Fil, Mag partenza | F , M arrivo");
				this.display(rs, 8);
				break;
			case 11:
				sql = "select * from spedizione;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Numero spedizione | CF fattorino | Data sped. | Veicolo | Indirizzo | Tel | Stato consegna | CF cliente");
				this.display(rs,11);
				break;	
			case 12:
				fil = this.getMySubsidiary(this.getCf(), stmt);
				mag = this.getNumMagazzino(stmt, scan, fil);
				spa = this.getIdSpazio(stmt, scan, fil, mag);
				scan.nextLine();
				System.out.println("Inserisci il codice prodotto da ritirare (0 per uscire): ");
				System.out.println("Codice prodotto | quantità  ");
				sql = "select codice, quantita\r\n" + 
						"from contiene\r\n" + 
						"where id_spazio = "+spa+" and\r\n" + 
						"num = "+mag+" and\r\n" + 
						"cod = '"+fil+"' ";
			
				int i=1,ko;
				prod = null;
				try {
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						System.out.println(i + ") " + rs.getString(1) +" - "+ rs.getString(2));
						i++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				do {
					System.out.print("Scelta : ");
					ko = scan.nextInt();
				}while(ko<0 || ko>i-1);
				
				sql = "select codice\r\n" + 
						"from contiene\r\n" + 
						"where id_spazio = "+spa+" and\r\n" + 
						"num = "+mag+" and\r\n" + 
						"cod = '"+fil+"'  "+
						"limit "+ko+" offset "+(ko-1)+";";
				try {
					rs = stmt.executeQuery(sql);
					if(rs.next())
						prod = rs.getString(1);

					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				System.out.println("Inserisci la quantita da ritirare : ");
				q = scan.nextInt();
				sql = "select elimina_contiene("+spa+","+mag+",'"+fil+"','"+prod+"',"+q+");";
				try {
					stmt.executeQuery(sql);
					System.out.println("Ritiro confermato!");
				} catch (SQLException e) {
					System.err.println("Errore! verifica i dati");
					System.out.println("Potresti non avere più la quantità sufficiente..");
				}
				break;		
			default:
				break;
			}

			System.out.println("");
		}while(k != 0);			
	}
	
	
	private int menu()
	{
		int k;
		
		System.out.println("Opzioni");
		System.out.println("* REGISTRA : ");
		System.out.println("    1) Un nuovo cliente");
		System.out.println("    2) Un nuovo contratto");
		System.out.println("    3) Un nuovo prodotto");
		System.out.println("    4) Assegna spazio a un cliente");
		System.out.println("    5) Inserisci un prodotto in uno spazio");
		System.out.println("    6) Un nuovo trasferimento");
		System.out.println("* VISUALIZZA : ");
		System.out.println("    7) Clienti");
		System.out.println("    8) Contratti");
		System.out.println("    9) Prodotti");
		System.out.println("    10) Trasferimenti");
		System.out.println("    11) Spedizioni");
		System.out.println("* RESTITUISCI : ");
		System.out.println("    12) Restituisci un prodotto");
		System.out.println("* ESCI :");
		System.out.println("    0) Esci");	
		
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k < 0 || k > 12);
		
		return k;
	}
	
	public void printInfo(String u, Statement stmt)
	{
		
		String sql = "select * from impiegato where cf = '" + u + "';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println("Nome : " + rs.getString(2));
				System.out.println("Cognome : " + rs.getString(3));
				System.out.println("Filiale N: " + rs.getString(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("");
		
	}
	
	private String getMySubsidiary(String u, Statement stmt)
	{
		String sql = "select impiegato.cod from impiegato where cf = '" + u + "';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getString(1);
			else
				return null;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
		
	

