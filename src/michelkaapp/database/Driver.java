/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import michelkaapp.objets.Achat;
import michelkaapp.objets.Carte;
import michelkaapp.objets.CategoriePrestation;
import michelkaapp.objets.Client;
import michelkaapp.objets.CodeBar;
import michelkaapp.objets.CuirChevelu;
import michelkaapp.objets.Densite;
import michelkaapp.objets.DetailInventaire;
import michelkaapp.objets.DetailPrestationFournie;
import michelkaapp.objets.EtatCheveux;
import michelkaapp.objets.FermeCaisse;
import michelkaapp.objets.GammeProduit;
import michelkaapp.objets.Inventaire;
import michelkaapp.objets.Mail;
import michelkaapp.objets.Marque;
import michelkaapp.objets.OuvreCaisse;
import michelkaapp.objets.Parametre;
import michelkaapp.objets.Parrainage;
import michelkaapp.objets.Prestation;
import michelkaapp.objets.PrestationFournie;
import michelkaapp.objets.PrestationTechnique;
import michelkaapp.objets.Produit;
import michelkaapp.objets.ProduitAchete;
import michelkaapp.objets.ProduitUtilise;
import michelkaapp.objets.Profession;
import michelkaapp.objets.Profile;
import michelkaapp.objets.Restant;
import michelkaapp.objets.Service;
import michelkaapp.objets.Somme;
import michelkaapp.objets.SousCategoriePrestation;
import michelkaapp.objets.SousFamille;
import michelkaapp.objets.SuiviCaisse;
import michelkaapp.objets.SuiviMaison;
import michelkaapp.objets.Tarif;
import michelkaapp.objets.TextureCheveux;
import michelkaapp.objets.Trace;
import michelkaapp.objets.TypeCheveux;
import michelkaapp.objets.TypePrestation;
import michelkaapp.objets.User;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

/**
 *
 * @author leyu
 */
public class Driver {

    private Connection connection;
    private String jdbc;
    private int port;
    private String nomBaseDonnee;
    private String motDePasse;
    private String utilisateur;
    private String hostname;

    /**
     * c'est le constructeur par defaut. Elle va parametre par default le type
     * jdbc utiliser, le port, l'utilisateur et le mot de passe de la base de
     * donnee
     */
    public Driver() {
        this.connection = null;
        jdbc = "mysql";
        this.port = 3306;
        this.motDePasse = "";
        this.utilisateur = "root";
        hostname = "localhost";
    }

    public Driver(String jdbc, String serverType, int port, String nomBaseDonnee,
            String motDePasse, String utilisateur, String hostname) {
        super();
        this.jdbc = jdbc;
        this.port = port;
        this.nomBaseDonnee = nomBaseDonnee;
        this.motDePasse = motDePasse;
        this.utilisateur = utilisateur;
        this.hostname = hostname;
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNomBaseDonnee() {
        return nomBaseDonnee;
    }

    public void setNomBaseDonnee(String nomBaseDonnee) {
        this.nomBaseDonnee = nomBaseDonnee;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Connection getConnection() {
        if (connection == null) {
            CreateConnecion();
        }
        return connection;
    }

    public boolean CreateConnecion() {
        try {
            connection = DriverManager.getConnection("jdbc:" + jdbc + "://" + hostname + ":" + port + "/" + nomBaseDonnee, utilisateur, motDePasse);
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }

        return connection != null;
    }

    public boolean close() {
        boolean isClose = false;
        if (connection != null) {
            try {
                connection.close();
                isClose = true;
            } catch (Exception e) {
            }
        }
        return isClose;
    }

    public User authenfication(String usr, String pwd) {
        User user = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "select id_user, name,lastname, email,telephone, username, password,"
                    + " profil.id_profil,name_profil from user, profil"
                    + " where user.password=MD5('" + pwd + "')"
                    + "and user.username ='" + usr + "' and user.id_profil = profil.id_profil";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                user = new User();
                Profile profil = new Profile(result.getInt("id_profil"), "name_profil");
                user.setProfile(profil);
                user.setId(result.getInt("id_user"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setNom(result.getString("name"));
                user.setPrenom(result.getString("lastname"));
                user.setTelephone(result.getString("telephone"));
            }
            result.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return user;
    }

    public boolean updateCarte(int reference_carte, String column, int value) {
        int teste = 0;
        String query = "update carte set " + column + " = " + value + " where reference_carte = " + reference_carte;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
//        trace
        insertTrace(new Trace(0, "mise à jour ", "carte", column + " =" + value, "", Utilis.user));
        return teste > 0;
    }

    public boolean attribuerCarte(Carte carte) {
        final String query = "update carte set date_attribution = CURDATE() , "
                + "date_expiration = date_add( CURDATE(), INTERVAL 3 YEAR ) , "
                + "nombre_utilisateur_carte  = ?, famille = ? , etat_carte = ?, couple = ? where reference_carte = ?";
        int teste = 0;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, carte.getNombreUtilisateurCarte());
            stat.setInt(2, carte.getFamilleNombre());
            stat.setInt(3, carte.getEtatCarte());
            stat.setBoolean(4, carte.isCouple());
            stat.setInt(5, carte.getReference());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teste > 0;
    }

    public boolean clonerCarte(Carte carte) {
        int teste = 0;
        final String query = "update carte set nb_pts_fidelite_achat  = ? , nombre_fidelite_parrainage = ? ,"
                + "nombre_fidelite_prestation = ? , couple = ? , famille = ?, nombre_utilisateur_carte = ? "
                + ", etat_carte = ? where reference_carte = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, carte.getNptsFideliteAchat());
            stat.setInt(2, carte.getNtpsParrainage());
            stat.setInt(3, carte.getNptPrestation());
            stat.setBoolean(4, carte.isCouple());
            stat.setInt(5, carte.getFamilleNombre());
            stat.setInt(6, carte.getNombreUtilisateurCarte());
            stat.setInt(7, carte.getEtatCarte());
            stat.setInt(8, carte.getReference());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public Carte trouverCarte(int id) {
        Carte carte = null;
        try {
            PreparedStatement stat = connection.prepareStatement("select * from carte where reference_carte = ? limit 1");
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                carte = new Carte();
                carte.setId(rs.getInt("id_carte"));
                carte.setReference(rs.getInt("reference_carte"));
                carte.setNombreUtilisateurCarte(rs.getInt("nombre_utilisateur_carte"));
                carte.setNptPrestation(rs.getInt("nombre_fidelite_prestation"));
                carte.setNptsFideliteAchat(rs.getInt("nb_pts_fidelite_achat"));
                carte.setNtpsParrainage(rs.getInt("nombre_fidelite_parrainage"));
                // carte.setEtatCarte(rs.getInt("etat_carte"));
                carte.setEtatCarte(rs.getInt("etat_carte"));

                carte.setDateReception(rs.getDate("date_reception").toString());
                carte.setFamilleNombre(rs.getInt("famille"));
                carte.setCouple(rs.getBoolean("couple"));
                System.out.println("etat_carte:/t" + rs.getInt("etat_carte"));

                if (rs.getDate("date_attribution") != null) {
                    carte.setDateAttribution(rs.getDate("date_attribution").toString());
                } else {
                    carte.setDateAttribution(null);
                }
                if (rs.getDate("date_expiration") != null) {
                    carte.setDateExpiration(rs.getDate("date_expiration").toString());
                } else {
                    carte.setDateExpiration(null);
                }
                if (carte.getEtatCarte() == 0) {
                    carte.setAfficheEtat("non attribué");
                } else if (carte.getEtatCarte() == 1) {
                    carte.setAfficheEtat("active");
                } else if (carte.getEtatCarte() == 2) {
                    carte.setAfficheEtat("bloquée");
                } else if (carte.getEtatCarte() == 3) {
                    carte.setAfficheEtat("perdue");
                } else {
                    carte.setAfficheEtat("expirée");
                }

            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return carte != null ? carte : null;
    }

    public Carte trouverCarte(Client client) {
        Carte carte = null;
        String query = "select * from carte where reference_carte  = ( select reference_carte from client where id_client = ? ) limit 1";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, client.getId());
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                carte = new Carte();
                carte.setId(rs.getInt("id_carte"));
                carte.setReference(rs.getInt("reference_carte"));
                carte.setFamilleNombre(rs.getInt("nombre_utilisateur_carte"));
                carte.setNptPrestation(rs.getInt("nombre_fidelite_prestation"));
                carte.setNptsFideliteAchat(rs.getInt("nb_pts_fidelite_achat"));
                carte.setNtpsParrainage(rs.getInt("nombre_fidelite_parrainage"));
                carte.setEtatCarte(rs.getInt("etat_carte"));
                carte.setDateReception(rs.getDate("date_reception").toString());
                carte.setFamilleNombre(rs.getInt("famille"));
                carte.setCouple(rs.getBoolean("couple"));
                if (rs.getDate("date_attribution") != null) {
                    carte.setDateAttribution(rs.getDate("date_attribution").toString());
                } else {
                    carte.setDateAttribution(null);
                }
                if (rs.getDate("date_expiration") != null) {
                    carte.setDateExpiration(rs.getDate("date_expiration").toString());
                } else {
                    carte.setDateExpiration(null);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return carte;
    }

    public List<Marque> listMarque() {
        List<Marque> list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT DISTINCT * FROM marque order by nom_marque");
            while (result.next()) {
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                list.add(marque);
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<TypePrestation> listTypePrestation() {
        List<TypePrestation> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM type_prestation");
            while (result.next()) {
                TypePrestation typePrestation = new TypePrestation(result.getInt("id_type_prestation"),
                        result.getString("nom_type_prestation"), result.getBoolean("est_technique"));
                list.add(typePrestation);
            }
            stat.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Produit> listProduit(String clause) {
        List<Produit> list = new ArrayList<>();

        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM produit LEFT JOIN sous_famille_produit ON sous_famille_produit.id_sous_famille = produit.id_sous_famille "
                    + "LEFT JOIN gamme_produit ON gamme_produit.id_gamme_produit = produit.id_gamme_produit LEFT JOIN marque ON produit.id_marque = marque.id_marque "
                    + clause);
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
                list.add(produit);
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<GammeProduit> listGamme() {
        List<GammeProduit> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT DISTINCT * FROM gamme_produit order by nom_gamme_produit");
            while (result.next()) {
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                list.add(gamme);
            }
            result.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<SousFamille> listSousFamille() {
        List<SousFamille> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT DISTINCT * FROM sous_famille_produit order by nom_sous_famille");
            while (rs.next()) {
                SousFamille sousFamille = new SousFamille(rs.getInt("id_sous_famille"), rs.getString("nom_sous_famille"));
                list.add(sousFamille);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<CategoriePrestation> ListCategoriePrestation(String name) {
        List<CategoriePrestation> list = new ArrayList<>();
        String query = "select * from categorie_prestation where id_type_prestation = (select id_type_prestation from "
                + "type_prestation where nom_type_prestation = ? )";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, name);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                CategoriePrestation categorie = new CategoriePrestation();

                categorie.setId(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("name_categorie"));
                categorie.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                categorie.setCode(rs.getString("code_categorie"));
                categorie.setEstSousCategorie(rs.getBoolean("est_une_sous_categorie"));
                categorie.setEstPrestation(rs.getBoolean("est_une_prestation"));
                categorie.setEstTechnique(rs.getBoolean("est_technique"));

                list.add(categorie);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public List<CategoriePrestation> ListCategoriePrestation(int id_type_prestation) {
        List<CategoriePrestation> list = new ArrayList<>();

        try {
            PreparedStatement stat = connection.prepareStatement("SELECT * FROM categorie_prestation WHERE id_type_prestation = ?");
            stat.setInt(1, id_type_prestation);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                CategoriePrestation categorie = new CategoriePrestation();

                categorie.setId(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("name_categorie"));
                categorie.setTypePrestation(new TypePrestation(id_type_prestation, "", true));
                categorie.setCode(rs.getString("code_categorie"));
                categorie.setEstSousCategorie(rs.getBoolean("est_une_sous_categorie"));
                categorie.setEstPrestation(rs.getBoolean("est_une_prestation"));
                categorie.setEstTechnique(rs.getBoolean("est_technique"));

                list.add(categorie);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            System.err.print(e);
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<SousCategoriePrestation> listSousCategoriesPrestation(int id) {
        List<SousCategoriePrestation> list = new ArrayList<>();
        String query = " select * from sous_categorie_prestation where id_categorie = " + id;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                SousCategoriePrestation sc = new SousCategoriePrestation();
                sc.setId(rs.getInt("id_sous_categorie"));
                sc.setNom(rs.getString("nom_sous_categorie"));
                sc.setCategoriePrestation(new CategoriePrestation(rs.getInt("id_categorie"), "", null, "", true, true, true));
                sc.setEstPrestation(rs.getBoolean("est_une_prestation"));
                sc.setEstTechnique(rs.getBoolean("est_technique"));
                sc.setCode(rs.getString("code_categorie"));

                list.add(sc);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Prestation> listPrestation(int id) {
        List<Prestation> list = new ArrayList<>();

        try {
            Statement stat = connection.createStatement();
            String query = "select * from prestation where id_sous_categorie = " + id;
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Prestation prestation = new Prestation();

                prestation.setId(rs.getInt("id_prestation"));
                prestation.setNom(rs.getString("nom_prestation"));
                prestation.setCout(rs.getInt("cout_prestation"));
                prestation.setSous_categorie(new SousCategoriePrestation(rs.getInt("id_sous_categorie"), "", null, true, true));
                prestation.setFixe(rs.getBoolean("cout_fixe"));
                prestation.setBorneInferieur(rs.getInt("borne_inferieur_prix"));
                prestation.setBorneSuperieur(rs.getInt("borne_superieur_prix"));

                list.add(prestation);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }
    
    
    public List<Trace> listTrace() {
        List<Trace> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM trace JOIN user ON trace.id_user = user.id_user JOIN profil ON user.id_profil = profil.id_profil  order by id desc LIMIT 100 ");
            while (rs.next()) {
                User user = new User();
                Trace trace = new Trace();

                Profile profil = new Profile(rs.getInt("id_profil"), "name_profil");
                user.setProfile(profil);

                user.setId(rs.getInt("id_user"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("lastname"));
                user.setTelephone(rs.getString("telephone"));
                trace.setValeur(rs.getString("valeur"));
                trace.setId(rs.getInt("id"));
                trace.setObjet(rs.getString("objet"));
                trace.setDate(rs.getString("date_trace"));
                trace.setAction(rs.getString("action"));
                if (trace.getValeur() != null) {
                    if (trace.getValeur().equalsIgnoreCase("etat_carte =0")) {
                        trace.setValeur("non attribuée");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =1")) {
                        trace.setValeur("active");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =2")) {
                        trace.setValeur("bloquée");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =3")) {
                        trace.setValeur("perdue");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =4")) {
                        trace.setValeur("expirée");
                    }
                }
                // String valeur = null;
                //  Trace trace = new Trace(rs.getInt("id"), rs.getString("action"), rs.getString("objet"),
                //         rs.getString("valeur"), rs.getString("date_trace"), user);
                trace.setUser(user);

                // System.out.println("test trace" + rs.getString("valeur"));
                list.add(trace);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<SuiviCaisse> listSuiviCaisse() {
        List<SuiviCaisse> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM suiviCaisse JOIN user ON suiviCaisse.id_user = user.id_user JOIN profil ON user.id_profil = profil.id_profil  order by id desc LIMIT 100 ");
            while (rs.next()) {
                User user = new User();
                SuiviCaisse trace = new SuiviCaisse();
                

                Profile profil = new Profile(rs.getInt("id_profil"), "name_profil");
                user.setProfile(profil);

                user.setId(rs.getInt("id_user"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("lastname"));
                user.setTelephone(rs.getString("telephone"));
                trace.setValeur(rs.getString("valeur"));
                trace.setId(rs.getInt("id"));
                trace.setObjet(rs.getString("objet"));
                trace.setDate(rs.getString("date_suivi"));
                trace.setAction(rs.getString("action"));
                trace.setModePaiement(rs.getString("mode_paiement"));
                trace.setRestant(rs.getInt("restant"));
                if (trace.getValeur() != null) {
                    if (trace.getValeur().equalsIgnoreCase("etat_carte =0")) {
                        trace.setValeur("non attribuée");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =1")) {
                        trace.setValeur("active");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =2")) {
                        trace.setValeur("bloquée");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =3")) {
                        trace.setValeur("perdue");
                    } else if (trace.getValeur().equalsIgnoreCase("etat_carte =4")) {
                        trace.setValeur("expirée");
                    }
                }
                // String valeur = null;
                //  Trace trace = new Trace(rs.getInt("id"), rs.getString("action"), rs.getString("objet"),
                //         rs.getString("valeur"), rs.getString("date_trace"), user);
                trace.setUser(user);

                // System.out.println("test trace" + rs.getString("valeur"));
                list.add(trace);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<User> listUser() {
        List<User> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM user JOIN profil ON user.id_profil = profil.id_profil ");
            while (rs.next()) {
                User user = new User();

                Profile profil = new Profile(rs.getInt("id_profil"), rs.getString("name_profil"));
                user.setProfile(profil);
                user.setId(rs.getInt("id_user"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("lastname"));
                user.setTelephone(rs.getString("telephone"));
                user.setUsername(rs.getString("username"));

                list.add(user);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;

    }

    public List<Densite> listDensite() {
        List<Densite> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from densite");
            while (rs.next()) {
                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                list.add(densite);
            }
            rs.close();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<EtatCheveux> listEtatCheveux() {
        List<EtatCheveux> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from etat_cheveux");
            while (rs.next()) {
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));

                list.add(etatCheveux);
            }
            rs.close();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<TypeCheveux> listTypeCheveux() {
        List<TypeCheveux> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from type_de_cheveux");
            while (rs.next()) {
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));

                list.add(typeCheveux);
            }
            rs.close();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<CuirChevelu> listCuirCheveux() {
        List<CuirChevelu> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM cuir_chevelu");
            while (rs.next()) {
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                list.add(crc);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<TextureCheveux> listTextureCheveux() {
        List<TextureCheveux> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from texture_cheveux");
            while (rs.next()) {
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                list.add(tc);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }

    public List<Profession> listProfession() {
        List<Profession> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from profession order by name_profession ");
            while (rs.next()) {
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));
                list.add(profession);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public List<Carte> listCarte() {
        List<Carte> list = new ArrayList<>();
        final String query = "select * from carte";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Carte carte = new Carte();
                carte.setId(rs.getInt("id_carte"));
                carte.setReference(rs.getInt("reference_carte"));
                carte.setNombreUtilisateurCarte(rs.getInt("nombre_utilisateur_carte"));
                carte.setNptsFideliteAchat(rs.getInt("nb_pts_fidelite_achat"));
                carte.setNptPrestation(rs.getInt("nombre_fidelite_prestation"));
                carte.setNtpsParrainage(rs.getInt("nombre_fidelite_parrainage"));
                carte.setEtatCarte(rs.getInt("etat_carte"));
                carte.setFamilleNombre(rs.getInt("famille"));
                carte.setDateReception(rs.getDate("date_reception").toString());
                carte.setCouple(rs.getBoolean("couple"));
                if (rs.getDate("date_expiration") == null) {
                    carte.setDateExpiration(null);
                } else {
                    carte.setDateExpiration(rs.getDate("date_expiration").toString());
                }
                if (rs.getDate("date_attribution") == null) {
                    carte.setDateAttribution(null);
                } else {
                    carte.setDateAttribution(rs.getDate("date_attribution").toString());
                }
                if (carte.getEtatCarte() == 0) {
                    carte.setAfficheEtat("non attribué");
                } else if (carte.getEtatCarte() == 1) {
                    carte.setAfficheEtat("active");
                } else if (carte.getEtatCarte() == 2) {
                    carte.setAfficheEtat("bloquée");
                } else if (carte.getEtatCarte() == 3) {
                    carte.setAfficheEtat("perdue");
                } else {
                    carte.setAfficheEtat("expirée");
                }
                list.add(carte);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;

    }

    public List<Carte> carteAndAnniversaire() {
        List<Carte> list = new ArrayList<>();
        final String query = "select * from carte left join client on client.reference_carte = "
                + "carte.reference_carte where est_proprietaire_carte = 1 and utilise_carte = 1 ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Carte carte = new Carte();
                carte.setId(rs.getInt("id_carte"));
                carte.setReference(rs.getInt("reference_carte"));
                carte.setNombreUtilisateurCarte(rs.getInt("nombre_utilisateur_carte"));
                carte.setNptsFideliteAchat(rs.getInt("nb_pts_fidelite_achat"));
                carte.setNptPrestation(rs.getInt("nombre_fidelite_prestation"));
                carte.setNtpsParrainage(rs.getInt("nombre_fidelite_parrainage"));
                carte.setEtatCarte(rs.getInt("etat_carte"));
                carte.setFamilleNombre(rs.getInt("famille"));
                carte.setDateReception(rs.getDate("date_reception").toString());
                carte.setAnniversaire(rs.getString("anniversaire_client"));
                if (rs.getDate("date_expiration") == null) {
                    carte.setDateExpiration(null);
                } else {
                    carte.setDateExpiration(rs.getDate("date_expiration").toString());
                }
                if (rs.getDate("date_attribution") == null) {
                    carte.setDateAttribution(null);
                } else {
                    carte.setDateAttribution(rs.getDate("date_attribution").toString());
                }
                if (carte.getEtatCarte() == 0) {
                    carte.setAfficheEtat("non attribué");
                } else if (carte.getEtatCarte() == 1) {
                    carte.setAfficheEtat("active");
                } else if (carte.getEtatCarte() == 2) {
                    carte.setAfficheEtat("bloquée");
                } else if (carte.getEtatCarte() == 3) {
                    carte.setAfficheEtat("perdue");
                } else {
                    carte.setAfficheEtat("expirée");
                }
                list.add(carte);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public Profession ActuelProfession(String key) {
        Profession profession = null;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from profession where name_profession = '" + key + "' limit 1");
            while (rs.next()) {
                profession = new Profession();
                profession.setId(rs.getInt("id_profession"));
                profession.setProfession(rs.getString("name_profession"));
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profession;
    }

    public List<Client> rechercherParTypeCheveux(String chaine) {
        List<Client> list = new ArrayList<>();
//        Client client = null;
        String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux "
                + " where client.id_type_de_cheveux  in (select type_de_cheveux.id_type_de_cheveux from type_de_cheveux where "
                + "type_de_cheveux.name_type_de_cheveux like ? ) and client.supprimer = 0";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, "%" + chaine + "%");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);

            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list.isEmpty() ? null : list;
    }

    public List<Client> rechercherClientParProduit(String chaine) {
        List<Client> list = new ArrayList<>();
        String query = "select * from client where id_client in (select id_client from achat where "
                + "id_achat = (select id_achat from produit_achete where id_produit = (select id_produit from "
                + "produit where name_produit like '" + chaine.charAt(0) + "%'))) AND client.supprimer = 0";

        Client client = null;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), null);
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), null);
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), null);
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), null);
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), null);
                Profession profession = new Profession(rs.getInt("id_profession"), null);

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public List<Carte> trouveCarteParOption(String option, String key) {
        List<Carte> list = new ArrayList<>();

        String query = "select * from carte where reference_carte in (select reference_carte from "
                + "client where " + option + " like '%" + key + "%' and est_proprietaire_carte = 1 )";

        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Carte carte = new Carte();
                carte.setId(rs.getInt("id_carte"));
                carte.setReference(rs.getInt("reference_carte"));
                carte.setFamilleNombre(rs.getInt("nombre_utilisateur_carte"));
                carte.setNptsFideliteAchat(rs.getInt("nb_pts_fidelite_achat"));
                carte.setNptPrestation(rs.getInt("nombre_fidelite_prestation"));
                carte.setNtpsParrainage(rs.getInt("nombre_fidelite_parrainage"));
                carte.setEtatCarte(rs.getInt("etat_carte"));
                carte.setDateReception(rs.getDate("date_reception").toString());
                if (rs.getDate("date_expiration") == null) {
                    carte.setDateExpiration(null);
                } else {
                    carte.setDateExpiration(rs.getDate("date_expiration").toString());
                }
                if (rs.getDate("date_attribution") == null) {
                    carte.setDateAttribution(null);
                } else {
                    carte.setDateAttribution(rs.getDate("date_attribution").toString());
                }
                if (carte.getEtatCarte() == 0) {
                    carte.setAfficheEtat("non attribué");
                } else if (carte.getEtatCarte() == 1) {
                    carte.setAfficheEtat("active");
                } else if (carte.getEtatCarte() == 2) {
                    carte.setAfficheEtat("bloquée");
                } else if (carte.getEtatCarte() == 3) {
                    carte.setAfficheEtat("perdue");
                } else {
                    carte.setAfficheEtat("expirée");
                }
                list.add(carte);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }

    public List<Client> rechercheparService(String chaine) {
        List<Client> list = new ArrayList<>();
        String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux "
                + "where client.id_client in (select prestation_technique.id_client from prestation_technique  "
                + " where prestation_technique.id_categorie_prestation_technique in ( select categorie_prestation_technique.id_categorie_prestation_technique from categorie_prestation_technique "
                + " where categorie_prestation_technique.nom_categorie_prestation_technique like ?) ) AND client.supprimer = 0";

        Client client = null;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, "%" + query + "%");
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), null);
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), null);
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), null);
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), null);
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), null);
                Profession profession = new Profession(rs.getInt("id_profession"), null);

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public Client proprietaireCarte(int ref) {
        int teste = 0;
        String query = "select * from client where reference_carte = " + ref + " and est_proprietaire_carte = 1";
        Client client = null;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), null);
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), null);
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), null);
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), null);
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), null);
                Profession profession = new Profession(rs.getInt("id_profession"), null);

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return client;
    }

    public Client rechercheClientFormCarte(int ref) {
        String query = "select * from client where reference_carte = " + ref + " and est_proprietaire_carte = 1 and utilise_carte = 1 ";
        Client client = null;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));
                Densite densite = new Densite(rs.getInt("id_densite"), null);
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), null);
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), null);
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), null);
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), null);
                Profession profession = new Profession(rs.getInt("id_profession"), null);
                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return client;
    }
//public Client 

    public List<Client> rechercheList(String key, String column) {
        List<Client> list = new ArrayList<>();
        final String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession"
                + " LEFT JOIN type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN "
                + "cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on "
                + "client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = "
                + "densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux  "
                + "where client.est_technique = 1 and  client." + column + " like ? AND client.supprimer = 0;";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, "%" + key + "%");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);

            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Client> toutLesClient() {
        List<Client> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("sellect * from client where supprimer = 0 ");
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), "non défini");
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), "non défini");
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), "non défini");
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), "non défini");
                Profession profession = new Profession(rs.getInt("id_profession"), "non défini");

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Client> ListClient() {
        List<Client> list = new ArrayList<>();
        final String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN "
                + "type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN "
                + "cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on "
                + "client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = densite.id_densite LEFT "
                + "JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux where est_technique = 1 AND client.supprimer = 0";

        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Tarif> listTarifCarte(){
    	List<Tarif> listTarif = new ArrayList<>();
    	try {
    	 Statement stat = connection.createStatement();
         ResultSet rs = stat.executeQuery("select * from tarif where detail_prestation not like \"%+%\"");
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    
    
    public List<Tarif> listTarifForfait(){
    	List<Tarif> listTarif = new ArrayList<>();
    	try {
    	 Statement stat = connection.createStatement();
         ResultSet rs = stat.executeQuery("select * from tarif where detail_prestation like \"%+%\"");
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    
   
    
    public String getLastOpenCaisse(){
     String date = null;
    	try {
    	 Statement stat = connection.createStatement();
         ResultSet rs = stat.executeQuery("select heure_ouverture from ouvreCaisse where id = (select max(id) from ouvreCaisse);");
         while (rs.next()) {
              date = rs.getString("heure_ouverture");
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return date;
    }
    
    public int chiffreAffaire(String dateOuvre, String dateFerme){
        int somme =0;
        String query="select sum(sommeRecu) from encaissement where date > ? and date  < ?;";
       	try {
       	 PreparedStatement stat = connection.prepareStatement(query);
       	 stat.setString(1, dateOuvre);
       	 stat.setString(2, dateFerme);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                 somme = rs.getInt(1);
                }
            rs.close();
            stat.close();
       	} catch (Exception e) {
   			e.printStackTrace();
   		}    

       	return somme;
       }
    
    
    
    public int moneyMode(String dateOuvre, String dateFerme, String mode){
        int somme =0;
        String query="select sum(sommeRecu) from encaissement where date > ? and date  < ? and modePayement = ?;";
       	try {
       	 PreparedStatement stat = connection.prepareStatement(query);
       	 stat.setString(1, dateOuvre);
       	 stat.setString(2, dateFerme);
       	 stat.setString(3, mode);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                 somme = rs.getInt(1);
                }
            rs.close();
            stat.close();
       	} catch (Exception e) {
   			e.printStackTrace();
   		}    

       	return somme;
       }


    public int moneyModeRestant(String dateOuvre, String dateFerme, String mode){
        int somme =0;
        String query="select sum(restant) from encaissement where date > ? and date  < ? and modePayement = ?;";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, dateOuvre);
            stat.setString(2, dateFerme);
            stat.setString(3, mode);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                somme = rs.getInt(1);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return somme;
    }
    
    
    
    public boolean deleteRestant(String tel) {
        int teste = 0;
        final String query = "DELETE FROM restant WHERE telephoneCli =? limit 1";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, tel);
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        if (teste > 0) {
        	 insertTrace(new Trace(0, "remboursement ", "Encaissement", tel, "", Utilis.user));
        }
        return teste > 0;
    }
    
    
    
    
    public Restant selectRestant(String tel) {
        Restant restant =new Restant();
        final String query = "Select * FROM restant WHERE telephoneCli =?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, tel);
           
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
            	restant.setId(rs.getInt("id"));
                restant.setNomCli(rs.getString("nomCli"));
                restant.setPrenomCli(rs.getString("prenomCli"));
                restant.setTelephoneCli(rs.getString("telephoneCli"));
                restant.setCommentaire(rs.getString("commentaireCli"));
                restant.setRestant(rs.getInt("montant"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        
        return restant;
    }
    
    
    
    
    public boolean deleteEncaisser(int montant, String dateP) {
        int teste = 0;
        final String query = "DELETE FROM encaissement WHERE restant =? and date >? limit 1";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, montant);
            stat.setString(2, dateP);
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
       
        return teste > 0;
    }
    
    public List<Restant> listRestants(String dateO, String dateF){
        List<Restant> listRestant = new ArrayList<>();
        String query = "select * from Restant where date_restant > ? and date_restant  < ? ";
        try {
        	PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, dateO);
            stat.setString(2, dateF);
            
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                Restant restant = new Restant();
                restant.setId(rs.getInt("id"));
                restant.setNomCli(rs.getString("nomCli"));
                restant.setPrenomCli(rs.getString("prenomCli"));
                restant.setTelephoneCli(rs.getString("telephoneCli"));
                restant.setCommentaire(rs.getString("commentaireCli"));
                restant.setRestant(rs.getInt("montant"));


                listRestant.add(restant);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listRestant.isEmpty() ? null : listRestant;
    }
    
    
    
    
    public List<Restant> listRestants(){
        List<Restant> listRestant = new ArrayList<>();
        String query = "select * from Restant";
        try {
        	PreparedStatement stat = connection.prepareStatement(query);
            
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                Restant restant = new Restant();
                restant.setId(rs.getInt("id"));
                restant.setNomCli(rs.getString("nomCli"));
                restant.setPrenomCli(rs.getString("prenomCli"));
                restant.setTelephoneCli(rs.getString("telephoneCli"));
                restant.setCommentaire(rs.getString("commentaireCli"));
                restant.setRestant(rs.getInt("montant"));
               


                listRestant.add(restant);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listRestant.isEmpty() ? null : listRestant;
    }


    public List<Tarif> listTarif(){
        List<Tarif> listTarif = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("select * from tarif ");
            while (rs.next()) {
                Tarif tarif = new Tarif();
                tarif.setId(rs.getInt("id_tarif"));
                tarif.setSexe(rs.getString("sexe"));
                tarif.setNom(rs.getString("nom_tarif"));
                tarif.setPrestation(rs.getString("nom_prestation"));
                tarif.setDetail(rs.getString("detail_prestation"));
                tarif.setPrix(rs.getInt("prix_tarif"));
                tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));

                listTarif.add(tarif);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTarif.isEmpty() ? null : listTarif;
    }



    public List<Tarif> listTarifSexe(String sexe){
    	List<Tarif> listTarif = new ArrayList<>();
    	String query="select * from tarif where sexe=?  ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
    	 stat.setString(1,sexe);
         ResultSet rs = stat.executeQuery();
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    
    public List<Tarif> listTarifAge(String nom){
    	List<Tarif> listTarif = new ArrayList<>();
    	String query="select * from tarif where nom_tarif=?  ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
    	 stat.setString(1,nom);
         ResultSet rs = stat.executeQuery();
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    
    
    
    public int sommeActu(){
    	int somme = 0;
    	try {
    	 Statement stat = connection.createStatement();
         ResultSet rs = stat.executeQuery("select sum(sommeRecu) as somme from encaissement where date > \"2020-08-19 8:00:00\" ");
         while (rs.next()) {
             somme = rs.getInt("somme");
             
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return somme;
    }
    
    public boolean insertSommeActu(int somme){
    	int teste = 0;
    	String query ="insert into encaissement() values(Null,NOW(),?) ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
         stat.setInt(1, somme);
               
         teste = stat.executeUpdate();    
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return teste > 0;
    }
    
    public boolean insertEncaisser(int somme,int restant, String mode){
    	int teste = 0;
    	String query ="insert into encaissement() values(Null,NOW(),?,?,?) ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
         stat.setInt(1, somme);
         stat.setInt(2,restant);
         stat.setString(3, mode);
         teste = stat.executeUpdate();    
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return teste > 0;
    }
    
    
    
    
    public boolean insertTarif(Tarif tarif){
    	int teste = 0;
    	String query ="insert into tarif() values(Null,?,?,?,?,?) ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
         stat.setString(1, tarif.getSexe());
         stat.setString(2,tarif.getNom());
         stat.setString(3, tarif.getPrestation());
         stat.setString(4, tarif.getDetail());
         stat.setInt(5, tarif.getPrix());
         teste = stat.executeUpdate();    
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return teste > 0;
    }
    
    
    public boolean insertRestant(Restant restant){
    	int teste = 0;
    	String query ="insert into Restant() values(Null,?,?,?,?,?,NOW()) ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
         stat.setString(1, restant.getNomCli());
         stat.setString(2,restant.getPrenomCli());
         stat.setString(3, restant.getTelephoneCli());
         stat.setString(4, restant.getCommentaire());
         stat.setInt(5, restant.getRestant());
         teste = stat.executeUpdate();    
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return teste > 0;
    }



  




    
 
    
    public User getUserPasseword(String login){
    	User user = null;
    	String query ="select * from user where password=MD5(?) " ;
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
    	 stat.setString(1, login);
         ResultSet rs = stat.executeQuery();
         while (rs.next()) {
        	 user = new User();
             user.setUsername(rs.getString("username"));
             user.setPassword(rs.getString("password"));
             user.setEmail(rs.getString("email"));
             user.setNom(rs.getString("name"));
             user.setPrenom(rs.getString("lastname"));
             user.setTelephone(rs.getString("telephone"));
             user.setId(rs.getInt("id_user"));
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return user;
    }
    
    public List<Tarif> listTarifForfait(String sexe, String nomTarif){
    	List<Tarif> listTarif = new ArrayList<>();
    	String query = "select * from tarif where detail_prestation like \"%+%\" and sexe=? and nom_tarif=? ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
    	 stat.setString(1, sexe);
    	 stat.setString(2, nomTarif);
         ResultSet rs = stat.executeQuery();
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    public List<Tarif> listTarifCarte(String sexe, String nomTarif){
    	List<Tarif> listTarif = new ArrayList<>();
    	String query = "select * from tarif where detail_prestation not like \"%+%\" and sexe=? and nom_tarif=? ";
    	try {
    	 PreparedStatement stat = connection.prepareStatement(query);
    	 stat.setString(1, sexe);
    	 stat.setString(2, nomTarif);
         ResultSet rs = stat.executeQuery();
         while (rs.next()) {
             Tarif tarif = new Tarif();
             tarif.setId(rs.getInt("id_tarif"));
             tarif.setSexe(rs.getString("sexe"));
             tarif.setNom(rs.getString("nom_tarif"));
             tarif.setPrestation(rs.getString("nom_prestation"));
             tarif.setDetail(rs.getString("detail_prestation"));
             tarif.setPrix(rs.getInt("prix_tarif"));
             tarif.setPrixString(Utilis.separatorNumber(rs.getInt("prix_tarif")));
             
             listTarif.add(tarif);
             }
         rs.close();
         stat.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}    

    	return listTarif.isEmpty() ? null : listTarif;
    }
    
    
    
    
    
    
    public List<Client> ListClient(int debut, int fin) {
        List<Client> list = new ArrayList<>();
        final String query = "SELECT * FROM client LEFT JOIN profession on "
                + "client.id_profession = profession.id_profession LEFT JOIN "
                + "type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu"
                + " on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux "
                + "on client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite "
                + "ON client.id_densite = densite.id_densite LEFT JOIN texture_cheveux ON "
                + "client.id_texture_cheveux = texture_cheveux.id_texture_cheveux where client.supprimer = 0" + " limit " + debut + " , " + fin;

        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public Client selectClientById(int id) {
        Client client = null;
        final String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux where id_client = " + id;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return client;
    }

    public Client lastInsertClient() {
        Client client = null;
        String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux ORDER by client.id_client DESC limit 1 ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return client;
    }

    public List<PrestationFournie> listPrestationFournie() {
        List<PrestationFournie> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM prestation_fournie");
            while (rs.next()) {
                PrestationFournie pf = new PrestationFournie();

                pf.setId(rs.getInt("id_prestation_fourni"));
                pf.setDate(rs.getDate("date_prestation_fourni").toString());
                pf.setClient_id(rs.getInt("id_client"));
                pf.setRemise(rs.getInt("remise_prestation"));
                pf.setMontant(rs.getInt("montant_paye"));
                pf.setFidelites(rs.getBoolean("utilise_fidelite"));

                list.add(pf);
            }
            rs.close();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<DetailPrestationFournie> listDetailPrestation(int id) {
        List<DetailPrestationFournie> list = new ArrayList<>();
        String query = "SELECT * FROM detail_prestation_fourni";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                DetailPrestationFournie dp = new DetailPrestationFournie();

                dp.setId(rs.getInt("id_detail_prestation_fourni"));
                dp.setId_prestation_fourni(rs.getInt("id_prestation_fourni"));
//                dp.setId_marque(rs.getInt("id_marque"));
                dp.setTechnique(rs.getString("technique"));
                dp.setMontage(rs.getString("montage"));
                dp.setTemps_de_pause(rs.getInt("temps_de_pause"));
                dp.setObeservations(rs.getString("observations"));
                dp.setCout_prestation(rs.getInt("cout_prestation"));
                dp.setId_prestation(rs.getInt("id_prestation"));

                list.add(dp);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }

    public List<CategoriePrestation> listPrestationTechnique(int id) {
        List<CategoriePrestation> list = new ArrayList<>();
        String query = "select * from categorie_prestation_technique where id_type_prestation = " + id;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                CategoriePrestation cat = new CategoriePrestation();
                cat.setId(rs.getInt("id_categorie_prestation_technique"));
                cat.setNom(rs.getString("nom_categorie_prestation_technique"));
                cat.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                list.add(cat);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    /*
     Faires des Insertions
     */
    public boolean insertPrestationFournie(PrestationFournie prestationFournie) {
        int teste = 0;
        String query = "insert into prestation_fournie () values (NULL,?,?,?,?,?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);

            stat.setString(1, prestationFournie.getDate());
            stat.setInt(2, prestationFournie.getClient_id());
            stat.setInt(3, prestationFournie.getRemise());
            stat.setInt(4, prestationFournie.getMontant());
            stat.setBoolean(5, prestationFournie.isFidelites());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teste > 0;
    }
    
    

    public boolean insertDetailsPrestation(DetailPrestationFournie detailPrestation) {
        int teste = 0;
        String query = "insert into detail_prestation_fourni () values (null,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, detailPrestation.getId_prestation_fourni());
            stat.setInt(2, detailPrestation.getId_prestation());
//            stat.setInt(3, detailPrestation.getId_marque());
            stat.setString(3, detailPrestation.getTechnique());
            stat.setString(4, detailPrestation.getMontage());
            stat.setInt(5, detailPrestation.getTemps_de_pause());
            stat.setString(6, detailPrestation.getObeservations());
            stat.setInt(7, detailPrestation.getCout_prestation());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return teste > 0;
    }

    public List<DetailPrestationFournie> listDetailPrestationFourniesClient(int id_client) {
        List<DetailPrestationFournie> list = new ArrayList<>();
        String query = "select * from detail_prestation_fourni where id_prestation_fourni in ( select id_prestation_fourni  "
                + "from prestation_fourni where id_client = ?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, id_client);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                DetailPrestationFournie detail = new DetailPrestationFournie();
                detail.setCout_prestation(rs.getInt("cout_prestation"));
                detail.setId_prestation(rs.getInt("id_prestation"));
                detail.setId(rs.getInt("id_detail_prestation_fourni"));
                detail.setTechnique(rs.getString("technique"));
                detail.setMontage(rs.getString("montage"));
                detail.setObeservations(rs.getString("observations"));
                detail.setTemps_de_pause(rs.getInt("temps_de_pause"));
                detail.setId_prestation_fourni(rs.getInt("id_prestation_fourni"));

                list.add(detail);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Produit> listProduitsUtiliserDansUnePrestation(int id_detail_prestation) {
        List<Produit> list = new ArrayList<>();
        String query = "select * from produit where id_produit in (select id_produit from produit_utilise where id_detail_prestation_fourni = ?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, id_detail_prestation);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Produit produit = new Produit();
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public boolean insertPrestationTechnique(PrestationTechnique pt) {
        int teste = 0;
        String query = "insert into prestation_technique () values (null,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, pt.getCategeorie().getId());
            stat.setInt(2, pt.getClient());
            stat.setString(3, Utilis.dateInput(pt.getDate()));
            stat.setString(4, pt.getMontage());
            stat.setString(5, pt.getTechnique());
            stat.setInt(6, pt.getPause());
            stat.setString(7, pt.getObservation());
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "insertion", "prestation technique", pt.toString(), "", Utilis.user));
        return teste > 0;
    }

    public boolean updatePrestationTechnique(PrestationTechnique pt) {
        int teste = 0;
        final String query = " update prestation_technique set id_categorie_prestation_technique = ?, date = ?,"
                + "montage = ? , technique = ? , temps_pause = ? , observations = ? where id_prestation_technique = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, pt.getCategeorie().getId());
            stat.setString(2, Utilis.dateInput(pt.getDate()));
            stat.setString(3, pt.getMontage());
            stat.setString(4, pt.getTechnique());
            stat.setInt(5, pt.getPause());
            stat.setString(6, pt.getObservation());
            stat.setInt(7, pt.getId());
            System.out.println("dans la modifications de prestation");
            teste = stat.executeUpdate();
            stat.close();

            System.out.println(teste + "," + pt.getId());

        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "mise a jour", "prestation technique", pt.toString(), null, Utilis.getUser()));
        }
        return teste > 0;
    }
//    public Client trouverClient()

    public List<PrestationTechnique> listPrestationTechnique() {
        List<PrestationTechnique> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM prestation_technique LEFT JOIN categorie_prestation_technique ON "
                    + "prestation_technique.id_categorie_prestation_technique = categorie_prestation_technique.id_categorie_prestation_technique "
                    + "LEFT JOIN type_prestation ON type_prestation.id_type_prestation = "
                    + "categorie_prestation_technique.id_type_prestation ORDER BY "
                    + "prestation_technique.id_prestation_technique DESC ");
            while (rs.next()) {
                PrestationTechnique pt = new PrestationTechnique();
                pt.setId(rs.getInt("id_prestation_technique"));
                CategoriePrestation cp = new CategoriePrestation();
                TypePrestation tp = new TypePrestation();
                tp.setId(rs.getInt("id_type_prestation"));
                tp.setNom("nom_type_prestation");
                pt.setTypePrestation(tp);
                cp.setId(rs.getInt("id_categorie_prestation_technique"));
                cp.setNom(rs.getString("name_categorie"));
                pt.setCategeorie(cp);
                pt.setClient(rs.getInt("id_client"));
                pt.setDate(rs.getDate("date").toString());
                pt.setMontage(rs.getString("montage"));
                pt.setTechnique(rs.getString("technique"));
                pt.setPause(rs.getInt("temps_pause"));
                pt.setObservation(rs.getString("observations"));

                list.add(pt);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<PrestationTechnique> listPrestationTechniqueClient(int id_client, int limit) {
        List<PrestationTechnique> list = new ArrayList<>();
        String query = "SELECT * FROM prestation_technique LEFT JOIN categorie_prestation_technique ON "
                + "prestation_technique.id_categorie_prestation_technique = categorie_prestation_technique.id_categorie_prestation_technique "
                + "LEFT JOIN type_prestation ON type_prestation.id_type_prestation = "
                + "categorie_prestation_technique.id_type_prestation WHERE prestation_technique.id_client = ? ORDER BY prestation_technique.id_prestation_technique DESC limit " + limit;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, id_client);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                PrestationTechnique pt = new PrestationTechnique();
                pt.setId(rs.getInt("id_prestation_technique"));
                CategoriePrestation cat = new CategoriePrestation();

                cat.setId(rs.getInt("id_categorie_prestation_technique"));
                cat.setNom(rs.getString("nom_categorie_prestation_technique"));
                cat.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                pt.setCategeorie(cat);

                TypePrestation tp = new TypePrestation(rs.getInt("id_type_prestation"), rs.getString("nom_type_prestation"), true);
                pt.setTypePrestation(tp);

                pt.setClient(rs.getInt("id_client"));
                pt.setDate(rs.getString("date"));
                pt.setMontage(rs.getString("montage"));
                pt.setTechnique(rs.getString("technique"));
                pt.setPause(rs.getInt("temps_pause"));
                pt.setObservation(rs.getString("observations"));

                list.add(pt);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public boolean insertProduit(Produit produit) {
        int teste = 0;
        try {
            String query = "insert into produit() values (null,?,?,?,?,?,?,?,?,?,?,?,?,0)";
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, produit.getNom());
            if (produit.getCode() != null && !produit.getCode().isEmpty()) {
                stat.setString(2, produit.getCode());
            } else {
                stat.setString(2, null);
            }
            stat.setInt(3, produit.getMarque().getId());
            stat.setBoolean(4, produit.isUtilisation());
            if (produit.getSousfamille() != null) {
                stat.setInt(5, produit.getSousfamille().getId());
            } else {
                stat.setInt(5, 0);
            }
            if (produit.getGammeProduit() != null) {
                stat.setInt(6, produit.getGammeProduit().getId());
            } else {
                stat.setInt(6, 0);
            }
            stat.setInt(7, produit.getPrix());
            stat.setInt(8, produit.getStockService());
            stat.setInt(9, produit.getStockVente());
            stat.setInt(10, produit.getSeuilService());
            stat.setInt(11, produit.getSeuilVente());
            stat.setString(12, produit.getInfo());
            teste = stat.executeUpdate();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.show();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Produit", produit.toString(), "", Utilis.user));
        }
        return teste > 0;
    }

    public boolean updateProduit(Produit produit) {
        int teste = 0;
        final String query = "update produit set name_produit  = ? , code_bar_produit = ? ,"
                + "id_marque = ? , utilisation_produit = ? , id_sous_famille = ? ,"
                + " id_gamme_produit = ? , prix_unitaire = ? ,qt_stock_service = ? ,"
                + " qt_stock_vente = ? , qt_seuil_alert_service = ? , qt_seuil_alert_vente = ?"
                + " , info_complementaire = ? where id_produit = " + produit.getId();
        PreparedStatement stat = prepareUpdateProduit(query, produit);
        if (stat != null) {
            try {
                teste = stat.executeUpdate();
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return teste > 0;
    }

    private PreparedStatement prepareUpdateProduit(String query, Produit produit) {
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(query);
            stat.setString(1, produit.getNom());
            stat.setString(2, produit.getCode());
            stat.setInt(3, produit.getMarque().getId());
            stat.setBoolean(4, produit.isUtilisation());
            stat.setInt(5, produit.getSousfamille().getId());
            stat.setInt(6, produit.getGammeProduit().getId());
            stat.setInt(7, produit.getPrix());
            stat.setInt(8, produit.getStockService());
            stat.setInt(9, produit.getStockVente());
            stat.setInt(10, produit.getSeuilService());
            stat.setInt(11, produit.getSeuilVente());
            stat.setString(12, produit.getInfo());
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stat;
    }

    public boolean existProduit(String code, String designation, boolean update) {
        int teste = 0;
        final String query = " select count(*) as exist_produit from produit where code_bar_produit <> NULL AND code_bar_produit = ? OR name_produit  = ? ";
        System.out.println(query);
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, code);
            stat.setString(2, designation);
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                teste = rs.getInt("exist_produit");
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        if (update) {
            return teste >= 2;
        }
        return teste > 0;
    }

    public boolean insertPrestation(Prestation prestation) {
        int teste = 0;

        try {
            String query = "insert into prestation()values(NULL,?,?,?,?,?,?)";
            PreparedStatement stat = connection.prepareStatement(query);

            stat.setString(1, prestation.getNom());
            stat.setInt(2, prestation.getCout());
            stat.setInt(3, prestation.getSous_categorie().getId());
            stat.setBoolean(4, prestation.isFixe());
            stat.setInt(5, prestation.getBorneInferieur());
            stat.setInt(6, prestation.getBorneSuperieur());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Prestation", prestation.toString(), "", Utilis.user));
        }
        return teste > 0;
    }
    
    
    

    public List<Integer> insertProduitsUtilise(List<ProduitUtilise> produitsList) {
        int teste = 0;
        List<Integer> list = new ArrayList<>();
        String query = "insert into produit_utilise () values (NULL,?,?)";
        int len = produitsList.size();
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            for (int i = 0; i < len; i++) {
                stat.setInt(1, produitsList.get(i).getId_prestationTechnique());
                stat.setInt(2, produitsList.get(i).getId_produit());
                teste = stat.executeUpdate();
                list.add(teste);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list;
    }

    public boolean insertUser(User user) {
        int teste = 0;
        String query = "insert into user() values (null,?,?,?,?,?,MD5(?),?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);

            stat.setString(1, user.getUsername());
            stat.setString(2, user.getNom());
            stat.setString(3, user.getPrenom());
            stat.setString(4, user.getEmail());
            stat.setString(5, user.getTelephone());
            stat.setString(6, user.getPassword());
            stat.setInt(7, user.getProfile().getId());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Utilisateur", user.toString(), "", Utilis.user));
        }
        return teste > 0;
    }

    public boolean insertTrace(Trace trace) {
        int teste = 0;
        String query = "insert into trace () values (NULL,?,?,?,?,now())";
        try {
            PreparedStatement stat = connection.prepareCall(query);
            stat.setString(1, trace.getAction());
            stat.setString(2, trace.getObjet());
            stat.setInt(3, trace.getUser().getId());
            stat.setString(4, trace.getValeur());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return teste > 0;
    }
    
    public boolean insertSuiviCaisse(SuiviCaisse trace) {
        int teste = 0;
        String query = "insert into suiviCaisse () values (NULL,?,?,?,?,now(),?,?)";
        try {
            PreparedStatement stat = connection.prepareCall(query);
            stat.setString(1, trace.getAction());
            stat.setString(2, trace.getObjet());
            stat.setInt(3, trace.getUser().getId());
            stat.setString(4, trace.getValeur());
            stat.setString(5, trace.getModePaiement());
            stat.setInt(6, trace.getRestant());


            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return teste > 0;
    }
    
    
    public boolean insertOuvrirCaisse(OuvreCaisse ouvre) {
        int teste = 0;
        String query = "insert into ouvreCaisse () values (NULL,?,?,?,now())";
        try {
            PreparedStatement stat = connection.prepareCall(query);
            stat.setInt(1, ouvre.getFond());
            stat.setString(2, ouvre.getOperateur());
            stat.setString(3, ouvre.getCommentaire());
           


            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        
        return teste > 0;
    }
    
    
    
    public boolean insertFermerCaisse(FermeCaisse ferme) {
        int teste = 0;
        String query = "insert into fermeCaisse () values (NULL,?,?,?,?,?,?,?,now())";
        try {
            PreparedStatement stat = connection.prepareCall(query);
            stat.setInt(1, ferme.getChiffre());
            stat.setInt(2, ferme.getCarte());
            stat.setInt(3, ferme.getEspece());
            stat.setInt(4, ferme.getCheque());
            stat.setInt(5, ferme.getAttente());
            stat.setString(6, ferme.getOperateur());
            stat.setString(7, ferme.getCommentaire());
           
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return teste > 0;
    }

    public boolean insertFicheTechnique(Client client) {
        int teste = 0;
        String query = "";
        PreparedStatement stat = null;
        try {
            if (client.isEst_proprietaire_carte()) {
                query = "insert into client () values (null,true,true,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
                stat = connection.prepareStatement(query);
                stat.setInt(1, client.getCarte().getReference());
                stat.setString(2, client.getNom());
                stat.setString(3, client.getPrenom());
                stat.setString(4, client.getAnniversaire());
                stat.setString(5, client.getMobile1());
                stat.setString(6, client.getMobile2());
                stat.setString(7, client.getTelBureau());
                stat.setString(8, client.getTelDomicile());
                stat.setString(9, client.getEmail());
                stat.setString(10, client.getAdresse());
                if (client.getProfession() == null) {
                    stat.setInt(11, 0);
                } else {
                    stat.setInt(11, client.getProfession().getId());
                }
                if (client.getTypeCheveux() != null) {
                    stat.setInt(12, client.getTypeCheveux().getId());
                } else {
                    stat.setInt(12, 0);
                }
                if (client.getCuirChevelu() != null) {
                    stat.setInt(13, client.getCuirChevelu().getId());
                } else {
                    stat.setInt(13, 0);
                }
                if (client.getDensiteCheveux() != null) {
                    stat.setInt(14, client.getDensiteCheveux().getId());
                } else {
                    stat.setInt(14, 0);
                }
                if (client.getTextureCheveux() != null) {
                    stat.setInt(15, client.getTextureCheveux().getId());
                } else {
                    stat.setInt(15, 0);
                }
                if (client.getEtatCheveux() != null) {
                    stat.setInt(16, client.getEtatCheveux().getId());
                } else {
                    stat.setInt(16, 0);
                }
                stat.setBoolean(17, client.isEstTechnique());

            } else if (client.isUtiliseCarte() && !client.isEst_proprietaire_carte()) {
                query = "insert into client () values (null,true,false,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
                stat = connection.prepareStatement(query);

                stat.setInt(1, client.getCarte().getReference());
                stat.setString(2, client.getNom());
                stat.setString(3, client.getPrenom());
                stat.setString(4, client.getAnniversaire());
                stat.setString(5, client.getMobile1());
                stat.setString(6, client.getMobile2());
                stat.setString(7, client.getTelBureau());
                stat.setString(8, client.getTelDomicile());
                stat.setString(9, client.getEmail());
                stat.setString(10, client.getAdresse());
                if (client.getProfession() == null) {
                    stat.setInt(11, 0);
                } else {
                    stat.setInt(11, client.getProfession().getId());
                }
                if (client.getTypeCheveux() != null) {
                    stat.setInt(12, client.getTypeCheveux().getId());
                } else {
                    stat.setInt(12, 0);
                }
                if (client.getCuirChevelu() != null) {
                    stat.setInt(13, client.getCuirChevelu().getId());
                } else {
                    stat.setInt(13, 0);
                }
                if (client.getDensiteCheveux() != null) {
                    stat.setInt(14, client.getDensiteCheveux().getId());
                } else {
                    stat.setInt(14, 0);
                }
                if (client.getTextureCheveux() != null) {
                    stat.setInt(15, client.getTextureCheveux().getId());
                } else {
                    stat.setInt(15, 0);
                }
                if (client.getEtatCheveux() != null) {
                    stat.setInt(16, client.getEtatCheveux().getId());
                } else {
                    stat.setInt(16, 0);
                }
                stat.setBoolean(17, client.isEstTechnique());

            } else if (!client.isUtiliseCarte()) {
                query = "insert into client () values (null,false,false,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
                stat = connection.prepareStatement(query);

                stat.setString(1, client.getNom());
                stat.setString(2, client.getPrenom());
                stat.setString(3, client.getAnniversaire());
                stat.setString(4, client.getMobile1());
                stat.setString(5, client.getMobile2());
                stat.setString(6, client.getTelBureau());
                stat.setString(7, client.getTelDomicile());
                stat.setString(8, client.getEmail());
                stat.setString(9, client.getAdresse());
                if (client.getProfession() == null) {
                    stat.setInt(10, 0);
                } else {
                    stat.setInt(10, client.getProfession().getId());
                }
                if (client.getTypeCheveux() != null) {
                    stat.setInt(11, client.getTypeCheveux().getId());
                } else {
                    stat.setInt(11, 0);
                }
                if (client.getCuirChevelu() != null) {
                    stat.setInt(12, client.getCuirChevelu().getId());
                } else {
                    stat.setInt(12, 0);
                }
                if (client.getDensiteCheveux() != null) {
                    stat.setInt(13, client.getDensiteCheveux().getId());
                } else {
                    stat.setInt(13, 0);
                }
                if (client.getTextureCheveux() != null) {
                    stat.setInt(14, client.getTextureCheveux().getId());
                } else {
                    stat.setInt(14, 0);
                }
                if (client.getEtatCheveux() != null) {
                    stat.setInt(15, client.getEtatCheveux().getId());
                } else {
                    stat.setInt(15, 0);
                }
                stat.setBoolean(16, client.isEstTechnique());
            }
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Client Technique", client.toString(), "", Utilis.user));
        }
        return teste > 0;
    }

    public boolean updateClient(Client client) {
        int teste = 0;
        String update = "update client set utilise_carte = ? ,  est_proprietaire_carte  = ? , "
                + " reference_carte = ? ,  name_client = ? , "
                + " lastname_client = ? ,  anniversaire_client = ? ,  mobile1 = ?,  mobile2 = ? , "
                + " tel_bureau = ? ,  tel_domicile = ? ,  email_client = ? ,  adresse_client = ? , "
                + " id_profession = ?, id_type_de_cheveux = ? , id_cuir_chevelu = ? , id_densite = ?, id_texture_cheveux = ?"
                + " , id_etat_cheveux = ? , est_technique = ?  where id_client  = ? ";
        try {
            PreparedStatement stat = connection.prepareStatement(update);
            stat.setBoolean(1, client.isUtiliseCarte());
            stat.setBoolean(2, client.isEst_proprietaire_carte());
            if (client.getCarte() != null) {
                stat.setInt(3, client.getCarte().getReference());
            } else {
                stat.setInt(3, 0);
            }
            stat.setString(4, client.getNom());
            stat.setString(5, client.getPrenom());
            stat.setString(6, client.getAnniversaire());
            stat.setString(7, client.getMobile1());
            stat.setString(8, client.getMobile2());
            stat.setString(9, client.getTelBureau());
            stat.setString(10, client.getTelDomicile());
            stat.setString(11, client.getEmail());
            stat.setString(12, client.getAdresse());

            if (client.getProfession() == null) {
                stat.setInt(13, 0);
            } else {
                stat.setInt(13, client.getProfession().getId());
            }
            if (client.getTypeCheveux() != null) {
                stat.setInt(14, client.getTypeCheveux().getId());
            } else {
                stat.setInt(14, 0);
            }
            if (client.getCuirChevelu() != null) {
                stat.setInt(15, client.getCuirChevelu().getId());
            } else {
                stat.setInt(15, 0);
            }
            if (client.getDensiteCheveux() != null) {
                stat.setInt(16, client.getDensiteCheveux().getId());
            } else {
                stat.setInt(16, 0);
            }
            if (client.getTextureCheveux() != null) {
                stat.setInt(17, client.getTextureCheveux().getId());
            } else {
                stat.setInt(17, 0);
            }
            if (client.getEtatCheveux() != null) {
                stat.setInt(18, client.getEtatCheveux().getId());
            } else {
                stat.setInt(18, 0);
            }
            stat.setBoolean(19, client.isEstTechnique());
            stat.setInt(20, client.getId());

            teste = stat.executeUpdate();

            stat.close();
        } catch (SQLException e) {
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Mise à jour", "Client Référence:"+client.getCarte().getReference(), client.toString(), "", Utilis.user));
        }
        System.out.println(teste);
        return teste > 0;
    }

    public boolean insertUtilisateurCarte(Client client) {
        int teste = 0;
        String query = "";
        PreparedStatement stat = null;
        try {
            if (client.isEst_proprietaire_carte()) {
                query = "insert into client () values (null,true,true,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,?)";
                stat = connection.prepareStatement(query);
                stat.setInt(1, client.getCarte().getReference());
                stat.setString(2, client.getNom());
                stat.setString(3, client.getPrenom());
                stat.setString(4, client.getAnniversaire());
                stat.setString(5, client.getMobile1());
                stat.setString(6, client.getMobile2());
                stat.setString(7, client.getTelBureau());
                stat.setString(8, client.getTelDomicile());
                stat.setString(9, client.getEmail());
                stat.setString(10, client.getAdresse());
                if (client.getProfession() == null) {
                    stat.setInt(11, 0);
                } else {
                    stat.setInt(11, client.getProfession().getId());
                }
                stat.setBoolean(12, client.isEstTechnique());
            } else if (!client.isEst_proprietaire_carte()) {
                query = "insert into client () values (null,true,false,?,?,?,?,?,?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,?)";
                stat = connection.prepareStatement(query);

                stat.setInt(1, client.getCarte().getReference());
                stat.setString(2, client.getNom());
                stat.setString(3, client.getPrenom());
                stat.setString(4, client.getAnniversaire());
                stat.setString(5, client.getMobile1());
                stat.setString(6, client.getMobile2());
                stat.setString(7, client.getTelBureau());
                stat.setString(8, client.getTelDomicile());
                stat.setString(9, client.getEmail());
                stat.setString(10, client.getAdresse());
                if (client.getProfession() == null) {
                    stat.setInt(11, 0);
                } else {
                    stat.setInt(11, client.getProfession().getId());
                }
                stat.setBoolean(12, client.isEstTechnique());
            }
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Utilisateur de carte", client.toString(), "", Utilis.user));
        }
        return teste > 0;
    }

    public boolean insertProfession(Profession profession) {
        int teste = 0;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate("insert into profession () values (null,'" + profession.getProfession() + "')");
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Ajout", "Client Technique", profession.toString(), "", Utilis.user));
        }
        return teste > 0;
    }

    public List<Integer> insertLotCarte(int debut, int fin, String date) {
        List<Integer> list = new ArrayList<>();
        int teste = 0;
        final String query = "insert into carte () values (null,?,0,0,0,0,0,?,null,null,0,0)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            for (int i = debut; i <= fin; i++) {
                stat.setInt(1, i);
                stat.setString(2, date);
                teste = stat.executeUpdate();
                list.add(teste);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "liste carte","debut:"+ debut+"fin"+fin, "", Utilis.user));

        return list.isEmpty() ? null : list;
    }

    public boolean insertCarte(int numero, String date) {
        int teste = 0;
        final String query = "insert into carte () values (null,?,0,0,0,0,0,?,null,null,0,0)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, numero);
//            stat.setInt(2, Integer.parseInt(Utilis.trouverParametre("MAX_USER_CARTE").getValeur()));
            stat.setString(2, date);
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "carte",""+ numero, "", Utilis.user));
        return teste > 0;
    }

    public List<PrestationTechnique> listPrestationTechniqueByIdClient(int id) {
        List<PrestationTechnique> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM prestation_technique where id_client = " + id + " ORDER BY id_prestation_technique DESC ");
            while (rs.next()) {
                PrestationTechnique pt = new PrestationTechnique();
                pt.setId(rs.getInt("id_prestation_technique"));
                CategoriePrestation cp = new CategoriePrestation();
                cp.setId(rs.getInt("id_categorie_prestation_technique"));
                pt.setCategeorie(cp);
                pt.setClient(rs.getInt("id_client"));
                pt.setDate(rs.getDate("date").toString());
                pt.setMontage(rs.getString("montage"));
                pt.setTechnique(rs.getString("technique"));
                pt.setPause(rs.getInt("temps_pause"));
                pt.setObservation(rs.getString("observations"));

                list.add(pt);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<CategoriePrestation> ListCategoriePrestation() {
        List<CategoriePrestation> list = new ArrayList<>();
        String query = "select * from categorie_prestation_technique join type_prestation on categorie_prestation_technique.id_type_prestation = type_prestation.id_type_prestation ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                CategoriePrestation cat = new CategoriePrestation();

                cat.setId(rs.getInt("id_categorie_prestation_technique"));
                cat.setNom(rs.getString("nom_categorie_prestation_technique"));
                cat.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), rs.getString("nom_type_prestation"), rs.getBoolean("est_technique")));
                list.add(cat);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Client> rechercherParService(boolean pronfond, int key) {
        List<Client> list = new ArrayList<>();
        String query = "";
        if (!pronfond) {
            query = "select * from client where id_client in (select id_client from prestation_technique "
                    + "where id_categorie_prestation_technique in (select id_categerie_prestation_technique from "
                    + "categeorie_prestation_technique where id_type_prestation = ? ) ) AND client.supprimer = 0 ";
        } else {
            query = "select * from client where id_client in (select id_client from  prestation_technique "
                    + "where id_categorie_prestation_technique = ?)";
        }
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, key);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));
                client.setEstTechnique(rs.getBoolean("est_technique"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), "non défini");
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), "non défini");
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), "non défini");
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), "non défini");
                Profession profession = new Profession(rs.getInt("id_profession"), "non défini");

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Produit> listProduitsTechnique(int key) {
        List<Produit> list = new ArrayList<>();
        String query = "select * from produit where id_produit in (select id_produit from produit_utilise "
                + "where id_prestation_technique = ? )";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, key);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), null);
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), null);
                Marque marque = new Marque(result.getInt("id_marque"), null, 0);
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));

                list.add(produit);
            }
            result.close();
            stat.close();
            stat = null;
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public boolean insertConseil(SuiviMaison conseil) {
        int teste = 0;
        String query = "insert into conseil () values (null, CURDATE(),?,?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, conseil.getClient());
            stat.setString(2, conseil.getConseil());
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "Suivi maison", conseil.getConseil(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertParrainage(Parrainage parraine) {
        int teste = 0;
        String query = "insert into parrainage () values (null,?,?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, parraine.getReference_carte());
            stat.setString(2, Utilis.dateInput(parraine.getDate()));
            stat.setString(3, parraine.getTelephone());
            stat.setString(4, parraine.getNomParraine());
            stat.setString(5, parraine.getPrenomParraine());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        insertTrace(new Trace(0, "Fidélité Parrainage", "Carte: " + parraine.getReference_carte(), parraine.getNomParraine() + " " + parraine.getPrenomParraine(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertVisite(Visite visite) {
        int teste = 0;
        String query = "insert into visite () values (null,?,?,?)";

        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, visite.getDate());
            stat.setInt(2, visite.getReferenceCarte());
            stat.setInt(3, visite.getNombrePoints());

            teste = stat.executeUpdate();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        insertTrace(new Trace(0, "Fidélité visite", "Carte: " + visite.getReferenceCarte(), "point: " + visite.getNombrePoints(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertPointVisite(Visite visite) {
        int teste = 0;
        String query = "update carte set nombre_fidelite_prestation = ( nombre_fidelite_prestation + ? ) where reference_carte = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, visite.getNombrePoints());
            stat.setInt(2, visite.getReferenceCarte());

            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste > 0;
    }

    public List<Visite> listVisites(int reference) {
        List<Visite> list = new ArrayList<>();
        String query = "select * from visite where reference_carte = ? order by id_visite desc ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, reference);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Visite visite = new Visite();
                visite.setId(rs.getInt("id_visite"));
                visite.setDate(rs.getDate("date_visite").toString());
                visite.setReferenceCarte(rs.getInt("reference_carte"));
                visite.setNombrePoints(rs.getInt("nombre_points"));
                list.add(visite);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Parrainage> listParrainage(int reference) {
        List<Parrainage> list = new ArrayList<>();
        String query = "select * from parrainage where reference_carte = " + reference + " order by date desc ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Parrainage p = new Parrainage();
                p.setId(rs.getInt("id_parrainage"));
                p.setDate(rs.getDate("date").toString());
                p.setReference_carte(rs.getInt("reference_carte"));
                p.setNomParraine(rs.getString("nom_parraine"));
                p.setPrenomParraine(rs.getString("prenom_parraine"));
                p.setTelephone(rs.getString("telephone"));

                p.setParraine(p.getNomParraine() + " " + p.getPrenomParraine());

                list.add(p);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public boolean updateUser(User user) {
        int teste = 0;
        String pwd = "MD5(?)";
        if (user.getPassword().length() >= 25) {
            pwd = " ? ";
        }
        String query = "update user set name = ?, lastname = ?, username = ?, password = " + pwd
                + ", email = ? ,  telephone = ? , id_profil = ? where id_user = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, user.getNom());
            stat.setString(2, user.getPrenom());
            stat.setString(3, user.getUsername());
            stat.setString(4, user.getPassword());
            stat.setString(5, user.getEmail());
            stat.setString(6, user.getTelephone());
            stat.setInt(7, user.getProfile().getId());
            stat.setInt(8, user.getId());

            teste = stat.executeUpdate();
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Mise à jour", "Utilisateur", utilisateur.toString(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertParametre(Parametre parametre) {
        int teste = 0;
        String query = "insert into parametre () values (?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, parametre.getCle());
            stat.setString(2, parametre.getValeur());
            stat.setBoolean(3, parametre.isAffiche());
            stat.setString(4, parametre.getCommentaire());

            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "Parametre", parametre.getCle(), "", Utilis.user));
        return teste > 0;
    }

    public List<Parametre> listParametres() {
        List<Parametre> list = new ArrayList<>();

        String query = "select * from parametre ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Parametre parametre = new Parametre();
                parametre.setCle(rs.getString("cle"));
                parametre.setValeur(rs.getString("valeur_parametre"));
                parametre.setAffiche(rs.getBoolean("affiche"));
                parametre.setCommentaire(rs.getString("explications"));
                parametre.setSuplementaire(rs.getString("suplementaire"));
                list.add(parametre);

            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public Parametre parametre(String cle) {
        Parametre parametre = null;
        String query = "Select * from  parametre where cle = '" + cle + "'";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                parametre = new Parametre();
                parametre.setCle(rs.getString("cle"));
                parametre.setValeur(rs.getString("valeur_parametre"));
                parametre.setAffiche(rs.getBoolean("affiche"));
                parametre.setCommentaire(rs.getString("explications"));
                parametre.setSuplementaire(rs.getString("suplementaire"));
            }
            rs.close();
            stat.close();;
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return parametre;
    }

    public List<Integer> updateParametre(List<Parametre> parametres) {
        int teste = 0;
        List<Integer> list = new ArrayList<>();
        String query = "update parametre set  valeur_parametre = ? , affiche = ?, explications = ? ,suplementaire  = MD5( ? ) where  cle = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            int len = parametres.size();
            for (int i = 0; i < len; i++) {
                stat.setString(1, parametres.get(i).getValeur());
                stat.setBoolean(2, parametres.get(i).isAffiche());
                stat.setString(3, parametres.get(i).getCommentaire());
                stat.setString(4, parametres.get(i).getSuplementaire());
                stat.setString(5, parametres.get(i).getCle());
                teste = stat.executeUpdate();//  
                list.add(teste);
            }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
           insertTrace(new Trace(0, "Ajout", "Parametre", parametres.get(0).getCle(), "", Utilis.user));

        return list.isEmpty() ? null : list;
    }

    public boolean updateParametre(Parametre parametre) {
        int teste = 0;
        String query = "update parametre set  valeur_parametre = ? , affiche = ?, explications = ? ,suplementaire  = MD5( ? ) where  cle = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, parametre.getValeur());
            stat.setBoolean(2, parametre.isAffiche());
            stat.setString(3, parametre.getCommentaire());
            stat.setString(4, parametre.getSuplementaire());
            stat.setString(5, parametre.getCle());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "parametre", "modification paramètre", ""+parametre.getValeur(),"", Utilis.user));
        return teste > 0;
    }

    public boolean viderTraces() {
        int teste = 1;
        String query = "TRUNCATE trace";
        try {
            Statement stat = connection.createStatement();
            stat.executeUpdate(query);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            teste = 0;
        }
        insertTrace(new Trace(0, "Vider", "Trace", "ok", "", Utilis.user));
        return teste > 0;
    }
//Charger la derniere prestation du client s'il y'a.

    public PrestationTechnique dernierePrestationClient(int id_client, int id_type_prestation) {
        PrestationTechnique pt = null;
        String query = "SELECT * FROM prestation_technique JOIN categorie_prestation_technique ON prestation_technique.id_categorie_prestation_technique = categorie_prestation_technique.id_categorie_prestation_technique WHERE prestation_technique.id_client = ? AND categorie_prestation_technique.id_type_prestation = ? ORDER by id_prestation_technique desc LIMIT 1 ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, id_client);
            stat.setInt(2, id_type_prestation);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                pt = new PrestationTechnique();
                CategoriePrestation cat = new CategoriePrestation();
                cat.setId(rs.getInt("id_categorie_prestation_technique"));
                cat.setNom(rs.getString("nom_categorie_prestation_technique"));
                cat.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                pt.setCategeorie(cat);
                pt.setClient(id_client);
                pt.setDate(rs.getDate("date").toString());
                pt.setId(rs.getInt("id_prestation_technique"));
                pt.setMontage(rs.getString("montage"));
                pt.setTechnique(rs.getString("technique"));
                pt.setObservation(rs.getString("observations"));
                pt.setPause(rs.getInt("temps_pause"));
                pt.setProduits(null);

            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return pt;
    }

    public void insertProfession(String[] list) {
        String query = "insert into profession () values (null,?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            for (int i = 0; i < list.length; i++) {
                stat.setString(1, list[i]);
                int j = stat.executeUpdate();
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean updateProduit(String champ, String valeur, int key) {
        int teste = 0;
        String query = "update produit set " + champ + " = " + valeur + " where id_produit = " + key;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "mis à jour", "Produit", "valeur:"+valeur, "", Utilis.user));

        return teste > 0;
    }

    public boolean mouvementStock(int id, String sens, int qt, String motif) {
        int teste = 0;
        final String qr = "insert into mouvement_stock()  values (null,now(),?,?,?,?)";
        try {
            PreparedStatement stat = getConnection().prepareStatement(qr);
            stat.setInt(1, id);
            stat.setString(2, sens);
            stat.setInt(3, qt);
            stat.setString(4, motif);
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        insertTrace(new Trace(0, "Mis à jour", "Stock", "sens:"+sens+"quantité:"+qt, "", Utilis.user));

        return teste > 0;
    }

    public Produit selectProduitById(String champ, String key) {
        Produit produit = null;
        String query = "SELECT * FROM produit where produit." + champ + " = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, key);
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                produit = new Produit();
//                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
//                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
//                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
//                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
//                produit.setSousfamille(sousFamille);
//                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
            }
            result.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return produit;
    }

    public boolean insertAchat(Achat achat) {
        int teste = 0;
        Carte carte=new Carte();
        String query = "insert into achat () values (NULL, NOW() ,?,?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, achat.getClient());
            stat.setInt(2, achat.getMontantAvantRemise());
            stat.setInt(3, achat.getRemiseAchat());
            stat.setInt(4, achat.getMontantApresRemise());
            stat.setBoolean(5, achat.isUtiliseFideite());

            teste = stat.executeUpdate();

            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Fidélité Achat", "Achat", "carte: " + carte.getId(), "", Utilis.user));
        return teste > 0;
    }

    public Achat dernierAchat(int client) {
        Achat achat = null;
        String query = "select * from achat where id_client = " + client + " order by id_achat desc limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                achat = new Achat();
                achat.setId(rs.getInt("id_achat"));
                achat.setDate(rs.getString("date_achat"));
                achat.setClient(rs.getInt("id_client"));
                achat.setMontantAvantRemise(rs.getInt("montant_avant_remise"));
                achat.setRemiseAchat(rs.getInt("remise_achat"));
                achat.setMontantApresRemise(rs.getInt("montant_apres_remise"));
                achat.setUtiliseFideite(rs.getBoolean("utilise_fidelite"));
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return achat;
    }

    public List<Integer> insertProduitAchete(List<ProduitAchete> paniers, int achat) {
        int teste = 0;
        List<Integer> list = new ArrayList<>();
        String query = "insert into produit_achete () values (NULL,?,?,?)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            int len = paniers.size();
            for (int i = 0; i < len; i++) {
                stat.setInt(1, achat);
                stat.setInt(2, paniers.get(i).getProduit().getId());
                stat.setInt(3, paniers.get(i).getQuantite());

                teste = stat.executeUpdate();
                list.add(teste);
            }
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
       insertTrace(new Trace(0, "Insertion ", "Produit","produit:"+ paniers.get(0).getProduit(), "", Utilis.user));

        return list.isEmpty() ? null : list;
    }

    public boolean updateAchat(Achat achat) {
        int teste = 0;
        String query = "update achat set montant_avant_remise = ? , remise_achat = ? , montant_apres_remise = ? , utilise_fidelite = ? where id_achat = ? and id_client = ? ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, achat.getMontantAvantRemise());
            stat.setInt(2, achat.getRemiseAchat());
            stat.setInt(3, achat.getMontantApresRemise());
            stat.setBoolean(4, achat.isUtiliseFideite());
            stat.setInt(5, achat.getId());
            stat.setInt(6, achat.getClient());
            teste = stat.executeUpdate();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        insertTrace(new Trace(0, "Mise à jour", "Achat", "montant: " + achat.getMontantApresRemise(), "", Utilis.user));
        return teste > 0;
    }

    public List<Client> rechercheParProduit(String key) {
        List<Client> list = new ArrayList<>();
        String query = "SELECT * FROM client LEFT JOIN profession on client.id_profession = profession.id_profession LEFT JOIN "
                + "type_de_cheveux on client.id_type_de_cheveux = type_de_cheveux.id_type_de_cheveux LEFT JOIN cuir_chevelu "
                + "on client.id_cuir_chevelu = cuir_chevelu.id_cuir_chevelu LEFT JOIN etat_cheveux on "
                + "client.id_etat_cheveux = etat_cheveux.id_etat_cheveux LEFT JOIN densite ON client.id_densite = "
                + "densite.id_densite LEFT JOIN texture_cheveux ON client.id_texture_cheveux = texture_cheveux.id_texture_cheveux "
                + "WHERE client.est_technique = 1 and client.supprimer = 0 and client.id_client IN ( SELECT achat.id_client FROM achat WHERE achat.id_achat"
                + " IN ( SELECT produit_achete.id_achat FROM produit_achete WHERE produit_achete.id_produit "
                + "IN ( SELECT produit.id_produit FROM produit WHERE produit.name_produit LIKE ? ) ) )";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, "%" + key + "%");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setUtiliseCarte(rs.getBoolean("utilise_carte"));
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                client.setAnniversaire(rs.getString("anniversaire_client"));
                client.setMobile1(rs.getString("mobile1"));
                client.setMobile2(rs.getString("mobile2"));
                client.setTelBureau(rs.getString("tel_bureau"));
                client.setTelDomicile(rs.getString("tel_domicile"));
                client.setAdresse(rs.getString("adresse_client"));
                client.setEmail(rs.getString("email_client"));
                client.setEst_proprietaire_carte(rs.getBoolean("est_proprietaire_carte"));

                Densite densite = new Densite(rs.getInt("id_densite"), rs.getString("densite"));
                EtatCheveux etatCheveux = new EtatCheveux(rs.getInt("id_etat_cheveux"), rs.getString("name_etat_cheveux"));
                TypeCheveux typeCheveux = new TypeCheveux(rs.getInt("id_type_de_cheveux"), rs.getString("name_type_de_cheveux"));
                CuirChevelu crc = new CuirChevelu(rs.getInt("id_cuir_chevelu"), rs.getString("name_cuir_chevelu"));
                TextureCheveux tc = new TextureCheveux(rs.getInt("id_texture_cheveux"), rs.getString("texture_cheveux"));
                Profession profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));

                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));

                client.setCarte(carte);
                client.setProfession(profession);
                client.setTypeCheveux(typeCheveux);
                client.setTextureCheveux(tc);
                client.setEtatCheveux(etatCheveux);
                client.setCuirChevelu(crc);
                client.setDensiteCheveux(densite);

                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list;
    }

    public boolean insertDetailInventaire(DetailInventaire detail) {
        int teste = 0;
        String query = "insert into detail_inventaire () values (NULL,?,?,?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, detail.getInventaire());
            stat.setInt(2, detail.getProduit().getId());
            stat.setInt(3, detail.getQuantiteRealVente());
            stat.setInt(4, detail.getQuantiteTheoriqueVente());
            stat.setInt(5, detail.getQuantiteRealService());
            stat.setInt(6, detail.getQuantiteTheoriqueService());

            teste = stat.executeUpdate();

            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return teste > 0;
    }

    public boolean insertInventaire(Inventaire inventaire) {
        int teste = 0;
        String query = "insert into inventaire () values (NULL,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, inventaire.getDate());
            stat.setString(2, inventaire.getCommentaire());

            teste = stat.executeUpdate();

            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "Inventaire", "du : " + inventaire.getDate(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertInventaire(List<Inventaire> inventaires) {
        int teste = 0;
        List<Integer> tests = new ArrayList<>();
        String query = "insert into inventaire () values (NULL,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            int len = inventaires.size();
            for (int i = 0; i < len; i++) {
                stat.setString(1, inventaires.get(i).getDate());
                stat.setString(2, inventaires.get(i).getCommentaire());

                tests.add(stat.executeUpdate());
            }

            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return Utilis.ArrayInsertTest(tests);
    }

    public boolean deleteInventaire(Inventaire inventaire) {
        int teste = 0;
        String query = "delete from inventaire where id = " + inventaire.getId();
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "Suppression", "du :" + inventaire.getDate(), "OK", "", Utilis.user));
        return teste > 0;
    }

    public Inventaire lastInsertInventaire() {
        Inventaire inventaire = null;
        String query = "select * from inventaire order by id_inventaire desc ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                inventaire = new Inventaire();
                inventaire.setId(rs.getInt("id_inventaire"));
                inventaire.setDate(rs.getDate("date_inventaire").toString());
                inventaire.setCommentaire(rs.getString("commentaire"));
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return inventaire;
    }

    public List<SuiviMaison> ListSuiviMaison(int client) {
        List<SuiviMaison> list = new ArrayList<>();
        String query = "select * from conseil where id_client = " + client + " order by id_conseil desc limit 10";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                SuiviMaison suivi = new SuiviMaison();
                suivi.setId(rs.getInt("id_client"));
                suivi.setDate(rs.getDate("date_conseil").toString());
                suivi.setClient(rs.getInt("id_client"));
                suivi.setConseil(rs.getString("conseil"));

                list.add(suivi);
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Achat> listAchatClient(int client) {
        List<Achat> list = new ArrayList<>();
        String query = "select * from achat  where id_client  = " + client + " order by id_achat desc ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Achat achat = new Achat();
                achat.setId(rs.getInt("id_achat"));
                achat.setClient(rs.getInt("id_client"));
                achat.setDate(rs.getString("date_achat"));
                achat.setRemiseAchat(rs.getInt("remise_achat"));
                achat.setMontantApresRemise(rs.getInt("montant_apres_remise"));
                achat.setMontantAvantRemise(rs.getInt("montant_avant_remise"));
                achat.setUtiliseFideite(rs.getBoolean("utilise_fidelite"));

                list.add(achat);

            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public List<Produit> listProduits(int achat) {
        List<Produit> list = new ArrayList<>();
        String query = "select id_produit , name_produit from produit where id_produit in ( select id_produit from produit_achete where id_achat = ? )";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, achat);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id_produit"));
                produit.setNom(rs.getString("name_produit"));
                list.add(produit);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public boolean pointAchat(int carte) {
        int teste = 0;
        String query = "update carte set nb_pts_fidelite_achat = ( nb_pts_fidelite_achat + 1 ) where reference_carte = " + carte;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste > 0;
    }

    public boolean point(int carte, int point, String fidelite) {
        int teste = 0;
        String query = "update carte set " + fidelite + " = ( " + fidelite + " + " + point + " ) where reference_carte = " + carte;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste > 0;
    }

    public boolean pointParrainage(int carte) {
        int teste = 0;
        String query = "update carte set nombre_fidelite_parrainage = ( nombre_fidelite_parrainage +  " + Utilis.trouverParametre("FIDELITE_PARRAINAGE").getValeur() + " ) where reference_carte = " + carte;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste > 0;
    }

    public boolean pointVisite(int carte) {
        int teste = 0;
        String query = "update carte set nombre_fidelite_prestation  = ( nombre_fidelite_prestation + 1 ) where reference_carte = " + carte;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste > 0;
    }

    public Profession lastProfession() {
        Profession profession = null;
        String query = "select * from profession order by id_profession desc limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                profession = new Profession();
                profession.setId(rs.getInt("id_profession"));
                profession.setProfession(rs.getString("name_profession"));

            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return profession;
    }

    public boolean insertEmailEnvoye(Mail mail) {
        int teste = 0;
        String query = "insert into mail_envoye () values (NULL,NOW(),?,?,?,?,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, mail.getExpediteur());
            stat.setString(2, mail.getDestinataire().toString());
            stat.setString(3, mail.getObjet());
            stat.setString(4, mail.getTexte());
            stat.setString(5, mail.getFichierAttache());
            stat.setBoolean(6, mail.isEtat());

            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        // insertTrace(new Trace(0, "Envoi d\'email", "Email", mail.getObjet(), "", Utilis.user));
        insertTrace(new Trace(0, "Envoi d\'email", "Email", mail.getObjet(), "", Utilis.user));

        return teste > 0;
    }

    public int nombreClient() {
        int count = 0;
        String query = "select count(*) as nombre from client ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("nombre");
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return count;
    }

    public int nombreCarte() {
        int teste = 0;
        String query = "select count(*) as nombre from carte ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                teste = rs.getInt("nombre");
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return teste;
    }

    public int countProduit() {
        int teste = 0;
        String query = " select count(*) nombre  from produit";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                teste = rs.getInt("nombre");

            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return teste;
    }

    public Marque lastMarque() {
        Marque marque = null;
        String query = "select * from  marque order by id_marque desc limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                marque = new Marque(rs.getInt("id_marque"), rs.getString("nom_marque"), rs.getInt("id_champ_utilisation_marque"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return marque;
    }

    public SousFamille lastSousFamille() {
        SousFamille sous = null;
        String query = " select * from sous_famille_produit order by id_sous_famille desc limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                sous = new SousFamille(rs.getInt("id_sous_famille"), rs.getString("nom_sous_famille"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return sous;
    }

    public GammeProduit lastGammeProduit() {
        GammeProduit gp = null;
        String query = "select * from gamme_produit order by id_gamme_produit desc limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                gp = new GammeProduit(rs.getInt("id_gamme_produit"), rs.getString("nom_gamme_produit"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return gp;
    }

    public boolean insertMarque(Marque marque) {
        int teste = 0;
        String query = "insert into marque () values (NULL,?,?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, marque.getMarque());
            stat.setInt(2, marque.getUtilisation());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return teste > 0;
    }

    public boolean insertGammeProduit(GammeProduit gp) {
        int teste = 0;
        String query = "insert into gamme_produit () values (NULL, ?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, gp.getGamme());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "Gamme Produit", gp.getGamme(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertSousFamille(SousFamille sf) {
        int teste = 0;
        String query = "insert into sous_famille_produit () values (NULL, ?) ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, sf.getSousFamille());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "Ajout", "Sous Famille Produit", sf.getSousFamille(), "", Utilis.user));
        return teste > 0;
    }

    public List<Service> listService() {
        List<Service> list = new ArrayList<>();
        String query = "SELECT * FROM prestation JOIN sous_categorie_prestation on sous_categorie_prestation.id_sous_categorie = prestation.id_sous_categorie JOIN categorie_prestation ON sous_categorie_prestation.id_categorie = categorie_prestation.id_categorie JOIN type_prestation on type_prestation.id_type_prestation = categorie_prestation.id_type_prestation ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);

            while (rs.next()) {
                Service service = new Service();
                TypePrestation typePrestation = new TypePrestation(rs.getInt("id_type_prestation"),
                        rs.getString("nom_type_prestation"), rs.getBoolean("est_technique"));
                service.setTypePrestation(typePrestation);
                CategoriePrestation categorie = new CategoriePrestation();

                categorie.setId(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("name_categorie"));
                categorie.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                categorie.setCode(rs.getString("code_categorie"));
                categorie.setEstSousCategorie(rs.getBoolean("est_une_sous_categorie"));
                categorie.setEstPrestation(rs.getBoolean("est_une_prestation"));
                categorie.setEstTechnique(rs.getBoolean("est_technique"));
                service.setCategoriePrestation(categorie);

                SousCategoriePrestation sc = new SousCategoriePrestation();
                sc.setId(rs.getInt("id_sous_categorie"));
                sc.setNom(rs.getString("nom_sous_categorie"));
                sc.setCategoriePrestation(new CategoriePrestation(rs.getInt("id_categorie"), "", null, "", true, true, true));
                sc.setEstPrestation(rs.getBoolean("est_une_prestation"));
                sc.setEstTechnique(rs.getBoolean("est_technique"));
                sc.setCode(rs.getString("code_categorie"));

                service.setSousCategoriePrestation(sc);
                Prestation prestation = new Prestation();

                prestation.setId(rs.getInt("id_prestation"));
                prestation.setNom(rs.getString("nom_prestation"));
                prestation.setCout(rs.getInt("cout_prestation"));
                prestation.setSous_categorie(new SousCategoriePrestation(rs.getInt("id_sous_categorie"), "", null, true, true));
                prestation.setFixe(rs.getBoolean("cout_fixe"));
                prestation.setBorneInferieur(rs.getInt("borne_inferieur_prix"));
                prestation.setBorneSuperieur(rs.getInt("borne_superieur_prix"));

                service.setPrestation(prestation);

                list.add(service);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public int nombrePrestation() {
        int nombre = 0;
        String query = "select count(*) nombre from prestation ";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                nombre = rs.getInt("nombre");
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return nombre;
    }

    public List<Produit> rechercheProduitParGamme(String key) {
        int val = 0;
        if (key.toLowerCase().contains("se")) {
            val = 1;
        }
        if (key.toLowerCase().contains("ve")) {
            val = 0;
        }
        List<Produit> list = new ArrayList<>();
        String query = "SELECT * FROM produit LEFT JOIN gamme_produit ON gamme_produit.id_gamme_produit = produit.id_gamme_produit "
                + "LEFT JOIN marque ON marque.id_marque = produit.id_marque LEFT JOIN sous_famille_produit ON "
                + "sous_famille_produit.id_sous_famille = produit.id_sous_famille WHERE utilisation_produit = " + val;

        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery(query);
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
                list.add(produit);
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return list.isEmpty() ? null : list;
    }

    public List<Produit> rechercheProduitParMarque(String key) {
        List<Produit> list = new ArrayList<>();
        String query = "SELECT * FROM produit LEFT JOIN gamme_produit ON gamme_produit.id_gamme_produit = produit.id_gamme_produit LEFT JOIN marque ON marque.id_marque = produit.id_marque LEFT JOIN sous_famille_produit ON sous_famille_produit.id_sous_famille = produit.id_sous_famille WHERE produit.id_marque in (SELECT marque.id_marque FROM marque WHERE marque.nom_marque like '%" + key + "%') and produit.supprime = 0";
        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery(query);
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
                list.add(produit);
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }

    public List<Produit> rechercheProduitParDesignation(String key) {
        List<Produit> list = new ArrayList<>();
        String query = "SELECT * FROM produit INNER JOIN gamme_produit ON gamme_produit.id_gamme_produit = produit.id_gamme_produit INNER JOIN marque ON marque.id_marque = produit.id_marque INNER JOIN sous_famille_produit ON sous_famille_produit.id_sous_famille = produit.id_sous_famille WHERE produit.name_produit like '%" + key + "%' and produit.supprime = 0";

        try {
            Statement stat = connection.createStatement();
            ResultSet result = stat.executeQuery(query);
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
                list.add(produit);
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return list.isEmpty() ? null : list;
    }
    
    
    
    
    
    public Produit rechercheProduit(String key) {
    	Produit produit=null;
        String query = "SELECT * FROM produit where name_produit =?";

        try {
        	 PreparedStatement stat = connection.prepareStatement(query);
        	 stat.setString(1, key);
             ResultSet result = stat.executeQuery();
          	   
            while (result.next()) {
            	produit =new Produit();              
                produit.setNom(result.getString("name_produit"));
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
              
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }

        return produit;
    }
    
    
    
    
    
    
    

    public List<Produit> rechercheProduitParCodeBarre(String key) {
        List<Produit> produits = new ArrayList<Produit>();
        String query = "SELECT * FROM produit left JOIN gamme_produit ON gamme_produit.id_gamme_produit = produit.id_gamme_produit left JOIN marque ON marque.id_marque = produit.id_marque left JOIN sous_famille_produit ON sous_famille_produit.id_sous_famille = produit.id_sous_famille WHERE produit.code_bar_produit =  '" + key.trim() + "' and produit.supprime = 0";
        try {
            Statement stat = getConnection().createStatement();
            ResultSet result = stat.executeQuery(query);
            while (result.next()) {
                SousFamille sousFamille = new SousFamille(result.getInt("id_sous_famille"), result.getString("nom_sous_famille"));
                GammeProduit gamme = new GammeProduit(result.getInt("id_gamme_produit"), result.getString("nom_gamme_produit"));
                Marque marque = new Marque(result.getInt("id_marque"), result.getString("nom_marque"), result.getInt("id_champ_utilisation_marque"));
                Produit produit = new Produit();
                produit.setId(result.getInt("id_produit"));
                produit.setNom(result.getString("name_produit"));
                produit.setCode(result.getString("code_bar_produit"));
                produit.setMarque(marque);
                produit.setUtilisation(result.getBoolean("utilisation_produit"));
                produit.setSousfamille(sousFamille);
                produit.setGammeProduit(gamme);
                produit.setPrix(result.getInt("prix_unitaire"));
                produit.setStockService(result.getInt("qt_stock_service"));
                produit.setStockVente(result.getInt("qt_stock_vente"));
                produit.setSeuilService(result.getInt("qt_seuil_alert_service"));
                produit.setSeuilVente(result.getInt("qt_seuil_alert_vente"));
                produit.setInfo(result.getString("info_complementaire"));
                if (produit.isUtilisation()) {
                    produit.setStock(produit.getStockService());
                } else {
                    produit.setStock(produit.getStockVente());
                }
                produits.add(produit);
            }
            result.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produits;
    }

    public List<Service> rechercheService(String mot, String champ) {
        List<Service> list = new ArrayList<>();
        String query = "SELECT * FROM prestation JOIN sous_categorie_prestation on sous_categorie_prestation.id_sous_categorie = "
                + "prestation.id_sous_categorie JOIN categorie_prestation ON sous_categorie_prestation.id_categorie = "
                + "categorie_prestation.id_categorie JOIN type_prestation on type_prestation.id_type_prestation = "
                + "categorie_prestation.id_type_prestation where " + champ + " like '%" + mot + "%'";

        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);

            while (rs.next()) {
                Service service = new Service();
                TypePrestation typePrestation = new TypePrestation(rs.getInt("id_type_prestation"),
                        rs.getString("nom_type_prestation"), rs.getBoolean("est_technique"));
                service.setTypePrestation(typePrestation);
                CategoriePrestation categorie = new CategoriePrestation();

                categorie.setId(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("name_categorie"));
                categorie.setTypePrestation(new TypePrestation(rs.getInt("id_type_prestation"), "", true));
                categorie.setCode(rs.getString("code_categorie"));
                categorie.setEstSousCategorie(rs.getBoolean("est_une_sous_categorie"));
                categorie.setEstPrestation(rs.getBoolean("est_une_prestation"));
                categorie.setEstTechnique(rs.getBoolean("est_technique"));
                service.setCategoriePrestation(categorie);

                SousCategoriePrestation sc = new SousCategoriePrestation();
                sc.setId(rs.getInt("id_sous_categorie"));
                sc.setNom(rs.getString("nom_sous_categorie"));
                sc.setCategoriePrestation(new CategoriePrestation(rs.getInt("id_categorie"), "", null, "", true, true, true));
                sc.setEstPrestation(rs.getBoolean("est_une_prestation"));
                sc.setEstTechnique(rs.getBoolean("est_technique"));
                sc.setCode(rs.getString("code_categorie"));

                service.setSousCategoriePrestation(sc);
                Prestation prestation = new Prestation();

                prestation.setId(rs.getInt("id_prestation"));
                prestation.setNom(rs.getString("nom_prestation"));
                prestation.setCout(rs.getInt("cout_prestation"));
                prestation.setSous_categorie(new SousCategoriePrestation(rs.getInt("id_sous_categorie"), "", null, true, true));
                prestation.setFixe(rs.getBoolean("cout_fixe"));
                prestation.setBorneInferieur(rs.getInt("borne_inferieur_prix"));
                prestation.setBorneSuperieur(rs.getInt("borne_superieur_prix"));

                service.setPrestation(prestation);

                list.add(service);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        return list.isEmpty() ? null : list;

    }

    public boolean destocker(String produit, String champ) {
        int teste = 0;
        String query = "update produit set " + champ + " = (" + champ + " - 1 ) where code_bar_produit = " + produit;
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "retrait stock", produit + "/1", teste > 0 ? "effectué" : "echec", null, Utilis.getUser()));
        return teste > 0;
    }

    public boolean stocker(int produit, String champ, int stock) {
        int teste = 0;
        String query = "update produit set " + champ + " = (" + champ + " + ? ) where id_produit = " + produit;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, stock);
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "ajout stock", produit + "/" + stock, teste > 0 ? "effectué" : "echec", null, Utilis.getUser()));
        return teste > 0;
    }

    public boolean deleteUser(User user) {
        int teste = 0;
        final String query = "DELETE FROM user WHERE user.id_user = " + user.getId();
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
        }
        insertTrace(new Trace(0, "suppression ", "Utilisateur", user.getUsername(), "", Utilis.user));
        return teste > 0;
    }

    public boolean insertCodeBar(CodeBar codeBar) {
        int teste = 0;
        final String query = "insert into code_bar () values (?,?,1,?,0)";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, codeBar.getCodeBar());
            stat.setInt(2, codeBar.getCarte());
            stat.setString(3, codeBar.getFidelite());

            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public boolean utiliserCodeBar(String code) {
        int teste = 0;
        final String query = " update code_bar set utilisable  = 0 where code_bar.code_bar = '" + code + "'";
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public CodeBar existCodeBar(String code) {
        CodeBar codeBar = null;
        String query = "SELECT * FROM code_bar WHERE code_bar.code_bar = '" + code + "'" + " and utilisable = 1 limit 1";
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                codeBar = new CodeBar(rs.getString("code_bar"), rs.getInt("carte"), rs.getBoolean("utilisable"), rs.getString("fidelite"), rs.getBoolean("imprime"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeBar;
    }

    public boolean tousChangerCarte(int nv, int old) {
        int teste = 0;
        final String query = "UPDATE client SET reference_carte = ? WHERE reference_carte = ? ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, nv);
            stat.setInt(2, old);
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teste > 0;
    }

    public boolean updateTarif(Tarif tarif) {
        int teste = 0;
        final String query = "update tarif set sexe = ? , nom_tarif  = ? , nom_prestation = ? "
                + ",detail_prestation= ?,prix_tarif = ? where id_tarif=? ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, tarif.getSexe());
            stat.setString(2, tarif.getNom());
            stat.setString(3, tarif.getPrestation());
            stat.setString(4, tarif.getDetail());
            stat.setInt(5, tarif.getPrix());
            stat.setInt(6,tarif.getId());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "mis à jour", "Tarif", tarif.getNom(), null, Utilis.getUser()));
        }
        return teste > 0;


    }


    public boolean updatePrestation(Prestation ps) {
        int teste = 0;
        final String query = "update prestation set nom_prestation = ? , cout_prestation  = ? , id_sous_categorie = ? "
                + ",cout_fixe= ?,borne_inferieur_prix = ? , borne_superieur_prix = ? where id_prestation = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, ps.getNom());
            stat.setInt(2, ps.getCout());
            stat.setInt(3, ps.getSous_categorie().getId());
            stat.setBoolean(4, ps.isFixe());
            stat.setInt(5, ps.getBorneInferieur());
            stat.setInt(6, ps.getBorneSuperieur());
            stat.setInt(7, ps.getId());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "mis à jour", "Service", ps.getNom(), null, Utilis.getUser()));
        }
        return teste > 0;
       // return teste > 0;
        // insertTrace(new Trace(0, "mis à jour", "Utilisateur", Utilis.EnregistrementSuccee, "", Utilis.user));
        // insertTrace(new Trace(0, "Suppression", "Client", "Effectuer", null, Utilis.getUser()));

    }

    public boolean aUnAvoir(int carte) {
        int teste = 0;
        final String query = "select count(*) nb from code_bar where utilisable = 1 and imprime = 0 and carte = " + carte;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                teste = rs.getInt("nb");
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public boolean remettreCompteurAvoirZero(CodeBar codebar) {
        int teste = 0;
        final String query = "update code_bar set imprime = 1 where fidelite = ? and carte = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, codebar.getFidelite());
            stat.setInt(2, codebar.getCarte());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public boolean updateCarteFamilleCouple(Carte carte) {
        int teste = 0;
        final String query = "update carte set famille = ? , couple = ? where reference_carte = " + carte.getReference();
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, carte.getFamilleNombre());
            stat.setBoolean(2, carte.isCouple());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
         insertTrace(new Trace(0, "mis à jour", "famille", "carte:"+carte.getReference(), "", Utilis.user));

        return teste > 0;
    }

    public Profession selectProfession(int id) {
        final String query = "select * from profession where id_profession = " + id;
        Profession profession = null;
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                profession = new Profession(rs.getInt("id_profession"), rs.getString("name_profession"));
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profession;
    }



    public boolean deleteTarif(Tarif tarif) {
        int teste = 0;
        final String query = "delete from tarif WHERE id_tarif = " + tarif.getId();
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Suppression", "Tarif" , "Succès", null, Utilis.getUser()));
        }
        return teste > 0;
    }

    public boolean deleteProduit(Produit produit) {
        int teste = 0;
        final String query = "update produit set supprime = 1 WHERE id_produit = " + produit.getId();
        try {
            Statement stat = connection.createStatement();
            teste = stat.executeUpdate(query);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teste > 0;
    }

    public List<Client> utilisateurCarteList() {
        final String query = "select name_client, lastname_client, reference_carte from client where utilise_carte = 1 and client.supprimer = 0 order by reference_carte";
        List<Client> list = new ArrayList<>();
        try {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Client client = new Client();
                client.setNom(rs.getString("name_client"));
                client.setPrenom(rs.getString("lastname_client"));
                Carte carte = new Carte();
                carte.setReference(rs.getInt("reference_carte"));
                client.setCarte(carte);
                list.add(client);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list.isEmpty() ? null : list;
    }

//    delete client
    public boolean delete(Client client) {
        int teste = 0;
        final String query = "update client set client.supprimer = 1 where id_client = ? ";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, client.getId());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
            insertTrace(new Trace(0, "Suppression", "Carte:" + client.getCarte().getReference(), "Succès", null, Utilis.getUser()));
        }
        return teste > 0;
    }

    public boolean deleteCarteReference(Client client) {
        final String query = "update client set reference_carte = 0 where reference_carte = ? and id_client <> ? ";
        int teste = 0;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, client.getCarte().getId());
            stat.setInt(2, client.getId());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* if (teste > 0) {
         insertTrace(new Trace(0, "Déaffectation", "Carte:"+client.getCarte().getReference(), "Succès", null, Utilis.getUser()));
         }*/
        return teste > 0;
    }

    public boolean delete(PrestationTechnique prestationTechnique) {
        int teste = 0;
        final String query = "delete from prestation_technique where id_prestation_technique = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setInt(1, prestationTechnique.getId());
            teste = stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
         insertTrace(new Trace(0, "Suppression", "Prestation","client:"+ prestationTechnique.getClient(), "", Utilis.user));

        return teste > 0;
    }

    public boolean effacerCardData(String id) {
        int teste = 0;
        final String qr = "update carte set nombre_utilisateur_carte = 0, "
                + "nb_pts_fidelite_achat = 0, nombre_fidelite_parrainage = 0,"
                + "nombre_fidelite_prestation = 0, etat_carte = 0, date_attribution = NULL,"
                + "famille = 0, couple = 0 where reference_carte = '" + id.trim() + "'";
        try {
            Statement stat = getConnection().createStatement();
            teste = stat.executeUpdate(qr);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (teste > 0) {
//         insertTrace(new Trace(0, "Déaffectation carte ", "Utilisateur", user.getUsername(), "", Utilis.user));
            insertTrace(new Trace(0, "Déaffectation", "Carte:" + id, "Succès", null, Utilis.getUser()));

        }
        return teste > 0;
    }

    public boolean supprimerCodeBarre(String carte) {
        int teste = 0;
        final String qr = "delete from code_bar where carte = '" + carte.trim() + "'";
        try {
            Statement stat = getConnection().createStatement();
            teste = stat.executeUpdate(qr);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teste > 0;
    }

    public boolean enleverCarteClient(String id, String carte) {
        int teste = 0;
        final String qr = "update client set utilise_carte = 0, est_proprietaire_carte = 0, reference_carte = 0 "
                + "where reference_carte  = '" + carte.trim() + "'" + " and id_client = '" + id.trim() + "'";
        try {
            Statement stat = getConnection().createStatement();
            teste = stat.executeUpdate(qr);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
       insertTrace(new Trace(0, "Supression", "Carte:", carte+"id:"+id, "", Utilis.user));

        return teste > 0;
    }

    public boolean enleverCarteClient(String carte) {
        int teste = 0;
        final String qr = "update client set utilise_carte = 0, est_proprietaire_carte = 0, reference_carte = 0 "
                + "where reference_carte  = '" + carte.trim() + "'";
        try {
            Statement stat = getConnection().createStatement();
            teste = stat.executeUpdate(qr);
            System.out.println(stat);
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
       insertTrace(new Trace(0, "Suppresson", "Carte", "carte:"+carte, "", Utilis.user));

        return teste > 0;
    }

}
