package michelkaapp.objets;

public class FermeCaisse {

	private int id;
    private int carte;
    private int espece;
    private int cheque;
    private int attente;
    private String operateur;
    private String commentaire;
    private int chiffre;
    
  
    
	public FermeCaisse() {
	
	}
	public FermeCaisse(int id, int carte, int espece, int cheque, int attente, String operateur, String commentaire, int chiffre) {
		this.id = id;
		this.carte = carte;
		this.espece = espece;
		this.cheque = cheque;
		this.attente = attente;
		this.operateur = operateur;
		this.commentaire = commentaire;
		this.chiffre=chiffre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarte() {
		return carte;
	}
	public void setCarte(int carte) {
		this.carte = carte;
	}
	public int getEspece() {
		return espece;
	}
	public void setEspece(int espece) {
		this.espece = espece;
	}
	public int getCheque() {
		return cheque;
	}
	public void setCheque(int cheque) {
		this.cheque = cheque;
	}
	public int getAttente() {
		return attente;
	}
	
	public int getChiffre() {
		return chiffre;
	}
	public void setChiffre(int chiffre) {
		this.chiffre = chiffre;
	}
	public void setAttente(int attente) {
		this.attente = attente;
	}
	public String getOperateur() {
		return operateur;
	}
	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	@Override
	public String toString() {
		return "FermeCaisse [id=" + id + ", carte=" + carte + ", espece=" + espece + ", cheque=" + cheque + ", attente="
				+ attente + ", operateur=" + operateur + ", commentaire=" + commentaire + ", chiffre=" + chiffre + "]";
	}
	
	
    
    
    
}
