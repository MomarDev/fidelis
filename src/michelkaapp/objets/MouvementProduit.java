/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class MouvementProduit {

    private String nom;
    private int quantite;
    private String prix ;
    private String date;
    private String motif;

    public MouvementProduit(String nom, int quantite, String prix, String date) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
        this.date = date;
    }

    public MouvementProduit() {
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateFrenchFormat(date);
    }

}
