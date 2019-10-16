package Pack_Magazzino;

import java.sql.Statement;

import Pack_Magazzino.Persona;

public class Magazziniere extends Persona{
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
		// TODO Auto-generated method stub
		
	}

}