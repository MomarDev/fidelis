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
public class Carte {

    private int id;
    private int reference;
    private int nptsFideliteAchat;
    private int ntpsParrainage;
    private int nptPrestation;
    private int etatCarte;
    private String dateReception;
    private String dateAttribution;
    private String dateExpiration;
    private int familleNombre;
    private String afficheEtat;
    private int nombreUtilisateurCarte;
    private boolean couple;
    private String anniversaire;

    public Carte() {
        couple = false;
    }

    public Carte(int id, int reference, int nptsFideliteAchat, int ntpsParrainage, int nptPrestation, int etatCarte, String dateReception, String dateAttribution, String dateExpiration, int familleNombre) {
        this.id = id;
        this.reference = reference;
        this.nptsFideliteAchat = nptsFideliteAchat;
        this.ntpsParrainage = ntpsParrainage;
        this.nptPrestation = nptPrestation;
        this.etatCarte = etatCarte;
        this.dateReception = dateReception;
        this.dateAttribution = dateAttribution;
        this.dateExpiration = dateExpiration;
        this.familleNombre = familleNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public int getNptsFideliteAchat() {
        return nptsFideliteAchat;
    }

    public void setNptsFideliteAchat(int nptsFideliteAchat) {
        this.nptsFideliteAchat = nptsFideliteAchat;
    }

    public int getNtpsParrainage() {
        return ntpsParrainage;
    }

    public void setNtpsParrainage(int ntpsParrainage) {
        this.ntpsParrainage = ntpsParrainage;
    }

    public int getNptPrestation() {
        return nptPrestation;
    }

    public void setNptPrestation(int nptPrestation) {
        this.nptPrestation = nptPrestation;
    }

    public int getEtatCarte() {
        return etatCarte;
    }

    public void setEtatCarte(int etatCarte) {
        this.etatCarte = etatCarte;
    }

    public String getDateReception() {
        return dateReception;
    }

    public void setDateReception(String dateReception) {
        this.dateReception = Utilis.dateFrenchFormat(dateReception);
    }

    public String getDateAttribution() {
        return dateAttribution;
    }

    public void setDateAttribution(String dateAttribution) {
        this.dateAttribution = Utilis.dateFrenchFormat(dateAttribution);
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateCreation) {
        this.dateExpiration = Utilis.dateFrenchFormat(dateCreation);
    }

    public int getFamilleNombre() {
        return familleNombre;
    }

    public void setFamilleNombre(int familleNombre) {
        this.familleNombre = familleNombre;
    }

    @Override
    public String toString() {
        return reference + "";
    }

    public String getAfficheEtat() {
        return afficheEtat;
    }

    public void setAfficheEtat(String afficheEtat) {
        this.afficheEtat = afficheEtat;
    }

    public int getNombreUtilisateurCarte() {
        return nombreUtilisateurCarte;
    }

    public void setNombreUtilisateurCarte(int nombreUtilisateurCarte) {
        this.nombreUtilisateurCarte = nombreUtilisateurCarte;
    }

    public boolean isCouple() {
        return couple;
    }

    public void setCouple(boolean couple) {
        this.couple = couple;
    }

    public String getAnniversaire() {
        return anniversaire;
    }

    public void setAnniversaire(String anniversaire) {
        this.anniversaire = anniversaire;
    }

}
