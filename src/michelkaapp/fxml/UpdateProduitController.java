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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UpdateProduitController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private AnchorPane form_prestation;
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
    @FXML
    private RadioButton rdBtn_service;
    @FXML
    private ToggleGroup tgl_utilisation;
    @FXML
    private RadioButton rdBtn_vente;
    @FXML
    private ComboBox<Marque> cbox_marque;
    @FXML
    private TextField marqueSelected;
    @FXML
    private ComboBox<SousFamille> cbox_sousFamille;
    @FXML
    private TextField sousFamilleSelected;
    @FXML
    private ComboBox<GammeProduit> cbox_gamme;
    @FXML
    private TextField newGamme;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_quitter;
    @FXML
    private Label titre_border_produit;

    private ObservableList<Marque> listMarque = null;
    private ObservableList<SousFamille> listSousFamille = null;
    private ObservableList<GammeProduit> listGamme = null;
    private String code = "0";
    private String designation = "";
    private Marque marque = null;
    private boolean utilisation = true;
    private SousFamille sousF = null;
    private GammeProduit gamme = null;
    private int stockable = 0;
    private int prix = 0;
    private String info = "";

    private Produit produit = null;
    @FXML
    private TextField seuil_alert_fd;
    private int seuil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.ProduitToUpdate != null) {
            try {
                produit = (Produit) Utilis.ProduitToUpdate.clone();
                Utilis.ProduitToUpdate = null;
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(UpdateProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            if (!nv.isEmpty() && Utilis.isNumber(nv)) {
                seuil = Integer.parseInt(nv);
            } else {
                seuil = 0;
            }
        });
        initializeComboboxes();
        autreAction();
        prepareUpdate();
    }

    private void initializeComboboxes() {
        if (MichelKaApp.connexionIsOK) {
            List<Marque> listM = Utilis.driver.listMarque();
            if (listM != null) {
                listMarque = FXCollections.observableArrayList(listM);
                cbox_marque.setItems(listMarque);
                cbox_marque.setValue(produit.getMarque());
            }
            List<GammeProduit> listG = Utilis.driver.listGamme();
            if (listG != null) {
                listGamme = FXCollections.observableArrayList(listG);
                cbox_gamme.setItems(listGamme);
                cbox_gamme.setValue(produit.getGammeProduit());
            }
            List<SousFamille> listF = Utilis.driver.listSousFamille();
            if (listF != null) {
                listSousFamille = FXCollections.observableArrayList(listF);
                cbox_sousFamille.setItems(listSousFamille);
                cbox_sousFamille.setValue(produit.getSousfamille());
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
            if (formIsOk() && code.equalsIgnoreCase(produit.getCode()) && !Utilis.driver.existProduit(code, designation, true)) {
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
                    if (Utilis.driver.updateProduit(produit)) {
                        Alert Ainfo = new Alert(Alert.AlertType.INFORMATION, "Le produit a été bien enregistré.", ButtonType.OK);
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
                Alert error = new Alert(Alert.AlertType.ERROR, "Veuillez revérifier les information que vous avez saisie.", ButtonType.OK);
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
        return marque != null && !designation.isEmpty()
                && !code.isEmpty();
    }

    private void autreAction() {
        cbox_marque.getItems().addAll(new Marque(0, "Créer", 0));

        cbox_marque.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getMarque().contains("Créer")) {
                    marqueSelected.setDisable(false);
                    marque = null;
                    marqueSelected.setText("");
                } else {
                    marqueSelected.setDisable(true);
                    marque = nv;
                }
            }
        });
        cbox_sousFamille.getItems().addAll(new SousFamille(0, "Créer"));

        cbox_sousFamille.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getSousFamille().contains("Créer")) {
                    sousFamilleSelected.setDisable(false);
                    sousFamilleSelected.setText("");
                    sousF = null;
                } else {
                    sousFamilleSelected.setDisable(true);
                    sousF = nv;
                }
            }
        });
        cbox_gamme.getItems().addAll(new GammeProduit(0, "Créer"));

        cbox_gamme.valueProperty().addListener((observableList, old, nv) -> {
            if (nv != null) {
                if (nv.getGamme().contains("Créer")) {
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

    private void prepareUpdate() {
        if (produit != null) {
            sousF = produit.getSousfamille();
            gamme = produit.getGammeProduit();
            marque = produit.getMarque();
            if (produit.isUtilisation()) {
                seuil = produit.getSeuilService();
                seuil_alert_fd.setText(produit.getSeuilService() + "");
            } else {
                seuil = produit.getSeuilVente();
                seuil_alert_fd.setText(produit.getSeuilVente() + "");
            }
            rdBtn_service.setSelected(produit.isUtilisation());
            rdBtn_vente.setSelected(!produit.isUtilisation());
            cbox_marque.setValue(produit.getMarque());
            cbox_sousFamille.setValue(produit.getSousfamille());
            cbox_gamme.setValue(produit.getGammeProduit());
            fld_code.setText(produit.getCode() + "");
            fld_designation.setText(produit.getNom());
            fld_info.setText(produit.getInfo());
            fld_prix.setText(produit.getPrix() + "");
            fld_stockable.setText(produit.getStock() + "");
            code = produit.getCode();
            designation = produit.getNom();
            stockable = produit.getStock();
            prix = produit.getPrix();
            utilisation = produit.isUtilisation();
        }
    }
}
