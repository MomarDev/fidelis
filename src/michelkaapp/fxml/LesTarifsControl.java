package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import michelkaapp.database.DashboardDAO;
import michelkaapp.objets.MouvementProduit;
import michelkaapp.objets.Tarif;
import michelkaapp.utilis.Utilis;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LesTarifsControl implements Initializable {

    @FXML
    private Button btn_supprimer;
    @FXML
    private RadioButton rad_carte;
    @FXML
    private RadioButton rad_tarif;
    @FXML
    private RadioButton rad_tout;
    @FXML
    private ComboBox combo_age;
    @FXML
    private ComboBox combo_sexe;
    @FXML
    private RadioButton rad_vente;
    @FXML
    private ToggleGroup gp_rad;
    @FXML
    private RadioButton rad_service;
    @FXML
    private RadioButton rad_autre;
    @FXML
    private Button btn_tarifs;
    @FXML
    private TableView<Tarif> tableView;
    @FXML
    private TableColumn<Tarif, String> col_nom;
    @FXML
    private TableColumn<Tarif, String> col_sexe;
    @FXML
    private TableColumn<Tarif, String> col_prestation;
    @FXML
    private TableColumn<Tarif, String> col_prix;
    @FXML
    private TableColumn<Tarif, String> col_detail;
    @FXML
    private Pagination pagination;
    private List<Tarif> tarifs = new ArrayList<>();
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
    private Tarif tarif =null;
    private Parent root;
    private Stage stage;
    private ObservableList<String> listAge = FXCollections.observableArrayList(
            " ADULTE", " ADOS -20 ans",  " Garçon -12 ans", " BEBE -5 ANS",  " FILLETTE -12 ANS"
    );
    private ObservableList<String> sexe = FXCollections.observableArrayList("Homme","Femme");

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        combo_age.getItems().addAll(listAge);
        combo_sexe.getItems().addAll(sexe);
        initTableView();
        initAction();
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sûre de vouloir vous déconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });

        tableView.setRowFactory(tv -> {
            TableRow<Tarif> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                tarif = row.getItem();
                
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Utilis.TarifToUpdate = row.getItem();
                    Stage updateStage = new Stage();
                    try {
                        System.out.println("clicked" + event.getClickCount());
                        Parent updateRoot = FXMLLoader.load(getClass().getResource("UpdateTarif.fxml"));
                        updateStage.setScene(new Scene(updateRoot));
                        updateStage.setResizable(false);
                        ++Utilis.Max_DEFAULT;
                        updateStage.show();
                        updateStage.setOnCloseRequest((o) -> {
                            --Utilis.Max_DEFAULT;
                        });
                        updateStage.setOnHidden((o) -> {
                            initTableView();
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(StockProduitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

        btn_supprimer.setOnMouseClicked(event -> {
            if (tarif != null ) {
                Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sûre de vouloir supprimer cette prestation?");
                Optional<ButtonType> rs = deleteDialog.showAndWait();
                if (rs.get() == ButtonType.OK) {
                    Utilis.driver.deleteTarif(tarif);
                    initTableView();
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
    }

    @FXML
    private void initSearch(ActionEvent event){
        if (event.getSource()==combo_sexe){
            tarifs = Utilis.driver.listTarifSexe((String) combo_sexe.getValue());
            initPagination(tarifs.size());
        }else {
        	
        
        if(event.getSource()==combo_age){
            tarifs = Utilis.driver.listTarifAge((String) combo_age.getValue());
            initPagination(tarifs.size());
        }
        }
    }

    private void initTableView() {

//        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
//        col_motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prixString"));
        col_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        col_prestation.setCellValueFactory(new PropertyValueFactory<>("prestation"));
        col_detail.setCellValueFactory(new PropertyValueFactory<>("detail"));


        

        tarifs = Utilis.driver.listTarif();
        tableView.setItems(FXCollections.observableArrayList(tarifs));
        initPagination(tarifs.size());
    }
    private void afficheFenetresSecondaire(String fxmlFile) throws IOException {
        if (Utilis.Max_DEFAULT < Utilis.DEFINED_FENETRE) {
            Utilis.Max_DEFAULT = Utilis.Max_DEFAULT + 1;
            Stage smoleFenetre = new Stage();
            smoleFenetre.setResizable(false);
            if(smoleFenetre.getOnCloseRequest()!=null) {

            }
            Parent smoleRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            smoleFenetre.setScene(new Scene(smoleRoot));
            smoleFenetre.setOnCloseRequest(event -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;    
               

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

    private void initAction(){
        gp_rad.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (nv == rad_carte) {
                type = "carte";
                tarifs = Utilis.driver.listTarifCarte();
                initPagination(tarifs.size());
            } else if (nv == rad_tarif) {
                type = "tarif";
                tarifs = Utilis.driver.listTarifForfait();
                initPagination(tarifs.size());
            } else if (nv == rad_tout) {
                type = "tout";
                tarifs = Utilis.driver.listTarif();
                initPagination(tarifs.size());
            }


        });
    }
 

    private void initPagination(int pages) {
        tableView.setItems(FXCollections.observableArrayList(tarifs));
        if (pages % INTERVAL_PAGE == 0) {
            pagination.setPageCount(pages / INTERVAL_PAGE);
        } else {
            pagination.setPageCount(pages / INTERVAL_PAGE + 1);
        }
        pagination.currentPageIndexProperty().addListener((obs, old, nv) -> {
            MIN_PAGE = nv.intValue() * INTERVAL_PAGE;
            MAX_PAGE = Math.min(MIN_PAGE + INTERVAL_PAGE, pages);
            tableView.setItems(FXCollections.observableArrayList(tarifs.subList(MIN_PAGE, MAX_PAGE)));
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
        if(event.getSource()==btn_tarifs) {
        	 Stage stageAddService = new Stage();
             stageAddService.setResizable(false);
             stageAddService.initModality(Modality.APPLICATION_MODAL);
             stageAddService.initOwner(btn_tarifs.getScene().getWindow());
             Parent rootAddService = FXMLLoader.load(getClass().getResource("AddTarifForm.fxml"));
             stageAddService.setScene(new Scene(rootAddService));
             Utilis.Max_DEFAULT++;
             stageAddService.showAndWait();
             if (Utilis.SERVICE_IS_UPDATE) {
                 Utilis.SERVICE_IS_UPDATE = false;
                 initTableView();
             }
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
