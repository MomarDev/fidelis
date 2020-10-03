/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import michelkaapp.database.DashboardDAO;
import michelkaapp.objets.MouvementProduit;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class DashboardProduitController implements Initializable {

    @FXML
    private DatePicker dpk_debut;
    @FXML
    private DatePicker dpk_fin;
    @FXML
    private RadioButton rad_vente;
    @FXML
    private ToggleGroup gp_rad;
    @FXML
    private RadioButton rad_service;
    @FXML
    private RadioButton rad_autre;
    @FXML
    private TableView<MouvementProduit> tableView;
    @FXML
    private TableColumn<MouvementProduit, String> col_nom;
    @FXML
    private TableColumn<MouvementProduit, Integer> col_quantite;
    @FXML
    private TableColumn<MouvementProduit, String> col_prix;
    @FXML
    private TableColumn<MouvementProduit, String> col_date;
    @FXML
    private Pagination pagination;
    private ArrayList<MouvementProduit> mouvementProduits = new ArrayList<>();
    private String dateDebut;
    private String dateFin;
    private String type = "tout";
    private int INTERVAL_PAGE = 50;
    private int MIN_PAGE = 0;
    private int MAX_PAGE = 50;
    @FXML
    private SplitPane pane_center;
    @FXML
    private AnchorPane pane_gauche;
    @FXML
    private Button logout;
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
    private HBox admin_bar;
    @FXML
    private Button btn_users;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    @FXML
    private AnchorPane pane_central;
    @FXML
    private Label titre_menu;
    @FXML
    private AnchorPane pane_technique;
    @FXML
    private AnchorPane pane_border;
    @FXML
    private Label titre_recherche_option;
    private Parent root;
    private Stage stage;
    private String dateRef = "2018-08-01";

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        initTableView();
        initActions();
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sûre de vouloir vous déconnecter?");
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
    }

    private void initTableView() {

//        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
//        col_motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        mouvementProduits = DashboardDAO.select("tout", Utilis.laDateDe(01), Utilis.NOW_LOCAL_DATE().toString());
        tableView.setItems(FXCollections.observableArrayList(mouvementProduits));
        initPagination(mouvementProduits.size());
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
                
               
            });
            smoleFenetre.show();
        } else {
            Utilis.LOGGER.log(Utilis.errorFenetres);
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }

    private void initActions() {
        dateFin = Utilis.NOW_LOCAL_DATE().toString();
        dateDebut = Utilis.laDateDe(01);
        dpk_debut.setPromptText(Utilis.dateFrenchFormat(dateDebut));
        dpk_fin.setPromptText(Utilis.dateFrenchFormat(dateFin));
        dpk_debut.valueProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.toString().isEmpty()) {
                dateDebut = nv.toString();
                if (dateDebut.compareTo(dateRef) >= 0) {
                    mouvementProduits = DashboardDAO.select(type, dateDebut, dateFin);
                    initPagination(mouvementProduits.size());
                } else {
                    Utilis.makeErrorAlert(Alert.AlertType.ERROR, "Desolé vous ne pouvez avoir les date inferieurs a 01-08-2018."
                            + "\nCar les valeurs risquesmi de ne pas etre exact.", "");
                }
            }
        });
        dpk_fin.valueProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.toString().isEmpty()) {
                dateFin = nv.toString();
                mouvementProduits = DashboardDAO.select(type, dateDebut, dateFin);
                initPagination(mouvementProduits.size());
            }
        });
        gp_rad.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (nv == rad_vente) {
                type = "achat";
            } else if (nv == rad_service) {
                type = "service";
            } else if (nv == rad_autre) {
                type = "autre";
            } else {
                type = "tout";
            }
            mouvementProduits = DashboardDAO.select(type, dateDebut, dateFin);
            initPagination(mouvementProduits.size());
        });
    }

    private void initPagination(int pages) {
        tableView.setItems(FXCollections.observableArrayList(mouvementProduits));
        if (pages % INTERVAL_PAGE == 0) {
            pagination.setPageCount(pages / INTERVAL_PAGE);
        } else {
            pagination.setPageCount(pages / INTERVAL_PAGE + 1);
        }
        pagination.currentPageIndexProperty().addListener((obs, old, nv) -> {
            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, pages);
            tableView.setItems(FXCollections.observableArrayList(mouvementProduits.subList(MIN_PAGE, MAX_PAGE)));
        });
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
        if (event.getSource() == btn_produit) {
            root = FXMLLoader.load(getClass().getResource("StockProduit.fxml"));
            stage = (Stage) btn_produit.getScene().getWindow();
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

}
