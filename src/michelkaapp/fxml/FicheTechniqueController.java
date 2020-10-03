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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.Achat;
import michelkaapp.objets.Carte;
import michelkaapp.objets.CategoriePrestation;
import michelkaapp.objets.Client;
import michelkaapp.objets.CuirChevelu;
import michelkaapp.objets.Densite;
import michelkaapp.objets.Parametre;
import michelkaapp.objets.PrestationTechnique;
import michelkaapp.objets.Produit;
import michelkaapp.objets.Profession;
import michelkaapp.objets.SuiviMaison;
import michelkaapp.objets.TypeCheveux;
import michelkaapp.objets.TypePrestation;
import michelkaapp.utilis.SendMail;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FicheTechniqueController implements Initializable {

    @FXML
    private SplitPane pane_center;
    @FXML
    private AnchorPane pane_gauche;
    @FXML
    private Button btn_fiche;
    @FXML
    private Button btn_carte;
    @FXML
    private Button btn_payement;
    @FXML
    private Button btn_service;
    @FXML
    private Button btn_produit;
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
    private TableView<Client> table_view;
    @FXML
    private AnchorPane pane_bas;
    @FXML
    private RadioButton radioButton_nom;
    @FXML
    private ToggleGroup toggleGroupe;
    @FXML
    private RadioButton radioButton_prenom;
    @FXML
    private RadioButton radioButton_cheveux;
    @FXML
    private RadioButton radioButton_service;
    @FXML
    private RadioButton radioButton_produit;
    @FXML
    private Pagination pagination;
    @FXML
    private Button carteButton;
    @FXML
    private Button envoyerMailButton;
    @FXML
    private Button ajouterFicheButton;
    @FXML
    private Button btn_users;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    

    
    @FXML
    private AnchorPane pane_border;
    @FXML
    private Label titre_recherche_option;
    @FXML
    private AnchorPane anchorPane;

    private Stage stage;
    private Parent root;
    @FXML
    private TableColumn<Client, String> nom_col;
    @FXML
    private TableColumn<Client, String> prenom_col;
    @FXML
    private TableColumn<Client, String> ann_col;
    @FXML
    private TableColumn<Client, String> tel_col;
    @FXML
    private TableColumn<Client, String> email_col;
    @FXML
    private TableColumn<Client, String> adresse_col;
    @FXML
    private TableColumn<Client, Profession> profession_col;
    @FXML
    private TableColumn<Client, Carte> carte_col;
    @FXML
    private TableColumn<Client, TypeCheveux> type_cheveux_col;
    @FXML
    private TableColumn<Client, Densite> densite_col;
    @FXML
    private TableColumn<Client, CuirChevelu> cuir_col;
    @FXML

    private ObservableList<Client> listClients;
    private String op_recherche = Utilis.recherche_option_name;
    private String key = null;
    private Client selectedClient = null;
//    Mail;
    List<Client> clients = null;
    @FXML
    private HBox admin_bar;
    private int MIN_PAGE = 0;
    private int MAX_PAGE = 9;
    private final int INTERVAL_PAGE = 10;
    private int PAGES = 0;

    private int seededMail = 0;
    private Parametre parametre;
    private List<Client> selectedForEmail = new ArrayList<>();
    @FXML
    private TableColumn client_checker;
    @FXML
    private Button logout;
    @FXML
    private TableView<PrestationTechnique> tablePrestation;
    @FXML
    private TableColumn<PrestationTechnique, String> prestation_date;
    @FXML
    private TableColumn<PrestationTechnique, TypePrestation> prestation_type;
    @FXML
    private TableColumn<PrestationTechnique, CategoriePrestation> prestation_categorie;
    @FXML
    private TableColumn<PrestationTechnique, Integer> prestation_pause;
    @FXML
    private TableColumn<PrestationTechnique, String> prestation_montage;
    @FXML
    private TableColumn<PrestationTechnique, String> prestation_technique;
    @FXML
    private TableColumn<PrestationTechnique, String> prestation_observation;
    @FXML
    private TableColumn<PrestationTechnique, List<Produit>> prestation_marques;
    @FXML
    private AnchorPane pane_bas1;
    @FXML
    private Button addPrestation;
    @FXML
    private Button changeView;
    @FXML
    private TableView<SuiviMaison> tableSuivi;
    @FXML
    private TableColumn<SuiviMaison, String> date_conseil;
    @FXML
    private TableColumn<SuiviMaison, String> conseil;
    @FXML
    private TableView<Achat> tableAchat;
    @FXML
    private TableColumn<Achat, String> date_achat;
    @FXML
    private TableColumn<Achat, List<Produit>> produit_achetes;
    @FXML
    private Button addconseil;
    @FXML
    private Button addAchat;
    public static boolean ISFROMTECHNIAUE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carteButton.setDisable(true);
        addPrestation.setOnMouseClicked(event -> {
            try {
                if (Utilis.clientActuel != null) {
                    afficheFenetresSecondaire("PopPupPrestationFormFromFiche.fxml");
                } else {
                    Alert error = new Alert(Alert.AlertType.WARNING, "Veuillez d'abord selectionner un client en cliquant sur sa ligne correspondante .");
                    error.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        changeView.setOnMouseClicked(event -> {
            if (pane_bas.isVisible()) {
                pane_bas.setVisible(false);
                changeView.setText("Voir Prestations Techniques");
                pane_bas1.setVisible(true);
            } else {
                pane_bas.setVisible(true);
                pane_bas1.setVisible(false);
                changeView.setText("Voir Suivi Maison");
            }
        });
        addAchat.setOnMouseClicked(event -> {
            if (selectedClient != null) {
                Utilis.clientActuel = selectedClient;
                Utilis.CONSEIL = false;
                if (selectedClient.isUtiliseCarte()) {
                    Carte carte = Utilis.driver.trouverCarte(selectedClient);
                    List<Achat> achats = Utilis.driver.listAchatClient(selectedClient.getId());
                    int size = achats == null ? 0 : achats.size();
                    if (size > 0 && carte.getNptsFideliteAchat() > 0 && (size % Integer.parseInt(Utilis.trouverParametre("FIDELITE_ACHAT").getValeur()) == 0 || Utilis.driver.aUnAvoir(carte.getReference()))) {
                        Stage impStage = new Stage();
                        impStage.setResizable(false);
                        FXMLLoader impRoot = null;
                        VBox pane = null;
                        try {
                            impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
                            pane = impRoot.load();
                            ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
                            controller.newInstence(selectedClient, "nb_pts_fidelite_achat", true);
                        } catch (IOException ex) {
                            Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        impStage.setScene(new Scene(pane));
                        impStage.show();
                    } else {
                        try {
                            afficheFenetresSecondaire("SuiviMaison.fxml");
                        } catch (IOException ex) {
                            Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    try {
                        afficheFenetresSecondaire("SuiviMaison.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
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
        

       
        
        addconseil.setOnMouseClicked(event -> {
            if (selectedClient != null) {
                Utilis.clientActuel = selectedClient;
                Utilis.CONSEIL = true;
                try {
                    afficheFenetresSecondaire("SuiviMaison.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        zone_recherche.requestFocus();
        if (Utilis.IsAdmin()) {
            envoyerMailButton.setVisible(true);
            admin_bar.setVisible(true);
        }
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sûre de vouloir vous deconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });
        if (MichelKaApp.connexionIsOK) {
            clients = Utilis.driver.ListClient();
            initTable();
            table_view.setRowFactory(tv -> {
                TableRow<Client> row = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemSup = new MenuItem("Supprimer");
                MenuItem itemMod = new MenuItem("Modifier");
                MenuItem itemDaff = new MenuItem("Déaffecter carte");
                itemSup.setOnAction(actionItem -> {
                    Alert alertSup = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sûre de vouloir supprimer ce Client?");
                    Optional<ButtonType> rs = alertSup.showAndWait();
                    if (rs.get() == ButtonType.OK) {
                        if (Utilis.driver.delete(row.getItem()) && row.getItem().isEst_proprietaire_carte()
                                && row.getItem().getCarte() != null && Utilis.driver.updateCarte(row.getItem().getCarte().getReference(), "etat_carte", 2) && Utilis.driver.deleteCarteReference(row.getItem())) {
                            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Le client à  été bien supprimé");
                            alertInfo.show();
                            initTable();
                            tableAchat.setItems(null);
                            tablePrestation.setItems(null);
                            tableSuivi.setItems(null);
                        }
                    } else {
                        System.out.println("No supprimer");
                    }
                });
                itemMod.setOnAction(actionItem -> {
                    selectedClient = row.getItem();
                    Utilis.clientActuel = selectedClient;
                    try {
                        updateClient();
                    } catch (IOException ex) {
                        Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                        Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                itemDaff.setOnAction(actIem -> {
                    supprimerEffectationCarte(row.getItem());
                });
                contextMenu.getItems().add(itemMod);
                contextMenu.getItems().add(itemSup);
                contextMenu.getItems().add(itemDaff);
                row.setOnMouseClicked(event -> {

                    if (!row.isEmpty()) {
                        selectedClient = row.getItem();
                        tablePrestation(row.getItem());
                        faireAchat();
                        faireConseil();
                    }
                    if (event.getClickCount() == 2 && (!row.isEmpty()) && event.getButton() == MouseButton.PRIMARY) {
                        selectedClient = row.getItem();
                        Utilis.clientActuel = selectedClient;
                        try {
                            updateClient();
                        } catch (IOException ex) {
                            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                            Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        if (!row.isEmpty()) {
                            Utilis.clientActuel = selectedClient = row.getItem();
                            if (!selectedClient.isUtiliseCarte()) {
                                carteButton.setDisable(false);
                            } else {
                                carteButton.setDisable(true);
                            }
                        }
                    }
                    if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                        contextMenu.show(row, event.getScreenX(), event.getScreenY());
                    }
                });
                return row;
            });
            PAGES = Utilis.driver.nombreClient();
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
                selectedForEmail.clear();
                initTable();
            });
        }

        parametre = Utilis.trouverParametre("MOIS_ANNIVERSAIRE");
        try {
            Utilis.MOIS_ANNIVERSAIRE = Integer.parseInt(parametre.getValeur());
            System.out.println("Mois pour anniversaire: " + Utilis.MOIS_ANNIVERSAIRE);
            System.out.println("Actuelle pour anniversaires: " + (Utilis.moisActuelle() + 1));
        } catch (Exception e) {
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            Utilis.MOIS_ANNIVERSAIRE = 0;
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
        }
        if (Utilis.MOIS_ANNIVERSAIRE <= Utilis.moisActuelle() + 1) {
            parametre.setValeur("" + (Utilis.moisActuelle() + 1));
            Utilis.driver.updateParametre(parametre);
            Utilis.MOIS_ANNIVERSAIRE = Utilis.moisActuelle() + 1;
        }
        if (Utilis.MOIS_ANNIVERSAIRE == Utilis.moisActuelle() + 1 && Utilis.procheFinDuMois() && !MichelKaApp.ANNIVERSAIRE_ENVOYEE) {
            MichelKaApp.ANNIVERSAIRE_ENVOYEE = true;
            // System.out.println("mail envoye");
            //parametre.setValeur("" + (Utilis.MOIS_ANNIVERSAIRE + 1));
            //if (parametre.getValeur().equals("13")) {
            //    parametre.setValeur("" + 1);
            //}
            //Utilis.driver.updateParametre(parametre);
            mailTask();

        }
        if (!MichelKaApp.ALERT_SEUIL_PRODUIT && Utilis.IsAdmin()) {
            alertStockTask();
            MichelKaApp.ALERT_SEUIL_PRODUIT = true;
        }
        runFocusOnRecherche(zone_recherche);
        initPrestationTableau();

        tablePrestation.setRowFactory(tv -> {
            TableRow<PrestationTechnique> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem itemSup = new MenuItem("Supprimer");
            MenuItem itemMod = new MenuItem("Modifier");

            contextMenu.getItems().add(itemMod);
            contextMenu.getItems().add(itemSup);
            row.setContextMenu(contextMenu);
            itemMod.setOnAction(actionItem -> {
                try {
                    afficheFenetresSecondaire("UpdatePopPupPrestationFormFromFiche.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            itemSup.setOnAction(actionItem -> {
                Alert alertSup = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sûre de vouloir supprimer cette prestation? \n Cette operation est irréverssible.");
                Optional<ButtonType> rs = alertSup.showAndWait();
                if (Utilis.driver.delete(row.getItem())) {
                    Alert alertInfoSup = new Alert(Alert.AlertType.INFORMATION, "La prestation du client a  été supprimé avec succés");
                    alertInfoSup.show();
                }
                if (rs.get() == ButtonType.OK) {
                }
            });
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    Utilis.prestationTechniqueActuel = row.getItem();
                    try {
                        afficheFenetresSecondaire("DetailPrestation.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(FicheTechniqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    Utilis.prestationTechnique = row.getItem();
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });

            return row;
        });
    }

    @FXML
    private void buttonListener(ActionEvent event) throws IOException {
        if (table_view.isFocused()) {
            table_view.getSelectionModel().clearSelection();
            envoyerMailButton.setDisable(true);
            carteButton.setDisable(true);
        }
        if (event.getSource() == btn_service) {
            root = FXMLLoader.load(getClass().getResource("StockService.fxml"));
            stage = (Stage) btn_service.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == btn_produit) {
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
        } 
        
        
        else if (event.getSource() == btn_users) {
            stage = (Stage) btn_users.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } 
        
       
        
        else if (event.getSource() == btn_traces) {
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
        }
        

        else if (event.getSource() == ajouterFicheButton) {
            Utilis.clientActuel = null;
            afficheFenetresSecondaire("PopUpTechniqueForm.fxml");
            updateClientView();
        } else if (event.getSource() == carteButton) {
            Utilis.clientActuel = selectedClient;
            System.err.print(Utilis.clientActuel.getId());
            ISFROMTECHNIAUE = true;
            afficheFenetresSecondaire("PopUpAjouterCarteUpdate.fxml");
            updateClientView();
        } else if (event.getSource() == btn_fiche) {
            carteButton.setDisable(true);
        } else if (event.getSource() == envoyerMailButton) {
            if (Utilis.isConnectedToInternet()) {
                if (!selectedForEmail.isEmpty()) {
                    Utilis.clientsTomail = selectedForEmail;
                } else {
                    Utilis.clientsTomail = clients;
                }
                afficheFenetresSecondaire("SendMail.fxml");
            } else {
                Alert alertInternet = new Alert(Alert.AlertType.WARNING, "Voulez-vous vérifier la connexion internet s'il vous plait?");
                alertInternet.show();
            }
        }
    }

    @FXML
    private void rechercheActions(ActionEvent event) {
        if (event.getSource() == radioButton_nom) {
            op_recherche = Utilis.recherche_option_name;
        } else if (event.getSource() == radioButton_cheveux) {
            op_recherche = Utilis.recherche_option_cheveux;
        } else if (event.getSource() == radioButton_prenom) {
            op_recherche = Utilis.recherche_option_lastname;
        } else if (event.getSource() == btn_rechercher) {
            selectedForEmail.clear();
            key = zone_recherche.getText();
            System.out.println("Zone texte: ");
            if (key != null && !key.isEmpty()) {
                if (radioButton_nom.isSelected() || radioButton_prenom.isSelected()) {
                    initTableResult(Utilis.driver.rechercheList(key, op_recherche));
                } else if (radioButton_cheveux.isSelected()) {
                    initTableResult(Utilis.driver.rechercherParTypeCheveux(key));
                } else if (radioButton_service.isSelected()) {
                    initTableResult(Utilis.driver.rechercheparService(key));
                } else if (radioButton_produit.isSelected()) {
                    initTableResult(Utilis.driver.rechercheParProduit(key));
                }
            } else {
                clients = Utilis.driver.ListClient();
                initTable();
            }
        }
    }

    private void initTable() {
    	if(Utilis.entrainPayer) {
        	btn_payement.setDisable(true);
        }
        //clients = Utilis.driver.ListClient();
        if (clients != null) {
            Utilis.clientsTomail = clients;
            PAGES = clients.size();
            if (PAGES % INTERVAL_PAGE == 0) {
                pagination.setPageCount(PAGES / INTERVAL_PAGE);
            } else {
                pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
            }
            if (clients.size() > INTERVAL_PAGE) {
                if (MAX_PAGE >= PAGES) {
                    listClients = FXCollections.observableArrayList(clients.subList(MIN_PAGE, clients.size()));
                } else {
                    listClients = FXCollections.observableArrayList(clients.subList(MIN_PAGE, MAX_PAGE));
                }
            } else {
                listClients = FXCollections.observableArrayList(clients);
            }
            table_view.setItems(listClients);
            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            ann_col.setCellValueFactory(new PropertyValueFactory<>("anniversaire"));
            tel_col.setCellValueFactory(new PropertyValueFactory<>("mobile1"));
            email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
            adresse_col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            profession_col.setCellValueFactory(new PropertyValueFactory<>("profession"));
            type_cheveux_col.setCellValueFactory(new PropertyValueFactory<>("typeCheveux"));
            densite_col.setCellValueFactory(new PropertyValueFactory<>("densiteCheveux"));
            cuir_col.setCellValueFactory(new PropertyValueFactory<>("cuirChevelu"));
            carte_col.setCellValueFactory(new PropertyValueFactory<>("carte"));
            client_checker.setCellValueFactory(new PropertyValueFactory(""));
            client_checker.setCellFactory(new Callback<TableColumn<Client, Boolean>, TableCell<Client, Boolean>>() {

                @Override
                public TableCell<Client, Boolean> call(TableColumn<Client, Boolean> param) {
                    final TableCell<Client, Boolean> cell = new TableCell<Client, Boolean>() {
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
//                                TODO:: faire une multiselection
                                Client itm = (Client) getTableRow().getItem();
                                //checkBox.selectedProperty().addListener((obs, old, nv) -> {
                                //Client c = (Client) getTableRow().getItem();
                                checkBox.setOnAction(event -> {
                                    Client c = (Client) getTableView().getItems().get(getIndex());

                                    System.out.println("test 1");
                                    if (checkBox.isSelected() == true) {
                                        System.out.println("test 2");

                                        if (c.getEmail() != null && !c.getEmail().isEmpty() && Utilis.isAEmailAdress(c.getEmail())) {
                                            selectedForEmail.add(c);
                                            System.out.println("test 3");

                                        } else {
                                            Alert noMail = new Alert(Alert.AlertType.ERROR, "Ce client n'a pas renseigné son adresse mail.");
                                            noMail.show();
                                            noMail.setOnHidden((v) -> {
                                                checkBox.setSelected(false);
                                            });
                                        }
                                    } else {
                                        selectedForEmail.remove(c);
                                        System.out.println("test 4");

                                    }
                                    System.out.println(Arrays.toString(selectedForEmail.toArray()));

                                });
                            }
                        }
                    };

                    return cell;
                }

            });

        } else {
            System.out.println("No ok!!!");
        }
    }

    @FXML
    private void clickCounter(MouseEvent event) {
        Object ob = event.getTarget();
        if (ob != table_view) {
            carteButton.setDisable(true);
        }
    }

    private void afficheFenetresSecondaire(String fxmlFile) throws IOException {
        if (Utilis.Max_DEFAULT < Utilis.DEFINED_FENETRE) {
            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT + 1;
            Stage smoleFenetre = new Stage();
            smoleFenetre.setResizable(false);
            if(smoleFenetre.getOnCloseRequest()!=null) {
            	Utilis.entrainPayer=false;
            	
            }
            Parent smoleRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            smoleFenetre.setScene(new Scene(smoleRoot));
            smoleFenetre.setOnCloseRequest(event -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                Utilis.entrainPayer=false;
                btn_payement.setDisable(false);
               
            });
            smoleFenetre.setOnHidden((o) -> {
                --Utilis.Max_DEFAULT;
                initTable();
                tablePrestation(selectedClient);
                faireAchat();
                faireConseil();
                ISFROMTECHNIAUE = false;
                selectedForEmail.clear();
            });
            smoleFenetre.show();
        } else {
            Utilis.LOGGER.log(Utilis.errorFenetres);
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }

    private void initTableResult(List<Client> list) {
        if (list != null) {
            clients = list;
            Utilis.clientsTomail = list;
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

    private void updateClient() throws IOException {
        Utilis.UPDATE_CLIENT_TECHNIQUE = true;
        if (selectedClient != null) {
            afficheFenetresSecondaire("PopUpTechniqueFormUpdate.fxml");
        }
    }

    private List<Client> clientAsouhaiter() {
        List<Client> clientsListToMails = null;
        if (clients != null) {
            System.out.println("the clients to mail");
            clientsListToMails = new ArrayList<>();
            for (Client toMail : clients) {
                if (toMail.getEmail() != null && !toMail.getEmail().isEmpty() && Utilis.isAEmailAdress(toMail.getEmail())
                        && toMail.getAnniversaire().split("-")[0].equalsIgnoreCase(Utilis.stringMonth((Utilis.moisActuelle() + 1) == 12 ? 0 : (Utilis.moisActuelle() + 1)))) {
                    clientsListToMails.add(toMail);
                    System.out.println(toMail.getEmail());
                }
            }
        } else {
            System.out.println("no one  to mail.");
        }
        return clientsListToMails;
    }

    void runFocusOnRecherche(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                node.requestFocus();
            }
        });
    }

    private void mailTask() {
        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(5000);
                return null;
            }
        };
        longTask.setOnSucceeded(
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        int moisProchain = Utilis.moisActuelle() + 1;
                        String msg = "";
                        if (moisProchain == 12) {
                            moisProchain = 0;
                            msg = "Vous êtes  à  " + (Utilis.nombreJourMoisActuel() - Utilis.jourActuel()) + " jours de "
                            + "la fin du mois de " + Utilis.stringMonth(Utilis.moisActuelle()) + ". Voulez-vous envoyer les mails "
                            + " alertes anniversaire pour le mois de " + Utilis.stringMonth(moisProchain) + "?";
                        } else {
                            msg = "Vous êtes  à  " + (Utilis.nombreJourMoisActuel() - Utilis.jourActuel()) + " jours de "
                            + "la fin du mois de " + Utilis.stringMonth(Utilis.moisActuelle()) + ". Voulez-vous envoyer les mails "
                            + " alertes anniversaire pour le mois de " + Utilis.stringMonth(Utilis.moisActuelle() + 1) + "?";
                        }
                        Alert alertMail = new Alert(Alert.AlertType.CONFIRMATION);
                        alertMail.getButtonTypes().clear();
                        alertMail.getButtonTypes().add(ButtonType.YES);
                        alertMail.getButtonTypes().add(ButtonType.NO);
                        alertMail.setContentText(msg);
                        Optional<ButtonType> result = alertMail.showAndWait();
                        if (result.get() == ButtonType.YES) {
                            List<Client> list = clientAsouhaiter();
                            if (list == null || list.isEmpty()) {
                                alertMail.close();
                                parametre.setValeur("" + (Utilis.moisActuelle() + 1));
                                Utilis.driver.updateParametre(parametre);
                                Utilis.notification(false, "Aucun mail à  envoyer pour ce mois ci.");
                                return;
                            }
                            if (Utilis.isConnectedToInternet()) {
                                sendMailTask(list);
                            } else {
                                Alert info = new Alert(Alert.AlertType.INFORMATION, Utilis.INTERNET_CONNECTION, ButtonType.OK);
                                info.show();
                            }
                        } else {
                            alertMail.close();
                        }
                    }
                }
        );
        new Thread(longTask)
                .start();
    }

    private void sendMailTask(List<Client> list) {
        int size = list.size();

        Task longMailTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                if (Utilis.CORPS_MAIL_ANNIVERSAIRE.isEmpty()) {
                    Utilis.CORPS_MAIL_ANNIVERSAIRE = Utilis.trouverParametre("TEXTE_EMAIL_AN").getValeur();
                }
                if (Utilis.OBJET_MAIL_ANNIVERSAIRE.isEmpty()) {
                    Utilis.OBJET_MAIL_ANNIVERSAIRE = Utilis.trouverParametre("OBJET_MAIL_AN").getValeur();
                }
                int i = 0;

                SendMail mail = new SendMail();
                mail.setUsername(Utilis.DefaultEmail);
                mail.setFichierJoint(null);
                mail.setHostname("smtp.gmail.com");
                mail.setPassword(Utilis.DefaultEmailPassword);
                mail.setMailServer("mail.smtp.host");
                mail.setSujet(Utilis.OBJET_MAIL_ANNIVERSAIRE);
                String bstMail = Utilis.trouverParametre("MAIL_BST").getValeur();
                String micheleMail = Utilis.trouverParametre("EMAIL_MICHELE_KA").getValeur();
                for (Client cli : list) {
                    String msg = Utilis.CORPS_MAIL_ANNIVERSAIRE.replace("[anniversaire client]", cli.getAnniversaire().split("-")[0]).replace("[taux remise]", Utilis.trouverParametre("REMISE_ANNIVERSAIRE").getValeur());
                    msg = msg.replace("[nom]", cli.getNom().toLowerCase());
                    msg = msg.replace("[prenom]", cli.getPrenom().toLowerCase());

                    if (bstMail != null && micheleMail != null && !bstMail.isEmpty() && !micheleMail.isEmpty()
                            && Utilis.isAEmailAdress(bstMail) && Utilis.isAEmailAdress(micheleMail)) {
                        mail.setDestinataires(new String[]{cli.getEmail(), bstMail, micheleMail,"mlamineka@gmail.com"});

                    } else {
                        // mail.setDestinataires(new String[]{cli.getEmail(), "youga.dieng@yahoo.fr", "micheleka@orange.sn"});
                        //mail.setDestinataires(new String[]{cli.getEmail(), "youga.dieng@yahoo.fr", "epape1100@gmail.com"});
                        // mail.setDestinataires(new String[]{cli.getEmail(),"",""});
                        mail.setDestinataires(new String[]{cli.getEmail(), micheleMail, "yougadieng@blackstar-tech.com","mlamineka@gmail.com"});

                    }

                    mail.setCorps(msg);

                    mail.send();
                    if (mail.isSeeded()) {
                        seededMail++;
                    }
                    i++;
                    updateProgress(i, size);
                    updateMessage("Nombre d'emails envoyés: " + seededMail);
                }
                if (isCancelled()) {
                    return i;
                }
                return size;
            }

            @Override
            protected void updateMessage(String message) {
                super.updateMessage(message);
            }

            @Override
            protected void updateProgress(long workDone, long max) {
                System.out.println("i= :" + workDone + " rest: " + max);

                super.updateProgress(workDone, max);
            }

        };

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // Need to force the alert to layout in order to grab the graphic,
        // as we are replacing the dialog pane with a custom pane
        alert.getDialogPane().applyCss();
        Node graphic = alert.getDialogPane().getGraphic();

        HBox hb = new HBox();
        // Create a new dialog pane that has a checkbox instead of the hide/show details button
        // Use the supplied callback for the action of the checkbox
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                ProgressIndicator optOut = new ProgressIndicator();
                optOut.progressProperty().bind(longMailTask.progressProperty());
                Label nombreSecced = new Label("Email envoyés");
                nombreSecced.textProperty().bind(longMailTask.messageProperty());
                hb.setSpacing(25);
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().add(optOut);
                hb.getChildren().add(nombreSecced);
                return hb;
            }
        });
//        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().setContentText("");
        // Fool the dialog into thinking there is some expandable content
        // a Group won't take up any space if it has no children
        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);
        // Reset the dialog graphic using the default style
        alert.getDialogPane().setGraphic(graphic);
        alert.setTitle("Alertes anniversaire: Envoi en cours...");
        alert.show();

        new Thread(longMailTask)
                .start();

        longMailTask.setOnSucceeded((Event event) -> {

            parametre.setValeur("" + (Utilis.MOIS_ANNIVERSAIRE + 1));
            if (parametre.getValeur().equals("13")) {
                parametre.setValeur("" + 1);
            }
            Utilis.driver.updateParametre(parametre);
            hb.getChildren().add(new Label("Echecs : " + (size - seededMail)));
            alert.getButtonTypes().add(ButtonType.OK);
        });
    }

    private void updateClientView() {
        if (Utilis.CLIENT_IS_UPDATE) {
            Utilis.CLIENT_IS_UPDATE = false;
            initTable();
        }
    }

    private void alertStockTask() {
        System.out.println("Alert Stock Call");
        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(10000);
                return null;
            }
        };
        longTask.setOnSucceeded((event) -> {
            Utilis.alertStock();
        });
        new Thread(longTask)
                .start();
    }

    private void tablePrestation(Client cl) {
        if (selectedClient != null) {
            List<PrestationTechnique> prestationTechniques = Utilis.driver.listPrestationTechniqueClient(cl.getId(), 5);
            if (prestationTechniques != null) {
                prestationTechniques = mettreProduit(prestationTechniques);
                tablePrestation.setItems(FXCollections.observableArrayList(prestationTechniques));
            } else {
                tablePrestation.setItems(null);
            }
        }
    }

    private void initPrestationTableau() {
        prestation_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        prestation_type.setCellValueFactory(new PropertyValueFactory<>("typePrestation"));
        prestation_categorie.setCellValueFactory(new PropertyValueFactory<>("categeorie"));
        prestation_pause.setCellValueFactory(new PropertyValueFactory<>("pause"));
        prestation_montage.setCellValueFactory(new PropertyValueFactory<>("montage"));
        prestation_technique.setCellValueFactory(new PropertyValueFactory<>("technique"));
        prestation_observation.setCellValueFactory(new PropertyValueFactory<>("observations"));
        prestation_marques.setCellValueFactory(new PropertyValueFactory<>("produits"));
    }

    private List<PrestationTechnique> mettreProduit(List<PrestationTechnique> pt) {
        pt.forEach(tv -> {
            tv.setProduits(Utilis.driver.listProduitsTechnique(tv.getId()));
        });
        return pt;
    }

    private void faireConseil() {
        if (selectedClient != null) {
            List<SuiviMaison> list = Utilis.driver.ListSuiviMaison(selectedClient.getId());
            if (list != null) {
                tableSuivi.setItems(FXCollections.observableArrayList(list));
                date_conseil.setCellValueFactory(new PropertyValueFactory<>("date"));
                conseil.setCellValueFactory(new PropertyValueFactory<>("conseil"));
            } else {
                tableSuivi.setItems(null);
            }
        } else {
            tableSuivi.setItems(null);
        }
    }

    /**
     * TODO:: faire apparaitre la liste des achat sur le tableau dedier
     */
    private void faireAchat() {
        if (selectedClient != null) {
            List<Achat> achatsClient = Utilis.driver.listAchatClient(selectedClient.getId());
            if (achatsClient != null) {
                int len = achatsClient.size();
                for (int i = 0; i < len; i++) {
                    achatsClient.get(i).setProduits(Utilis.driver.listProduits(achatsClient.get(i).getId()));
                }
                tableAchat.setItems(FXCollections.observableArrayList(achatsClient));
                date_achat.setCellValueFactory(new PropertyValueFactory<>("date"));
                produit_achetes.setCellValueFactory(new PropertyValueFactory<>("produits"));
            } else {
                tableAchat.setItems(null);
            }
        } else {
            tableAchat.setItems(null);
        }
    }
//    Pour la suppression de l'affectation de la carte.

    private void supprimerEffectationCarte(Client client) {
        if (client.isUtiliseCarte() && client.getCarte().getReference() != 0) {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setContentText("Etes vous sûre de vouloir désaffecter la carte de ce client?");
            Optional<ButtonType> rs = conf.showAndWait();
            if (rs.get() == ButtonType.OK) {
                if (client.isEst_proprietaire_carte()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Ce client est propriétaire de la carte.\nUne désaffectation aura une répercusion sur tous les clients utilisant la même carte!");
                    Optional<ButtonType> result = alert.showAndWait();
                    /*if (result.get() == ButtonType.OK && Utilis.driver.enleverCarteClient(client.getCarte().getReference() + "")
                     && Utilis.driver.supprimerCodeBarre(client.getCarte().getReference() + "") &&
                     Utilis.driver.effacerCardData(client.getCarte().getReference() + "")) {
                     Utilis.makeErrorAlert(Alert.AlertType.INFORMATION, "Le client a Ã©tÃ© dÃ©affectÃ© de sa carte. ", client.getCarte().getReference() + "");
                     */
                    if (result.get() == ButtonType.OK && Utilis.driver.enleverCarteClient(client.getCarte().getReference() + "")) {
                        System.out.println("1 entré ok");
                        if (Utilis.driver.supprimerCodeBarre(client.getCarte().getReference() + "")) {
                            System.out.println("2 eme  entre ok");
                        }
                        if (Utilis.driver.effacerCardData(client.getCarte().getReference() + "")) {
                            System.out.println("3e ok");
                        }
                        Utilis.makeErrorAlert(Alert.AlertType.INFORMATION, "Le client a été désaffecté de sa carte. ", client.getCarte().getReference() + "");

                    }
                   // Utilis.makeErrorAlert(Alert.AlertType.INFORMATION, "Le client a Ã©tÃ© dÃ©affectÃ© de sa carte. ", client.getCarte().getReference() + "");
                    else {
                        Utilis.makeErrorAlert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de la désaffectation de la carte.", client.getCarte().getReference() + "");
                    }
                } else {
                    if (Utilis.driver.enleverCarteClient(client.getId() + "", client.getCarte().getReference() + "")) {
                        Carte carte = Utilis.driver.trouverCarte(client);
                        if (carte.getNombreUtilisateurCarte() > 1) {
                            boolean bol = Utilis.driver.updateCarte(carte.getReference(), "nombre_utilisateur_carte", (carte.getNombreUtilisateurCarte() - 1));
                            if (bol) {
                                System.out.println("le nombre d'utilisateur a été mis à  jour!");
                            }
                        }
                    } else {
                        Utilis.makeErrorAlert(Alert.AlertType.ERROR, "La désaffectation de la carte n'a pas réussi", ".");
                    }
                }
            }
        } else {
            Utilis.makeErrorAlert(Alert.AlertType.ERROR, "Une carte n'a pas encore été effecté a ce client ", ".");
        }
    }

}
