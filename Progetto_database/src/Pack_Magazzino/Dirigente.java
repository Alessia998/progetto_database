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
	
	public Dirigente(String cf) {
		super(cf,"","");	
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
		System.out.println("\n---- Opzioni: ----");
		System.out.println("* AGGIUNGI:");
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
		System.out.println("* GESTIONE FILIALE:");
		System.out.println("	13) Aggiungi magazzino al tuo filiale ");
		System.out.println("	14) Aggiungi un altro spazio a un magazzino ");
		System.out.println("* ESCI:");
		System.out.println("	0) Esci");
		
		System.out.print("\nScelta: ");
		scelta = scan.nextInt();
		
		switch(scelta)
		{
			case 1:{
				System.out.println("\n---- Inserimento di un nuovo custode ----");
				
				System.out.print("Inserisci il codice fiscale: ");
				scan.nextLine();
				String cf = scan.nextLine().toUpperCase();
				
				System.out.print("Inserisci il nome: ");
				String nome = scan.nextLine();
				
				System.out.print("Inserisci il cognome: ");
				String cognome = scan.nextLine();
				
				System.out.print("Inserisci la data di nascita (aaaa-mm-gg): ");
				String d_nascita = scan.nextLine();
				
				System.out.print("Inserisci il numero di telefono: ");
				String tel = scan.nextLine();
				
				System.out.print("Inserisci la mail: ");
				String mail = scan.nextLine();
				
				sql = "insert into custode values ('"+ cf +"','"+ nome +"','"+ cognome +"',"
						+ "'"+ d_nascita +"', '"+ tel +"', '"+ mail +"')";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Custode inserito correttamente!");
				} catch (SQLException e1) {
					System.err.println("Errore inserimento custode!");
					this.printHint();
					break;
				}
				
				System.out.print("Inserisci la password per il nuovo custode: ");
				String psw = scan.nextLine();
				sql = "create user "+cf+" with password '"+ psw +"';\r\n" + 
						"grant usage on schema public to "+ cf +";\r\n" + 
						"grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to "+ cf +";\r\n" + 
						"grant execute on all functions in schema public to "+ cf +";";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Nuovo utente registrato!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}break;
			case 2:{
				String cod = null;
				int num;
				
				
				System.out.println("\n---- Inserimento di un nuovo magazziniere ----");
				
				System.out.print("Inserisci il codice fiscale: ");
				scan.nextLine();
				String cf = scan.nextLine().toUpperCase();
				
				System.out.print("Inserisci il nome: ");
				String nome = scan.nextLine();
				
				System.out.print("Inserisci il cognome: ");
				String cognome = scan.nextLine();
				
				System.out.print("Inserisci la data di nascita (aaaa-mm-gg): ");
				String d_nascita = scan.nextLine();
				
				System.out.print("Inserisci il numero di telefono: ");
				String tel = scan.nextLine();
				
				System.out.print("Inserisci la mail: ");
				String mail = scan.nextLine();
				
				sql = "select * from filiale where cf='"+this.getCf()+"'";
				
				
				try {
					rs = stmt.executeQuery(sql);
					if(!rs.next())
					{
						System.out.println("\nNon hai filiali collegate al tuo account!");
						break;
					}
					else
					{
						cod = this.getCodFiliale(stmt, scan);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				num = this.getNumMagazzino(stmt, scan, cod);
				if(num == 0) break;
				
				sql = "insert into magazziniere values ('"+ cf +"','"+ nome +"','"+ cognome +"',"
						+ "'"+ d_nascita +"', '"+ tel +"', '"+ mail +"',"+ num +",'"+ cod +"')";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Magazziniere inserito correttamente!");
				} catch (SQLException e1) {
					System.err.println("Errore inserimento Magazziniere!");
					this.printHint();
					break;
				}
				
				System.out.print("Inserisci la password per il nuovo magazziniere: ");
				scan.nextLine();
				String psw = scan.nextLine();
				
				sql = "create user "+ cf +" with password '"+ psw +"';\r\n" + 
						"grant usage on schema public to "+ cf +";\r\n" + 
						"grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale, spazio_contratto to "+ cf +";\r\n" + 
						"grant all on contiene to "+ cf +";\r\n" + 
						"grant execute on all functions in schema public to "+ cf +";";
				
				try {
					
					stmt.executeUpdate(sql);
					System.out.println("Nuovo utente registrato!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}break;
			case 3:{
				
				System.out.println("\n---- Inserimento di un nuovo impiegato ----");
				
				System.out.print("Inserisci il codice fiscale: ");
				scan.nextLine();
				String cf = scan.nextLine();
				cf = cf.toUpperCase();
				
				System.out.print("Inserisci il nome: ");
				String nome = scan.nextLine();
				
				System.out.print("Inserisci il cognome: ");
				String cognome = scan.nextLine();
				
				System.out.print("Inserisci la data di nascita (aaaa-mm-gg): ");
				String d_nascita = scan.nextLine();
				
				System.out.print("Inserisci il numero di telefono: ");
				String tel = scan.nextLine();
				
				System.out.print("Inserisci la mail: ");
				String mail = scan.nextLine();
				
				sql = "insert into impiegato values ('"+ cf +"','"+ nome +"','"+ cognome +"',"
						+ "'"+ d_nascita +"', '"+ tel +"', '"+ mail +"',(select cod from filiale"+
						 " where cf = '"+ this.getCf() +"'))";
				try {
					stmt.executeUpdate(sql);
					System.out.println("Impiegato inserito correttamente!");
				} catch (SQLException e1) {
					System.err.println("Errore inserimento impiegato!");
					this.printHint();
					System.err.println("      - Forse non dirigi ancora niente...");
					break;
				}
				
				System.out.print("Inserisci la password per il nuovo impiegato: ");
				String psw = scan.nextLine();
				sql = "create user "+ cf + " with password '"+ psw +"' createrole;\r\n" + 
						"grant usage on schema public to "+cf+" with grant option;\r\n" + 
						"grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to "+cf+" with grant option;\r\n" + 
						"grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to "+cf+" with grant option;\r\n" + 
						"grant execute on all functions in schema public to "+cf+" with grant option;";
				try {	
					stmt.executeUpdate(sql);
					System.out.println("Nuovo utente registrato!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}break;
			case 4:{
				
				System.out.println("\n---- Inserimento di un nuovo fattorino ----");
				
				scan.nextLine();
				System.out.print("Inserisci il codice fiscale: ");
				String cf = scan.nextLine().toUpperCase();
				
				System.out.print("Inserisci il nome: ");
				String nome = scan.nextLine();
				
				System.out.print("Inserisci il cognome: ");
				String cognome = scan.nextLine();
				
				System.out.print("Inserisci la data di nascita (aaaa-mm-gg): ");
				String d_nascita = scan.nextLine();
				
				System.out.print("Inserisci il numero di telefono: ");
				String tel = scan.nextLine();
				
				System.out.print("Inserisci la mail: ");
				String mail = scan.nextLine();
				
				sql = "insert into fattorino values ('"+ cf +"','"+ nome +"','"+ cognome +"',"
						+ "'"+ d_nascita +"', '"+ tel +"', '"+ mail +"')";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Fattorino inserito correttamente!");
				} catch (SQLException e1) {
					System.err.println("Errore inserimento fattorino!");
					this.printHint();
					break;
				}
				
				System.out.print("Inserisci la password per il nuovo fattorino: ");
				String psw = scan.nextLine();
				
				sql = "create user "+ cf +" with password '"+ psw +"';\r\n" + 
						"grant usage on schema public to "+ cf +";\r\n" + 
						"grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to "+ cf +";\r\n" + 
						"grant all on spedizione, trasferimenti to "+ cf +";\r\n" + 
						"grant execute on all functions in schema public to "+ cf +";";
				
				try {	
					stmt.executeUpdate(sql);
					System.out.println("Nuovo utente registrato!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}break;
			case 5:{
				
				System.out.println("\n---- Inserimento di un nuovo veicolo ----");
				
				System.out.print("Inserisci la targa: ");
				scan.nextLine();
				String targa = scan.nextLine();
				
				System.out.print("Inserisci la marca: ");
				String marca = scan.nextLine();
				
				System.out.print("Inserisci il modello: ");
				String modello = scan.nextLine();
				
				System.out.print("Inserisci la capacita': ");
				int cap = scan.nextInt();
				
				sql = "insert into veicolo values ('"+ targa +"','"+ marca +"','"+ modello +"',"+ cap +")";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Veicolo inserito correttamente!");
				} catch (SQLException e1) {
					System.err.println("Errore inserimento veicolo!");
					System.out.println("Hint: - Verifica di non inserire un veicolo che esiste già.");
					break;
				}
				
			}break;
			case 6:
				sql = "select * from custode";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale   | Nome | Cognome | Data di nascita | Telefono | Mail");
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
					System.out.println("Codice fiscale   | Nome | Cognome | Data di nascita | Telefono | Mail | Codice Magazzino");
					this.display(rs, 8);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 8:
				sql = "select * from impiegato";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale   | Nome | Cognome | Data di nascita | Telefono | Mail | Codice Filiale");
					this.display(rs, 7);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				sql = "select * from veicolo";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Targa  | Marca | Modello | Capacita'");
					this.display(rs, 4);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 10:
				sql = "select spazio.num as Magazzino, spazio.id_spazio, descrizione\r\n" + 
						"from spazio\r\n" + 
						"where cod = (select cod from filiale where cf = '"+this.getCf()+"')\r\n" + 
						"order by num, id_spazio";				
				try {
					int temp_num=0;
					Integer mag, id_spa;
					rs = stmt.executeQuery(sql);
					System.out.println("Numero Magazzino | ID_spazio | Descrizione");
					while(rs.next())
					{
						mag = rs.getInt(1);
						id_spa = rs.getInt(2);
						if(mag != temp_num) 
						{
							temp_num = mag;
							System.out.print(mag + " - ");
						}
						else
						{
							for(int i=0;i< mag.toString().length();i++)
								System.out.print(" ");
							System.out.print("   ");
						}
						
						System.out.print(id_spa + " , ");
						System.out.println(rs.getString(3));
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case 11:
				sql = "select * from cliente";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale   | Nome | Cognome | Telefono | Piano");
					this.display(rs, 5);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 12:
				sql = "select cliente.cf_cli, cliente.nome, cliente.cognome\r\n" + 
						"from cliente, contratto, impiegato\r\n" + 
						"where impiegato.cod = (select filiale.cod \r\n" + 
						"					   from filiale \r\n" + 
						"					   where cf = '"+this.getCf()+"') \r\n" + 
						"					   and\r\n" + 
						"cliente.cf_cli = contratto.cf_cli and\r\n" + 
						"contratto.cf = impiegato.cf";
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("Codice fiscale   | Nome | Cognome");
					this.display(rs, 3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 13:
				String denom, citta, via, tel;
				int num;
				scan.nextLine();
				System.out.println("Inserisci la denominazione del magazzino : ");
				denom = scan.nextLine();
				System.out.println("Inserisci la citta'  : ");
				citta = scan.nextLine();
				System.out.println("Inserisci la via : ");
				via = scan.nextLine();
				System.out.println("Inserisci il numero civico : ");
				num = scan.nextInt();
				scan.nextLine();
				System.out.println("Inserisci il numero di telefono : ");
				tel = scan.nextLine();
				
				sql = "select insert_magazzino('"+denom+"','"+citta+"','"+via+"','"+num+"','"+tel+"',(select cod from filiale where cf = '"+this.getCf()+"'))";
				
				try {
					stmt.execute(sql);
					System.out.println("Magazzino inserito correttamente!");
				} catch (SQLException e1) {
					System.out.println("Errore inserimento magazzino!");
					System.out.println("Forse non stai gestendo nessuna filiale...");
				}
				break;
			case 14:
				sql = "(select cod from filiale where cf = '"+this.getCf()+"')";
				String my_fil = null;
				
			try {
				rs = stmt.executeQuery(sql);

				if( rs.next())
					my_fil = rs.getString(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Non dirigi nessuna filiale...");
				break;
			}
				
				int mag = this.getNumMagazzino(stmt, scan, my_fil);
				scan.nextLine();
				if(mag == 0)
				{
					System.out.println("Operazione annullata.");
					break;
				}
				System.out.println("Inserisci la descrizione dello spazio : ");
				String descr = scan.nextLine();
				
				sql = "select insert_spazio("+mag+",'"+my_fil+"','"+descr+"')";
				
			try {
				stmt.execute(sql);
				System.out.println("Spazio inserito.");
			} catch (SQLException e) {
				System.out.println("Errore di inserimento spazio...");
				e.printStackTrace();
			}

				break;
			default:
				
				scelta = 0;
				break;
		}
		
		}while(scelta != 0 && scelta < 15);
		
	}

}