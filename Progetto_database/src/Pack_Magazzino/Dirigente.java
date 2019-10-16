package Pack_Magazzino;

import java.sql.Statement;

import Pack_Magazzino.Persona;

public class Dirigente extends Persona{

	private String data_nascita;
	private String mail;

	public Dirigente(String cf, String nome, String cognome) {
		super(cf,nome,cognome);	
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
		// TODO Auto-generated method stub
		
	}

}