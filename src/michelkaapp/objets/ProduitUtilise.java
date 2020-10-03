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
public class ProduitUtilise {

    int id_produit_utilise;
    int id_prestationTechnique;
    int id_produit;
//    private Produit produit;

    public ProduitUtilise(int id_produit_utilise, int id_prestationTechnique, int id_produit) {
        this.id_produit_utilise = id_produit_utilise;
        this.id_prestationTechnique = id_prestationTechnique;
        this.id_produit = id_produit;
    }


    public ProduitUtilise() {
    }

    public int getId_produit_utilise() {
        return id_produit_utilise;
    }

    public void setId_produit_utilise(int id_produit_utilise) {
        this.id_produit_utilise = id_produit_utilise;
    }

    public int getId_prestationTechnique() {
        return id_prestationTechnique;
    }

    public void setId_prestationTechnique(int id_prestationTechnique) {
        this.id_prestationTechnique = id_prestationTechnique;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

}
