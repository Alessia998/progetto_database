package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Pack_Magazzino.Persona;

public class Magazziniere extends Persona{
	
	private Scanner scan  = new Scanner(System.in);
	private String data_nascita;
	private String mail;
	private int num;
	private String cod;
	

	public Magazziniere(String cf, String nome, String cognome, int num , String cod ) {
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


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getCod() {
		return cod;
	}


	public void setCod(String cod) {
		this.cod = cod;
	}


	@Override
	public void startOptions(Statement stmt) {
		
		int scelta;
		String sql;
		ResultSet rs = null;
		
		sql = "select * from magazziniere where cf = '"+ this.getCf() +"'";

		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			
			this.setNome(rs.getString("nome"));
			this.setCognome(rs.getString("cognome"));
			this.setNum(rs.getInt("num"));
			this.setCod(rs.getString("cod"));
			
			System.out.println("\nNome   : " + this.getNome());
			System.out.println("Cognome: " + this.getCognome());
			System.out.println("Magazzino (NUM Magazzino - Filiale): " 
			                  + this.getNum() + " - " + this.getCod());
		} catch (SQLException e) {
			System.err.println("Errore di recupero informazioni!");
			e.printStackTrace();
			return;
		}
		
		do {
			
			System.out.println("\n---- Opzioni: ----");
			System.out.println("* VISUALIZZA:");
			System.out.println("    1) Spazi del tuo magazzino");
			System.out.println("    2) Prodotti nel tuo magazzino");
			System.out.println("* REGISTRA:");
			System.out.println("    3) Prodotto in spazio");
			System.out.println("* ESCI :");
			System.out.println("    0) Esci");
			
			System.out.print("\nScelta: ");
			//scan.nextLine();
			scelta = scan.nextInt();
			System.out.println("");
			
			switch(scelta) {
			
			case 1:
				
				sql = "select * from spazio where num = '"+ this.getNum() +"'"
						+ " and cod = '"+ this.getCod() +"'";
				
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("(ID | N Magazzino | Filiale | Descrizione)");
					display(rs,rs.getMetaData().getColumnCount());
				} catch (SQLException e) {
					System.err.println("Errore recupero informazioni spazi");
					e.printStackTrace();
					return;
				}
				
				break;

			case 2:
				
				sql = "select id_spazio, codice, quantita from contiene where"
					+ " num = "+ this.getNum() + " and cod = '"+ this.getCod() +"'";
				
				try {
					rs = stmt.executeQuery(sql);
					System.out.println("(ID Spazio | Codice Prodotto | Quantità)");
					display(rs,rs.getMetaData().getColumnCount());
				} catch (SQLException e) {
					System.err.println("Errore recupero informazioni spazi");
					e.printStackTrace();
					return;
				}
				
				break;

			case 3:
				
				// TODO : Da completare
				
				break;

			case 0: default:
				break;
			}
			
		} while(scelta < 4 && scelta > 0);
		
		
	}

}