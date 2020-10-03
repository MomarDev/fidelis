package michelkaapp.objets;

public class Tarif implements Cloneable {
	
	private int id;
	private String sexe;
	private String nom;
	private String prestation;
	private String detail;
	private int prix;
	private String prixString;
	
	public Tarif() {
		
	}
	
	
	public Tarif(int id, String sexe, String nom, String prestation, String detail, int prix, String prixString) {
		this.id = id;
		this.sexe = sexe;
		this.nom = nom;
		this.prestation = prestation;
		this.detail = detail;
		this.prix = prix;
		this.prixString = prixString;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrestation() {
		return prestation;
	}
	public void setPrestation(String prestation) {
		this.prestation = prestation;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	
	

	public String getPrixString() {
		return prixString;
	}


	public void setPrixString(String prixString) {
		this.prixString = prixString;
	}


	@Override
	public String toString() {
		return "Tarif [nom=" + nom + ", prestation=" + prestation + ", detail=" + detail + ", prix=" + prix + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
