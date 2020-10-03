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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.MichelKaApp;
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
public class UpdateServiceFormController implements Initializable {

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
    private ComboBox<TypePrestation> cbox_type_prestation;
    @FXML
    private ComboBox<CategoriePrestation> cbox_categories_prestation;
    @FXML
    private ComboBox<SousCategoriePrestation> cbox_sous_categories_prestation;
    @FXML
    private TextField fdl_prix;
    @FXML
    private TextField fld_prestation;

    private ObservableList<TypePrestation> listTypePrestation = null;
    private ObservableList<CategoriePrestation> listCategoriesPrestation = null;
    private ObservableList<SousCategoriePrestation> listSousCategories = null;
    private TypePrestation type = null;
    private CategoriePrestation cat = null;
    private SousCategoriePrestation sousCat = null;
    private String prestation = "";
    private int prix = 0;
    private Service service;

    private int prixI = 0;
    private int prixS = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Utilis.ServiceToUpdate != null) {
            try {
                service = (Service) Utilis.ServiceToUpdate.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(UpdateServiceFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        initializerComboboxes();
    }

    private void initializerComboboxes() {
        if (MichelKaApp.connexionIsOK) {
            List<TypePrestation> listP = Utilis.driver.listTypePrestation();
            if (listP != null) {
                listTypePrestation = FXCollections.observableArrayList(listP);
                cbox_type_prestation.setItems(listTypePrestation);
            }
            if (Utilis.ServiceToUpdate != null) {
                try {
                    service = (Service) Utilis.ServiceToUpdate.clone();
                    prepareUpdate();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(UpdateServiceFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            if (!fdl_prix.getText().isEmpty() && !fld_prestation.getText().isEmpty()) {
                if (Utilis.isNumber(fdl_prix.getText())) {
                    prix = Integer.parseInt(fdl_prix.getText());
                } else {
                    String tab[] = fdl_prix.getText().split("-");
                    if (tab.length == 2 && Utilis.isNumber(tab[0]) && Utilis.isNumber(tab[1]));
                    {
                        prixI = Integer.parseInt(tab[0]);
                        prixS = Integer.parseInt(tab[1]);
                        service.getPrestation().setBorneInferieur(prixI);
                        service.getPrestation().setBorneSuperieur(prixS);
                    }
                }
                if (prix == 0 && prixI == 0 && prixS == 0) {
                    Alert errr = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm);
                    errr.show();
                    return;
                }
                prestation = fld_prestation.getText();
            }
            sousCat = cbox_sous_categories_prestation.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.QuestionValideForm);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (formIsOK()) {
                    Prestation ps = new Prestation(service.getPrestation().getId(), prestation, prix, sousCat);
                    ps.setBorneSuperieur(prixS);
                    ps.setBorneInferieur(prixI);
                    if (prixI == 0 && prixS == 0) {
                        ps.setFixe(true);
                    }else
                    {
                        ps.setFixe(false);
                    }
                    if (Utilis.driver.updatePrestation(ps)) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION, "La prestation a été mise à  jour avec succée.");
                        info.show();
                        cat = null;
                        sousCat = null;
                        prestation = "";
                        prix = 0;
                        Stage stage = (Stage) btn_enregistrer.getScene().getWindow();
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        Utilis.SERVICE_IS_UPDATE = true;
                        stage.close();
                    } else {
                        Alert erroSql = new Alert(Alert.AlertType.ERROR, "Une erreur c'est produite lors de l'enregistrement de la prestation", ButtonType.OK);
                        erroSql.show();
                    }
                } else {
                    Alert erreForm = new Alert(Alert.AlertType.ERROR, "Veuillez revérifier les informations saisis s'il vous plait.", ButtonType.OK);
                    erreForm.show();
                }
            } else {
                alert.close();
            }
        }
    }

    @FXML
    private void comboBoxActions(ActionEvent event) {
        if (event.getSource() == cbox_type_prestation) {
            type = cbox_type_prestation.getSelectionModel().getSelectedItem();
            if (MichelKaApp.connexionIsOK) {
                List<CategoriePrestation> lc = Utilis.driver.ListCategoriePrestation(type.getId());
                if (lc != null) {
                    listCategoriesPrestation = FXCollections.observableArrayList(lc);
                    cbox_categories_prestation.setItems(listCategoriesPrestation);
                }
                cbox_sous_categories_prestation.setItems(null);
            }
        } else if (event.getSource() == cbox_categories_prestation) {
            cat = cbox_categories_prestation.getSelectionModel().getSelectedItem();
            if (!listCategoriesPrestation.isEmpty()) {
                if (cbox_categories_prestation.getSelectionModel().getSelectedItem() != null) {
                    List<SousCategoriePrestation> sc = Utilis.driver.listSousCategoriesPrestation(cbox_categories_prestation.getSelectionModel().getSelectedItem().getId());
                    if (sc != null) {
                        listSousCategories = FXCollections.observableList(sc);
                        cbox_sous_categories_prestation.setItems(listSousCategories);
                    }
                }
            } else if (event.getSource() == cbox_sous_categories_prestation) {
                sousCat = cbox_sous_categories_prestation.getSelectionModel().getSelectedItem();
            }
        }
    }

    private boolean formIsOK() {
        return (type != null && cat != null && sousCat != null) && (!prestation.isEmpty());
    }

    private void prepareUpdate() {
        type = service.getTypePrestation();
        cat = service.getCategoriePrestation();
        sousCat = service.getSousCategoriePrestation();
        cbox_type_prestation.setValue(type);
        cbox_categories_prestation.setValue(cat);
        cbox_sous_categories_prestation.setValue(sousCat);
        fdl_prix.setText(service.getPrix());
        fld_prestation.setText(service.getPrestation().getNom());
    }
}
