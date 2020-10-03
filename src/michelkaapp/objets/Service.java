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
public class Service implements Cloneable{

    private TypePrestation typePrestation;
    private CategoriePrestation categoriePrestation;
    private SousCategoriePrestation sousCategoriePrestation;
    private Prestation prestation;
    private String prix;

    public Service() {
    }

    public Service(TypePrestation typePrestation, CategoriePrestation categoriePrestation, SousCategoriePrestation sousCategoriePrestation, Prestation prestation, String prix) {
        this.typePrestation = typePrestation;
        this.categoriePrestation = categoriePrestation;
        this.sousCategoriePrestation = sousCategoriePrestation;
        this.prestation = prestation;
        this.prix = prix;
    }

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }

    public CategoriePrestation getCategoriePrestation() {
        return categoriePrestation;
    }

    public void setCategoriePrestation(CategoriePrestation categoriePrestation) {
        this.categoriePrestation = categoriePrestation;
    }

    public SousCategoriePrestation getSousCategoriePrestation() {
        return sousCategoriePrestation;
    }

    public void setSousCategoriePrestation(SousCategoriePrestation sousCategoriePrestation) {
        this.sousCategoriePrestation = sousCategoriePrestation;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        if (prestation.isFixe()) {
            prix = prestation.getCout() + "";
        } else {
            prix = prestation.getBorneInferieur() + "-" + prestation.getBorneSuperieur();
        }
        
        this.prestation = prestation;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
