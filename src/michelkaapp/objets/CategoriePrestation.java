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
public class CategoriePrestation {

    private int id;
    private String nom;
    private TypePrestation typePrestation;
    private String code;
    private boolean estSousCategorie;
    private boolean estPrestation;
    private boolean estTechnique;
    
    public CategoriePrestation() {
    }

    public CategoriePrestation(int id, String nom, TypePrestation typePrestation, String code, boolean estSousCategorie, boolean estPrestation, boolean estTechnique) {
        this.id = id;
        this.nom = nom;
        this.typePrestation = typePrestation;
        this.code = code;
        this.estSousCategorie = estSousCategorie;
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

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEstSousCategorie() {
        return estSousCategorie;
    }

    public void setEstSousCategorie(boolean estSousCategorie) {
        this.estSousCategorie = estSousCategorie;
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

    
}
