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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Client;
import michelkaapp.objets.Parrainage;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FideliteParrainageController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane border_pane;
    @FXML
    private Button btn_add_parrainage;
    @FXML
    private AnchorPane info_border;

    private Stage stage;
//    private Parent root;
    private Client client = null;
    @FXML
    private TableView<Parrainage> tableauParrainage;
    @FXML
    private TableColumn<Parrainage, String> date_col;
    @FXML
    private TableColumn<Parrainage, String> parraine_col;
    @FXML
    private TableColumn<Parrainage, String> telephone_col;
    @FXML
    private Label total_point_parrainge;
    @FXML
    private Label nom_prenom_client;
    @FXML
    private Label carte_reference;
    @FXML
    private Button btn_utiliser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            client = (Client) Utilis.clientActuel.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(FideliteParrainageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (client != null) {
            nom_prenom_client.setText(client.getNom() + " " + client.getPrenom());
            carte_reference.setText(client.getCarte().getReference() + "");
            List<Parrainage> list = Utilis.driver.listParrainage(client.getCarte().getReference());
            if (list != null) {
                ObservableList<Parrainage> obs = FXCollections.observableArrayList(list);
                tableauParrainage.setItems(obs);
                date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
                parraine_col.setCellValueFactory(new PropertyValueFactory<>("parraine"));
                telephone_col.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            }
            client.setCarte(Utilis.driver.trouverCarte(client.getCarte().getReference()));
            System.out.println(client.getCarte().getNtpsParrainage());
            total_point_parrainge.setText("Total points parrainage: " + client.getCarte().getNtpsParrainage());
        }
        btn_utiliser.setOnMouseClicked(event -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            if ((Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur()) <= client.getCarte().getNtpsParrainage()) && Utilis.driver.updateCarte(client.getCarte().getReference(), "nombre_fidelite_parrainage", (client.getCarte().getNtpsParrainage() - (Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur()))))) {
                Alert sure = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sure de vouloir réinitialiser les points parrainage du client?");
                Optional<ButtonType> result = sure.showAndWait();
                if (result.get() == ButtonType.OK) {
                    info.setContentText("Le client vient d'utiliser " + Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur()));
                    total_point_parrainge.setText("Total points parrainage: " + (client.getCarte().getNtpsParrainage() - Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur())));
                    client.getCarte().setNtpsParrainage((client.getCarte().getNtpsParrainage() - Integer.parseInt(Utilis.trouverParametre("MAX_FIDELITE_PARRAINAGE").getValeur())));
                }
            } else {
                info.setContentText("Le client n'a pas encore atteint le nombre de point fidélité prédéfinie.");
            }
            info.show();
        });
    }

    @FXML
    private void handlerButton(ActionEvent event) throws IOException {
        if (event.getSource() == btn_add_parrainage) {
            if (client == null) {
                Utilis.LOGGER.log("Fidelite Parrainage: Aucun propriétaire de carte selectioné.");
                Alert error = new Alert(Alert.AlertType.ERROR, "Aucun propriétaire de carte selectioné.", ButtonType.OK);
                error.show();
            } else {
                stage = (Stage) btn_add_parrainage.getScene().getWindow();
                Stage stageParrainageForm = new Stage();
                stageParrainageForm.setResizable(false);
                Parent rootParrainageForm = FXMLLoader.load(getClass().getResource("FideliteParrainageForm.fxml"));
                stageParrainageForm.setScene(new Scene(rootParrainageForm));
                stage.close();
                stageParrainageForm.show();
            }
        }
        Utilis.Carte_IS_UPDATE = true;
    }

}
