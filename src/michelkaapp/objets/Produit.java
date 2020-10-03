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
public class Produit implements Cloneable {

    private int id;
    private String nom;
    private String code;
    private Marque marque;
    private boolean utilisation;
    private SousFamille sousfamille;
    private GammeProduit gammeProduit;
    private int prix;
    private int stockService;
    private int stockVente;
    private int seuilService;
    private int seuilVente;
    private String info;
    private String famille;
    private int stock;

    public Produit() {
    }

    public Produit(int id, String nom, String code, Marque marque, boolean utilisation, SousFamille sousfamille, GammeProduit gammeProduit, int prix, int stockService, int stockVente, int seuilService, int seuilVente, String info) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.marque = marque;
        this.utilisation = utilisation;
        this.sousfamille = sousfamille;
        this.gammeProduit = gammeProduit;
        this.prix = prix;
        this.stockService = stockService;
        this.stockVente = stockVente;
        this.seuilService = seuilService;
        this.seuilVente = seuilVente;
        this.info = info;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        if (utilisation) {
            this.stockService = stock;
        } else {
            this.stockVente = stock;
        }
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public boolean isUtilisation() {
        return utilisation;
    }

    public void setUtilisation(boolean utilisation) {
        this.utilisation = utilisation;
        if (utilisation) {
            setFamille("Service");
            stock = stockService;
        } else {
            setFamille("Vente");
            stock = stockVente;
        }
    }

    public SousFamille getSousfamille() {
        return sousfamille;
    }

    public void setSousfamille(SousFamille sousfamille) {
        this.sousfamille = sousfamille;
    }

    public GammeProduit getGammeProduit() {
        return gammeProduit;
    }

    public void setGammeProduit(GammeProduit gammeProduit) {
        this.gammeProduit = gammeProduit;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getStockService() {
        return stockService;
    }

    public void setStockService(int stockService) {
        this.stockService = stockService;
    }

    public int getStockVente() {
        return stockVente;
    }

    public void setStockVente(int stockVente) {
        this.stockVente = stockVente;
    }

    public int getSeuilService() {
        return seuilService;
    }

    public void setSeuilService(int seuilService) {
        this.seuilService = seuilService;
    }

    public int getSeuilVente() {
        return seuilVente;
    }

    public void setSeuilVente(int seuilVente) {
        this.seuilVente = seuilVente;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

}
