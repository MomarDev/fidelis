package michelkaapp.objets;

import javafx.scene.control.Button;

public class Restant {
	private int id;
	private String nomCli;
	private String prenomCli;
	private String commentaire;
	private String telephoneCli;
	private int restant;
	private Button button;
	
	
	
	public Restant() {
	
	}



	public Restant(int id, String nomCli, String prenomCli, String commentaire, String telephoneCli, int restant) {
		this.id = id;
		this.nomCli = nomCli;
		this.prenomCli = prenomCli;
		this.commentaire = commentaire;
		this.telephoneCli = telephoneCli;
		this.restant=restant;
		
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNomCli() {
		return nomCli;
	}



	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}



	public String getPrenomCli() {
		return prenomCli;
	}



	public void setPrenomCli(String prenomCli) {
		this.prenomCli = prenomCli;
	}



	public String getCommentaire() {
		return commentaire;
	}



	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



	public String getTelephoneCli() {
		return telephoneCli;
	}



	public void setTelephoneCli(String telephoneCli) {
		this.telephoneCli = telephoneCli;
	}



	public int getRestant() {
		return restant;
	}



	public void setRestant(int restant) {
		this.restant = restant;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "Restant [id=" + id + ", nomCli=" + nomCli + ", prenomCli=" + prenomCli + ", commentaire=" + commentaire
				+ ", telephoneCli=" + telephoneCli + ", restant=" + restant + "]";
	}


	

	
	

	
	
}
