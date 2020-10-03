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
public class CodeBar {

    private String codeBar;
    private int carte;
    private boolean utilisable;
    private String fidelite;
    private boolean imprimer;

    public CodeBar() {
    }

    public CodeBar(String codeBar, int carte, boolean utilisable, String fidelite, boolean imprimer) {
        this.codeBar = codeBar;
        this.carte = carte;
        this.utilisable = utilisable;
        this.fidelite = fidelite;
        this.imprimer = imprimer;
    }

    public boolean isImprimer() {
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public String getFidelite() {
        return fidelite;
    }

    public void setFidelite(String fidelite) {
        this.fidelite = fidelite;
    }

    public String getCodeBar() {
        return codeBar;
    }

    public int getCarte() {
        return carte;
    }

    public void setCarte(int carte) {
        this.carte = carte;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }

    public boolean isUtilisable() {
        return utilisable;
    }

    public void setUtilisable(boolean utilisable) {
        this.utilisable = utilisable;
    }

}
