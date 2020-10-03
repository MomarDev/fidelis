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
public class TypePrestation implements Cloneable {

    private int id;
    private String nom;
    private boolean estTechnique;

    public TypePrestation() {

    }

    public TypePrestation(int id, String nom, boolean estTechnique) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
