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
public class Parametre {

    private String cle;
    private String valeur;
    private boolean affiche;
    private String commentaire;
    private boolean modified = false;
    private String suplementaire;

    public Parametre() {
    }

    public Parametre(String cle, String valeur, boolean affiche, String commentaire, boolean modified) {
        this.cle = cle;
        this.valeur = valeur;
        this.affiche = affiche;
        this.commentaire = commentaire;
        this.modified = modified;
    }

    public String getSuplementaire() {
        return suplementaire;
    }

    public void setSuplementaire(String suplementaire) {
        this.suplementaire = suplementaire;
    }

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public boolean isAffiche() {
        return affiche;
    }

    public void setAffiche(boolean affiche) {
        this.affiche = affiche;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return cle;
    }

}
