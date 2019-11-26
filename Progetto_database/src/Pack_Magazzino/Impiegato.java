package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


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
		String cod = null, nom, desc, fil, prod;
		int spa, mag, q;
		
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
						"grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to "+cli.getCf()+" with grant option;\r\n" + 
						"grant all on spedizione, prod_sped, my_seq1, contiene to "+cli.getCf()+" with grant option;\r\n" + 
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
				System.out.println("Insersci l'indice codice fiscale del cliente : ");
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
				String num_c = null;
				scan.nextLine();
				mag = this.getNumMagazzino(stmt, scan, this.getMySubsidiary(this.getCf(), stmt)); 
				if(mag == 0) break;
				int id_spazio = this.getIdSpazio(stmt, scan, this.getMySubsidiary(this.getCf(), stmt), mag);
				if(id_spazio == 0) break;
				sql = "select num_c from contratto";
				System.out.println("Inserisci l'indice del contratto : ");

				try {
				num_c = this.chooseInfo(sql, stmt, scan, "contratto", "num_c").toString();
				}catch(Exception e) {
					break;
				}
				
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
				//194-238 righe da fixare
				System.out.println("Scelta del Cliente: ");
				sql = "select cf_cli from cliente";
				fil = this.getMySubsidiary(this.getCf(), stmt);
				String cf_cli = "";

				try {
					cf_cli = this.chooseInfo(sql, stmt, scan, "cliente", "cf_cli").toString();
				}catch(Exception e) {
					break;
				}
				
				int scelta;
				List<Integer> magazzini = new ArrayList<Integer>();
				sql = "select num\r\n" + 
						"from spazio_contratto, contratto \r\n" + 
						"where spazio_contratto.num_c = contratto.num_c \r\n" + 
						"and contratto.cf_cli = '"+ cf_cli +"'\r\n" + 
						"and spazio_contratto.cod = '"+ fil +"'\r\n" + 
						"and data_fine >= current_date\r\n"
						+ "group by num";
				
				System.out.println("Scelta del magazzino (0 per uscire): ");
				try {
					
					int i=1;
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						magazzini.add(rs.getInt(1));
						System.out.println(i++ + ") " + magazzini.get(magazzini.size() - 1));
					}				
				}catch(Exception e){
					break;
				}
				
				
				System.out.println("Scelta : ");
				do {
					scelta = scan.nextInt();
				}while(scelta < 0 || scelta > magazzini.size());
				
				if(scelta == 0) break;
				
				mag = magazzini.get(scelta - 1);
				
				
				List<Integer> spazi = new ArrayList<Integer>();
				sql = "select id_spazio\r\n" + 
						"from spazio_contratto, contratto \r\n" + 
						"where spazio_contratto.num_c = contratto.num_c \r\n" + 
						"and contratto.cf_cli = '"+ cf_cli +"'\r\n" + 
						"and spazio_contratto.cod = '"+ fil +"'\r\n" + 
						"and num = "+ mag +"\r\n" +
						"and data_fine >= current_date";
				
				System.out.println("Scelta dello spazio (0 per uscire): ");
				try {
					
					int i=1;
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						spazi.add(rs.getInt(1));
						System.out.println(i++ + ") " + spazi.get(spazi.size() - 1));
					}				
				}catch(Exception e){
					break;
				}
				

				System.out.println("Scelta : ");
				do {
					scelta = scan.nextInt();
				}while(scelta < 0 || scelta > spazi.size());
				
				if(scelta == 0) break;
				
				spa = spazi.get(scelta - 1);
				
				sql = "select codice from prodotto";
				try {
					System.out.println("Scelta del prodotto");
					cod = this.chooseInfo(sql, stmt, scan, "prodotto", "codice").toString();
				}catch(Exception e) {
					break;
				}				
				
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
				
				break;
			case 6:
				
				String cf;
				String targa;
				String cProd;
				String c1, c2;
				Integer n1, n2;
				String data;
				
				/*	Workflow:
				 * 		- Faccio scegliere il cliente
				 * 		- faccio scegliere il prodotto tra quelli del cliente
				 * 		- Faccio scegliere la quantità
				 * 		- faccio scegliere la filiale e il magazzino di arrivo
				 * 		- Faccio scegliere fattorino, targa, data
				 * 
				 * 
				 * */
				
				
				// Scelta del cliente
				scan.nextLine();
				System.out.println("\n Scegli il codice fiscale del cliente : ");
				sql = "select cf_cli from cliente";
				try {
					cf = this.chooseInfo(sql, stmt, scan, "cliente", "cf_cli").toString();
				} catch (Exception e) {
					break;
				}
				
				c1 = this.getMySubsidiary(this.getCf(), stmt);
				
				//Scelta del prodotto da mandare tra quelli del cliente
				scan.nextLine();
				System.out.println("\n Inserisci l'indice del prodotto che si vuole trasferire : ");
				sql = "select distinct(prodotto.codice) from contratto, spazio_contratto sp_co, spazio, contiene, prodotto\r\n" + 
						"	where cf_cli = '"+ cf +"' and\r\n" + 
						"	sp_co.num_c = contratto.num_c and\r\n" + 
						"	spazio.cod = sp_co.cod and\r\n" + 
						"	spazio.num = sp_co.num and\r\n" + 
						"	spazio.id_spazio = sp_co.id_spazio and\r\n" + 
						"	spazio.cod = contiene.cod and\r\n" + 
						"	spazio.num = contiene.num and\r\n" + 
						"	spazio.id_spazio = contiene.id_spazio and\r\n" + 
						"	contiene.codice = prodotto.codice\r\n" +
						"   and contiene.cod = '"+ c1 +"'";
				try {
					cProd = this.chooseInfo(sql, stmt, scan, "prodotto", "codice").toString();
				} catch (Exception e) {
					break;
				}
				
				
				System.out.println("\n Seleziona il magazzino di partenza: ");
				
				sql = "select distinct(sp.num)\r\n" + 
						"from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr\r\n" + 
						"where pr.codice = co.codice and\r\n" + 
						"	sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = '"+ cProd +"' and\r\n" + 
						"	sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c\r\n" + 
						"	and contr.cf_cli = '"+ cf +"' and sp.cod = '"+ c1 +"'";
				
				try {
					n1 = (Integer)this.chooseInfo(sql, stmt, scan, "spazio", "num");
				}catch(Exception e) {
					System.out.println("Magazzino non selezionato");
					break;
				}
				
				System.out.println("Insersci la quantità che deve essere trasferita :"); 
				q = scan.nextInt();
				
				System.out.println("Scegli filiale di arrivo: ");
				c2 = this.getCodFiliale(stmt, scan);
				System.out.println("Scegli magazzino di arrivo: ");
				n2 = this.getNumMagazzino(stmt, scan, c2);
				
				
				scan.nextLine();
				System.out.println("Inserisci la data di trasferimento (aaaa-mm-gg): ");
				data = scan.nextLine();
				
				String cf_fat = this.getCfWorker(stmt, scan, "fattorino");
				if(cf_fat == null) break;
				
				System.out.println("Inserisci la targa del veicolo : ");
				sql = "select targa from veicolo";
				try {
					targa = this.chooseInfo(sql, stmt, scan, "veicolo", "targa").toString();
				} catch (Exception e) {
					break;
				}
				
				sql = "select trasferisci('"+cf+"','"+data+"','"+cf_fat+"','"+targa+"'"
						+ ",'"+n1+"','"+c1+"','"+n2+"','"+c2+"','"+cProd+"',"+q+");";

				try {
					stmt.execute(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("Errore registrazione trasferimento");
					break;
				}

				System.out.println("Il trasferimento è stato registrato");
				
				break;
			case 7:
				sql = "select * from cliente";
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
				sql = "select * from contratto";
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
				sql = "select * from prodotto";
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
				sql = "select * from trasferimenti";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Numero trasferimento | Data sped. | CF fattorino | Veicolo | Stato consegna | Fil, Mag partenza | F , M arrivo");
				this.display(rs, 9);
				break;
			case 11:
				sql = "select * from spedizione";
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
				if (mag == 0) break;
				spa = this.getIdSpazio(stmt, scan, fil, mag);
				if (spa == 0) break;
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
		
		System.out.println("\n");
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
		
	

