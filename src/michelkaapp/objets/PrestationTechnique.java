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
public class PrestationTechnique implements Cloneable{

    private int id;
    private CategoriePrestation categeorie;
    private int client;
    private String date;
    private List<Produit> produits;
    private String montage;
    private String technique;
    private int pause;
    private String observation;
    private TypePrestation typePrestation;

    public PrestationTechnique() {
    }

    public PrestationTechnique(int id, CategoriePrestation categeorie, int client, String date, List<Produit> produits, String montage, String technique, int pause, String observation) {
        this.id = id;
        this.categeorie = categeorie;
        this.client = client;
        this.date = Utilis.dateFrenchFormat(date);
        this.produits = produits;
        this.montage = montage;
        this.technique = technique;
        this.pause = pause;
        this.observation = observation;
    }

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoriePrestation getCategeorie() {
        return categeorie;
    }

    public void setCategeorie(CategoriePrestation categeorie) {
        this.categeorie = categeorie;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateFrenchFormat(date);
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getMontage() {
        return montage;
    }

    public void setMontage(String montage) {
        this.montage = montage;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
        this.pause = pause;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return categeorie.getNom();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
