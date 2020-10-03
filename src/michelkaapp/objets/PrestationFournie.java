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
public class PrestationFournie {

   private int id;
   private String date ;
   private int client_id ;
   private int  remise ;
   private int montant ;
   private boolean fidelites ;

    public PrestationFournie(int id, String date, int client_id, int remise, int montant, boolean fidelites) {
        this.id = id;
        this.date = date;
        this.client_id = client_id;
        this.remise = remise;
        this.montant = montant;
        this.fidelites = fidelites;
    }

    public PrestationFournie() {
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
        this.date = date;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public boolean isFidelites() {
        return fidelites;
    }

    public void setFidelites(boolean fidelites) {
        this.fidelites = fidelites;
    }
   
}
