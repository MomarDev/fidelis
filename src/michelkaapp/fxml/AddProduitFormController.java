/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
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
public class AddProduitFormController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane form_prestation;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_quitter;
    @FXML
    private Label titre_border_produit;
    @FXML
    private ComboBox<Marque> cbox_marque;
    @FXML
    private ComboBox<SousFamille> cbox_sousFamille;
    @FXML
    private ComboBox<GammeProduit> cbox_gamme;

    private ObservableList<Marque> listMarque = null;
    private ObservableList<SousFamille> listSousFamille = null;
    private ObservableList<GammeProduit> listGamme = null;
    @FXML
    private RadioButton rdBtn_service;
    @FXML
    private ToggleGroup tgl_utilisation;
    @FXML
    private RadioButton rdBtn_vente;
    @FXML
    private TextField fld_code;
    @FXML
    private TextField fld_designation;
    @FXML
    private TextField fld_stockable;
    @FXML
    private TextField fld_prix;
    @FXML
    private TextField fld_info;

    private String code = "0";
    private String designation = "";
    private Marque marque = null;
    private boolean utilisation = true;
    private SousFamille sousF = null;
    private GammeProduit gamme = null;
    private int stockable = 0;
    private int prix = 0;
    private String info = "";
    @FXML
    private TextField marqueSelected;
    @FXML
    private TextField sousFamilleSelected;
    @FXML
    private TextField newGamme;
    @FXML
    private TextField seuil_alert_fd;
    private int seuil = 0;
    @FXML
    private Label vente_color;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (rdBtn_service.isSelected()) {
            fld_prix.setDisable(true);
        }
   
        tgl_utilisation.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (nv == rdBtn_service) {
                fld_prix.setDisable(true);
                fld_prix.setText("");
                vente_color.setVisible(false);
            } else {
                fld_prix.setDisable(false);
                  vente_color.setVisible(true);
            }
        });
        fld_code.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                if (!Utilis.isNumber(nv)) {
                    code = "0";
                    fld_code.setStyle("-fx-background-color: #fcb900");
                } else {
                    fld_code.setStyle(null);
                    code = nv;
                }
            }
        });
        fld_designation.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                designation = nv;
            }
        });
        fld_stockable.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                if (!Utilis.isNumber(nv)) {
                    stockable = 0;
                    fld_stockable.setStyle("-fx-background-color: #fcb900");
                } else {
                    stockable = Integer.parseInt(nv);
                    fld_stockable.setStyle(null);
                }
            }
        });

        fld_prix.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                if (!Utilis.isNumber(nv)) {
                    prix = 0;
                    fld_prix.setStyle("-fx-background-color: #fcb900");
                } else {
                    prix = Integer.parseInt(nv);
                    fld_prix.setStyle(null);
                }
            }
        });

        fld_info.textProperty().addListener((obs, old, nv) -> {
            if (nv != null) {
                info = nv;
            } else {
                info = "";
            }
        });
        seuil_alert_fd.textProperty().addListener((obs, old, nv) -> {
            if (nv != null && !nv.isEmpty() && Utilis.isNumber(nv)) {
                seuil = Integer.parseInt(nv);
            } else {
                seuil_alert_fd.setText("");
            }
        });
        initializeComboboxes();
        autreAction();
    }

    private void initializeComboboxes() {
        if (MichelKaApp.connexionIsOK) {
            List<Marque> listM = Utilis.driver.listMarque();
            if (listM != null) {
                listMarque = FXCollections.observableArrayList(listM);
                cbox_marque.setItems(listMarque);
            }
            List<GammeProduit> listG = Utilis.driver.listGamme();
            if (listG != null) {
                listGamme = FXCollections.observableArrayList(listG);
                cbox_gamme.setItems(listGamme);
            }
            List<SousFamille> listF = Utilis.driver.listSousFamille();
            if (listF != null) {
                listSousFamille = FXCollections.observableArrayList(listF);
                cbox_sousFamille.setItems(listSousFamille);
            }
        }
    }

    @FXML
    private void handlerButtonAction(ActionEvent event) {
        if (event.getSource() == btn_quitter) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_quitter.getScene().getWindow();
                Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                stage.close();
            } else {
                alert.close();
            }
        } else if (event.getSource() == btn_enregistrer) {
            creerFieldProposition();
            if (formIsOk()) {
                if (Utilis.driver.existProduit(code, designation, false)) {
                    Alert existAlert = new Alert(Alert.AlertType.ERROR, "Le code barres ou la d�signation est d�j� utilis� sur un autre produit ", ButtonType.OK);
                    existAlert.show();
                    return;
                }
                Produit produit = new Produit();
                produit.setCode(code);
                produit.setNom(designation);
                produit.setInfo(info);
                produit.setSousfamille(sousF);
                produit.setGammeProduit(gamme);
                produit.setMarque(marque);
                produit.setUtilisation(utilisation);
                produit.setStock(stockable);
                produit.setPrix(prix);
                if (produit.isUtilisation()) {
                    produit.setSeuilService(seuil);
                } else {
                    produit.setSeuilVente(seuil);
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(Utilis.QuestionValideForm);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (Utilis.driver.insertProduit(produit)) {
                        prix = 0;
                        Alert Ainfo = new Alert(Alert.AlertType.INFORMATION, "Le produit a �t� bien enregistrer.", ButtonType.OK);
                        Ainfo.show();
                        Stage stage = (Stage) btn_enregistrer.getScene().getWindow();
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        Utilis.PRODUIT_IS_UPDATE = true;
                        stage.close();
                    }
                } else {
                    alert.close();
                }
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, "Veuillez rev�rifier les information que vous avez saisie.", ButtonType.OK);
                error.show();
            }
        }
    }

    @FXML
    private void formHandlerAction(ActionEvent event) {
        if (event.getSource() == rdBtn_service) {
            if (rdBtn_service.isSelected()) {
                utilisation = true;
            }
        } else if (event.getSource() == rdBtn_vente) {
            if (rdBtn_vente.isSelected()) {
                utilisation = false;
            }
        }

    }

    private boolean formIsOk() {
        return marque != null && !designation.isEmpty() && (rdBtn_vente.isSelected() ? (prix != 0) : true);
    }

    private void autreAction() {
        cbox_marque.getItems().addAll(new Marque(0, "Cr�er", 0));

        cbox_marque.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getMarque().contains("Cr�er")) {
                    marqueSelected.setDisable(false);
                    marque = null;
                    marqueSelected.setText("");
                } else {
                    marqueSelected.setDisable(true);
                    marque = nv;
                }
            }
        });
        cbox_sousFamille.getItems().addAll(new SousFamille(0, "Cr�er"));

        cbox_sousFamille.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getSousFamille().contains("Cr�er")) {
                    sousFamilleSelected.setDisable(false);
                    sousFamilleSelected.setText("");
                    sousF = null;
                } else {
                    sousFamilleSelected.setDisable(true);
                    sousF = nv;
                }
            }
        });
        cbox_gamme.getItems().addAll(new GammeProduit(0, "Cr�er"));

        cbox_gamme.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getGamme().contains("Cr�er")) {
                    newGamme.setDisable(false);
                    newGamme.setText("");
                    gamme = null;
                } else {
                    newGamme.setDisable(true);
                    gamme = nv;
                }
            }
        });
    }

    public void creerFieldProposition() {
        if (!marqueSelected.isDisabled() && !marqueSelected.getText().isEmpty()) {
            Utilis.driver.insertMarque(new Marque(0, marqueSelected.getText(), 0));
            marque = Utilis.driver.lastMarque();
        }
        if (!sousFamilleSelected.isDisabled() && !sousFamilleSelected.getText().isEmpty()) {
            Utilis.driver.insertSousFamille(new SousFamille(0, sousFamilleSelected.getText()));
            sousF = Utilis.driver.lastSousFamille();
        }
        if (!newGamme.isDisabled() && !newGamme.getText().isEmpty()) {
            Utilis.driver.insertGammeProduit(new GammeProduit(0, newGamme.getText()));
            gamme = Utilis.driver.lastGammeProduit();
        }
    }
}
