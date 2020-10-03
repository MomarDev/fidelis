/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import michelkaapp.objets.Client;
import michelkaapp.objets.Visite;
import michelkaapp.utilis.Utilis;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class FideliteVisiteFormController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane form_prestation;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Label form_titre;
    @FXML
    private AnchorPane info_border;

    private Stage stage;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField nombrePoints;
    @FXML
    private Label nom_proprietaire;
    @FXML
    private Label reference_carte;

    private Client client = null;
    private Visite visite = null;

    private int points = 0;
    private String date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            client = (Client) Utilis.clientActuel.clone();
        } catch (CloneNotSupportedException ex) {
            Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
            Logger.getLogger(FideliteVisiteFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (client != null) {
            nom_proprietaire.setText(client.getNom() + " " + client.getPrenom());
            reference_carte.setText(client.getCarte().toString());
        }
        datePicker.setValue(Utilis.NOW_LOCAL_DATE());
    }

    private void initForm() {
        date = datePicker.getValue().toString();
        try {
            points = Integer.parseInt(nombrePoints.getText());
        } catch (NumberFormatException e) {
            Utilis.LOGGER.log(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            points = 0;
            Alert alert = new Alert(Alert.AlertType.ERROR, Utilis.ErrorNumber, ButtonType.OK);
            alert.show();
        }
    }

    private boolean formIsOk() {
        return !date.isEmpty() && points > 0;
    }

    private void creerForm() {
        visite = new Visite();
        visite.setDate(date);
        visite.setNombrePoints(Utilis.pointAvaloir(points, "nombre_fidelite_prestation"));
        visite.setReferenceCarte(client.getCarte().getReference());
    }

    @FXML
    private void handlerButton(ActionEvent event) {
        if (event.getSource() == btn_enregistrer) {
            initForm();
            stage = (Stage) btn_enregistrer.getScene().getWindow();
            if (formIsOk()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Etes vous sûr de vouloir enregistrer ces informations?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    creerForm();
                    if (Utilis.driver.insertVisite(visite) && Utilis.driver.point(client.getCarte().getReference(), Utilis.pointAvaloir(points, "nombre_fidelite_prestation"), "nombre_fidelite_prestation")) {
                        List<Visite> visites = Utilis.driver.listVisites(client.getCarte().getReference());
                        if (visites != null && !visites.isEmpty() && visites.size() >= Integer.parseInt(Utilis.trouverParametre("FIDELITE_VISITE").getValeur())) {
                            Stage impStage = new Stage();
                            impStage.setResizable(false);
                            FXMLLoader impRoot = null;
                            VBox pane = null;
                            System.out.println("la taille visite --->"+visites.size()+" fidelite-->>"+Utilis.trouverParametre("FIDELITE_VISITE").getValeur());
                            try {
                                impRoot = new FXMLLoader(getClass().getResource("ImpressionFidelite.fxml"));
                                pane = impRoot.load();
                                ImpressionFideliteController controller = (ImpressionFideliteController) impRoot.getController();
                                controller.newInstence(client, "nombre_fidelite_prestation", false);
                            } catch (IOException ex) {
                                Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            impStage.setScene(new Scene(pane));
                            impStage.show();
                        }
                        Alert info = new Alert(Alert.AlertType.INFORMATION, Utilis.EnregistrementSuccee, ButtonType.OK);
                        info.show();
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        stage.close();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                        error.show();
                    }
                } else {
                    alert.close();
                }
            }

        }
        if (event.getSource() == btn_annuler) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_annuler.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        }
        Utilis.Carte_IS_UPDATE = true;
    }
}
