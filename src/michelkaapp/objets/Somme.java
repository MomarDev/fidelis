package michelkaapp.objets;

import java.util.Date;

public class Somme {
	private int id;
	private Date date;
	private int somme;
	
	public Somme() {
		
	}
	
	public Somme(int id, Date date, int somme) {
		super();
		this.id = id;
		this.date = date;
		this.somme = somme;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getSomme() {
		return somme;
	}
	public void setSomme(int somme) {
		this.somme = somme;
	}

	
}
