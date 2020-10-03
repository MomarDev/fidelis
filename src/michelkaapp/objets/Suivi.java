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
public class Suivi {

    private int id;
    private String date;
    private SuiviMaison conseil;
    private int achat;

    public Suivi() {
    }

    public Suivi(int id, String date, SuiviMaison conseil, int achat) {
        this.id = id;
        this.date = date;
        this.conseil = conseil;
        this.achat = achat;
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

    public SuiviMaison getConseil() {
        return conseil;
    }

    public void setConseil(SuiviMaison conseil) {
        this.conseil = conseil;
    }

    public int getAchat() {
        return achat;
    }

    public void setAchat(int achat) {
        this.achat = achat;
    }
    
    

}
