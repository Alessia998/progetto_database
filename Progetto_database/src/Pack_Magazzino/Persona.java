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
	
	public Object chooseInfo(String sql, Statement stmt, Scanner scan, String nomeTab, String nomeKey) {
		
		//String sql = "select " + nomeKey + " from " + nomeTab;
		ResultSet rs = null;
		int i=1,k;
		
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Dati Sbagliati!");
			e.printStackTrace();
			return null;
		}
		
		
		try {
			System.out.println(" ---- OPZIONI ----");
			while(rs.next()){
				System.out.println(i + ") " + rs.getObject(nomeKey));
				i ++;
			}
		} catch (SQLException e) {
			System.out.println("SQL result error!");
			e.printStackTrace();
			return null;
		}
		
		do {
			System.out.print("Inserire indice dell'elenco (0 per uscire) :");
			k = scan.nextInt();
		}while(k<0 || k>i-1);
		
		if(k == 0)
			return null;
		
		sql = "select "+ nomeKey +" from " + nomeTab + "\r\n" + 
				"limit "+k+" offset "+(k-1)+";";
		
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getObject(nomeKey);
	
		} catch (SQLException e) {
			System.out.println("SQL result error!");
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
	
	//tab - prende in input il nome della tabella che si vuole interrogare
	public String getCfWorker(Statement stmt, Scanner scan,String tab)
	{
		int i = 1,k;
		ResultSet rs=null;
		String sql = "select cf from "+tab+";";
		System.out.println("Scegli il codice fiscale del cliente : ");
		try {
			rs = stmt.executeQuery(sql);		
			while(rs.next())
			{
				System.out.println(i + ") " + rs.getShort(1));
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
		
		sql = "select cf from "+tab
				+"limit "+k+" offset "+(k-1)+";";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getString(1);	
		} catch (SQLException e) {
			e.getMessage();
		}	
		return "";
	}
	
	public abstract void startOptions(Statement stmt);

}