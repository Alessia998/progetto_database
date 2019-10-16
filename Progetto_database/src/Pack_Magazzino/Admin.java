package Pack_Magazzino;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin extends Persona{

	public Admin() {
		super();
	}

	@Override
	public void startOptions(Statement stmt) {
		
		String sql;
		int k;
		Scanner scan;

		do {
			
			System.out.println("Connected as ADMIN!");
			System.out.println("Opzioni a disposizione: ");
			System.out.println("1) Inserisci un nuovo dirigente");
			System.out.println("2) Inserisci una nuova filiale");
			System.out.println("3) Stampa tutti dirigenti");
			System.out.println("4) Stampa tutte le filiali");
			System.out.println("5) Elimina un dirigente (solo se non gestisce nessuna filiale)");
			System.out.println("0) Exit");
			
			scan = new Scanner(System.in);
			k = scan.nextInt();
			
			
			switch (k) {
				case 0:
					System.out.println("---EXIT---");
					break;
				case 1:
					String cf,nome,cognome;
					
					System.out.println("Inserimento nuovo dirigente. ");
					System.out.println("Codice fiscale :");
					scan.nextLine();
					cf = scan.nextLine();
					System.out.print("Nome: ");
					nome = scan.nextLine();
					System.out.print("Congome: ");
					cognome = scan.nextLine();
					Dirigente dir = new Dirigente(cf, nome, cognome);
					System.out.print("Inserisci la nuova password per il dirigente: ");
					dir.setPass(scan.nextLine());
					System.out.print("Data di nascita (aaaa-mm-gg) :");
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
					sql = "create user "+dir.getCf()+" with password '"+dir.getPass()+"';"+
							"GRANT USAGE ON SCHEMA public TO "+dir.getCf()+";\r\n" + 
							"GRANT SELECT ON dirigente to "+dir.getCf()+";"+
							"GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO "+dir.getCf()+";";
	
					//System.out.println(sql);
					try {
						stmt.executeUpdate(sql);
						System.out.println("Nuovo dirigente registrato!");
					} catch (SQLException e) {
						e.printStackTrace();
					}
	
					break;
				case 2:
	
					break;
				case 3:
	
					break;
				case 4:
	
					break;
				case 5:
	
					break;
				default:
					break;
			}
		}while(k>0 && k <5);
		scan.close();
	}
}
