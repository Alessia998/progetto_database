package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.sun.net.httpserver.Authenticator.Result;

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
		String cod, nom, desc, fil;
		int spa, mag;
		
		printInfo(this.getCf(), stmt);
		
		do {
			k = menu();
			switch (k) {
			case 0:
				System.out.println("---EXIT---");
				System.exit(0);
				break;
			case 1:
				scan.nextLine();
				System.out.println("Inserisci il codice fiscale : ");
				this.setCf(scan.nextLine());
				System.out.println("Inserisci il nome : ");
				this.setNome(scan.nextLine());
				System.out.println("Inserisci il cognome : ");
				this.setCognome(scan.nextLine());
				System.out.println("Insersci il numero di telefono :");
				this.setTel(scan.nextLine());
				
				sql = "select crea_cliente('"+this.getCf()+"','"+this.getNome()+"','"+this.getCognome()+"','"+this.getTel()+"');";
				try {
					stmt.executeQuery(sql);
				} catch (SQLException e1) {
					System.err.println(((SQLException)e1).getMessage());
					break;
				}	
				System.out.println("--- Cliente inserito. ---");
				break;
			case 2:
				Contratto con = new Contratto();
				scan.nextLine();
				System.out.println("Insersci il codice fiscale del cliente : ");
				con.setCf_cli(scan.nextLine());
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
					System.err.println(((SQLException)e1).getMessage());
					scan.nextLine();
					scan.nextLine();
					System.out.println("Premi invio per continuare...");
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
				} catch (SQLException e1) {
					System.err.println(((SQLException)e1).getMessage());
					System.err.println("Prodotto non inserito!");
					scan.nextLine();
					scan.nextLine();
					System.out.println("Premi invio per continuare...");	
				}
				break;
			case 4:
				
				break;
			case 5:
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
			case 6:
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
			case 7:
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
			case 8:
	
				break;
			case 9:
				
				break;	
			case 10:
				/*System.out.println("Inserisci il codice prodotto : ");
				cod = scan.nextLine();
				System.out.println("Inserisci il codice della filiale : ");
				fil = scan.nextLine();
				System.out.println("Inserisci il codice del magazzino : ");*/
				//System.out.println(this.getNumMagazzino(stmt, scan));
				//sono qui
				break;		
			default:
				break;
			}
			/*System.out.println("-----------------------------------------------------");
			System.out.println("Risultati:");
			System.out.println("-----------------------------------------------------");
			try {
				ResultSet rs = stmt.executeQuery(sql);
				this.display(rs, 4);
			} catch (SQLException e) {
				System.out.println("Non trovo niente... premi invio");
				scan.nextLine();
			}
			System.out.println("-----------------------------------------------------");*/		
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
		System.out.println("    4) Un nuovo trasferimento");
		System.out.println("* VISUALIZZA : ");
		System.out.println("    5) Clienti");
		System.out.println("    6) Contratti");
		System.out.println("    7) Prodotti");
		System.out.println("    8) Trasferimenti");
		System.out.println("    9) Spedizioni");
		System.out.println("* RESTITUISCI : ");
		System.out.println("    10) Restituisci un prodotto");
		System.out.println("* ESCI :");
		System.out.println("    0) Esci");	
		
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k < 0 || k > 11);
		
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
		
	

}