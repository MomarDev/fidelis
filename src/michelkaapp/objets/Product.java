package michelkaapp.objets;

public class Product {
    private String nom;
    private int prixU;
    private int quantite;
    private int prixTotal;
    private String prixUString;
    private String prixTotalString;


    public Product(String nom, int prixU, int quantite, int prixTotal, String prixUString, String prixTotalString) {
        this.nom = nom;
        this.prixU = prixU;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
        this.prixUString = prixUString;
        this.prixTotalString = prixTotalString;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrixU() {
        return prixU;
    }

    public void setPrixU(int prixU) {
        this.prixU = prixU;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getPrixUString() {
        return prixUString;
    }

    public void setPrixUString(String prixUString) {
        this.prixUString = prixUString;
    }

    public String getPrixTotalString() {
        return prixTotalString;
    }

    public void setPrixTotalString(String prixTotalString) {
        this.prixTotalString = prixTotalString;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }
}
