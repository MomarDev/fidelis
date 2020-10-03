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
public class EtatCheveux {

    private int id;
    private String EtatCheveux;

    public EtatCheveux() {
    }

    public EtatCheveux(int id, String EtatCheveux) {
        this.id = id;
        this.EtatCheveux = EtatCheveux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtatCheveux() {
        return EtatCheveux;
    }

    public void setEtatCheveux(String EtatCheveux) {
        this.EtatCheveux = EtatCheveux;
    }

    @Override
    public String toString() {
        return EtatCheveux;
    }

}
