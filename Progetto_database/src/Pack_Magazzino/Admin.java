package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin extends Persona{
	
	
	public Admin() {
		super();
	}
	
	public Admin(String u) {
		super(u,"","");
		
	}
	
	

	@Override
	public void startOptions(Statement stmt) {
		
		String sql;
		int k;
		Scanner scan;
		ResultSet res = null;
		System.out.println(" ---- Connected as ADMIN! ---- ");

		do {
			
			System.out.println("Opzioni:");
			System.out.println("Opzioni a disposizione: ");
			System.out.println("1) Inserisci un nuovo dirigente");
			System.out.println("2) Inserisci una nuova filiale");
			System.out.println("3) Stampa tutti dirigenti");
			System.out.println("4) Stampa tutte le filiali");
			System.out.println("5) Elimina un dirigente (solo se non gestisce nessuna filiale)");
			System.out.println("0) Exit");
			System.out.print("Scelta : ");
			
			scan = new Scanner(System.in);
			k = scan.nextInt();
			
			
			switch (k) {
				case 0:
					System.out.println("---EXIT---");
					break;
				case 1:{
					
					String cf,nome,cognome;
					
					System.out.println("Inserimento nuovo dirigente. ");
					System.out.println("Codice fiscale :");
					scan.nextLine();
					cf = scan.nextLine();
					cf = cf.toUpperCase();
					System.out.print("Nome: ");
					nome = scan.nextLine();
					System.out.print("Congome: ");
					cognome = scan.nextLine();
					Dirigente dir = new Dirigente(cf, nome, cognome);
					System.out.print("Inserisci la nuova password per il dirigente: ");
					dir.setPass(scan.nextLine());
					System.out.print("Data di nascita (aaaa-mm-gg): ");
					dir.setData_nascita(scan.nextLine());
					System.out.print("Telefono: ");
					dir.setTel(scan.nextLine());
					System.out.print("Mail:");
					dir.setMail(scan.nextLine());
					sql = "insert into dirigente values ('"+dir.getCf()+"','"+dir.getNome()+"','"+dir.getCognome()+"',"+dir.getData_nascita(1)+","+dir.getTel(1)+","+dir.getMail(1)+")";
					try {
						stmt.execute(sql);
					} catch (SQLException e) {
						System.err.println("Formato dati incoretto!... )");
						e.printStackTrace();
						scan.close();
						return;	
					}
					sql = "create user "+dir.getCf()+" with password '"+dir.getPass()+"' createrole;\n" + 
							"grant usage on schema public to "+dir.getCf()+";\n" + 
							"grant select on all tables in schema public to "+dir.getCf()+";\n" + 
							"grant all on custode, magazziniere, impiegato, fattorino, veicolo, my_seq1, my_seq2 to "+dir.getCf()+";\n" + 
							"grant execute on all functions in schema public to "+this.getCf()+";";
					try {
						stmt.executeUpdate(sql);
						System.out.println("Nuovo dirigente registrato!");
					} catch (SQLException e) {
						e.printStackTrace();
					}
	
					break;
				}
				case 2:{
					
					System.out.println("Inserimento nuova Filiale");
					System.out.print("Inserisci il codice filiale (10 caratteri): ");
					scan.nextLine();
					String cod = scan.nextLine();
					
					System.out.print("Inserisci il nome della filiale: ");
					String nome = scan.nextLine();
					
					System.out.print("Inserisci la città della filiale: ");
					String citta = scan.nextLine();
					
					System.out.print("Inserisci la via della filiale: ");
					String via = scan.nextLine();
					
					System.out.print("Inserisci il numero civico della filiale: ");
					String civico = scan.nextLine();
					
					System.out.print("Inserisci il numero di telefono della filiale: ");
					String tel = scan.nextLine();
					
					System.out.println("Scegli il dirigente da associare alla nuova filiale:");
					sql = "select cf from dirigente";
					
					try 
					{
						res = stmt.executeQuery(sql);
						for (int i = 0; res.next(); i++) 
							System.out.println((i+1) + ") " + res.getString(1));
						
					} catch (SQLException e) {
						System.err.println("Errore di query al DB!");
						e.printStackTrace();
					}
						
					System.out.print("Inserisci codifce fiscale: ");
					String cf = scan.nextLine();
					
					sql = "insert into filiale values ('"+ cod +"','"+ nome +"','"+
							citta +"','"+ via +"',"+ civico +",'"+ tel +"','"+ cf +"')";
					
					try {
						
						stmt.executeUpdate(sql);
						System.out.println("Filiale inserita correttamente!");
					} catch (SQLException e) {
						System.err.println("Codice Fiscale Errato !");
					}
					break;
				}
				case 3:{
	
					System.out.println("Stampa di tutti i dirigenti:");
					sql = "select * from dirigente";
					
					try {
						res = stmt.executeQuery(sql);
						while(res.next()) {
						    for(int columnIndex = 1; columnIndex <= res.getMetaData().getColumnCount(); columnIndex++) {
						        Object object = res.getObject(columnIndex);
						        System.out.printf("%s  ", object == null ? "NULL" : object.toString());
						    }
						    System.out.println();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				}
				case 4:{
	
					System.out.println("Stampa di tutte le filiali:");
					sql = "select * from filiale";
					
					try {
						res = stmt.executeQuery(sql);
						while(res.next()) {
						    for(int columnIndex = 1; columnIndex <= res.getMetaData().getColumnCount(); columnIndex++) {
						        Object object = res.getObject(columnIndex);
						        System.out.printf("%s  ", object == null ? "NULL" : object.toString());
						    }
						    System.out.println();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				}
				case 5:
					int result = 0;
					System.out.println("Iserisci il codice fiscale dirigente da eliminare :");
					scan.nextLine();
					String nome = scan.nextLine();
					sql = "delete from dirigente\n" + 
							"where cf =\n" + 
							"(select res.cf from (select d.cf from dirigente d\n" + 
							"except \n" + 
							"select d.cf from dirigente d,filiale f\n" + 
							"where d.cf = f.cf) as res\n" + 
							"where res.cf = '"+nome+"');";

					try {					
						result = stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
						System.err.println("Codice dirigente Errato !");
					}
					
					if( result == 1)
					{
						sql = "REASSIGN OWNED BY "+nome+" TO "+this.getCf()+";\n" + 
						"DROP OWNED BY "+nome+";\n" + 
						"drop user "+nome+";";
						
						try {
							stmt.execute(sql);
							System.out.println("Dirigente rimosso correttamente!");
							stmt.close();
						} catch (SQLException e) {
							System.err.println("Non si riesece a rimuovere l'utente da pgadmin!");
						}			
					}
					else
					{
						System.out.println("Dirigente non esiste oppure non lo si pu� rimuovere");
					}
					break;
				default:
					break;
			}
		}while(k>0 && k <6);
		scan.close();
	}
}
