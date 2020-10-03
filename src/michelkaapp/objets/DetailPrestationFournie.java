/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leyu
 */
public class DetailPrestationFournie {

    private int id;
    private int id_prestation_fourni;
    private int id_prestation;
//    private int id_marque;
    private String technique;
    private String montage;
    private int temps_de_pause;
    private String obeservations;
    private int cout_prestation;
    private List<Produit> produits;

    public DetailPrestationFournie(int id, int id_prestation_fourni, int id_prestation, String technique, String montage, int temps_de_pause, String obeservations, int cout_prestation) {
        this.id = id;
        this.id_prestation_fourni = id_prestation_fourni;
        this.id_prestation = id_prestation;
//        this.id_marque = id_marque;
        this.technique = technique;
        this.montage = montage;
        this.temps_de_pause = temps_de_pause;
        this.obeservations = obeservations;
        this.cout_prestation = cout_prestation;
        produits = new ArrayList<>();
    }

    public DetailPrestationFournie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_prestation_fourni() {
        return id_prestation_fourni;
    }

    public void setId_prestation_fourni(int id_prestation_fourni) {
        this.id_prestation_fourni = id_prestation_fourni;
    }

    public int getId_prestation() {
        return id_prestation;
    }

    public void setId_prestation(int id_prestation) {
        this.id_prestation = id_prestation;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getMontage() {
        return montage;
    }

    public void setMontage(String montage) {
        this.montage = montage;
    }

    public int getTemps_de_pause() {
        return temps_de_pause;
    }

    public void setTemps_de_pause(int temps_de_pause) {
        this.temps_de_pause = temps_de_pause;
    }

    public String getObeservations() {
        return obeservations;
    }

    public void setObeservations(String obeservations) {
        this.obeservations = obeservations;
    }

    public int getCout_prestation() {
        return cout_prestation;
    }

    public void setCout_prestation(int cout_prestation) {
        this.cout_prestation = cout_prestation;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }


}
