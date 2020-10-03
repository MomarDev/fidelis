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
import michelkaapp.objets.CategoriePrestation;
import michelkaapp.objets.Prestation;
import michelkaapp.objets.Service;
import michelkaapp.objets.SousCategoriePrestation;
import michelkaapp.objets.TypePrestation;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class StockServiceController implements Initializable {

    @FXML
    private Button btn_tarif;
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
    private AnchorPane pane_central;
    @FXML
    private Label titre_menu;
    @FXML
    private TextField zone_recherche;
    @FXML
    private Button btn_rechercher;
    @FXML
    private Button btn_payement;
    @FXML
    private AnchorPane pane_technique;
    @FXML
    private TableView<Service> table_view;
    @FXML
    private AnchorPane pane_bas;
    @FXML
    private RadioButton type_prestation_rd;
    @FXML
    private ToggleGroup toggleGroupe;
    @FXML
    private RadioButton categorie_rd;
    @FXML
    private RadioButton prestation_rd;

    private Parent root;
    private Stage stage;
    @FXML
    private AnchorPane pane_border;
    @FXML
    private Label titre_recherche_option;
    @FXML
    private Button btn_add_service;
    @FXML
    private Button btn_users;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    @FXML
    private HBox admin_bar;
    @FXML
    private TableColumn<Service, TypePrestation> type_prestation_col;
    @FXML
    private TableColumn<Service, CategoriePrestation> categorie_col;
    @FXML
    private TableColumn<Service, SousCategoriePrestation> sous_categorie_col;
    @FXML
    private TableColumn<Service, Prestation> prestation_col;
    @FXML
    private TableColumn<Service, String> prix_prestation_col;
    @FXML
    private Pagination pagination;

    private int MIN_PAGE = 0;
    private int MAX_PAGE = 12;
    private final int INTERVAL_PAGE = 12;
    private int PAGES = 0;
    private String mot = "";
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
        if (Utilis.IsAdmin()) {
            admin_bar.setVisible(true);
            btn_tarif.setVisible(true);
        }

        zone_recherche.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                mot = nv;
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
        
        
        
        PAGES = Utilis.driver.nombrePrestation();
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
        table_view.setRowFactory(tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Utilis.ServiceToUpdate = row.getItem();
                    try {
                        Stage stageUpdateService = new Stage();
                        Parent updateRoot = FXMLLoader.load(getClass().getResource("UpdateServiceForm.fxml"));
                        stageUpdateService.setScene(new Scene(updateRoot));
                        stageUpdateService.setResizable(false);
                        stageUpdateService.setOnCloseRequest((o) -> {
                            --Utilis.Max_DEFAULT;
                        });
                        stageUpdateService.setOnHidden((o) -> {
                            --Utilis.Max_DEFAULT;
                            initTable();
                        });
                        stageUpdateService.show();
                    } catch (IOException ex) {
                        Logger.getLogger(StockServiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
         list = Utilis.driver.listService();

        initTable();
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
        if (event.getSource() == btn_produit) {
            root = FXMLLoader.load(getClass().getResource("StockProduit.fxml"));
            stage = (Stage) btn_produit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
		        
		        if (event.getSource() == btn_tarif) {
		
		            stage = (Stage) btn_traces.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("LesTarifs.fxml"));
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
		
		}
       
        if (event.getSource() == btn_carte) {
            root = FXMLLoader.load(getClass().getResource("Cartes.fxml"));
            stage = (Stage) btn_carte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_add_service) {
            Stage stageAddService = new Stage();
            stageAddService.setResizable(false);
            stageAddService.initModality(Modality.APPLICATION_MODAL);
            stageAddService.initOwner(btn_add_service.getScene().getWindow());
            Parent rootAddService = FXMLLoader.load(getClass().getResource("AddServiceForm.fxml"));
            stageAddService.setScene(new Scene(rootAddService));
            Utilis.Max_DEFAULT++;
            stageAddService.showAndWait();
            if (Utilis.SERVICE_IS_UPDATE) {
                Utilis.SERVICE_IS_UPDATE = false;
                initTable();
            }
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
    List<Service> list = Utilis.driver.listService();

    private void initTable() {

        //List<Service> list = Utilis.driver.listService();
        if (list != null) {
            //PAGES = Utilis.driver.nombrePrestation();
            PAGES  =list.size();
            if (PAGES % INTERVAL_PAGE == 0) {
                pagination.setPageCount(PAGES / INTERVAL_PAGE);
            } else {
                pagination.setPageCount(PAGES / INTERVAL_PAGE + 1);
            }
            if (list.size() > INTERVAL_PAGE) {
                if (MAX_PAGE >= PAGES) {
                    table_view.setItems(FXCollections.observableArrayList(list.subList(MIN_PAGE, list.size())));
                } else {
                    table_view.setItems(FXCollections.observableArrayList(list.subList(MIN_PAGE, MAX_PAGE)));
                }
            } else {
                table_view.setItems(FXCollections.observableArrayList(list));
            }

            // table_view.setItems(FXCollections.observableArrayList(list));
            prestation_col.setCellValueFactory(new PropertyValueFactory<>("prestation"));
            type_prestation_col.setCellValueFactory(new PropertyValueFactory<>("typePrestation"));
            categorie_col.setCellValueFactory(new PropertyValueFactory<>("categoriePrestation"));
            sous_categorie_col.setCellValueFactory(new PropertyValueFactory<>("sousCategoriePrestation"));
            prix_prestation_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
        }

    }

    @FXML
    private void optionRecherche(ActionEvent event) {
        if (prestation_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheService(mot, "prestation.nom_prestation"));
        }
        if (type_prestation_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheService(mot, "type_prestation.nom_type_prestation"));
        }
        if (categorie_rd.isSelected()) {
            initTableResult(Utilis.driver.rechercheService(mot, "categorie_prestation.name_categorie"));
        }
    }

    private void initTableResult(List<Service> list) {
        if (list != null) {
            this.list=list;
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
