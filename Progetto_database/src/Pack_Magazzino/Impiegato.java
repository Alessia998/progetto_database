package Pack_Magazzino;

import java.sql.Statement;

import Pack_Magazzino.Persona;

public class Impiegato extends Persona {
	private String data_nascita;
	private String mail;
	private String cod;


	public Impiegato(String cf, String nome, String cognome,String cod) {
		super(cf,nome,cognome);
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
		// TODO Auto-generated method stub
		
	}

}