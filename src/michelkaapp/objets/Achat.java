/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

import java.util.List;
import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class Achat {
    private int id;
    private String date ;
    private int client;
    private int montantAvantRemise;
    private int remiseAchat;
    private int montantApresRemise;
    private boolean utiliseFideite;
    private List<Produit> produits;

    public Achat() {
    }

    public Achat(int id, String date, int client, int montantAvantRemise, int remiseAchat, int montantApresRemise, boolean utiliseFideite) {
        this.id = id;
        this.date = Utilis.dateTimeFrenchFormat(date);
        this.client = client;
        this.montantAvantRemise = montantAvantRemise;
        this.remiseAchat = remiseAchat;
        this.montantApresRemise = montantApresRemise;
        this.utiliseFideite = utiliseFideite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateTimeFrenchFormat(date);
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getMontantAvantRemise() {
        return montantAvantRemise;
    }

    public void setMontantAvantRemise(int montantAvantRemise) {
        this.montantAvantRemise = montantAvantRemise;
    }

    public int getRemiseAchat() {
        return remiseAchat;
    }

    public void setRemiseAchat(int remiseAchat) {
        this.remiseAchat = remiseAchat;
    }

    public int getMontantApresRemise() {
        return montantApresRemise;
    }

    public void setMontantApresRemise(int montantApresRemise) {
        this.montantApresRemise = montantApresRemise;
    }

    public boolean isUtiliseFideite() {
        return utiliseFideite;
    }

    public void setUtiliseFideite(boolean utiliseFideite) {
        this.utiliseFideite = utiliseFideite;
    }    

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
    
    
    
}
