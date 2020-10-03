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
public class DetailInventaire {
    private int id;
    private int inventaire;
    private Produit produit;
    private int quantiteRealVente;
    private int quantiteTheoriqueVente;
    private int quantiteRealService;
    private int quantiteTheoriqueService;
    private String commentaire;

    public DetailInventaire() {
    }

    public DetailInventaire(int id, int inventaire, Produit produit, int quantiteRealVente, int quantiteTheoriqueVente, int quantiteRealService, int quantiteTheoriqueService) {
        this.id = id;
        this.inventaire = inventaire;
        this.produit = produit;
        this.quantiteRealVente = quantiteRealVente;
        this.quantiteTheoriqueVente = quantiteTheoriqueVente;
        this.quantiteRealService = quantiteRealService;
        this.quantiteTheoriqueService = quantiteTheoriqueService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventaire() {
        return inventaire;
    }

    public void setInventaire(int inventaire) {
        this.inventaire = inventaire;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantiteRealVente() {
        return quantiteRealVente;
    }

    public void setQuantiteRealVente(int quantiteRealVente) {
        this.quantiteRealVente = quantiteRealVente;
    }

    public int getQuantiteTheoriqueVente() {
        return quantiteTheoriqueVente;
    }

    public void setQuantiteTheoriqueVente(int quantiteTheoriqueVente) {
        this.quantiteTheoriqueVente = quantiteTheoriqueVente;
    }

    public int getQuantiteRealService() {
        return quantiteRealService;
    }

    public void setQuantiteRealService(int quantiteRealService) {
        this.quantiteRealService = quantiteRealService;
    }

    public int getQuantiteTheoriqueService() {
        return quantiteTheoriqueService;
    }

    public void setQuantiteTheoriqueService(int quantiteTheoriqueService) {
        this.quantiteTheoriqueService = quantiteTheoriqueService;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    
    
    
}
