/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.fxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.CategoriePrestation;
import michelkaapp.objets.Client;
import michelkaapp.objets.PrestationTechnique;
import michelkaapp.objets.Produit;
import michelkaapp.objets.ProduitUtilise;
import michelkaapp.objets.TypePrestation;
import michelkaapp.utilis.Utilis;
import org.controlsfx.control.CheckListView;

/**
 * FXML Controller class
 *
 * @author leyu
 */
public class PopPupPrestationFormFromFicheController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private Label label_titre_popup_prestation;
    @FXML
    private CheckListView<Produit> marquesListes;
    @FXML
    private ComboBox<TypePrestation> typePrestation;
    @FXML
    private TextArea observations;
    @FXML
    private TextArea montage;
    @FXML
    private TextField tempsDePause;
    @FXML
    private DatePicker datePricker;
    @FXML
    private Button btn_enregistrer;
    @FXML
    private Button btn_annuler;
    @FXML
    private AnchorPane form_prestation;
    @FXML
    private AnchorPane pane_title_id;

    ObservableList<TypePrestation> listTypePrestation = null;
    ObservableList<CategoriePrestation> categoriePrestationTechnique = null;

    private Stage stage;
    private Parent root;

    @FXML
    private TextArea technique;

    private String date;
    private String montage_texte;
    private String observation_texte;
    private TypePrestation tPrestation = null;
    private PrestationTechnique prestation = null;
    private int temp_pause;
    private CategoriePrestation catPrestation = null;
    private String technique_texte = null;
    @FXML
    private ComboBox<CategoriePrestation> prestationTechnique;
    @FXML
    private Label nom_client;
    @FXML
    private Label reference_carte_client;
    @FXML
    private Label identifiant_observation;
    @FXML
    private Label identifiant_montage;
    @FXML
    private Label identifiant_technique;

    private Client client;
    private Carte carte;

    private ObservableList<Produit> produits = null;
    private ObservableList<Produit> selectedProduits = null;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        initializer();
        datePricker.setValue(Utilis.NOW_LOCAL_DATE());
    }

    @FXML
    private void buttonListener(ActionEvent event) {
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
        if (event.getSource() == btn_enregistrer) {
            if (!Utilis.isNumber(tempsDePause.getText())) {
                Alert errorForm = new Alert(Alert.AlertType.WARNING, Utilis.ErrorNumber + " temps de pause.", ButtonType.CANCEL);
                errorForm.show();
                return;
            }
            if (catPrestation == null || tPrestation == null) {
                Alert error = new Alert(Alert.AlertType.WARNING, "Veuillez choisir correctement le Type de Prestation et la Prestation s'il vous plait.");
                error.show();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etes vous sûr de vouloir enregistrer?");
            initForm();
            Optional<ButtonType> resutlt = alert.showAndWait();
            if (resutlt.get() == ButtonType.OK) {
                if (formIsOk()) {
                    boolean teste = true;
                    creerForm();
                    teste = Utilis.driver.insertPrestationTechnique(prestation);
                    if (teste) {
                        List<PrestationTechnique> listPT = Utilis.driver.listPrestationTechniqueByIdClient(client.getId());
                        if (listPT != null) {
                            prestation = listPT.get(0);
                            List<ProduitUtilise> listProduit = new ArrayList<>();
                            int len = selectedProduits.size();
                            for (int i = 0; i < len; i++) {
                                ProduitUtilise produitUtilise = new ProduitUtilise();
                                produitUtilise.setId_prestationTechnique(prestation.getId());
                                produitUtilise.setId_produit(selectedProduits.get(i).getId());
                                listProduit.add(produitUtilise);
                            }
                            if (!listProduit.isEmpty()) {
                                teste = testeOkInsertProduitsUtilise(Utilis.driver.insertProduitsUtilise(listProduit));
                            }
                        }
                    }
                    if (teste) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION, "Enregistrement réussi", ButtonType.FINISH);
                        info.show();
                        Utilis.PRESTATION_IS_UPDATE = true;

                        stage = (Stage) btn_enregistrer.getScene().getWindow();
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        stage.close();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorEnregistrementDB, ButtonType.CLOSE);
                        error.show();
                    }
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm, ButtonType.CANCEL);
                    error.show();
                }
            }
        }
    }

    private void initializer() {
        List<Produit> listProduits = Utilis.driver.listProduit("WHERE produit.supprime = 0");
        if (listProduits != null) {
            produits = FXCollections.observableArrayList(listProduits);
            marquesListes.setItems(produits);
        }
        if (Utilis.clientActuel != null) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                List<PrestationTechnique> lisp = Utilis.driver.listPrestationTechniqueClient(client.getId(), 1);
                if (lisp != null) {
                    prestation = lisp.get(0);
                    tPrestation = prestation.getTypePrestation();
                    catPrestation = prestation.getCategeorie();
                }
                System.out.println(prestation);
            } catch (CloneNotSupportedException ex) {
                System.out.print("clonage non effectuer");
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(PopPupPrestationFormFromFicheController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (client != null) {
            nom_client.setText(client.getNom() + " " + client.getPrenom());
            if (client.getCarte() == null || client.getCarte().getReference() == 0) {
                reference_carte_client.setText("pas de carte");
            } else {
                reference_carte_client.setText(client.getCarte().getReference() + "");
            }
        }
        List<TypePrestation> listTP = Utilis.driver.listTypePrestation();

        if (listTP != null) {
            listTP.remove(4);
            listTP.remove(3);
            listTypePrestation = FXCollections.observableArrayList(listTP);
            typePrestation.setItems(listTypePrestation);
            if (prestation != null) {
                typePrestation.setValue(tPrestation);
                List<CategoriePrestation> categoriesList = Utilis.driver.listPrestationTechnique(tPrestation.getId());
                categoriePrestationTechnique = FXCollections.observableArrayList(categoriesList);
                prestationTechnique.setItems(categoriePrestationTechnique);
                prestationTechnique.setValue(prestation.getCategeorie());
                tempsDePause.setText(prestation.getPause() + "");
                List<Produit> lp = Utilis.driver.listProduitsTechnique(prestation.getId());
                if (lp != null) {
                    prestation.setProduits(lp);
                    int len = produits.size();
                    int lenp = lp.size();
                    for (int i = 0; i < len; i++) {
                        for (int j = 0; j < lenp; j++) {
                            if (produits.get(i).getId() == lp.get(j).getId()) {
                                marquesListes.getCheckModel().check(produits.get(i));
                            }
                        }
                    }
                }
                initLastPrestation();
            }
        }

        typePrestation.valueProperty().addListener((ObservableValue<? extends TypePrestation> ob, TypePrestation old, TypePrestation nv) -> {
            tPrestation = nv;
            List<CategoriePrestation> categoriesList = Utilis.driver.listPrestationTechnique(nv.getId());
            categoriePrestationTechnique = FXCollections.observableArrayList(categoriesList);
            prestationTechnique.setItems(categoriePrestationTechnique);
            if (tPrestation.toString().contains("uel")) {
                prestationTechnique.setValue(categoriePrestationTechnique.get(0));
            }
        });
        prestationTechnique.valueProperty().addListener((obs, old, nv) -> {
            catPrestation = nv;
            if (nv.getNom().contains("uel")) {
                identifiant_montage.setText("Technique");
                identifiant_technique.setText("Service/Observations");
                identifiant_technique.setVisible(true);
                identifiant_observation.setVisible(false);
                observations.setVisible(false);
                technique.setVisible(true);
            }
            if (tPrestation.getNom().contains("texture")) {
                if (nv.getNom().contains("manente")) {
                    identifiant_technique.setText("Service/Observations");
                    observations.setVisible(false);
                    identifiant_observation.setVisible(false);
                    technique.setVisible(true);
                    montage.setVisible(true);
                    identifiant_montage.setVisible(true);
                    identifiant_montage.setText("Montage");
                    identifiant_technique.setVisible(true);
                } else {
                    observations.setVisible(false);
                    identifiant_observation.setVisible(false);
                    identifiant_technique.setVisible(false);
                    technique.setVisible(false);
                    identifiant_montage.setText("Service/Observations");
                }
            }
            if (tPrestation.getNom().contains("leur")) {
                if (nv.getNom().contains("tion")) {
                    identifiant_montage.setText("Service/Observations");
                    montage.setVisible(true);
                    identifiant_observation.setVisible(false);
                    observations.setVisible(false);
                    technique.setVisible(false);
                    identifiant_technique.setVisible(false);
                } else {
                    identifiant_observation.setVisible(false);
                    observations.setVisible(false);
                    identifiant_montage.setText("Technique");
                    identifiant_technique.setText("Service/Observations");
                    identifiant_technique.setVisible(true);
                    technique.setVisible(true);
                }
            }
        });
        tempsDePause.textProperty().addListener((obs, old, nv) -> {
            if (!nv.isEmpty() && !Utilis.isNumber(nv)) {
                tempsDePause.setStyle("-fx-background-color:red");
            } else {
                tempsDePause.setStyle(null);
            }
        });
    }

    private void initForm() {
        date = datePricker.getValue().toString();
        if (Utilis.isNumber(tempsDePause.getText())) {
            temp_pause = Integer.parseInt(tempsDePause.getText());
        } else {
            temp_pause = 0;
        }
        if (catPrestation != null) {
            if (catPrestation.getNom().contains("manente")) {
                observation_texte = technique.getText();
                montage_texte = montage.getText();
            } else if (catPrestation.getNom().contains("sage")) {
                observation_texte = montage.getText();
            } else if (catPrestation.getNom().contains("uel")) {
                observation_texte = technique.getText();
                technique_texte = montage.getText();
            } else if (catPrestation.getNom().contains("ration")) {
                observation_texte = montage.getText();
            } else if (catPrestation.getNom().contains("ches")) {
                technique_texte = montage.getText();
                observation_texte = technique.getText();
            }
        }
        selectedProduits = marquesListes.getCheckModel().getCheckedItems();
    }

    private boolean formIsOk() {
        return !date.isEmpty() && Utilis.isNumber(tempsDePause.getText());
    }

    private boolean testeOkInsertProduitsUtilise(List<Integer> listeTeste) {
        boolean teste = true;
        int taille = listeTeste.size();
        for (int i = 0; i < taille; i++) {
            if (listeTeste.get(i) <= 0) {
                teste = false;
                break;
            }
        }
        return teste;
    }

    private void viderForm() {
        technique_texte = null;
        date = null;
        observation_texte = null;
        montage_texte = null;
        temp_pause = 0;
        produits = marquesListes.getSelectionModel().getSelectedItems();
    }

    private void creerForm() {
        prestation = new PrestationTechnique();
        prestation.setClient(client.getId());
        prestation.setDate(date);
        CategoriePrestation cp = new CategoriePrestation();
        cp.setId(catPrestation.getId());
        prestation.setCategeorie(cp);
        prestation.setMontage(montage_texte);
        prestation.setObservation(observation_texte);
        prestation.setTechnique(technique_texte);
        prestation.setPause(temp_pause);
    }

    private void initLastPrestation() {
        if (tPrestation.getNom().contains("texture")) {
            if (prestation.getCategeorie().getNom().contains("manente")) {
                identifiant_technique.setText("Observations");
                montage.setText(prestation.getObservation());
                identifiant_technique.setVisible(true);
                observations.setVisible(false);
                identifiant_observation.setVisible(false);
                technique.setVisible(true);
                montage.setVisible(true);
                identifiant_montage.setText("Montage");
                montage.setText(prestation.getMontage());
                identifiant_montage.setVisible(true);
            } else {
                observations.setVisible(false);
                identifiant_observation.setVisible(false);
                identifiant_technique.setVisible(false);
                technique.setVisible(false);
                identifiant_montage.setText("Observations");
                montage.setText(prestation.getObservation());
            }
        }
        if (tPrestation.getNom().contains("leur")) {
            if (prestation.getCategeorie().getNom().contains("tion")) {
                identifiant_montage.setText("Observations");
                montage.setText(prestation.getObservation());
                montage.setVisible(true);
                identifiant_observation.setVisible(false);
                observations.setVisible(false);
                technique.setVisible(false);
                identifiant_technique.setVisible(false);
            } else {
                identifiant_observation.setVisible(false);
                observations.setVisible(false);
                identifiant_montage.setText("Technique");
                montage.setText(prestation.getTechnique());
                identifiant_technique.setText("Observations");
                technique.setText(prestation.getObservation());
                identifiant_technique.setVisible(true);
                technique.setVisible(true);
            }
        }
    }
}
