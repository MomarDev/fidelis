/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.UNCONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import michelkaapp.database.Driver;
import michelkaapp.objets.*;
import org.controlsfx.control.Notifications;

/**
 *
 * @author leyu
 */
public class Utilis {

	public static Panier panier = new Panier();
    public static String Titre_Menu_Switching = "Fiches Techniques";
    public static final String DialogConfirmationAjoutFicheTechnique = "Etes vous sûr de vouloir enregistrer la fiche?";
    public static final String DialogConfirmationPrestation = "Etes vous sûr de vouloir enregistrer la prestation?";
    public static final String DialogConfirmationCarteAdd = "Etes vous sûr de vouloir enregistrer la carte avec ces informations?";
    public static String FormPrestationTitle = "Forme Texture";
    public static final String QuestionValideForm = "Etes vous sûr de vouloir enregistrer ces informations?";
    public static final String Quitter = "Etes vous sûr de vouloir quitter?";
    public static final String RENSEIGNECHAMP = "Veuillez renseigner correctement les champs.";
    public static final String ParametreRequest = "Etes vous sûr de vouloir enregistrer ces paramétres?";
    public static User user;
    public static int caisse=0;
    public static boolean entrainPayer=false;
    public static int WindowsDefaults = 5;
    public static int WindowsMax = 10;
    public static String db_name = "michelekadb";
    public static Driver driver;
    public static String Success_Values = "Succés";
    public static String Error_Values = "Echec";
    public static String ErrorForm = "Veuillez revérifier les informations que vous avez saisies.";
    public static String ErrorPanier = "Votre Panier est vide.";
    public static String recherche_option_name = "name_client";
    public static String recherche_option_lastname = "lastname_client";
    public static String recherche_option_cheveux = "id_type_de_cheveux";
    public static String recherche_option_service = "id_client";
    public static String recherche_option_carte = "reference_carte";
    public static String ErrorNumber = "Désolé ,Veuillez renseigner des valeurs alpha numérique pour le champs ";
    public static String ErrorNombre = "Désolé ,Veuillez renseigner une valeur numérique pour ce champ";
    public static String EnregistrementSuccee = "L'enregistrement s'est bien effectué!";
    public static String ErrorEnregistrementDB = "Une erreur s'est produite lors de l'enregistrement. Veuillez réessayer s'il vous plait.";
    public static String NonDefinie = "la Carte n'est pas définie!";
    public static Client clientActuel = null;
    public static TypePrestation actuelTypePrestation = null;
    public static List<Client> ListClientActuel = null;
    public static List<Client> clientsTomail = null;
    public static Carte carteActuel = null;
    public static User SelectedUser = null;
    public static int Max_Windows = 10;
    public static int Min_Windows = 3;
    public static int Max_DEFAULT = 0;
    public static int DEFINED_FENETRE = 0;
    public static String errorFenetres = "Le nombre de fenetres possibles est compris entre 10 et 3";
    public static String MAIL_ERROR = "Désolé ,veuillez verifier l'email saisi";
    public static int Max_Tentative_connexion_Failed = 5;
    public static int Failed_Connexion = 0;
    public static String DefaultEmail = null;
    public static String DefaultEmailPassword = null;
    public static String INTERNET_CONNECTION = "Pas de connexion internet, vous ne pouvez pas envoyer de mail";
    
    private static String listCaseString = "'Ã©Ã Ã¨Ã¹Ã¢Ã®Ã´Ã»Ã«Ã¯Ã¼Å“";
    private static String listCorrespondance = "eeaeuaioueiue";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALIDE_NUMBER = Pattern.compile("[0-9]+");
    public static final Pattern VALIDE_CHAINE = Pattern.compile("[aA-zZ]");

//    les valeurs pour achat, visite et parrainage
    public static int NOMRBE_VISITE = 0;
    public static int NOMBRE_ACHAT = 10;
    public static int NOMBRE_PARRAINAGE = 0;

    public static int BONUS_VISITE = 0;
    public static int BONUS_ACHAT = 5;
    public static int BONUS_PARRAINAGE = 0;
    public static boolean CONSEIL = true;
    public static boolean UPDATE_CLIENT_TECHNIQUE = false;
    public static String CORPS_MAIL_ANNIVERSAIRE = "";
    public static String OBJET_MAIL_ANNIVERSAIRE = "";
    public static int NOMBRE_JOUR_AVANT_FIN_MOIS = 0;
    public static Mail MAIL_ANNIVERSAIRE = null;
    public static int MOIS_ANNIVERSAIRE = 0;
    public static String NOM_ENVOYEUR_MAIL = "Salon Michele Ka";
    public static List<Parametre> PARAMETRES_APPLICATION = null;
    public static boolean PARAMETRE_IS_UPDATE = false;
    static int point = -1;
    public static boolean Carte_IS_UPDATE = false;
    public static boolean SERVICE_IS_UPDATE = false;
    public static boolean PRODUIT_IS_UPDATE = false;
    public static boolean CLIENT_IS_UPDATE = false;
    public static boolean PRESTATION_IS_UPDATE = false;
    public static ApplicationLogger LOGGER = new ApplicationLogger();
    public static PrestationTechnique prestationTechniqueActuel = null;
    public static TypePrestation DetailTypePrestation = null;
    public static boolean A_IMPRIMER = false;
    public static Produit ProduitToUpdate = null;
    public static Logout LOGOUT = new Logout();
    public static Service ServiceToUpdate = null;
    public static Restant RestantToUpdate = null;
    public static Tarif TarifToUpdate = null;
    public static PrestationTechnique prestationTechnique = null;
    public static String PARA_SOURCE_PATH = "SAVE_SOURCE_PATH";
    public static String PARA_DEST_PATH = "SAVE_DEST_PATH";
    public static String RESTOR_DIR = "db_michele_ka";

    /**
     * les informations pour la base de donnée.
     */
    public static PreferenceDB BASE_DE_DONNEE = null;


    /*bidul*/
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Utilis.user = user;
    }

    public static boolean IsAdmin() {
        return user.getProfile().getId() == 1;
    }
//Pour avoir la date par default du system

    public static final LocalDate NOW_LOCAL_DATE() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
//Pour supprimer les caractaire accentuer

    public static String supprimerLesCaractaireAccentue(String value) {
        String nv = null;
        if (value != null) {
            nv = value.toLowerCase();
            int len = nv.length();
            int lenCorrespondance = listCaseString.length();
            for (int i = 0; i < len - 1; i++) {
                for (int j = 0; j < lenCorrespondance - 1; j++) {
                    if (listCaseString.charAt(j) == nv.charAt(i)) {
                        nv = nv.replace(nv.charAt(i), listCorrespondance.charAt(j));
                    }
                }
            }
        }
        return nv;
    }

    public static boolean isAEmailAdress(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    public static boolean isNumber(String number) {
        Matcher matcher = VALIDE_NUMBER.matcher(number);
        return matcher.matches();
    }
    
    public static boolean isChaine(String number) {
        Matcher matcher = VALIDE_CHAINE.matcher(number);
        return matcher.matches();
    }

    public static String separatorNumber(int number) {
        String finalNumber = "";
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        finalNumber = formatter.format(number);

        return finalNumber;
    }

    public static boolean ArrayInsertTest(List<Integer> list) {
        int len = list.size();

        for (int i = 0; i < len; i++) {
            boolean teste = list.get(i) > 0;
            if (teste == false) {
                return teste;
            }
        }
        return true;
    }

    public static String dateFrenchFormat(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String dateTab[] = date.split("-");
        LocalDate localDate = LocalDate.of(Integer.parseInt(dateTab[0]), Integer.parseInt(dateTab[1]), Integer.parseInt(dateTab[2]));
        String dateInFrench = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return dateInFrench;
    }

    public static String dateTimeFrenchFormat(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String tab[] = date.split(" ");
        String dates[] = tab[0].split("-");
        return dates[2] + "/" + dates[1] + "/" + dates[0] + " " + tab[1];
    }

    public static String dateInput(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String tab[] = date.split("/");
        LocalDate dateSql = LocalDate.of(Integer.parseInt(tab[2]), Integer.parseInt(tab[1]), Integer.parseInt(tab[0]));
        String formatSql = dateSql.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formatSql;
    }

    public static String dateFrenchFormat(LocalDate date) {
        String dateInFrench = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return dateInFrench;
    }

//    public static String localTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        TimeZone tz = TimeZone.getDefault();
//        sdf.setTimeZone(tz);
//        return sdf.;
//    }
    public static boolean isConnectedToInternet() {
        boolean teste = true;
        try {
            URL url = new URL("http://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                System.out.println("connection internet ok");
                
            }
            connection.disconnect();
        } catch (MalformedURLException ex) {
            teste = false;
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
            Logger.getLogger(Utilis.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error to connect...");
        } catch (IOException ex) {
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
            Logger.getLogger(Utilis.class.getName()).log(Level.SEVERE, null, ex);
            teste = false;
            System.out.println("Error to connect...");
        }
        return teste;
    }

    public static boolean procheFinDuMois() {
        Calendar calendar = Calendar.getInstance();
        System.out.println((calendar.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(Utilis.trouverParametre("INTERVAL_MOIS").getValeur())) >= calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return (calendar.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(Utilis.trouverParametre("INTERVAL_MOIS").getValeur())) >= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int moisActuelle() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static String stringMonth(int monthNumber) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.FRANCE);
        String[] months = dfs.getMonths();
        if (monthNumber >= 0 && monthNumber <= 11) {
            month = months[monthNumber];
        } else {
            month = months[0];
        }
        return month;
    }

    public static int nombreJourMoisActuel() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int jourActuel() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int jourDeSemaine() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static Parametre trouverParametre(String cle) {
        Parametre parametre = null;
        if (PARAMETRES_APPLICATION == null) {
            PARAMETRES_APPLICATION = driver.listParametres();
        } else {
            for (Parametre p : PARAMETRES_APPLICATION) {
                if (p.getCle().equalsIgnoreCase(cle)) {
                    parametre = p;
                    break;
                }
            }
        }

        return parametre;
    }

    public static Alert DonnerPointDialog(String titre, String fidelite, String texte, String champ, Carte carte) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setResizable(false);
        point = 0;
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();

        // Create a new dialog pane that has a checkbox instead of the hide/show details button
        // Use the supplied callback for the action of the checkbox
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                TextField input = new TextField();
                input.setPromptText("Total " + fidelite);
                input.textProperty().addListener((ObservableValue<? extends String> obs, String old, String nv) -> {
                    if (nv != null && !nv.isEmpty() && isNumber(nv)) {
                        point = Integer.parseInt(nv);
                        input.setStyle(null);

                    } else {
                        input.setStyle("-fx-background-color: #fcda00");
                        System.out.println("is empty!");
                    }
                });
                Label label = new Label("Montant " + fidelite);
                HBox hbox_input = new HBox();
                hbox_input.getChildren().add(label);
                hbox_input.getChildren().add(new Separator(Orientation.VERTICAL));
                hbox_input.getChildren().add(input);
                vbox.getChildren().add(hbox_input);
                return vbox;
            }
        });
//        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().setContentText(texte);
        // Fool the dialog into thinking there is some expandable content
        // a Group won't take up any space if it has no children
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        // Reset the dialog graphic using the default style
        alert.getDialogPane().setGraphic(graphic);
        alert.setTitle(titre);
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setResizable(false);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            if (point <= 0) {
                Alert notPoint = new Alert(Alert.AlertType.INFORMATION, "Vous n'avez pas accordé les points au client de la carte " + carte.getReference() + ".", ButtonType.OK);
                notPoint.show();
            } else {
                driver.point(carte.getReference(), pointAvaloir(point, champ), champ);
                if (champ.equals("nombre_fidelite_prestation")) {
                    driver.insertVisite(new Visite(0, carte.getReference(), NOW_LOCAL_DATE().toString(), pointAvaloir(point, champ)));
                }
            }
        }
        return alert;
    }

    public static Alert AchatRemiseDialog(int totalAvantRemise, int totalApresRemise, Client client) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        point = 0;
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();

        // Create a new dialog pane that has a checkbox instead of the hide/show details button
        // Use the supplied callback for the action of the checkbox
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                Label tt = new Label("Total avant remise: " + totalAvantRemise);
                Label ta = new Label("Total aprés remise: " + totalApresRemise);
                vbox.getChildren().add(tt);
                vbox.getChildren().add(new Separator(Orientation.VERTICAL));
                vbox.getChildren().add(ta);
                return vbox;
            }
        });
//        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().setContentText("Achat de: " + client.getNom() + " " + client.getPrenom());
        // Fool the dialog into thinking there is some expandable content
        // a Group won't take up any space if it has no children
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        // Reset the dialog graphic using the default style
        alert.getDialogPane().setGraphic(graphic);
        alert.setTitle("Achat");
        alert.getButtonTypes().addAll(ButtonType.YES);
        alert.setResizable(false);
        return alert;
    }

    public static Date heureActuel() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()), Locale.FRANCE);
        return calendar.getTime();
    }

    public static int pointAvaloir(int tt, String champ) {
        int remise = 10;
        if (champ.toLowerCase().contains("nb_pts_fidelite_achat")) {
            remise = Integer.parseInt(trouverParametre("BONUS_ACHAT").getValeur());
        } else {
            remise = Integer.parseInt(trouverParametre("BONUS_VISITE").getValeur());
        }
        return (int) ((tt * remise) / 100);
    }

    public static void valouirPointFidelite(Carte carte, String champ) {
        if (champ.contains("nb_pts_fidelite_achat")) {
        }
    }

    public static boolean lancerImprimente(Node node, Carte carte, String champ) {
        return false;
    }

    public static Alert ProduitDivers(Produit produit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();
        alert.initModality(Modality.APPLICATION_MODAL);
        // Create a new dialog pane that has a checkbox instead of the hide/show details button
        // Use the supplied callback for the action of the checkbox
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                TextField input = new TextField();
                input.setPromptText("Valeur ");
                input.textProperty().addListener((ObservableValue<? extends String> obs, String old, String nv) -> {
                    if (nv != null && !nv.isEmpty() && isNumber(nv)) {
                        produit.setPrix(Integer.parseInt(nv));
                        input.setStyle(null);

                    } else {
                        input.setStyle("-fx-background-color: #fcda00");
                        System.out.println("is empty!");
                    }
                });
                Label label = new Label("Mettre la valeur du produit ");
                HBox hbox_input = new HBox();
                hbox_input.setSpacing(10);
                hbox_input.getChildren().add(label);
                hbox_input.getChildren().add(new Separator(Orientation.VERTICAL));
                hbox_input.getChildren().add(input);
                vbox.getChildren().add(hbox_input);
                return vbox;
            }
        });
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        // Reset the dialog graphic using the default style
        alert.getDialogPane().setGraphic(graphic);
//        alert.setTitle(titre);
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setResizable(false);
        Optional<ButtonType> result = alert.showAndWait();
        return alert;
    }

    public static void nodeToImage(Node node, File file, int w, int h) {
        BufferedImage snapShot;
// create an instance of WritableImage with size of node (width and hieght)
        WritableImage tempImage = new WritableImage(w, h);
// take snapshot
        node.snapshot(null, tempImage);
        try {
// take snap shot of node
            snapShot = SwingFXUtils.fromFXImage(tempImage, null);
// now write that image with png extension type -- you can use any type of image extension --
            ImageIO.write(snapShot, "png", file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean diminuerDuStock(Produit produit, int quantite) {
        System.out.println("QT est de : " + produit.getStock());
        System.out.println("la quantite achete est de :" + quantite);
        if (produit.getStock() >= quantite) {
            produit.setStock(produit.getStock() - quantite);
//            if (produit.isUtilisation()) {
//                produit.setStock(produit.getStockService() - quantite);
//            } else {
//                produit.setStock(produit.getStockVente() - quantite);
//            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "La surveillance du stock indique que le produit " + produit.getNom() + " n'existe pas en quantité souffisant  ou elle est épuisée.",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                produit.setStock(0);
                if (produit.isUtilisation()) {
                    produit.setStockService(0);
                } else {
                    produit.setStockVente(0);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean verifierQuantiteProduit(Produit produit, int quantite) {
        if (produit.getStock() < quantite) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Désolé le produit " + produit.getNom() + " n'est en pas quantité suffisant. Quantité restante est de " + produit.getStock());
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean venteProduits(List<ProduitAchete> list) {
        if (list != null && !list.isEmpty()) {
            list.forEach((pUtilise) -> {
                if (diminuerDuStock(pUtilise.getProduit(), pUtilise.getQuantite()) && driver.updateProduit(pUtilise.getProduit())) {
                    System.out.println("fait" + pUtilise.getProduit().getNom());
                } else {
                    System.out.println("no fait" + pUtilise.getProduit().getNom());
                }
            });
        } else {
            return false;
        }
        return true;
    }
    
    public static String separateurEspace(String mot) {
    	
    	final String SEPARATEUR = " ";
    	
    	String date[] = mot.split(SEPARATEUR);
    	String jour = Utilis.dateFrenchFormat(date[0]);
    	
    	
    	return jour + " à " + date[1];
    }
    
    public static void alertStock() { 
        List<Produit> listp = driver.listProduit("WHERE produit.supprime = 0");
        List<Produit> listpAlert = new ArrayList<>();
        if (listp != null) {
            listp.forEach((p) -> {
                if (p.isUtilisation() && p.getStock() <= p.getSeuilService()) {
                    listpAlert.add(p);
                }
                if (!p.isUtilisation() && p.getStock() <= p.getSeuilVente()) {
                    listpAlert.add(p);
                }
            });
        }
        if (!listpAlert.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Stock");
            Node graphic = alert.getGraphic();
            alert.getDialogPane().applyCss();
            alert.setDialogPane(new DialogPane() {
                @Override
                protected Node createDetailsButton() {
                    VBox vbox = new VBox();
                    vbox.setSpacing(5);
                    TableView<Produit> table = new TableView<>();
                    TableColumn<Produit, String> nom = new TableColumn<>("nom");
                    TableColumn<Produit, Integer> stock = new TableColumn<>("stock");
                    TableColumn<Produit, Integer> seuilv = new TableColumn<>("Seuil Stock Vente");
                    TableColumn<Produit, Integer> seuils = new TableColumn<>("Seuil Stock Service");
                    table.getColumns().addAll(nom, stock, seuilv, seuils);
                    nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    seuilv.setCellValueFactory(new PropertyValueFactory<>("seuilVente"));
                    seuils.setCellValueFactory(new PropertyValueFactory<>("seuilService"));
                    table.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
                    table.setItems(FXCollections.observableArrayList(listpAlert));
                    HBox hbox = new HBox(table);
                    vbox.getChildren().add(hbox);
                    Button btn = new Button("Fermer");
                    btn.setOnMouseClicked((ev) -> {
                        ((Stage) alert.getDialogPane().getScene().getWindow()).close();
                    });
                    vbox.getChildren().add(btn);
                    return vbox;
                }

            });
//            alert.getButtonTypes().add(ButtonType.OK);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setResizable(false);
//            alert.getButtonTypes().clear();
            alert.setResizable(false);
            alert.getDialogPane().setExpandableContent(new Group());
            alert.getDialogPane().setExpanded(false);
            alert.getDialogPane().setGraphic(graphic);
            alert.setResizable(false);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setOnCloseRequest(event -> {
                ((Stage) alert.getDialogPane().getScene().getWindow()).close();
            });
            alert.show();
        }
    }

    public static List<ProduitAchete> regrouperProduitAcheter(List<ProduitAchete> listp) {
        List<ProduitAchete> np = new ArrayList<>();
        if (listp != null && !listp.isEmpty()) {
            listp.forEach((pa) -> {
                if (!np.isEmpty()) {
                    np.forEach((nv) -> {
                        if (nv.getProduit().getId() == pa.getProduit().getId()) {
                            nv.setQuantite(nv.getQuantite() + pa.getQuantite());
                        } else {
                            np.add(pa);
                        }
                    });
                } else {
                    np.add(pa);
                }
            });
        } else {
            return listp;
        }
        System.out.println("le nombre de type de produit est de " + np.size());
        return np;
    }

    /*
    *Pour envoyer des alert a l'utilisateur suite a des actions qui se sont terminer 
    *avec succes ou avec des erreurs.
    *Identifier les champs erreur pour le rendre le message beaucoup plus especifique 
    *pour l'utilisateur
    *@param Alert.AlertType alertType,
    *@param String message,
    *@param String champ
    *return 
     */
    public static void makeErrorAlert(Alert.AlertType alertType, String message, String champ) {
        Alert alert = new Alert(alertType, message + " " + champ, ButtonType.OK);
        alert.show();
    }

//    faire les focus sur Node
    public static void runFocusOnRecherche(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                node.requestFocus();
            }
        });
    }
//    faire une notification.

    public static void notification(boolean succed, String msg) {

        Notifications notify = Notifications.create().title(succed ? "Succés" : "Echec").text(msg);
        if (succed) {
            notify.show();
        } else {
            notify.showError();
        }
    }

    public static String laDateDe(int jour) {
        String date = NOW_LOCAL_DATE().toString();
        String tab[] = date.split("-");
        tab[2] = "" + jour;
        return tab[0] + "-" + tab[1] + "-" + tab[2];
    }
}
