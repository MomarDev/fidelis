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
public class SousFamille {

    private int id;
    private String sousFamille;

    public SousFamille() {
    }

    public SousFamille(int id, String sousFamille) {
        this.id = id;
        this.sousFamille = sousFamille;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSousFamille() {
        return sousFamille;
    }

    public void setSousFamille(String sousFamille) {
        this.sousFamille = sousFamille;
    }

    @Override
    public String toString() {
        return sousFamille;
    }

}
