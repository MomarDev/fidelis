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
public class Densite {
    private int id;
    private String densite;

    public Densite() {
    }

    public Densite(int id, String densite) {
        this.id = id;
        this.densite = densite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDensite() {
        return densite;
    }

    public void setDensite(String densite) {
        this.densite = densite;
    }

    @Override
    public String toString() {
        return densite;
    }
 
}
