package michelkaapp.objets;

public class OuvreCaisse {

	private int id;
    private int fond;
    private String operateur;
    private String commentaire;
    private String date;
    
    
    
	public OuvreCaisse() {
		
	}



	public OuvreCaisse(int id, int fond, String operateur, String commentaire, String date) {
		this.id = id;
		this.fond = fond;
		this.operateur = operateur;
		this.commentaire = commentaire;
		this.date = date;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getFond() {
		return fond;
	}



	public void setFond(int fond) {
		this.fond = fond;
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



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "OuvreCaisse [id=" + id + ", fond=" + fond + ", operateur=" + operateur + ", commentaire=" + commentaire
				+ ", date=" + date + "]";
	}
    
    
    
    
    
}
