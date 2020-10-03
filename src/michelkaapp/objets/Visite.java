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
public class Visite {
    private int id;
    private int referenceCarte;
    private String date;
    private int nombrePoints;

    public Visite() {
    }

    public Visite(int id, int referenceCarte, String date, int nombrePoints) {
        this.id = id;
        this.referenceCarte = referenceCarte;
        this.date = date;
        this.nombrePoints = nombrePoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReferenceCarte() {
        return referenceCarte;
    }

    public void setReferenceCarte(int referenceCarte) {
        this.referenceCarte = referenceCarte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNombrePoints() {
        return nombrePoints;
    }

    public void setNombrePoints(int nombrePoints) {
        this.nombrePoints = nombrePoints;
    }
    
    
    
}
