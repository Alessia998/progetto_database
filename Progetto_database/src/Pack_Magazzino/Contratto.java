/*
 * Classe : Impiegato
 * Descrizione : Utilizzata per facilitare la gestione delle informazioni di ogni contratto nel codice Java
 * */
package Pack_Magazzino;

import java.sql.Statement;
import java.util.Scanner;

public class Contratto {
	private String num_c;
	private String data_inizio;
	private String data_fine;
	private int num_spazi;
	private String cf;
	private String cf_cli; 
	private String nome_piano;
	
	
	
	public Contratto(String num_c, String data_inizio, int num_spazi, String cf, String cf_cli)
	{
		setNum_c(num_c);
		setData_inizio(data_inizio);
		setData_fine(data_fine);
		setNum_spazi(num_spazi);
		setCf(cf);
		setCf_cli(cf_cli);
	}
	
	public Contratto() {
		
	}


	public String getNum_c() {
		return num_c;
	}



	public void setNum_c(String num_c) {
		this.num_c = num_c;
	}



	public String getData_inizio() {
		return data_inizio;
	}



	public void setData_inizio(String data_inizio) {
		this.data_inizio = data_inizio;
	}



	public String getData_fine() {
		return data_fine;
	}



	public void setData_fine(String data_fine) {
		this.data_fine = data_fine;
	}



	public int getNum_spazi() {
		return num_spazi;
	}



	public void setNum_spazi(int num_spazi) {
		this.num_spazi = num_spazi;
	}



	public String getCf() {
		return cf;
	}



	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setCf_cli(Statement stmt)
	{
		this.cf_cli = new Cliente().getCfCliente(stmt, new Scanner(System.in));
	}

	public String getCf_cli() {
		return cf_cli;
	}
	



	public void setCf_cli(String cf_cli) {
		this.cf_cli = cf_cli;
	}



	public String getNome_piano() {
		return nome_piano;
	}



	public void setNome_piano(String nome_piano) {
		this.nome_piano = nome_piano;
	}
}
