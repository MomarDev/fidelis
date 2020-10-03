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
public class SousCategoriePrestation {

    private int id;
    private String nom;
    private CategoriePrestation categoriePrestation;
    private boolean estPrestation;
    private boolean estTechnique;
    private String code;

    public SousCategoriePrestation() {
    }

    public SousCategoriePrestation(int id, String nom, CategoriePrestation categoriePrestation, boolean estPrestation, boolean estTechnique) {
        this.id = id;
        this.nom = nom;
        this.categoriePrestation = categoriePrestation;
        this.estPrestation = estPrestation;
        this.estTechnique = estTechnique;
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

    public CategoriePrestation getCategoriePrestation() {
        return categoriePrestation;
    }

    public void setCategoriePrestation(CategoriePrestation categoriePrestation) {
        this.categoriePrestation = categoriePrestation;
    }

    public boolean isEstPrestation() {
        return estPrestation;
    }

    public void setEstPrestation(boolean estPrestation) {
        this.estPrestation = estPrestation;
    }

    public boolean isEstTechnique() {
        return estTechnique;
    }

    public void setEstTechnique(boolean estTechnique) {
        this.estTechnique = estTechnique;
    }

    @Override
    public String toString() {
        return nom;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
