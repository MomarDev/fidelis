/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import michelkaapp.objets.MouvementProduit;
import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class DashboardDAO {

    public static final String ACHAT = "achat";
    public static final String RETRAIT = "retrait stock";
    public static final String AUTRE = "autre";
    public static final String TABLE_PRODUIT = "produit";
    public static final String TABLE_MOUV = "mouvement_stock";
    public static final String AJOUT = "ajout stock";
    public static final String DESTOCK = "destockage";
    public static final String STOCK = "stockage";

    public static ArrayList<MouvementProduit> select(String type, String dateDebut, String dateFin) {
        ArrayList<MouvementProduit> mouvementProduits = new ArrayList<>();
        String qr = null;
        boolean isAll = true;
        if (type.equalsIgnoreCase("tout")) {
            qr = "select produit.name_produit, SUM(produit.prix_unitaire) as prix_unitaire,"
                    + " SUM(mouvement_stock.quantite) as quantite, mouvement_stock.date_mouvement , mouvement_stock.motif from "
                    + TABLE_PRODUIT + " left join " + TABLE_MOUV + " on "
                    + TABLE_MOUV + '.' + "id_produit = " + TABLE_PRODUIT + '.' + "id_produit "
                    + "where " + TABLE_MOUV + '.' + "sens = ? and (" + TABLE_MOUV + '.'
                    + "date_mouvement between ? and ?) " + " group by " + TABLE_PRODUIT + "." + "id_produit "
                    + " order  by " + TABLE_MOUV + '.' + "date_mouvement";
        } else {
            isAll = false;
            qr = "select produit.name_produit, SUM(produit.prix_unitaire) as prix_unitaire,"
                    + " SUM(mouvement_stock.quantite) as quantite, mouvement_stock.date_mouvement , mouvement_stock.motif from "
                    + TABLE_PRODUIT + " left join " + TABLE_MOUV + " on "
                    + TABLE_MOUV + '.' + "id_produit = " + TABLE_PRODUIT + '.' + "id_produit "
                    + "where " + TABLE_MOUV + '.' + "sens = ? and " + TABLE_MOUV + '.' + "motif = ? and (" + TABLE_MOUV + '.' + "date_mouvement between ? and ? )"
                    + "group by " + TABLE_PRODUIT + "." + "id_produit  order  by " + TABLE_MOUV + '.' + "date_mouvement";
        }
        try {
            PreparedStatement stat = Utilis.driver.getConnection().prepareStatement(qr);
            if (isAll) {

                stat.setString(1, DESTOCK);
                stat.setString(2, dateDebut);
                stat.setString(3, dateFin);
            } else {
                stat.setString(1, DESTOCK);
                stat.setString(2, type);
                stat.setString(3, dateDebut);
                stat.setString(4, dateFin);
            }
            System.out.println(stat);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                mouvementProduits.add(getMouvementProduit(rs));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mouvementProduits;
    }

    private static MouvementProduit getMouvementProduit(ResultSet rs) throws SQLException {
        MouvementProduit mv = new MouvementProduit(rs.getString("name_produit"),
                rs.getInt("quantite"), rs.getString("prix_unitaire"), rs.getString("date_mouvement"));
        mv.setMotif(rs.getString("motif"));
        if (mv.getPrix().equals("0")) {
            mv.setPrix(null);
        }
        return mv;
    }

}
