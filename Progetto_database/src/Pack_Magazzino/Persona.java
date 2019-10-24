package Pack_Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
	
	public void display(ResultSet rs, int colNum)
	{
		try {
			while(rs.next())
			{
				for(int i=1; i <= colNum; i++)
				{
					System.out.print(rs.getString(i) + (i<colNum?", ":";\n"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
	}
	
	public String getCodFiliale(Statement stmt, Scanner scan)
	{
		int i=1,k;
		String sql = "select cod from filiale;";
		ResultSet rs = null;
		System.out.println("Scegli il codice filiale : ");
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println(i + ") " + rs.getString(1));
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k<0 || k>i-1);
		
		sql = "select cod from filiale\r\n" + 
				"limit "+k+" offset "+(k-1)+";";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getString(1);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public int getNumMagazzino(Statement stmt, Scanner scan, String fil)
	{
		int i=1,k;
		String sql = "select magazzino.num from filiale,magazzino\r\n" + 
				"where filiale.cod = magazzino.cod and filiale.cod = '"+fil+"'";
		ResultSet rs = null;
		System.out.println("Scegli il numero magazzino : ");
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println(i + ") " + rs.getString(1));
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k<0 || k>i-1);
		
		sql = "select magazzino.num from filiale,magazzino\r\n" + 
				"where filiale.cod = magazzino.cod and filiale.cod = '"+fil+"'"+
				"limit "+k+" offset "+(k-1)+";";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getInt(1);

			
		} catch (SQLException e) {
			e.getMessage();
		}	
		return 0;
	}
	
	public int getIdSpazio(Statement stmt, Scanner scan, String fil, int mag)
	{
		int i=1,k;
		String sql = "select spazio.id_spazio from spazio, magazzino, filiale\r\n" + 
				"where filiale.cod = magazzino.cod and magazzino.num = spazio.num\r\n" + 
				"	  and magazzino.cod = spazio.cod and filiale.cod = '"+fil+"'\r\n" + 
				"	  and magazzino.num = '"+mag+"';";
		ResultSet rs = null;
		System.out.println("Scegli il numero spazio : ");
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println(i + ") " + rs.getString(1));
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do {
			System.out.print("Scelta : ");
			k = scan.nextInt();
		}while(k<0 || k>i-1);
		
		sql = "select spazio.id_spazio from spazio, magazzino, filiale\r\n" + 
				"where filiale.cod = magazzino.cod and magazzino.num = spazio.num\r\n" + 
				"	  and magazzino.cod = spazio.cod and filiale.cod = '"+fil+"'\r\n" + 
				"	  and magazzino.num = '"+mag+"'"+
				"limit "+k+" offset "+(k-1)+";";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getInt(1);

			
		} catch (SQLException e) {
			e.getMessage();
		}	
		return 0;
	}
	
	public abstract void startOptions(Statement stmt);

}