/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.objets;

import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class SuiviMaison {

    private int id;
    private int client;
    private String conseil;
    private String date;

    public SuiviMaison() {
    }

    public SuiviMaison(int id, int client, String conseil, String date) {
        this.id = id;
        this.client = client;
        this.conseil = conseil;
        this.date = Utilis.dateFrenchFormat(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String getConseil() {
        return conseil;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateFrenchFormat(date);
    }
}
