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
public class GammeProduit {

    private int id;
    private String gamme;

    public GammeProduit() {
    }

    public GammeProduit(int id, String gamme) {
        this.id = id;
        this.gamme = gamme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    @Override
    public String toString() {
        return gamme;
    }

}
