package michelkaapp.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
import michelkaapp.objets.SuiviCaisse;
import michelkaapp.objets.User;
import michelkaapp.utilis.Utilis;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SuiviCaisseControl implements Initializable {
    @FXML
    private Button btn_setting;

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
    private Stage stage;
    private Parent root;
    @FXML
    private TableView table_view;
    @FXML
    private Button btn_vider;
    @FXML
    private TableColumn<SuiviCaisse, String> date_column;
    @FXML
    private TableColumn<SuiviCaisse, String> action_column;
    @FXML
    private TableColumn<SuiviCaisse, String> objet_column;
    @FXML
    private TableColumn<SuiviCaisse, User> user_column;
    @FXML
    private TableColumn<SuiviCaisse, String> valeur_column;
    @FXML
    private TableColumn<SuiviCaisse, String> mode_column;
    @FXML
    private TableColumn<SuiviCaisse, Integer> restant;
    @FXML
    private Button btn_payement;
    ObservableList<SuiviCaisse> listTrace = null;
    @FXML
    private HBox admin_bar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (MichelKaApp.connexionIsOK) {
            List<SuiviCaisse> listT = Utilis.driver.listSuiviCaisse();

            if (listT != null) {
                listTrace = FXCollections.observableArrayList(listT);
                table_view.setItems(listTrace);
                date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
                action_column.setCellValueFactory(new PropertyValueFactory<>("action"));
                objet_column.setCellValueFactory(new PropertyValueFactory<>("objet"));
                user_column.setCellValueFactory(new PropertyValueFactory<>("user"));
                valeur_column.setCellValueFactory(new PropertyValueFactory<>("valeur"));
                mode_column.setCellValueFactory(new PropertyValueFactory<>("mode"));

            }
            if (Utilis.IsAdmin()) {
                admin_bar.setVisible(true);
            }
        }

        btn_payement.setOnMouseClicked(event -> {

            try {

                afficheFenetresSecondaire("Payement.fxml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

    }

    private void afficheFenetresSecondaire(String fxmlFile) throws IOException {
        if (Utilis.Max_DEFAULT < Utilis.DEFINED_FENETRE) {
            Stage smoleFenetre = new Stage();
            smoleFenetre.setResizable(false);
            Parent smoleRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
            smoleFenetre.setScene(new Scene(smoleRoot));
            smoleFenetre.onCloseRequestProperty().addListener(event -> {
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;

            });
            smoleFenetre.show();
        } else {
            Alert errorFenetre = new Alert(Alert.AlertType.ERROR, Utilis.errorFenetres, ButtonType.OK);
            errorFenetre.show();
        }
    }

    @FXML
    private void buttonListener() {
    }



}
