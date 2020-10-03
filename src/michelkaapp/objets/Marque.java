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
public class Marque {

    private int id;
    private String marque;
    private int utilisation;

    public Marque() {
    }

    public Marque(int id, String marque, int utilisation) {
        this.id = id;
        this.marque = marque;
        this.utilisation = utilisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(int utilisation) {
        this.utilisation = utilisation;
    }

    @Override
    public String toString() {
        return marque;
    }

}
