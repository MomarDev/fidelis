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
public class Trace {

    private int id;
    private String action;
    private String objet;
    private String valeur;
    private String date;
    private User user;

    public Trace() {
    }

    public Trace(int id, String action, String objet, String valeur, String date, User user) {
        this.id = id;
        this.action = action;
        this.objet = objet;
        this.valeur = valeur;
        this.date = Utilis.dateTimeFrenchFormat(date);
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = Utilis.dateTimeFrenchFormat(date);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trace{" + "id=" + id + ", action=" + action + ", objet=" + objet + ", valeur=" + valeur + ", date=" + date + ", user=" + user + '}';
    }

}
