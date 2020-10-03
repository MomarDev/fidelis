/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.Trace;
import michelkaapp.objets.User;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class TracesController implements Initializable {

   
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
    private Button btn_users;
    @FXML
    private Button btn_payement;
    @FXML
    private Button btn_setting;
    @FXML
    private Button btn_traces;
    @FXML
    private AnchorPane pane_central;
    @FXML
    private Label titre_menu;
    @FXML
    private AnchorPane pane_border;
    @FXML
    private TableView<Trace> table_view;
    @FXML
    private Button btn_vider;
    @FXML
    private TableColumn<Trace, String> date_column;
    @FXML
    private TableColumn<Trace, String> action_column;
    @FXML
    private TableColumn<Trace, String> objet_column;
    @FXML
    private TableColumn<Trace, User> user_column;
    @FXML
    private TableColumn<Trace, String> valeur_column;
   
    private Stage stage;
    private Parent root;
    ObservableList<Trace> listTrace = null;
    @FXML
    private HBox admin_bar;
    @FXML
    private Button logout;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	

    	
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
        
    	
        btn_vider.setDisable(true);
        if (MichelKaApp.connexionIsOK) {
            List<Trace> listT = Utilis.driver.listTrace();
            
            if (listT != null) {
                listTrace = FXCollections.observableArrayList(listT);
                table_view.setItems(listTrace);
                date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
                action_column.setCellValueFactory(new PropertyValueFactory<>("action"));
                objet_column.setCellValueFactory(new PropertyValueFactory<>("objet"));
                user_column.setCellValueFactory(new PropertyValueFactory<>("user"));
                valeur_column.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            }
            if (Utilis.IsAdmin()) {
                admin_bar.setVisible(true);
            }
        }
        logout.setOnMouseClicked(event -> {
            Alert alertLogout = new Alert(Alert.AlertType.CONFIRMATION);
            alertLogout.setContentText("Etes vous sure de vouloir vous déconnecter?");
            Optional<ButtonType> result = alertLogout.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Logout: " + Utilis.LOGOUT.logout(((Stage) logout.getScene().getWindow())));
            }
        });
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


    @FXML
    private void buttonListener(ActionEvent event) throws IOException {
        if (event.getSource() == btn_users) {
            stage = (Stage) btn_users.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
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
        if (event.getSource() == btn_service) {
            root = FXMLLoader.load(getClass().getResource("StockService.fxml"));
            stage = (Stage) btn_service.getScene().getWindow();
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
        if (event.getSource() == btn_carte) {
            root = FXMLLoader.load(getClass().getResource("Cartes.fxml"));
            stage = (Stage) btn_carte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_fiche) {
            root = FXMLLoader.load(getClass().getResource("FicheTechnique.fxml"));
            stage = (Stage) btn_fiche.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (event.getSource() == btn_vider) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes sÃ»r de vouloir supprimer historiques des utilisateurs?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (Utilis.driver.viderTraces()) {
                    table_view.setItems(null);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setContentText("L'historique des utilisateurs a été supprimé avec succés");
                    info.show();
                } else {
                    Alert info = new Alert(Alert.AlertType.ERROR);
                    info.setContentText("Historique des utilisateurs n'a pas été supprimer!");
                    info.show();
                }
            }
        }
    }

}
