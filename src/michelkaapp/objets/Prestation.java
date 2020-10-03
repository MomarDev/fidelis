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
public class Prestation {

    private int id;
    private String nom;
    private int cout;
    private SousCategoriePrestation sous_categorie;
    private boolean fixe;
    private int borneInferieur;
    private int borneSuperieur;

    public Prestation() {
    }

    public Prestation(int id, String nom, int cout, SousCategoriePrestation sous_categorie) {
        this.id = id;
        this.nom = nom;
        this.cout = cout;
        this.sous_categorie = sous_categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public SousCategoriePrestation getSous_categorie() {
        return sous_categorie;
    }

    public void setSous_categorie(SousCategoriePrestation sous_categorie) {
        this.sous_categorie = sous_categorie;
    }

    public boolean isFixe() {
        return fixe;
    }

    public void setFixe(boolean fixe) {
        this.fixe = fixe;
    }

    public int getBorneInferieur() {
        return borneInferieur;
    }

    public void setBorneInferieur(int borneInferieur) {
        this.borneInferieur = borneInferieur;
    }

    public int getBorneSuperieur() {
        return borneSuperieur;
    }

    public void setBorneSuperieur(int borneSuperieur) {
        this.borneSuperieur = borneSuperieur;
    }
    

    @Override
    public String toString() {
        return nom ;
    }

}
