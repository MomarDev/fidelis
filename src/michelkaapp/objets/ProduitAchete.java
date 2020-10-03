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
public class ProduitAchete {

    private int id;
    private int achat;
    private Produit produit;
    private int quantite;

    public ProduitAchete() {
    }

    public ProduitAchete(int id, int achat, Produit produit, int quantite) {
        this.id = id;
        this.achat = achat;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAchat() {
        return achat;
    }

    public void setAchat(int achat) {
        this.achat = achat;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return produit.getNom() + " quantite: " + quantite;
    }

}
