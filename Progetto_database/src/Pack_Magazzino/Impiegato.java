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
	
	public void creaContratto()
	{
		
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
						"grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, spazio to "+cli.getCf()+" with grant option;\r\n" + 
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
				//qui aggiungo gestione dello spazio
				break;
			case 5:
				scan.nextLine();
				System.out.println("Inserisci il codice fiscale del cliente : ");
				String cf = scan.nextLine();
				System.out.println("Inserisci la data di trasferimento (aaaa-mm-gg): ");
				String data = scan.nextLine();
				System.out.println("Inserisci il codice fisacle del fattorino : ");
				String cf_fat = scan.nextLine();
				System.out.println("Inserisci la targa del veicolo : ");
				String targa = scan.nextLine();
				System.out.println("Inserisci il paese di destinazione (predefinito: Italia) : ");
				String paese = scan.nextLine();
				System.out.println("Inserisci il codice prodotto che si vuole mandare : ");
				String cProd = scan.nextLine();
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
			case 6:
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
			case 7:
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
			case 8:
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
			case 9:
				sql = "select * from trasferimenti;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Numero trasferimento | Data sped. | CF fattorino | Veicolo | Stato consegna | Fil, Mag partenza | F , M arrivo");
				this.display(rs, 8);
				break;
			case 10:
				sql = "select * from spedizione;";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Numero spedizione | CF fattorino | Data sped. | Veicolo | Indirizzo | Tel | Stato consegna | CF cliente");
				this.display(rs,11);
				break;	
			case 11:
				fil = this.getMySubsidiary(this.getCf(), stmt);
				mag = this.getNumMagazzino(stmt, scan, fil);
				spa = this.getIdSpazio(stmt, scan, fil, mag);
				scan.nextLine();
				System.out.println("Inserisci il codice prodotto da ritirare : ");
				prod = scan.nextLine();
				System.out.println("Inserisci la quantita da ritirare : ");
				q = scan.nextInt();
				sql = "select elimina_contiene('"+spa+"','"+mag+"','"+fil+"','"+prod+"','"+q+"');";
				try {
					stmt.executeQuery(sql);
				} catch (SQLException e) {
					System.err.println("Errore! verifica i dati");
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
		System.out.println("    4)");
		System.out.println("    5) Un nuovo trasferimento");
		System.out.println("* VISUALIZZA : ");
		System.out.println("    6) Clienti");
		System.out.println("    7) Contratti");
		System.out.println("    8) Prodotti");
		System.out.println("    9) Trasferimenti");
		System.out.println("    10) Spedizioni");
		System.out.println("* RESTITUISCI : ");
		System.out.println("    11) Restituisci un prodotto");
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
		
	

