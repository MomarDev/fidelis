/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import michelkaapp.objets.GammeProduit;
import michelkaapp.objets.Marque;
import michelkaapp.objets.Produit;
import michelkaapp.objets.SousFamille;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class StockProduitController implements Initializable {

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
    private TableView<Produit> table_view;
    @FXML
    private AnchorPane pane_bas;
    @FXML
    private RadioButton designation_rd;
    @FXML
    private ToggleGroup toggleGroupe;
    @FXML
    private RadioButton marque_rd;
    @FXML
    private RadioButton code_rd;
    @FXML
    private Button btn_add_produit;

    private Stage stage;
    private Parent root;
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
    private Button btn_add_stock;
    @FXML
    private Button btn_sup_stock;
    @FXML
    private Button btn_inventaire;
    @FXML
    private HBox admin_bar;
    @FXML
    private TableColumn<Produit, String> designation_col;
    @FXML
    private TableColumn<Produit, String> code_col;
    @FXML
    private TableColumn<Produit, Marque> marque_col;
    @FXML
    private TableColumn<Produit, String> famille_col;
    @FXML
    private TableColumn<Produit, SousFamille> sous_famille_col;
    @FXML
    private TableColumn<Produit, GammeProduit> gamme_col;
    @FXML
    private TableColumn<Produit, Integer> stock_col;
    @FXML
    private TableColumn<Produit, Integer> prix_col;
    @FXML
    private TableColumn<Produit, String> info_col;

    private Produit produit = null;
    @FXML
    private Pagination pagination;
    private int MIN_PAGE = 0;
    private int MAX_PAGE = 30;
    private int PAGES = 0;
    private int INTERVAL_PAGE = 30;
    private String key = "";
    @FXML
    private Button logout;
    @FXML
    private Button supprimer;
    @FXML
    private Button btn_dash;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.IsAdmin()) {
            admin_bar.setVisible(true);
            btn_dash.setVisible(true);
        }

        zone_recherche.requestFocus();
        PAGES = Utilis.driver.countProduit();
        if (PAGES % INTERVAL_PAGE == 0) {
            pagination.setPageCount(PAGES / INTERVAL_PAGE);
        } else {
            pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
        }
        supprimer.setOnMouseClicked(event -> {
            if (produit != null && !produit.getNom().toLowerCase().contains("divers")) {
                Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sûre de vouloir supprimer ce produit?");
                Optional<ButtonType> rs = deleteDialog.showAndWait();
                if (rs.get() == ButtonType.OK) {
                    Utilis.driver.deleteProduit(produit);
                    initTable();
                }
            }
        });
        list = Utilis.driver.listProduit("WHERE produit.supprime = 0");

        initTable();
        pagination.currentPageIndexProperty().addListener((obs, old, nv) -> {
            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, PAGES);
            System.out.println(nv.intValue());
            System.out.println("MIN:" + MIN_PAGE);
            System.out.println("MAX:" + MAX_PAGE);
            initTable();
        });
        zone_recherche.textProperty().addListener((obs, old, nv) -> {
            if (!code_rd.isSelected()) {
                if (nv != null) {
                    key = nv;
                }
            } else {
                if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                    key = nv;
                } else if (nv != null && !nv.isEmpty() && !Utilis.isNumber(nv)) {
                    Utilis.makeErrorAlert(Alert.AlertType.ERROR, "Le champ attend actuellement des chiffres! ", "ex:123");
                }
            }
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
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sûre de vouloir vous deconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });
        table_view.setRowFactory(tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                produit = row.getItem();
                if (produit != null && produit.getNom().toLowerCase().contains("divers")) {
                    supprimer.setDisable(true);
                } else {
                    supprimer.setDisable(false);
                }
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Utilis.ProduitToUpdate = row.getItem();
                    Stage updateStage = new Stage();
                    try {
                        System.out.println("clicked" + event.getClickCount());
                        Parent updateRoot = FXMLLoader.load(getClass().getResource("UpdateProduit.fxml"));
                        updateStage.setScene(new Scene(updateRoot));
                        updateStage.setResizable(false);
                        ++Utilis.Max_DEFAULT;
                        updateStage.show();
                        updateStage.setOnCloseRequest((o) -> {
                            --Utilis.Max_DEFAULT;
                        });
                        updateStage.setOnHidden((o) -> {
                            initTable();
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(StockProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
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
        }
        if (event.getSource() == btn_carte) {
            root = FXMLLoader.load(getClass().getResource("Cartes.fxml"));
            stage = (Stage) btn_carte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
       
        if (event.getSource() == btn_service) {
            root = FXMLLoader.load(getClass().getResource("StockService.fxml"));
            stage = (Stage) btn_service.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_add_produit) {
            Stage stageProduit = new Stage();
            stageProduit.setResizable(false);
            Parent rootProduit = FXMLLoader.load(getClass().getResource("AddProduitForm.fxml"));
            stageProduit.initModality(Modality.APPLICATION_MODAL);
            stageProduit.initOwner(btn_add_produit.getScene().getWindow());
            stageProduit.setScene(new Scene(rootProduit));
            stageProduit.setOnHidden((o) -> {
                initTable();
            });
            stageProduit.showAndWait();
        }
        if (event.getSource() == btn_sup_stock) {
            Stage stageMouvement = new Stage();
            stageMouvement.setResizable(false);
            Parent rootMouvement = FXMLLoader.load(getClass().getResource("MouvementStock.fxml"));
            stageMouvement.initModality(Modality.APPLICATION_MODAL);
            stageMouvement.initOwner(btn_sup_stock.getScene().getWindow());
            stageMouvement.setScene(new Scene(rootMouvement));
            stageMouvement.setOnHidden((o) -> {
                initTable();
            });
            stageMouvement.showAndWait();
        }
        if (event.getSource() == btn_add_stock) {
            Stage stageMouvement = new Stage();
            stageMouvement.setResizable(false);
            Parent rootMouvement = FXMLLoader.load(getClass().getResource("MouvementStockAdd.fxml"));
            stageMouvement.initModality(Modality.APPLICATION_MODAL);
            stageMouvement.initOwner(btn_sup_stock.getScene().getWindow());
            stageMouvement.setScene(new Scene(rootMouvement));
            stageMouvement.setOnHidden((o) -> {
                initTable();
            });
            stageMouvement.showAndWait();
        }
        if (event.getSource() == btn_users) {
            stage = (Stage) btn_users.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_traces) {
            stage = (Stage) btn_traces.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Traces.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_setting) {
            stage = (Stage) btn_setting.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Parametres.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        if (event.getSource() == btn_inventaire) {
            Parent InventaireRoot = FXMLLoader.load(getClass().getResource("Inventaire.fxml"));
            Stage InvStage = new Stage();
            InvStage.setScene(new Scene(InventaireRoot));
            InvStage.setResizable(false);
            InvStage.show();
        }
        if (event.getSource() == btn_dash) {

                        stage = (Stage) btn_traces.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("DashboardProduit.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
           
        }
        if (Utilis.PRODUIT_IS_UPDATE) {
            Utilis.PRODUIT_IS_UPDATE = false;
            initTable();
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
               
            });
            smoleFenetre.show();
        } else {
            Utilis.LOGGER.log(Utilis.errorFenetres);
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }
 List<Produit> list = null;
    private void initTable() {
        //List<Produit> list = Utilis.driver.listProduit("WHERE produit.supprime = 0");
        if (list != null) {
            if (list.size() > INTERVAL_PAGE) {
                 if ( MAX_PAGE >= PAGES){
                   table_view.setItems(FXCollections.observableArrayList(list.subList(MIN_PAGE, list.size())));
                } else
                table_view.setItems(FXCollections.observableArrayList(list.subList(MIN_PAGE, MAX_PAGE)));
            } else {
                table_view.setItems(FXCollections.observableArrayList(list));
            }
            designation_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            code_col.setCellValueFactory(new PropertyValueFactory<>("code"));
            marque_col.setCellValueFactory(new PropertyValueFactory<>("marque"));
            famille_col.setCellValueFactory(new PropertyValueFactory<>("famille"));
            sous_famille_col.setCellValueFactory(new PropertyValueFactory<>("sousfamille"));
            gamme_col.setCellValueFactory(new PropertyValueFactory<>("gammeProduit"));
            stock_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
            prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
            info_col.setCellValueFactory(new PropertyValueFactory<>("info"));
        }
    }

    @FXML
    private void rechercheOptions(ActionEvent event) {
        if (code_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheProduitParCodeBarre(key));
        }
        if (marque_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheProduitParMarque(key));
        }
        if (designation_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheProduitParDesignation(key));
        }
    }

    private void initTableResult(List<Produit> list) {
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
