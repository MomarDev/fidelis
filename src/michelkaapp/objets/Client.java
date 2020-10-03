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
public class Client implements Cloneable {

    private int id;
    private String nom;
    private String prenom;
    private String anniversaire;
    private String mobile1;
    private String mobile2;
    private String telBureau;
    private String telDomicile;
    private String email;
    private String adresse;
    private Profession profession;
    private TypeCheveux typeCheveux;
    private CuirChevelu cuirChevelu;
    private Densite densiteCheveux;
    private TextureCheveux textureCheveux;
    private EtatCheveux etatCheveux;
    private Carte carte;
    private boolean est_proprietaire_carte;
    private boolean utiliseCarte;
    private int nombreUtilisateur;
    private boolean estTechnique;

    public Client() {
        estTechnique = false;
    }

    public boolean isEstTechnique() {
        return estTechnique;
    }

    public void setEstTechnique(boolean estTechnique) {
        this.estTechnique = estTechnique;
    }

    public Client(int id, String nom, String prenom, String anniversaire, String mobile1, String mobile2, String telBureau, String telDomicile, String email, String adresse, Profession profession, TypeCheveux typeCheveux, CuirChevelu cuirChevelu, Densite densiteCheveux, TextureCheveux textureCheveux, EtatCheveux etatCheveux, Carte carte, boolean est_proprietaire_carte, boolean utiliseCarte) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.anniversaire = anniversaire;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.telBureau = telBureau;
        this.telDomicile = telDomicile;
        this.email = email;
        this.adresse = adresse;
        this.profession = profession;
        this.typeCheveux = typeCheveux;
        this.cuirChevelu = cuirChevelu;
        this.densiteCheveux = densiteCheveux;
        this.textureCheveux = textureCheveux;
        this.etatCheveux = etatCheveux;
        this.carte = carte;
        this.est_proprietaire_carte = est_proprietaire_carte;
        this.utiliseCarte = utiliseCarte;
      
    }

    public boolean isUtiliseCarte() {
        return utiliseCarte;
    }

    public void setUtiliseCarte(boolean utiliseCarte) {
        this.utiliseCarte = utiliseCarte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAnniversaire() {
        return anniversaire;
    }

    public void setAnniversaire(String anniversaire) {
        this.anniversaire = anniversaire;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }
    
    
	public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getTelBureau() {
        return telBureau;
    }

    public void setTelBureau(String telBureau) {
        this.telBureau = telBureau;
    }

    public String getTelDomicile() {
        return telDomicile;
    }

    public void setTelDomicile(String telDomicile) {
        this.telDomicile = telDomicile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public TypeCheveux getTypeCheveux() {
        return typeCheveux;
    }

    public void setTypeCheveux(TypeCheveux typeCheveux) {
        this.typeCheveux = typeCheveux;
    }

    public CuirChevelu getCuirChevelu() {
        return cuirChevelu;
    }

    public void setCuirChevelu(CuirChevelu cuirChevelu) {
        this.cuirChevelu = cuirChevelu;
    }

    public Densite getDensiteCheveux() {
        return densiteCheveux;
    }

    public void setDensiteCheveux(Densite densiteCheveux) {
        this.densiteCheveux = densiteCheveux;
    }

    public TextureCheveux getTextureCheveux() {
        return textureCheveux;
    }

    public void setTextureCheveux(TextureCheveux textureCheveux) {
        this.textureCheveux = textureCheveux;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public boolean isEst_proprietaire_carte() {
        return est_proprietaire_carte;
    }

    public void setEst_proprietaire_carte(boolean est_proprietaire_carte) {
        this.est_proprietaire_carte = est_proprietaire_carte;
    }

    public EtatCheveux getEtatCheveux() {
        return etatCheveux;
    }

    public void setEtatCheveux(EtatCheveux etatCheveux) {
        this.etatCheveux = etatCheveux;
    }

    public int getNombreUtilisateur() {
        return nombreUtilisateur;
    }

    public void setNombreUtilisateur(int nombreUtilisateur) {
        this.nombreUtilisateur = nombreUtilisateur;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " "+ carte;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
