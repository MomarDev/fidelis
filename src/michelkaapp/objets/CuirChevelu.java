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
public class CuirChevelu {

    private int id;
    private String cuirCheveulu;

    public CuirChevelu() {
    }

    public CuirChevelu(int id, String cuirCheveulu) {
        this.id = id;
        this.cuirCheveulu = cuirCheveulu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuirCheveulu() {
        return cuirCheveulu;
    }

    public void setCuirCheveulu(String cuirCheveulu) {
        this.cuirCheveulu = cuirCheveulu;
    }

    @Override
    public String toString() {
        return cuirCheveulu;
    }

}
