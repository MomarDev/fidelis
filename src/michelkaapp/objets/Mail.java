/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

/**
 *
 * @author leyu
 */
public class Mail implements Cloneable{

    private int id;
    private String date ;
    private String expediteur;
    private DestinataireMail destinataire;
    private String objet;
    private String fichierAttache;
    private String texte;
    private boolean etat;

    public Mail() {
    }

    public Mail(int id, String expediteur, DestinataireMail destinataire, String objet, String fichierAttache, String texte, boolean etat) {
        this.id = id;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.objet = objet;
        this.fichierAttache = fichierAttache;
        this.texte = texte;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public DestinataireMail getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(DestinataireMail destinataire) {
        this.destinataire = destinataire;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getFichierAttache() {
        return fichierAttache;
    }

    public void setFichierAttache(String fichierAttache) {
        this.fichierAttache = fichierAttache;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
