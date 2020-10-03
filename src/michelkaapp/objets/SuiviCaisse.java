package michelkaapp.objets;

import michelkaapp.utilis.Utilis;

public class SuiviCaisse {
	private int id;
    private String action;
    private String objet;
    private String valeur;
    private String date;
    private User user;
    private String modePaiement;
    private int restant;

    public SuiviCaisse() {
    }

    public SuiviCaisse(int id, String action, String objet, String valeur, String date, User user,String modePaiement,int restant) {
        this.id = id;
        this.action = action;
        this.objet = objet;
        this.valeur = valeur;
        this.date = Utilis.dateTimeFrenchFormat(date);
        this.user = user;
        this.restant = restant;
        this.modePaiement = modePaiement;
    }
    

    public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public int getRestant() {
		return restant;
	}

	public void setRestant(int restant) {
		this.restant = restant;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateTimeFrenchFormat(date);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@Override
	public String toString() {
		return "SuiviCaisse [id=" + id + ", action=" + action + ", objet=" + objet + ", valeur=" + valeur + ", date="
				+ date + ", user=" + user + ", modePaiement=" + modePaiement + ", restant=" + restant + "]";
	}

    
}
