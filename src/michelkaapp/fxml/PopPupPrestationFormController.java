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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import michelkaapp.objets.Carte;
import michelkaapp.objets.CategoriePrestation;
import michelkaapp.objets.Client;
import michelkaapp.objets.DetailPrestationFournie;
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
public class PopPupPrestationFormController implements Initializable {

    @FXML
    private AnchorPane second_pane;
    @FXML
    private Label label_titre_popup_prestation;
    @FXML
    private CheckListView<Produit> marquesListes;
    private MenuButton categoriePrestation;
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
    private AnchorPane pane_title_id;

    private Stage stage;
    @FXML
    private AnchorPane form_prestation;
    @FXML
    private TextArea technique;

    private ObservableList<Produit> ob_produits = null;
    private ObservableList<Produit> produits = null;
    private String technique_texte;
    private String observation_texte;
    private String montage_texte;
    private int temp_de_pause = 0;
    private List<DetailPrestationFournie> detailPrestationFourni;
    private Client client;
    private Carte carte;
    private String date;

    TypePrestation typePrestation = null;
    @FXML
    private Label nom_client;
    @FXML
    private Label reference_carte_client;
    @FXML
    private ComboBox<CategoriePrestation> prestation;
    @FXML
    private Label identifiant_observation;
    @FXML
    private Label identifiant_montage;
    @FXML
    private Label identifiant_technique;

    private CategoriePrestation prestationTechnique;
    int position = 0;
    private PrestationTechnique prestaTchni;
    private List<ProduitUtilise> listeProduits;
    private List<Integer> listItemsSelected = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typePrestation = Utilis.actuelTypePrestation;
        if (Utilis.clientActuel != null) {
            try {
                client = (Client) Utilis.clientActuel.clone();
                nom_client.setText(client.getNom() + " " + client.getPrenom());
                reference_carte_client.setText(client.getCarte().getReference() + "");
//                Voir la derniere prestation du client technique
                prestaTchni = Utilis.driver.dernierePrestationClient(client.getId(), typePrestation.getId());
            } catch (CloneNotSupportedException ex) {
                Utilis.LOGGER.log(Arrays.toString(ex.getStackTrace()));
                Logger.getLogger(PopPupPrestationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        datePricker.setValue(Utilis.NOW_LOCAL_DATE());
        List<Produit> listProduits = Utilis.driver.listProduit("WHERE produit.supprime = 0");
        if (listProduits != null) {
            ob_produits = FXCollections.observableArrayList(listProduits);
            marquesListes.setItems(ob_produits);
            marquesListes.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends Produit> c) -> {
                System.out.println(marquesListes.getCheckModel().getCheckedItems());
                produits = marquesListes.getCheckModel().getCheckedItems();
            });
        }

        String title = label_titre_popup_prestation.getText().split(">")[0];
        label_titre_popup_prestation.setText(title + "> " + Utilis.FormPrestationTitle);
        if (Utilis.FormPrestationTitle.contains("Soins")) {
            technique.setDisable(false);
            technique.setVisible(false);
        }
        if (Utilis.FormPrestationTitle.contains("Forme")) {
        }
        if (Utilis.FormPrestationTitle.contains("Couleurs")) {
        }
        if (Utilis.FormPrestationTitle.contains("uel")) {
            identifiant_montage.setText("Technique");
            identifiant_technique.setText("Observations");
            technique.setVisible(true);
            observations.setVisible(false);
            identifiant_observation.setVisible(false);
        }
        List<CategoriePrestation> list = Utilis.driver.listPrestationTechnique(typePrestation.getId());
        ObservableList<CategoriePrestation> ob = FXCollections.observableArrayList(list);
        prestation.setItems(ob);
        if (Utilis.FormPrestationTitle.contains("uel")) {
            prestation.setValue(list.get(0));
            prestationTechnique = list.get(0);
        }
        if (prestaTchni != null) {
            List<Produit> lp = Utilis.driver.listProduitsTechnique(prestaTchni.getId());
            if (lp != null) {
                prestaTchni.setProduits(lp);
                int len = ob_produits.size();
                int lenp = lp.size();
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < lenp; j++) {
                        if (ob_produits.get(i).getId() == lp.get(j).getId()) {
                            marquesListes.getCheckModel().check(ob_produits.get(i));
                        }
                    }
                }
            }

            prestation.setValue(prestaTchni.getCategeorie());
            initLastPrestation();
        }
        prestation.valueProperty().addListener((obs, old, nv) -> {
            viderForm();
            prestationTechnique = nv;
            if (typePrestation.getNom().contains("texture")) {
                if (nv.getNom().contains("manente")) {
                    identifiant_technique.setText("Observations");
                    identifiant_technique.setVisible(true);
                    if (prestaTchni != null) {
                        technique.setText(prestaTchni.getObservation());
                    }
                    observations.setVisible(false);
                    identifiant_observation.setVisible(false);
                    technique.setVisible(true);
                    montage.setVisible(true);
                    identifiant_montage.setText("Montage");
                    identifiant_montage.setVisible(true);
                    if (prestaTchni != null) {
                        montage.setText(prestaTchni.getMontage());
                    }
                } else {
                    observations.setVisible(false);
                    identifiant_observation.setVisible(false);
                    identifiant_technique.setVisible(false);
                    technique.setVisible(false);
                    identifiant_montage.setText("Observations");
                    if (prestaTchni != null) {
                        identifiant_montage.setText(prestaTchni.getObservation());
                    }
                }
            }
            if (typePrestation.getNom().contains("leur")) {
                if (nv.getNom().contains("tion")) {
                    identifiant_montage.setText("Observations");
                    if (prestaTchni != null) {
                        montage.setText(prestaTchni.getObservation());
                    }
                    montage.setVisible(true);
                    identifiant_observation.setVisible(false);
                    observations.setVisible(false);
                    technique.setVisible(false);
                    identifiant_technique.setVisible(false);
                    System.out.println("couleur");
                } else {
                    identifiant_observation.setVisible(false);
                    observations.setVisible(false);
                    identifiant_montage.setText("Technique");
                    if (prestaTchni != null) {
                        montage.setText(prestaTchni.getTechnique());
                    }
                    identifiant_technique.setText("Observations");
                    identifiant_technique.setVisible(true);
                    technique.setVisible(true);
                    if (prestaTchni != null) {
                        technique.setText(prestaTchni.getObservation());
                    }
                }
            }

        });
    }

    @FXML
    private void buttonListener(ActionEvent event) {
        if (event.getSource() == btn_annuler) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Utilis.Quitter);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = (Stage) btn_annuler.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
        if (event.getSource() == btn_enregistrer) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dialogue de confirmation");
            alert.setHeaderText(Utilis.DialogConfirmationPrestation);
            initForm();
            if (temp_de_pause == 0) {
                Alert errorForm = new Alert(Alert.AlertType.WARNING, Utilis.ErrorForm, ButtonType.CANCEL);
                errorForm.show();
                return;
            }
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.get() == ButtonType.OK) {
                if (formIsOk()) {
                    creerForm();
                    if (Utilis.driver.insertPrestationTechnique(prestaTchni)) {
                        List<PrestationTechnique> list = Utilis.driver.listPrestationTechnique();
                        if (list != null) {
                            prestaTchni = list.get(0);
                            if (produits != null && !produits.isEmpty()) {
                                int len = produits.size();
                                listeProduits = new ArrayList<>();
                                for (int i = 0; i < len; i++) {
                                    listeProduits.add(new ProduitUtilise(0, prestaTchni.getId(), produits.get(i).getId()));
                                    System.out.print(produits.get(i).getNom());
                                }
                                if (!listeProduits.isEmpty()) {
                                    List<Integer> lst = Utilis.driver.insertProduitsUtilise(listeProduits);
                                    if (testeOkInsertProduitsUtilise(lst)) {
                                        System.out.println("Toute les produits de la prestation ont bien etait enregistrer");
                                    }
                                }
                            } else {
                                System.out.print("Aucun produit n'a etait charger:" + listeProduits);
                            }
                        }
                       
                        Utilis.PRESTATION_IS_UPDATE = true;
                        Alert info = new Alert(Alert.AlertType.INFORMATION, Utilis.EnregistrementSuccee, ButtonType.FINISH);
                        info.show();
                        stage = (Stage) btn_enregistrer.getScene().getWindow();
                        Utilis.Max_DEFAULT = Utilis.Max_DEFAULT - 1;
                        stage.close();
                    }
                } else {
                    Alert errorForm = new Alert(Alert.AlertType.ERROR, Utilis.ErrorForm, ButtonType.OK);
                    errorForm.show();
                }
            }
        }
    }

    private void initForm() {
        date = datePricker.getValue().toString();
        if (Utilis.isNumber(tempsDePause.getText())) {
            temp_de_pause = Integer.parseInt(tempsDePause.getText());
        } else {
            temp_de_pause = 0;
        }
        if (prestationTechnique != null) {
            if (prestationTechnique.getNom().contains("manente")) {
                observation_texte = technique.getText();
                montage_texte = montage.getText();
            } else if (prestationTechnique.getNom().contains("sage")) {
                observation_texte = montage.getText();
            } else if (prestationTechnique.getNom().contains("uel")) {
                observation_texte = technique.getText();
                technique_texte = montage.getText();
            } else if (prestationTechnique.getNom().contains("ration")) {
                observation_texte = montage.getText();
            } else if (prestationTechnique.getNom().contains("ches")) {
                technique_texte = montage.getText();
                observation_texte = technique.getText();
            }
        }
    }

    private boolean formIsOk() {
        return temp_de_pause > 0 && !date.isEmpty() && prestationTechnique != null;
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
        temp_de_pause = 0;
        ob_produits = marquesListes.getSelectionModel().getSelectedItems();
    }

    private void creerForm() {
        prestaTchni = new PrestationTechnique();
        prestaTchni.setClient(client.getId());
        prestaTchni.setDate(date);
        CategoriePrestation cp = new CategoriePrestation();
        cp.setId(prestationTechnique.getId());
        prestaTchni.setCategeorie(cp);
        prestaTchni.setMontage(montage_texte);
        prestaTchni.setObservation(observation_texte);
        prestaTchni.setTechnique(technique_texte);
        prestaTchni.setPause(temp_de_pause);
    }

    private void initLastPrestation() {
        if (typePrestation.getNom().contains("texture")) {
            if (prestaTchni.getCategeorie().getNom().contains("manente")) {
                identifiant_technique.setText("Observations");
                montage.setText(prestaTchni.getObservation());
                identifiant_technique.setVisible(true);
                observations.setVisible(false);
                identifiant_observation.setVisible(false);
                technique.setVisible(true);
                montage.setVisible(true);
                identifiant_montage.setText("Montage");
                montage.setText(prestaTchni.getMontage());
                identifiant_montage.setVisible(true);
            } else {
                observations.setVisible(false);
                identifiant_observation.setVisible(false);
                identifiant_technique.setVisible(false);
                technique.setVisible(false);
                identifiant_montage.setText("Observations");
                montage.setText(prestaTchni.getObservation());
            }
        }
        if (typePrestation.getNom().contains("leur")) {
            if (prestaTchni.getCategeorie().getNom().contains("tion")) {
                identifiant_montage.setText("Observations");
                montage.setText(prestaTchni.getObservation());
                montage.setVisible(true);
                identifiant_observation.setVisible(false);
                observations.setVisible(false);
                technique.setVisible(false);
                identifiant_technique.setVisible(false);
            } else {
                identifiant_observation.setVisible(false);
                observations.setVisible(false);
                identifiant_montage.setText("Technique");
                montage.setText(prestaTchni.getTechnique());
                identifiant_technique.setText("Observations");
                technique.setText(prestaTchni.getObservation());
                identifiant_technique.setVisible(true);
                technique.setVisible(true);
            }
        }
        datePricker.setValue(Utilis.NOW_LOCAL_DATE());
        tempsDePause.setText(prestaTchni.getPause() + "");
        prestationTechnique = prestaTchni.getCategeorie();
    }
}
