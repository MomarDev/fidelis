/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.Achat;
import michelkaapp.objets.Carte;
import michelkaapp.objets.Client;
import michelkaapp.objets.CodeBar;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class CartesController implements Initializable {

    @FXML
    private SplitPane pane_center;
    @FXML
    private AnchorPane pane_gauche;
    @FXML
    private Button btn_fiche;
    @FXML
    private Button btn_carte;
    @FXML
    private Button btn_service;
    @FXML
    private Button btn_produit;
    @FXML
    private Button btn_payement;
    @FXML
    private AnchorPane pane_central;
    @FXML
    private Label titre_menu;
    @FXML
    private TextField zone_recherche;
    @FXML
    private Button btn_rechercher;
    @FXML
    private AnchorPane pane_technique;
    @FXML
    private TableView<Carte> table_view;
    @FXML
    private TableColumn<Carte, Integer> reference_carte;
    @FXML
    private TableColumn<Carte, Integer> pointsPrestation;
    @FXML
    private TableColumn<Carte, String> etat_carte;
    @FXML
    private AnchorPane pane_bas;
    @FXML
    private RadioButton radioButton_nom;
    @FXML
    private ToggleGroup toggleGroupe;
    @FXML
    private RadioButton radioButton_prenom;

    private Stage stage;
    private Parent root;
    @FXML
    private Button btn_attribuer_carte;
    @FXML
    private Button btn_fidelite_visite;
    @FXML
    private Button btn_fidelite_parrainage;
    @FXML
    private Button btn_fidelite_achat;
    @FXML
    private Label result_carte;
    @FXML
    private AnchorPane pane_border;
    @FXML
    private Label titre_recherche_option;
    @FXML
    private Button btn_users;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    @FXML
    private Button btn_lot_carte;
    @FXML
    private Button btn_imprimer;
 
    @FXML
    private TableColumn<Carte, String> date_reception;
    @FXML
    private TableColumn<Carte, String> date_attribution;
    @FXML
    private TableColumn<Carte, String> date_expiration;
    @FXML
    private Label nom_prenom_personne;
    @FXML
    private Label telephone_carte;
    @FXML
    private Label nombre_personne_carte;
    @FXML
    private Label reference_carte_personne;
    private Carte carte;
    private Client client;
    @FXML
    private RadioButton radiobtn_ref_carte;

    private String option_recherche = Utilis.recherche_option_name;

    private String key;
    @FXML
    private HBox admin_bar;
    @FXML
    private Pagination pagination;
    private int MIN_PAGE = 0;
    private int MAX_PAGE = 9;
    private final int INTERVAL_PAGE = 10;
    private int PAGES = 0;
    @FXML
    private Label affectation_renseignement;
    private int nb = 0;
    @FXML
    private TableColumn anniversaire;
    @FXML
    private RadioButton rd_anniversaire;
    @FXML
    private HBox pane_annee_recherche;
    @FXML
    private ComboBox<String> moisComBox;
    @FXML
    private ComboBox<String> jourComBox;

    private ObservableList<String> listJour = FXCollections.observableArrayList(
            "",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    );

    private ObservableList<String> listMois = FXCollections.observableArrayList("",
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre"
    );
    private int jour = 0;

    private String mois = "";
    @FXML
    private Button logout;
    @FXML
    private Button btn_bloquer;
    @FXML
    private Button btn_verifier_code;
    private String codeString;
    @FXML
    private Tooltip tooltip;
    private List<Client> clients = null;
    private String msg;
    @FXML
    private TableColumn checkbox_mail;
    private List<Client> selectedForEmail = null;
    @FXML
    private Button btn_mail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	btn_fidelite_visite.setVisible(false);
    	btn_fidelite_parrainage.setVisible(false);
    	btn_fidelite_achat.setVisible(false);
    	
        if (Utilis.IsAdmin()) {
            selectedForEmail = new ArrayList<>();
            btn_mail.setDisable(false);
        }
        toggleGroupe.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (nv == rd_anniversaire) {
                pane_annee_recherche.setVisible(true);
            } else {
                pane_annee_recherche.setVisible(false);
            }
        });

        zone_recherche.requestFocus();
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sûre de vouloir vous deconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });

        initialisation();
        if (Utilis.IsAdmin()) {
            admin_bar.setVisible(true);
            btn_lot_carte.setDisable(false);
            btn_bloquer.setDisable(false);
        }
        PAGES = Utilis.driver.nombreCarte();
        if (PAGES % INTERVAL_PAGE == 0) {
            pagination.setPageCount(PAGES / INTERVAL_PAGE);
        } else {
            pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
        }
        pagination.currentPageIndexProperty().addListener((obs, old, nv) -> {
            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, PAGES);
            System.out.println(nv.intValue());
            System.out.println("MIN:" + MIN_PAGE);
            System.out.println("MAX:" + MAX_PAGE);

            initTable();
        });
        btn_bloquer.setOnMouseClicked(event -> {
            if (btn_bloquer.getText().toLowerCase().contains("bloquer")) {
                Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sûre de vous");
                
                Optional<ButtonType> rs = deleteDialog.showAndWait();
                if (rs.get() == ButtonType.OK) {
                	if (carte.getEtatCarte() == 1) {
                        if (Utilis.driver.updateCarte(carte.getReference(), "etat_carte", 2)) {
                            btn_bloquer.setText("Débloquer");
                        }
                    }
                	
                	if (carte.getEtatCarte() == 2) {
                        if (Utilis.driver.updateCarte(carte.getReference(), "etat_carte", 1)) {
                            btn_bloquer.setText("Débloquer");
                        }
                    }
                	btn_bloquer.setText("Débloquer");
                }else if (rs.get() == ButtonType.CANCEL) {
                	
                		if (carte.getEtatCarte() == 1) {
                            if (Utilis.driver.updateCarte(carte.getReference(), "etat_carte", 1)) {
                                btn_bloquer.setText("Bloquer");
                            }
                            
                        }	
                	
                }
                
                
            }
            
            initTable();
        });
        
        if(Utilis.entrainPayer) {
        	btn_payement.setDisable(true);
        }
        
        btn_payement.setOnMouseClicked(event -> {
            
       	 try {
       		 
				afficheFenetresSecondaire("Payement.fxml");
				btn_payement.setDisable(true);
				
				Utilis.entrainPayer=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
       });
        
        btn_verifier_code.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Node graphic = alert.getGraphic();
            alert.setDialogPane(new DialogPane() {
                @Override
                protected Node createDetailsButton() {
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);
                    Label code = new Label("Code Barre");
                    TextField fd_code = new TextField();
                    fd_code.setPromptText("Entrez le code barre SVP");
                    fd_code.textProperty().addListener((obs, old, nv) -> {
                        if (Utilis.isNumber(nv)) {
                            fd_code.setStyle(null);
                            codeString = nv;
                            System.out.println(nv);
                        } else {
                            fd_code.setStyle("-fx-background-color:red");
                            codeString = "";
                        }
                    });
                    hbox.getChildren().addAll(code, fd_code);
                    vbox.getChildren().add(hbox);
                    return vbox;
                }
            });
            alert.getDialogPane().setExpandableContent(new Group());
            alert.getDialogPane().setExpanded(true);
            // Reset the dialog graphic using the default style
            alert.getDialogPane().setGraphic(graphic);
            alert.setTitle("Vérification de code");
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES && codeString != null && !codeString.isEmpty()) {
                CodeBar codebar = Utilis.driver.existCodeBar(codeString);
//                System.out.println(codebar.getCodeBar());
                if (codebar != null) {
                    Alert utiliserCode = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous utiliser le bonus de la carte " + codebar.getCarte() + "?");
                    Optional<ButtonType> rs = utiliserCode.showAndWait();
                    if (rs.get() == ButtonType.OK && Utilis.driver.utiliserCodeBar(codebar.getCodeBar())) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Vous venez d'utiliser les bonus de carte " + codebar.getCarte());
                        al.show();
                    }
                } else {
                    Alert errorCode = new Alert(Alert.AlertType.ERROR, "Le bonus avec le code " + codeString + " n'existe pas ou il est deja utilisé");
                    errorCode.show();
                }
            }
        });
        runFocusOnRecherche(zone_recherche);
    }

    @FXML

    private void buttonListener(ActionEvent event) throws IOException {

        if (event.getSource() == btn_fiche) {
            root = FXMLLoader.load(getClass().getResource("FicheTechnique.fxml"));
            stage = (Stage) btn_fiche.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == btn_service) {
            root = FXMLLoader.load(getClass().getResource("StockService.fxml"));
            stage = (Stage) btn_service.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 

        else if (event.getSource() == btn_produit) {
            root = FXMLLoader.load(getClass().getResource("StockProduit.fxml"));
            stage = (Stage) btn_produit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == btn_carte) {
            root = FXMLLoader.load(getClass().getResource("Cartes.fxml"));
            stage = (Stage) btn_carte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == btn_attribuer_carte) {
            Utilis.carteActuel = carte;
            Utilis.clientActuel = null;
            afficheFenetresSecondaire("PopUpAjouterCarte.fxml");
            updateTable();
        } else if (event.getSource() == btn_fidelite_visite) {
            Utilis.clientActuel = client;
            if (client != null) {
                carte = Utilis.driver.trouverCarte(client);
                List<Visite> visites = Utilis.driver.listVisites(carte.getReference());
                int size = visites == null ? 0 : visites.size();
                if (size >= Integer.parseInt(Utilis.trouverParametre("FIDELITE_VISITE").getValeur()) && carte.getNptPrestation() > 0) {
                    Stage impStage = new Stage();
                    impStage.setResizable(false);
                    FXMLLoader impRoot = null;
                    VBox pane = null;
                    try {
                        impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
                        pane = impRoot.load();
                        ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
                        controller.newInstence(client, "nombre_fidelite_prestation", true);
                    } catch (IOException ex) {
                        Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    impStage.setScene(new Scene(pane));
                    impStage.show();
                } else {
                    afficheFenetresSecondaire("FideliteVisite.fxml");
                    updateTable();
                }
            }
        } else if (event.getSource() == btn_fidelite_parrainage) {
            Utilis.clientActuel = client;
            if (client != null) {
                afficheFenetresSecondaire("FideliteParrainage.fxml");
                updateTable();
            }
        } else if (event.getSource() == btn_fidelite_achat) {
            Utilis.clientActuel = client;
            if (client != null) {
                carte = Utilis.driver.trouverCarte(client);
                List<Achat> achats = Utilis.driver.listAchatClient(client.getId());
                int size = achats == null ? 0 : achats.size();
                //if (size > 0 && carte.getNptsFideliteAchat() > 0 && ((size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()) == 0) || Utilis.driver.aUnAvoir(carte.getReference()))) {
                if (size > 0 && carte.getNptsFideliteAchat() > 0 && ((size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()) == 0))) {

                    Stage impStage = new Stage();
                    impStage.setResizable(false);
                    FXMLLoader impRoot = null;
                    VBox pane = null;
                    System.out.println("size:" + size);
                    System.out.println("Nfidelite achatr:" + carte.getNptsFideliteAchat());
                    System.out.println("size mod fide param:" + size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()));
                    System.out.println("driver a un avoir:" + Utilis.driver.aUnAvoir(carte.getReference()));

                    try {
                        impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
                        pane = impRoot.load();
                        ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
                        controller.newInstence(client, "nb_pts_fidelite_achat", true);
                    } catch (IOException ex) {
                        Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    impStage.setScene(new Scene(pane));
                    impStage.show();
                } else {
                    afficheFenetresSecondaire("FideliteAchat.fxml");
                    updateTable();
                }
            }
        } else if (event.getSource() == btn_users) {
            stage = (Stage) btn_users.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else if (event.getSource() == btn_traces) {
            stage = (Stage) btn_traces.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Traces.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else if (event.getSource() == btn_setting) {
            stage = (Stage) btn_setting.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Parametres.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else if (event.getSource() == btn_lot_carte) {
            afficheFenetresSecondaire("ReceptionLotCarte.fxml");
            updateTable();
        } else if (event.getSource() == btn_imprimer) {
            if (Utilis.carteActuel != null && Utilis.carteActuel.getEtatCarte() == 1) {
                afficheFenetresSecondaire("Imprimer.fxml");
            }
        }
    }

    private void initialisation() {
        btn_mail.setOnMouseClicked(event -> {
            if (!selectedForEmail.isEmpty()) {
                Utilis.clientsTomail = selectedForEmail;
            } else {
                Utilis.clientsTomail = clients;
            }
            if (Utilis.clientsTomail != null && !Utilis.clientsTomail.isEmpty() && Utilis.isConnectedToInternet()) {
                try {
                    afficheFenetresSecondaire("SendMail.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(CartesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        if (MichelKaApp.connexionIsOK) {
            list = Utilis.driver.listCarte();

            initTable();
            table_view.getSelectionModel().selectedItemProperty().addListener((obs, old, nv) -> {
                carte = nv;
                Utilis.carteActuel = nv;
                if (carte != null && carte.getEtatCarte() == 1) {
                    reference_carte_personne.setText(carte.toString());
                    nombre_personne_carte.setText(carte.getNombreUtilisateurCarte() + "");
                    client = Utilis.driver.rechercheClientFormCarte(carte.getReference());
                    System.out.println("client:" + client + " carte:" + carte);
                    if (client != null) {
                        client.setCarte(carte);
                    }
                    if (client != null) {
                        nom_prenom_personne.setText(client.getNom() + " " + client.getPrenom());
                        telephone_carte.setText(client.getMobile1() + "");
                    } else {
                        nom_prenom_personne.setText("");
                        telephone_carte.setText("");
                    }
                } else {
                    clean();
                }
            });

            table_view.setRowFactory(tv -> {
                TableRow<Carte> row = new TableRow<>();
                row.setOnMouseEntered(event -> {
                    if (!row.isEmpty()) {
                        tooltip.setText("Utilisateurs: " + utilisateursCarte(row.getItem().getReference()));
                    }
                });
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty()) {
                        carte = row.getItem();
                        if (carte.getEtatCarte() >= 2 || carte.getEtatCarte() == 1) {
                            btn_bloquer.setDisable(false);
                            if (carte.getEtatCarte() == 2) {
                                btn_bloquer.setText("Débloquer");
                            } else {
                                btn_bloquer.setText("Bloquer");
                            }
                        } else {
                            btn_bloquer.setDisable(true);
                        }
                    }
                    if (event.getClickCount() == 2 && (!row.isEmpty()) && row.getItem().getEtatCarte() == 1) {
                        client = Utilis.driver.rechercheClientFormCarte(carte.getReference());
                        Utilis.clientActuel = client;
                        Utilis.UPDATE_CLIENT_TECHNIQUE = true;
                        try {
                            afficheFenetresSecondaire("PopUpAjouterCarteUpdate.fxml");
                        } catch (IOException ex) {
                            Logger.getLogger(CartesController.class.getName()).log(Level.SEVERE, null, ex);
                            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                        }
                    } else {

                    }
                });
                return row;
            });
        }
        moisComBox.getItems().addAll(listMois);
        jourComBox.getItems().addAll(listJour);

        jourComBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Utilis.isNumber(newValue)) {
                jour = Integer.parseInt(newValue);
            } else {
                jour = 0;
            }
            if (jour >= 30) {
                mois = jourComBox.getValue();
                if (mois.equals("Février")) {
                    jourComBox.setValue("29");
                    jour = 29;
                }
            }
            if (jour == 31) {
                mois = moisComBox.getValue();
                if (mois.equals("Avril") || mois.equals("Juin") || mois.equals("Septembre") || mois.equals("Novembre")) {
                    jourComBox.setValue("30");
                    jour = 30;
                }
            }
        });

        moisComBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            mois = newValue;
            if (newValue.equals("Février") && jour >= 30) {
                jourComBox.setValue("29");
            }
            if ((newValue.equals("Avril") || newValue.equals("Juin") || newValue.equals("Septembre") || newValue.equals("Novembre"))
                    && jour == 31) {
                jourComBox.setValue("30");
            }
        });

    }

    @FXML
    private void actionsRecherche(ActionEvent event) {
        if (event.getSource() == radioButton_nom) {
            option_recherche = Utilis.recherche_option_name;
        } else if (event.getSource() == radioButton_prenom) {
            option_recherche = Utilis.recherche_option_lastname;
        } else if (event.getSource() == radiobtn_ref_carte) {
            option_recherche = Utilis.recherche_option_carte;
        } else if (event.getSource() == rd_anniversaire) {
            option_recherche = "anniversaire_client";
        } else if (event.getSource() == btn_rechercher) {
            if (option_recherche.contains("anniversaire_client")) {
                String j = "";
                if (jour <= 0) {
                    j = "";
                } else {
                    j = "" + jour;
                }
                key = mois + "-" + j;
                if (key.length() == 1 && key.contains("-")) {
                    key = "";
                }
            } else {
                key = zone_recherche.getText();
            }
            if (key == null || key.isEmpty()) {
                list = Utilis.driver.listCarte();

                initTable();
            } else {
                if (radioButton_nom.isSelected() || radioButton_prenom.isSelected() || rd_anniversaire.isSelected()) {
                    initTableResult(Utilis.driver.trouveCarteParOption(option_recherche, key));
                    key = "";
                } else if (radiobtn_ref_carte.isSelected()) {
                    if (Utilis.isNumber(key)) {
                        int id = Integer.parseInt(key);
                        List<Carte> list = new ArrayList<>();
                        list.add(Utilis.driver.trouverCarte(id));
                        initTableResult(list);
                    } else {
                        Alert errorRecherche = new Alert(Alert.AlertType.ERROR, "L'option avec Référence carte attend des valeurs numerique", ButtonType.OK);
                        errorRecherche.show();
                    }
                }
            }
        }
    }
    List<Carte> list = Utilis.driver.listCarte();

    private void initTable() {
        // List<Carte> list = Utilis.driver.listCarte();
        if (list != null) {
            System.out.println("entré liste: min" + MIN_PAGE);
            PAGES = list.size();
            if (PAGES % INTERVAL_PAGE == 0) {
                pagination.setPageCount(PAGES / INTERVAL_PAGE);
                System.out.println("Pair : PAGES / INTERVAL_PAGE:" + PAGES + "/" + INTERVAL_PAGE);
            } else {
                pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
                System.out.println("Impair : PAGES / INTERVAL_PAGE:" + PAGES + "/" + INTERVAL_PAGE);

            }
            ObservableList<Carte> ob = null;
            if (PAGES > INTERVAL_PAGE) {
                System.out.println("le if ");
                ob = FXCollections.observableArrayList(list.subList(MIN_PAGE, MAX_PAGE));
            } else {
                System.out.println("le else ");

                ob = FXCollections.observableArrayList(list);
            }
            btn_bloquer.setDisable(true);
            table_view.setItems(ob);
            reference_carte.setCellValueFactory(new PropertyValueFactory<>("reference"));
            pointsPrestation.setCellValueFactory(new PropertyValueFactory<>("nptPrestation"));
            date_attribution.setCellValueFactory(new PropertyValueFactory<>("dateAttribution"));
            date_expiration.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
            date_reception.setCellValueFactory(new PropertyValueFactory<>("dateReception"));
            etat_carte.setCellValueFactory(new PropertyValueFactory<>("afficheEtat"));
            affectation_renseignement.setText("Cartes affectées: " + nombreCarteEffecte(list) + " | Non affectées: " + (list.size() - nb));
            anniversaire.setCellFactory(new Callback<TableColumn<Carte, Boolean>, TableCell<Carte, Boolean>>() {

                @Override
                public TableCell<Carte, Boolean> call(TableColumn<Carte, Boolean> param) {
                    final TableCell<Carte, Boolean> cell = new TableCell<Carte, Boolean>() {
                        final Label label = new Label();

                        @Override
                        protected void updateItem(Boolean item, boolean empty) {

                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                HBox hBox = new HBox(label);
                                hBox.setAlignment(Pos.CENTER);
                                setGraphic(hBox);
                                Carte ct = (Carte) getTableRow().getItem();
                                if (ct != null) {
                                    if (ct.getEtatCarte() > 0) {
                                        Client c = Utilis.driver.proprietaireCarte(ct.getReference());
                                        if (c != null) {
                                            label.setText(c.getAnniversaire());
                                        }
                                    }
                                }
                            }
                        }

                    };

                    return cell;
                }

            });
            checkbox_mail.setCellFactory(new Callback<TableColumn<Carte, Boolean>, TableCell<Carte, Boolean>>() {

                @Override
                public TableCell<Carte, Boolean> call(TableColumn<Carte, Boolean> param) {
                    final TableCell<Carte, Boolean> cell = new TableCell<Carte, Boolean>() {
                        final CheckBox checkBox = new CheckBox();

                        @Override
                        protected void updateItem(Boolean item, boolean empty) {

                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                HBox hBox = new HBox(checkBox);
                                hBox.setAlignment(Pos.CENTER);
                                setGraphic(hBox);
                                // checkBox.selectedProperty().addListener((obs, old, nv) -> {
                                checkBox.setOnAction(event -> {
                                    //Carte ct = (Carte) getTableRow().getItem();
                                    Carte c = (Carte) getTableView().getItems().get(getIndex());

                                    if (c != null) {
                                        if (c.getEtatCarte() > 0) {
                                            Client cl = Utilis.driver.proprietaireCarte(c.getReference());
                                            if (c != null) {
                                                if (checkBox.isSelected() == true) {
                                                    if (cl.getEmail() != null && !cl.getEmail().isEmpty() && Utilis.isAEmailAdress(cl.getEmail())) {
                                                        selectedForEmail.add(cl);
                                                    } else {
                                                        Alert noMail = new Alert(Alert.AlertType.ERROR, "Ce client n'a pas renseigné son adresse mail.");
                                                        noMail.show();
                                                        noMail.setOnHidden((v) -> {
                                                            checkBox.setSelected(false);
                                                        });
                                                    }
                                                } else {
                                                    selectedForEmail.remove(c);
                                                }
                                                System.out.println(Arrays.toString(selectedForEmail.toArray()));
                                            }
                                        }
                                        if (checkBox.isSelected() == true && c.getEtatCarte() <= 0) {
                                            Alert errorClient = new Alert(Alert.AlertType.ERROR, "La carte est non attribuée à  un client.");
                                            errorClient.show();
                                            errorClient.setOnHidden((v) -> {
                                                checkBox.setSelected(false);
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    };
                    return cell;
                }
            });
        }
//        for les utilisateur de carte;
        clients = Utilis.driver.utilisateurCarteList();
    }

    private String utilisateursCarte(int referenceCarte) {
        msg = "";
        if (clients != null && !clients.isEmpty()) {
            clients.forEach(utilisateur -> {
                if (utilisateur.getCarte().getReference() == referenceCarte) {
                    msg += ", " + utilisateur.toString();
                }
            });
        }
        if (msg.isEmpty()) {
            msg = "Aucun";
        }
        return msg;
    }

    private void afficheFenetresSecondaire(String fxmlFile) throws IOException {
        if (Utilis.Max_DEFAULT < Utilis.DEFINED_FENETRE) {
            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT + 1;
            Stage smoleFenetre = new Stage();
            smoleFenetre.setResizable(false);
            smoleFenetre.setOnCloseRequest(event -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                Utilis.entrainPayer=false;
                btn_payement.setDisable(false);
            });
            Parent smoleRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            smoleFenetre.setScene(new Scene(smoleRoot));
            smoleFenetre.setOnHidden((even) -> {
                --Utilis.Max_DEFAULT;
                initTable();
                if (selectedForEmail != null) {
                    selectedForEmail.clear();
                }
            });
            //smoleFenetre.initStyle(StageStyle.UTILITY);
            smoleFenetre.show();
            
        } else {
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }

    private void clean() {
        reference_carte_personne.setText("");
        nombre_personne_carte.setText("");
        nom_prenom_personne.setText("");
        telephone_carte.setText("");
    }

    private void initTableResult(List<Carte> list) {
        if (list != null) {
            this.list = list;
            PAGES = list.size();
            if (PAGES % INTERVAL_PAGE == 0) {
                pagination.setPageCount(PAGES / INTERVAL_PAGE);
            } else {
                pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
            }
            if (PAGES > INTERVAL_PAGE) {
                table_view.setItems(FXCollections.observableArrayList(list.subList(MIN_PAGE, MAX_PAGE)));
            } else {
                table_view.setItems(FXCollections.observableArrayList(list));
            }
        } else {
            table_view.setItems(null);
        }
    }

    private void updateTable() {
        if (Utilis.Carte_IS_UPDATE) {
            initTable();
            Utilis.Carte_IS_UPDATE = false;
        }
    }

    private int nombreCarteEffecte(List<Carte> list) {
        nb = 0;
        list.forEach((t) -> {
            if (t.getEtatCarte() == 1) {
                nb++;
            }
        });
        return nb;
    }

    void runFocusOnRecherche(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
//                repeatFocus(node);
                node.requestFocus();
            }
        });
    }
}
