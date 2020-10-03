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
public class Parrainage {

    private int id;
    private int reference_carte;
    private String date;
    private String nomParraine;
    private String prenomParraine;
    private String telephone;
    private String parraine;
    public Parrainage() {
    }

    public Parrainage(int id, int reference_carte, String date, String nomParraine, String prenomParraine) {
        this.id = id;
        this.reference_carte = reference_carte;
        this.date = date;
        this.nomParraine = nomParraine;
        this.prenomParraine = prenomParraine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReference_carte() {
        return reference_carte;
    }

    public void setReference_carte(int reference_carte) {
        this.reference_carte = reference_carte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNomParraine() {
        return nomParraine;
    }

    public void setNomParraine(String nomParraine) {
        this.nomParraine = nomParraine;
    }

    public String getPrenomParraine() {
        return prenomParraine;
    }

    public void setPrenomParraine(String prenomParraine) {
        this.prenomParraine = prenomParraine;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getParraine() {
        return parraine;
    }

    public void setParraine(String parraine) {
        this.parraine = parraine;
    }
    

    @Override
    public String toString() {
        return "Parrainage{" + "reference_carte du parrain=" + reference_carte + ", date=" + date + ", nomParraine=" + nomParraine + ", prenomParraine=" + prenomParraine + '}';
    }

}
