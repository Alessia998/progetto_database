package Pack_Magazzino;

import java.sql.Statement;

public abstract class Persona {
	private String cf;
	private String nome;
	private String cognome;
	private String tel;
	private String pass;
	
	public Persona(String cf, String nome, String cognome)
	{
		this.setCf(cf);
		this.setNome(nome);
		this.setCognome(cognome);
	}
	
	public Persona() {
		this.setCf("");
		this.setNome("");
		this.setCognome("");
	}
	
	

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getTel() {
		return tel;
	}
	
	public String getTel(int i) {
			if (this.tel.length()==0) return "''";
			
			return "'"+tel+"'";
		
	}


	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public abstract void startOptions(Statement stmt);

}